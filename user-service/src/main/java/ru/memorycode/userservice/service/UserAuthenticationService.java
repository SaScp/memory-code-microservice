package ru.memorycode.userservice.service;

import org.springframework.validation.BindingResult;
import ru.memorycode.userservice.dto.auth.LoginUserEntityDto;

public interface UserAuthenticationService {

    public Boolean saveAuthData(LoginUserEntityDto entity);

}
