package org.example.controller;

import org.example.entity.SystemConfig;
import org.example.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system-config")
public class SystemConfigController {
    @Autowired
    private SystemConfigService systemConfigService;

    @PostMapping
    public boolean save(@RequestBody SystemConfig systemConfig) {
        return systemConfigService.save(systemConfig);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Integer id) {
        return systemConfigService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody SystemConfig systemConfig) {
        return systemConfigService.updateById(systemConfig);
    }

    @GetMapping
    public List<SystemConfig> list() {
        return systemConfigService.list();
    }

    @GetMapping("/{id}")
    public SystemConfig getById(@PathVariable Integer id) {
        return systemConfigService.getById(id);
    }

    @GetMapping("/key/{key}")
    public List<SystemConfig> getLikeKey(@PathVariable String key) {
        return systemConfigService.getLikeByKey(key);
    }
}
