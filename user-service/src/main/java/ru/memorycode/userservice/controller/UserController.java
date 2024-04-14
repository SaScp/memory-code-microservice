package ru.memorycode.userservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.auth.LoginUserEntityDto;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;

import ru.memorycode.userservice.service.UserAuthenticationService;
import ru.memorycode.userservice.service.UserService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

   private UserService userService;

    private UserAuthenticationService userAuthenticationService;

    private ModelMapper modelMapper;

    @PostMapping("/save")
    @Operation(summary = "save telegram user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelegramUserDto.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "user save",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class)
                    )
            )
    )
    public ResponseEntity<HttpStatus> saveUser(@RequestBody TelegramUserDto telegramUserEntity) {
        log.info("Save user: {}", telegramUserEntity);
        return userService.save(telegramUserEntity)? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/save-auth-data", consumes = "application/json")
    @Operation(summary = "save user from site",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginUserEntityDto.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "user auth save",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class)
                    )
            )
    )
    public Mono<ResponseEntity<HttpStatus>> saveAuthData(@RequestBody LoginUserEntityDto userEntityDto) {
        log.info("Save user from site: {}", userEntityDto);
        return userAuthenticationService.saveAuthData(userEntityDto)? Mono.just(ResponseEntity.ok().build()) :
                Mono.just(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/get/{userId}", produces = "application/json")
    @Operation(summary = "get data by id",
            parameters = @Parameter(
                    name = "userId",
                    description = "id telegram user"
                    , required = true

            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "get data by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TelegramUserDto.class)
                    )
            )
    )
    public Mono<ResponseEntity<TelegramUserDto>> getUserByUserId(@PathVariable Long userId) {
        log.info("Get user by id: {}", userId);
        return userService.getUserByUserId(userId).map(user -> ResponseEntity.ok(modelMapper.map(user, TelegramUserDto.class)));
    }

    @PatchMapping(value = "/update", produces = "application/json")
    @Operation(summary = "update user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TelegramUserDto.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "user update",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TelegramUserDto.class)
                    )
            )
    )
    public ResponseEntity<TelegramUserDto> updateUser(@RequestBody TelegramUserDto telegramUserEntity) {
        log.info("Update user: {}", telegramUserEntity);
        return ResponseEntity.ok(modelMapper.map(userService.update(telegramUserEntity), TelegramUserDto.class));
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "delete telegram user by id",
            parameters = @Parameter(
                    name = "userId",
                    description = "id telegram user",
                    required = true,
                    schema = @Schema(type = "Long")
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "delete telegram user by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class)
                    )
            )
    )
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {
        log.info("Delete user by id: {}", userId);
        return userService.delete(userId)? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
