package com.deverick.sftpserver.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RootObject {

    @JsonProperty("users")
    public User[] users;

    //@JsonIgnore
    public int total;

    //@JsonIgnore
    public int skip;

    //@JsonIgnore
    public int limit;
}
