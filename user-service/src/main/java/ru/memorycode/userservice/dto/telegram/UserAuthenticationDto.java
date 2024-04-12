package ru.memorycode.userservice.dto.telegram;

import lombok.Data;

@Data
public class UserAuthenticationDto {
    private String login;


    private String password;
}
