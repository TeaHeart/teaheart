package org.example.controller;

import org.example.domain.Account;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping
    public boolean save(@RequestBody Account entity) {
        return accountService.save(entity);
    }

    @DeleteMapping
    public boolean removeById(Integer id) {
        return accountService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody Account entity) {
        return accountService.updateById(entity);
    }

    @GetMapping
    public List<Account> list() {
        return accountService.list();
    }
}
