package ru.memorycode.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.memorycode.dto.LoginUserEntityDto;
import ru.memorycode.userservice.model.auth.LoginUserEntity;
import ru.memorycode.userservice.service.LoginService;

import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {

    private LoginService loginService;

    @PostMapping("/get-token")
    public Mono<Map> getUser(@RequestBody LoginUserEntityDto loginUserEntityDto) {
        return loginService.login(loginUserEntityDto.getEmail(), loginUserEntityDto.getPassword());
    }



}
