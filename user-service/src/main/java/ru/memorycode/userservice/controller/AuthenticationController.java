package ru.memorycode.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.auth.LoginUserEntityDto;
import ru.memorycode.userservice.dto.auth.RegistrationUserEntityDto;
import ru.memorycode.userservice.service.LoginService;
import ru.memorycode.userservice.service.RegistrationService;

import java.io.IOException;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private LoginService loginService;

    private RegistrationService registrationService;

    @PostMapping(value = "/login", produces = "application/json")
    public Mono<Map> getUser(@RequestBody LoginUserEntityDto loginUserEntityDto) {
        return loginService.login(loginUserEntityDto.getLogin(), loginUserEntityDto.getPassword());
    }

    @PostMapping(value = "/registration", produces = "application/json")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationUserEntityDto userEntityDto) throws IOException, InterruptedException {
        return registrationService.registerUser(userEntityDto);
    }

}
