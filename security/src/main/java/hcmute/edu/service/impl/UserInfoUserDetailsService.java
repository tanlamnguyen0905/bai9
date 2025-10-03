package hcmute.edu.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hcmute.edu.dao.UserInfoUserDetails;
import hcmute.edu.entity.Users;
import hcmute.edu.repository.UserRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public UserInfoUserDetailsService(UserRepository repo){ this.userRepository = repo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.findByUsername(username)
           .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserInfoUserDetails(u);
    }
}
