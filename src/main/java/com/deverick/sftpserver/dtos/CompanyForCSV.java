package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class CompanyForCSV {

    @JsonProperty("department")
    private String department;

    @JsonProperty("name")
    private String name;

    @JsonProperty("title")
    private String title;

    @JsonUnwrapped
    @JsonProperty("address")
    private CompanyAddressForCSV address;
}
