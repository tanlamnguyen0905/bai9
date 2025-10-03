package hcmute.edu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello public!";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello, authenticated user!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello, admin!";
    }
}
