package hcmute.edu.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hcmute.edu.entity.Users;
import hcmute.edu.repository.UserRepository;

@Service
public record UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	public String addUser(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User added!";
		
	}

}
