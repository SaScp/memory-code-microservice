package ru.memorycode.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @PostMapping(value = "/login", produces = "application/json")
    @Operation(summary = "login user on site",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginUserEntityDto.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "user login",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    )
    public Mono<Map> getUser(@RequestBody LoginUserEntityDto loginUserEntityDto) {
        return loginService.login(loginUserEntityDto.getLogin(), loginUserEntityDto.getPassword());
    }


}
