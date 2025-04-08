package org.example.controller;

import org.example.entity.Policy;
import org.example.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @PostMapping
    public boolean save(@RequestBody Policy policy) {
        return policyService.save(policy);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Integer id) {
        return policyService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody Policy policy) {
        return policyService.updateById(policy);
    }

    @GetMapping
    public List<Policy> list() {
        return policyService.list();
    }

    @GetMapping("/{id}")
    public Policy getById(@PathVariable Integer id) {
        return policyService.getById(id);
    }

    @GetMapping("/enabled/{enabled}")
    public List<Policy> listByEnabled(@PathVariable boolean enabled) {
        return policyService.listByEnabled(enabled);
    }
}
