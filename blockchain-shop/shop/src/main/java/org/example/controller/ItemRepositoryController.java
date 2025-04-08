package org.example.controller;

import org.example.domain.ItemRepository;
import org.example.service.ItemRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemRepository")
public class ItemRepositoryController {
    @Autowired
    ItemRepositoryService itemRepositoryService;

    @PostMapping
    public boolean save(@RequestBody ItemRepository entity) {
        return itemRepositoryService.save(entity);
    }

    @DeleteMapping
    public boolean removeById(Integer id) {
        return itemRepositoryService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody ItemRepository entity) {
        return itemRepositoryService.updateById(entity);
    }

    @GetMapping
    public List<ItemRepository> list() {
        return itemRepositoryService.list();
    }
}
