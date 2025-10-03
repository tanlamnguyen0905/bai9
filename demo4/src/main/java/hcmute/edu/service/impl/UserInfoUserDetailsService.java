package hcmute.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hcmute.edu.dao.UserInfoUserDetails;
import hcmute.edu.entity.Users;
import hcmute.edu.repository.UserRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;
    

    public UserInfoUserDetailsService(UserRepository repo){ this.userRepository = repo; }
    
    public UserInfoUserDetailsService() {};

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.findByUsername(username)
           .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserInfoUserDetails(u);
    }
}
