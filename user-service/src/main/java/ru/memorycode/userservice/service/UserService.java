package ru.memorycode.userservice.service;

import org.springframework.validation.BindingResult;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;
import ru.memorycode.userservice.model.User;

public interface UserService {

    Boolean save(TelegramUserDto entity);

    Mono<User> getUserByUserId(Long userId);

    User update(TelegramUserDto entity);

    boolean delete(Long userId);
}
