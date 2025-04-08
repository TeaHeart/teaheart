package org.example.controller;

import org.example.contract.Shop;
import org.example.domain.Goods;
import org.example.eth.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping
    public Map<String, Goods> goodsOf(String[] item) {
        Map<String, Goods> map = new LinkedHashMap<>();
        for (String s : item) {
            map.put(s, shopService.goodsOf(s));
        }
        return map;
    }

    @PostMapping
    public Shop.PutEventResponse putForce(@RequestBody Shop.PutEventResponse putEventResponse) {
        return shopService.putForce(putEventResponse.item, putEventResponse.price);
    }

    @DeleteMapping
    public Shop.PullEventResponse pullForce(String item) {
        return shopService.pullForce(item);
    }

    @PutMapping
    public Shop.BoughtEventResponse buyForce(@RequestBody Shop.BoughtEventResponse boughtEventResponse) {
        return shopService.buyForce(boughtEventResponse.buyers, boughtEventResponse.item);
    }
}
