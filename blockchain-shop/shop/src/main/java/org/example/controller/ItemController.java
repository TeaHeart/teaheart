package org.example.controller;

import org.example.contract.Item;
import org.example.eth.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping
    public Map<String, String> ownerOf(String[] item) {
        Map<String, String> map = new LinkedHashMap<>();
        for (String s : item) {
            map.put(s, itemService.ownerOf(s));
        }
        return map;
    }

    @PostMapping
    public Item.TransferEventResponse mint(@RequestBody Item.TransferEventResponse transferEventResponse) {
        return itemService.mint(transferEventResponse.to);
    }

    @PutMapping
    public Item.TransferEventResponse transferForce(@RequestBody Item.TransferEventResponse transferEventResponse) {
        return itemService.transferForce(transferEventResponse.to, transferEventResponse.item);
    }
}
