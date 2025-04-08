package org.example.controller;

import org.example.model.Stock;
import org.example.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping
    public boolean save(@RequestBody Stock stock) {
        return stockService.save(stock);
    }

    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable int id) {
        return stockService.removeById(id);
    }

    @PutMapping
    public boolean update(@RequestBody Stock stock) {
        return stockService.updateById(stock);
    }

    @GetMapping
    public List<Stock> list() {
        return stockService.listWithGoods();
    }

    @GetMapping("/{id}")
    public Stock get(@PathVariable int id) {
        return stockService.getOneWithGoodsById(id);
    }

    @GetMapping("/gid/{gid}")
    public List<Stock> listByGid(@PathVariable int gid) {
        return stockService.listWithGoodsByGid(gid);
    }
}
