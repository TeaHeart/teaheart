package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody User user) {
        return userService.updateById(user);
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/username/{username}")
    public List<User> getLikeUsername(@PathVariable String username) {
        return userService.getLikeUsername(username);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/logout")
    public boolean logout() {
        return userService.logout();
    }

    @PostMapping("/change-password")
    public boolean changePassword(@RequestBody User user) {
        return userService.changePassword(user);
    }

    @GetMapping("/me")
    public User me() {
        return userService.me();
    }
}
