package ru.memorycode.userservice.dto.auth;

import lombok.Data;

@Data
public class RegistrationUserEntityDto {

    private String name;

    private String email;

    private String phone;

}
