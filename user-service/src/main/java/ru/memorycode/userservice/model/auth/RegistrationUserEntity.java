package ru.memorycode.userservice.model.auth;

import lombok.Data;

@Data
public class RegistrationUserEntity {

    private String name;

    private String password;

    private String login;

    private String email;

    private String phone;

    private String password_confirmation;

    private Boolean checked;

    private Boolean sendPassword;
}
