package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CoordinatesForCSV {

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;
}
