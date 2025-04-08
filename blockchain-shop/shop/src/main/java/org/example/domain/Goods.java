package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private String seller;
    private String item;
    private BigInteger price;
    private Boolean onSale;
}
