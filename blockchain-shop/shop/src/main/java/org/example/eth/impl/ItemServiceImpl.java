package org.example.eth.impl;

import lombok.SneakyThrows;
import org.example.contract.Item;
import org.example.eth.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.tx.TransactionManager;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.example.contract.Item.FUNC_OWNEROF;
import static org.web3j.abi.FunctionEncoder.encode;
import static org.web3j.abi.FunctionReturnDecoder.decode;
import static org.web3j.abi.TypeReference.create;

@Repository
public class ItemServiceImpl implements ItemService {
    @Autowired
    Item item;
    @Autowired
    TransactionManager transactionManager;
    @Autowired
    DefaultBlockParameter defaultBlockParameter;

    /**
     * 对应 Item 合约中的 ownerOf
     *
     * @param item 物品的 address
     * @return 物品所有者的 address
     */
    @SneakyThrows
    @Override
    public String ownerOf(String item) {
        final Function function = new Function(
                FUNC_OWNEROF,
                singletonList(new Address(item)),
                singletonList(create(Address.class)));
        String rawResult = transactionManager.sendCall(this.item.getContractAddress(), encode(function), defaultBlockParameter);
        List<?> result = decode(rawResult, function.getOutputParameters());
        return ((Address) result.get(0)).getValue();
    }

    /**
     * 对应 Item 合约中的 mint
     *
     * @param to 接收者
     * @return 转让事件
     */
    @SneakyThrows
    @Override
    public Item.TransferEventResponse mint(String to) {
        return item.getTransferEvents(item.mint(to).send()).get(0);
    }

    /**
     * 对应 Item 合约中的 transferForce
     *
     * @param to    接收者
     * @param token 物品 address
     * @return 转让事件
     */
    @SneakyThrows
    @Override
    public Item.TransferEventResponse transferForce(String to, String token) {
        return item.getTransferEvents(item.transferForce(to, token).send()).get(0);
    }
}
