package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class AddressForCSV {

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("stateCode")
    private String stateCode;

    @JsonProperty("postalCode")
    private String postalCode;

    @JsonUnwrapped
    @JsonProperty("coordinates")
    private CoordinatesForCSV coordinates;

    @JsonProperty("country")
    private String country;
}
