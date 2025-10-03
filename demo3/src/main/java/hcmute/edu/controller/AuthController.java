package hcmute.edu.controller;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import hcmute.edu.entity.Role;
import hcmute.edu.entity.Users;
import hcmute.edu.repository.RoleRepository;
import hcmute.edu.repository.UserRepository;

import java.util.Set;

@Controller
@Validated
public class AuthController {
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PasswordEncoder encoder;

  public AuthController(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
    this.userRepo = userRepo; this.roleRepo = roleRepo; this.encoder = encoder;
  }

  @GetMapping("/login")
  public String loginPage() { return "login"; }

  @GetMapping("/register")
  public String registerForm() { return "register"; }

  @PostMapping("/register")
  public String doRegister(
      @RequestParam @NotBlank String username,
      @RequestParam @Email String email,
      @RequestParam @NotBlank String password,
      Model model) {

    if (userRepo.existsByUsername(username) || userRepo.existsByEmail(email)) {
      model.addAttribute("error", "Username hoặc email đã tồn tại");
      return "register";
    }
    Users u = new Users();
    u.setUsername(username);
    u.setEmail(email);
    u.setPassword(encoder.encode(password));
    Role userRole = roleRepo.findByName("ROLE_USER").orElseThrow();
    u.setRoles(Set.of(userRole));
    userRepo.save(u);

    return "redirect:/login?registered";
  }
}
