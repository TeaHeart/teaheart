package org.example.eth.impl;

import lombok.SneakyThrows;
import org.example.eth.GethService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.geth.Geth;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.List;

@Repository
public class GethServiceImpl implements GethService {
    @Autowired
    Geth geth;
    @Autowired
    DefaultBlockParameter defaultBlockParameter;
    @Autowired
    ContractGasProvider contractGasProvider;

    /**
     * 获取该用户的 ETH 余额
     *
     * @param account 用户的地址
     * @return 该用户的 ETH 余额
     */
    @SneakyThrows
    @Override
    public BigInteger balanceOf(String account) {
        return geth.ethGetBalance(account, defaultBlockParameter).send().getBalance();
    }

    /**
     * 列出链上所有的用户 address
     *
     * @return 所有用户的 address
     */
    @SneakyThrows
    @Override
    public List<String> accounts() {
        return geth.personalListAccounts().send().getAccountIds();
    }

    /**
     * 创建一个新的用户
     *
     * @param password 密码
     * @return 新用户的 address
     */
    @SneakyThrows
    @Override
    public String newAccount(String password) {
        return geth.personalNewAccount(password).send().getAccountId();
    }

}
