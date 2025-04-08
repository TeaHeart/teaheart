package org.example.controller;

import org.example.domain.ItemInfo;
import org.example.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemInfo")
public class ItemInfoController {
    @Autowired
    ItemInfoService itemInfoService;

    @PostMapping
    public boolean save(@RequestBody ItemInfo entity) {
        return itemInfoService.save(entity);
    }

    @DeleteMapping
    public boolean removeById(Integer id) {
        return itemInfoService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody ItemInfo entity) {
        return itemInfoService.updateById(entity);
    }

    @GetMapping
    public List<ItemInfo> list() {
        return itemInfoService.list();
    }
}