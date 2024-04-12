package ru.memorycode.userservice.service;

import org.springframework.validation.BindingResult;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;
import ru.memorycode.userservice.model.User;
import ru.memorycode.userservice.model.auth.TelegramUserEntity;

public interface UserService {

    Boolean save(TelegramUserEntity entity);

    User getUserByUserId(Long userId);

    User update(TelegramUserEntity entity);
}
