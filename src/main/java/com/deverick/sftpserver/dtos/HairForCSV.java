package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class HairForCSV {

    @JsonProperty("color")
    private String color;

    @JsonProperty("type")
    private String type;
}
