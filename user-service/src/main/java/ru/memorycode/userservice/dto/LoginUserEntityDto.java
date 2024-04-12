package ru.memorycode.userservice.dto;

import lombok.Data;

@Data
public class LoginUserEntityDto {

    private String email;

    private String password;
}
