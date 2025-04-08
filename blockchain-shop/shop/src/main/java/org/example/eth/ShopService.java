package org.example.eth;

import org.example.contract.Shop;
import org.example.domain.Goods;

import java.math.BigInteger;

public interface ShopService {
    Goods goodsOf(String item);

    Shop.PutEventResponse putForce(String item, BigInteger price);

    Shop.PullEventResponse pullForce(String item);

    Shop.BoughtEventResponse buyForce(String buyers, String item);
}
