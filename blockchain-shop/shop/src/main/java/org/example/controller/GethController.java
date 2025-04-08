package org.example.controller;

import org.example.eth.GethService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/geth")
public class GethController {
    @Autowired
    GethService gethService;

    @GetMapping("/balance")
    public BigInteger balanceOf(String account) {
        return gethService.balanceOf(account);
    }

    @GetMapping
    public List<String> accounts() {
        return gethService.accounts();
    }

    @PostMapping
    public String newAccount(@RequestBody String password) {
        return gethService.newAccount(password);
    }
}
