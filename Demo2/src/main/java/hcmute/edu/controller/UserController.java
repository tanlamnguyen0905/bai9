package hcmute.edu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hcmute.edu.entity.Users;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final hcmute.edu.service.UserService userService;
	@PostMapping("/new")
	public String addUser(@RequestBody Users user) {
		return userService.addUser(user);
	}

}
