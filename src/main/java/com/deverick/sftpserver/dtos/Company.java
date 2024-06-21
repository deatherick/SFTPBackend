package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Company {

    private String department;

    private String name;

    private String title;

    @JsonUnwrapped
    private Address address;
}
