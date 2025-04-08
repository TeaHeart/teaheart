package org.example.controller;

import org.example.model.Goods;
import org.example.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping
    public boolean save(@RequestBody Goods goods) {
        return goodsService.save(goods);
    }

    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable int id) {
        return goodsService.removeById(id);
    }

    @PutMapping
    public boolean update(@RequestBody Goods goods) {
        return goodsService.updateById(goods);
    }

    @GetMapping
    public List<Goods> list() {
        return goodsService.list();
    }

    @GetMapping("/{id}")
    public Goods get(@PathVariable int id) {
        return goodsService.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<Goods> getLikeName(@PathVariable String name) {
        return goodsService.getLikeName(name);
    }
}
