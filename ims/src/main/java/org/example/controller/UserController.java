package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable int id) {
        return userService.removeById(id);
    }

    @PutMapping
    public boolean update(@RequestBody User user) {
        return userService.updateById(user);
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping("/username/{username}")
    public List<User> getLikeUsername(@PathVariable String username) {
        return userService.getLikeUsername(username);
    }
}
