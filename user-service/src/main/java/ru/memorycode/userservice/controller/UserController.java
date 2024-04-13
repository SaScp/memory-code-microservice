package ru.memorycode.userservice.controller;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

@RestController
@AllArgsConstructor
@RequestMapping("/user")

public class UserController {

    private UserService userService;

    private UserAuthenticationService userAuthenticationService;

    private ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody TelegramUserDto telegramUserEntity) {
        return userService.save(telegramUserEntity)? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("/save-auth-data")
    public Mono<ResponseEntity<HttpStatus>> saveAuthData(@RequestBody LoginUserEntityDto userEntityDto) {
        return userAuthenticationService.saveAuthData(userEntityDto)? Mono.just(ResponseEntity.ok().build()) :
                Mono.just(ResponseEntity.badRequest().build());
    }

    @GetMapping("/get/{userId}")
    public Mono<ResponseEntity<TelegramUserDto>> getUserByUserId(@PathVariable Long userId) {
        return userService.getUserByUserId(userId).map(user -> ResponseEntity.ok(modelMapper.map(user, TelegramUserDto.class)));
    }

    @PatchMapping("/update")
    public ResponseEntity<TelegramUserDto> updateUser(@RequestBody TelegramUserDto telegramUserEntity) {
        return ResponseEntity.ok(modelMapper.map(userService.update(telegramUserEntity), TelegramUserDto.class));
    }

    @DeleteMapping("/delete")

    public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long userId) {
        return userService.delete(userId)? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
