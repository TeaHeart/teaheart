package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.contract.Coin;
import org.example.contract.Item;
import org.example.contract.Shop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Slf4j
@SpringBootApplication
public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        log.info("{}", context.getBean(Geth.class));
        log.info("{}", context.getBean(TransactionManager.class));
        log.info("{}", context.getBean(ContractGasProvider.class));

        log.info("{}", context.getBean(Geth.class).ethAccounts().send().getAccounts().get(0));
        log.info("{}", context.getBean(Coin.class).getContractAddress());
        log.info("{}", context.getBean(Item.class).getContractAddress());
        log.info("{}", context.getBean(Shop.class).getContractAddress());
    }

    /**
     * 创建 Geth
     *
     * @return Geth
     */
    @Bean
    public Geth geth(@Value("${blockchain.url}") String url) {
        return Geth.build(new HttpService(url));
    }

    /**
     * 创建 TransactionManager
     *
     * @param geth    geth
     * @param address 从配置文件中读取的 admin, 为空自动获取
     * @return TransactionManager
     */
    @Bean
    @SneakyThrows
    public TransactionManager clientTransactionManager(Geth geth, @Value("${blockchain.admin}") String address) {
        if (!StringUtils.hasLength(address)) {
            address = geth.ethAccounts().send().getAccounts().get(0);
        }
        return new ClientTransactionManager(geth, address);
    }

    /**
     * 创建 ContractGasProvider
     *
     * @param geth     geth
     * @param gasLimit 从配置文件中读取的 gasLimit
     * @return ContractGasProvider
     */
    @Bean
    @SneakyThrows
    public ContractGasProvider contractGasProvider(Geth geth, @Value("${blockchain.gas-limit}") BigInteger gasLimit) {
        BigInteger gasPrice = geth.ethGasPrice().send().getGasPrice();
        return new StaticGasProvider(gasPrice, gasLimit);
    }

    @Bean
    public DefaultBlockParameter defaultBlockParameter() {
        return DefaultBlockParameterName.LATEST;
    }

    /**
     * 创建或加载 Coin 合约
     *
     * @param address             从配置文件中读取的合约地址, 为空自动重新部署新的合约
     * @param geth                geth
     * @param transactionManager  transactionManager
     * @param contractGasProvider contractGasProvider
     * @return Coin
     */
    @Bean
    @SneakyThrows
    public Coin coin(@Value("${blockchain.coin-address}") String address, Geth geth, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        if (StringUtils.hasLength(address)) {
            return Coin.load(address, geth, transactionManager, contractGasProvider);
        }
        return Coin.deploy(geth, transactionManager, contractGasProvider).send();
    }

    /**
     * 创建或加载 Item 合约
     *
     * @param address             从配置文件中读取的合约地址, 为空自动重新部署新的合约
     * @param geth                geth
     * @param transactionManager  transactionManager
     * @param contractGasProvider contractGasProvider
     * @return Item
     */
    @Bean
    @SneakyThrows
    public Item item(@Value("${blockchain.item-address}") String address, Geth geth, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        if (StringUtils.hasLength(address)) {
            return Item.load(address, geth, transactionManager, contractGasProvider);
        }
        return Item.deploy(geth, transactionManager, contractGasProvider).send();
    }

    /**
     * 创建或加载 Shop 合约
     *
     * @param address             从配置文件中读取的合约地址, 为空自动重新部署新的合约
     * @param geth                geth
     * @param transactionManager  transactionManager
     * @param contractGasProvider contractGasProvider
     * @param coin                coin
     * @param item                item
     * @return Shop
     */
    @Bean
    @SneakyThrows
    public Shop shop(@Value("${blockchain.shop-address}") String address, Geth geth, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Coin coin, Item item) {
        if (StringUtils.hasLength(address)) {
            return Shop.load(address, geth, transactionManager, contractGasProvider);
        }
        Shop shop = Shop.deploy(geth, transactionManager, contractGasProvider, coin.getContractAddress(), item.getContractAddress()).send();
        address = shop.getContractAddress();
        coin.allowAccess(address).send();
        item.allowAccess(address).send();
        return shop;
    }
}
