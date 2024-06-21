package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CompanyCoordinatesForCSV {

    @JsonProperty("company_lat")
    private String lat;

    @JsonProperty("company_lng")
    private String lng;
}
