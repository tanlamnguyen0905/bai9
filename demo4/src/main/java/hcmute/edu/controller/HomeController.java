package hcmute.edu.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String index(Authentication auth, Model model) {
    model.addAttribute("principal", auth);
    
    return "index";
  }

  @GetMapping("/403")
  public String accessDenied() { return "403"; }
}
