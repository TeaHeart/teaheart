package org.example.controller;

import org.example.model.User;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return authService.login(user);
    }

    @PostMapping("/logout")
    public boolean logout() {
        return authService.logout();
    }

    @PostMapping("/change-password")
    public boolean changePassword(@RequestBody User user) {
        return authService.changePassword(user);
    }

    @GetMapping("/me")
    public User me() {
        return authService.me();
    }
}
