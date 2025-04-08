package org.example.eth;

import java.math.BigInteger;
import java.util.List;

public interface GethService {
    BigInteger balanceOf(String account);

    List<String> accounts();

    String newAccount(String password);
}
