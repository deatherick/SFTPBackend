package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;

    private String firstName;

    private String lastName;

    private String maidenName;

    private int age;

    private String gender;

    private String email;

    private String phone;

    private String username;

    private String password;

    private String birthDate;

    private String image;

    private String bloodGroup;

    private String height;

    private String weight;

    private String eyeColor;

    //@JsonUnwrapped
    private Hair hair;

    private String ip;

    //@JsonUnwrapped
    private Address address;

    private String macAddress;

    private String university;

    //@JsonUnwrapped
    private Bank bank;

    @JsonUnwrapped
    private Company company;

    private String ein;

    private String ssn;

    private String userAgent;

    //@JsonUnwrapped
    private Crypto crypto;

    private String role;
}
