package org.example.eth;

import org.example.contract.Coin;

import java.math.BigInteger;

public interface CoinService {

    BigInteger balanceOf(String account);

    Coin.TransferEventResponse mint(String to, BigInteger amount);

    Coin.TransferEventResponse transferForce(String from, String to, BigInteger amount);
}
