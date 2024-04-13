package ru.memorycode.userservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data

public class LoginUserEntityDto {

    @JsonProperty("user_id")

    private Long userId;


    private String login;


    private String password;
}
