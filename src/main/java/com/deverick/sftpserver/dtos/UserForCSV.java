package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class UserForCSV {
    @JsonProperty("id")
    private int id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("maidenName")
    private String maidenName;

    @JsonProperty("age")
    private int age;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("image")
    private String image;

    @JsonProperty("bloodGroup")
    private String bloodGroup;

    @JsonProperty("height")
    private String height;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("eyeColor")
    private String eyeColor;

    @JsonUnwrapped
    @JsonProperty("hair")
    private HairForCSV hair;

    @JsonProperty("ip")
    private String ip;

    @JsonUnwrapped
    @JsonProperty("address")
    private AddressForCSV address;

    @JsonProperty("macAddress")
    private String macAddress;

    @JsonProperty("university")
    private String university;

    @JsonUnwrapped
    @JsonProperty("bank")
    private Bank bank;

    @JsonUnwrapped
    @JsonProperty("company")
    private CompanyForCSV company;

    @JsonProperty("ein")
    private String ein;

    @JsonProperty("ssn")
    private String ssn;

    @JsonProperty("userAgent")
    private String userAgent;

    @JsonUnwrapped
    @JsonProperty("crypto")
    private Crypto crypto;

    @JsonProperty("role")
    private String role;
}
