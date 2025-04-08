package org.example.eth;

import org.example.contract.Item;

public interface ItemService {
    String ownerOf(String item);

    Item.TransferEventResponse mint(String to);

    Item.TransferEventResponse transferForce(String to, String item);
}
