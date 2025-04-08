package org.example.controller;

import org.example.contract.Coin;
import org.example.eth.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    CoinService coinService;

    @GetMapping
    public Map<String, BigInteger> balanceOf(String[] account) {
        Map<String, BigInteger> map = new LinkedHashMap<>();
        for (String s : account) {
            map.put(s, coinService.balanceOf(s));
        }
        return map;
    }

    @PostMapping
    public Coin.TransferEventResponse mint(@RequestBody Coin.TransferEventResponse transferEventResponse) {
        return coinService.mint(transferEventResponse.to, transferEventResponse.amount);
    }

    @PutMapping
    public Coin.TransferEventResponse transferForce(@RequestBody Coin.TransferEventResponse transferEventResponse) {
        return coinService.transferForce(transferEventResponse.from, transferEventResponse.to, transferEventResponse.amount);
    }
}
