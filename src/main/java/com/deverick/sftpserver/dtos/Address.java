package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private String address;

    private String city;

    private String state;

    private String stateCode;

    private String postalCode;

    @JsonUnwrapped
    private Coordinates coordinates;

    private String country;
}
