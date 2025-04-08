package org.example.eth.impl;

import lombok.SneakyThrows;
import org.example.contract.Coin;
import org.example.eth.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.example.contract.Coin.FUNC_BALANCEOF;
import static org.web3j.abi.FunctionEncoder.encode;
import static org.web3j.abi.FunctionReturnDecoder.decode;
import static org.web3j.abi.TypeReference.create;

@Repository
public class CoinServiceImpl implements CoinService {
    @Autowired
    Coin coin;
    @Autowired
    TransactionManager transactionManager;
    @Autowired
    DefaultBlockParameter defaultBlockParameter;

    /**
     * 对应 Coin 合约中的 balanceOf
     *
     * @param account 用户的 address
     * @return 该用户的代币余额
     */
    @SneakyThrows
    @Override
    public BigInteger balanceOf(String account) {
        final Function function = new Function(
                FUNC_BALANCEOF,
                singletonList(new Address(account)),
                singletonList(create(Uint256.class)));
        String rawResult = transactionManager.sendCall(coin.getContractAddress(), encode(function), defaultBlockParameter);
        List<?> result = decode(rawResult, function.getOutputParameters());
        return ((Uint256) result.get(0)).getValue();
    }

    /**
     * 对应 Coin 合约中的 mint
     *
     * @param to     接收者
     * @param amount 代币数量
     * @return 转账事件
     */
    @SneakyThrows
    @Override
    public Coin.TransferEventResponse mint(String to, BigInteger amount) {
        return coin.getTransferEvents(coin.mint(to, amount).send()).get(0);
    }

    /**
     * 对应 Coin 合约中的 transferForce
     *
     * @param to     接收者
     * @param amount 代币数量
     * @return 转账事件
     */
    @SneakyThrows
    @Override
    public Coin.TransferEventResponse transferForce(String from, String to, BigInteger amount) {
        return coin.getTransferEvents(coin.transferForce(from, to, amount).send()).get(0);
    }
}
