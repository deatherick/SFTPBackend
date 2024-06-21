package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class CompanyAddressForCSV {

    @JsonProperty("company_address")
    private String address;

    @JsonProperty("company_city")
    private String city;

    @JsonProperty("company_state")
    private String state;

    @JsonProperty("company_stateCode")
    private String stateCode;

    @JsonProperty("company_postalCode")
    private String postalCode;

    @JsonUnwrapped
    @JsonProperty("coordinates")
    private CompanyCoordinatesForCSV coordinates;

    @JsonProperty("company_country")
    private String country;
}
