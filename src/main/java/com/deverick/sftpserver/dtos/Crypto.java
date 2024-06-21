package com.deverick.sftpserver.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Crypto {

    private String coin;

    private String wallet;

    private String network;
}
