package com.deverick.sftpserver.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Bank {

    private String cardExpire;

    private String cardNumber;

    private String cardType;

    private String currency;

    private String iban;
}
