package ru.memorycode.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.service.LoginService;

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
