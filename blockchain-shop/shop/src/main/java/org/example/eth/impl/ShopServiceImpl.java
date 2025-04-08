package org.example.eth.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.SneakyThrows;
import org.example.contract.Shop;
import org.example.domain.Account;
import org.example.domain.Goods;
import org.example.domain.ItemRepository;
import org.example.eth.ShopService;
import org.example.mapper.AccountMapper;
import org.example.mapper.ItemRepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.example.contract.Shop.FUNC_GOODSOF;
import static org.web3j.abi.FunctionEncoder.encode;
import static org.web3j.abi.TypeReference.create;

@Repository
public class ShopServiceImpl implements ShopService {
    @Autowired
    Shop shop;
    @Autowired
    TransactionManager transactionManager;
    @Autowired
    DefaultBlockParameter defaultBlockParameter;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    ItemRepositoryMapper itemRepositoryMapper;

    /**
     * 对应 Goods 合约中的 goodsOf
     *
     * @param item 物品的 address
     * @return 商品信息
     */
    @SneakyThrows
    @Override
    public Goods goodsOf(String item) {
        final Function function = new Function(
                FUNC_GOODSOF,
                singletonList(new Address(item)),
                asList(create(Address.class), create(Uint256.class), create(Bool.class)));
        String rawResult = transactionManager.sendCall(shop.getContractAddress(), encode(function), defaultBlockParameter);
        List<?> result = FunctionReturnDecoder.decode(rawResult, function.getOutputParameters());
        Goods goods = new Goods();
        goods.setSeller(((Address) result.get(0)).getValue());
        goods.setItem(item);
        goods.setPrice(((Uint256) result.get(1)).getValue());
        goods.setOnSale(((Bool) result.get(2)).getValue());
        return goods;
    }

    /**
     * 对应 Goods 合约中的 putForce
     *
     * @param item  物品的 address
     * @param price 物品的价格
     * @return 物品上架事件
     */
    @SneakyThrows
    @Override
    public Shop.PutEventResponse putForce(String item, BigInteger price) {
        Shop.PutEventResponse putEventResponse = shop.getPutEvents(shop.putForce(item, price).send()).get(0);
        itemRepositoryMapper.update(null, new LambdaUpdateWrapper<ItemRepository>()
                .eq(ItemRepository::getToken, item)
                .set(ItemRepository::getIsAvailable, false)
        );
        return putEventResponse;
    }

    /**
     * 对应 Goods 合约中的 pullForce
     *
     * @param item 物品的 address
     * @return 物品下架事件
     */
    @SneakyThrows
    @Override
    public Shop.PullEventResponse pullForce(String item) {
        Shop.PullEventResponse pullEventResponse = shop.getPullEvents(shop.pullForce(item).send()).get(0);
        itemRepositoryMapper.update(null, new LambdaUpdateWrapper<ItemRepository>()
                .eq(ItemRepository::getToken, item)
                .set(ItemRepository::getIsAvailable, true)
        );
        return pullEventResponse;
    }

    /**
     * 对应 Goods 合约中的 buyForce
     *
     * @param buyers 买家的 address
     * @param item   物品的 address
     * @return 物品购买事件
     */
    @SneakyThrows
    @Override
    public Shop.BoughtEventResponse buyForce(String buyers, String item) {
        Shop.BoughtEventResponse boughtEventResponse = shop.getBoughtEvents(shop.buyForce(buyers, item).send()).get(0);
        Account account = accountMapper.selectOne(new LambdaUpdateWrapper<Account>().eq(Account::getToken, buyers));
        itemRepositoryMapper.update(null, new LambdaUpdateWrapper<ItemRepository>()
                .eq(ItemRepository::getToken, item)
                .set(ItemRepository::getAccountId, account.getId())
                .set(ItemRepository::getIsAvailable, true)
        );
        return boughtEventResponse;
    }
}
