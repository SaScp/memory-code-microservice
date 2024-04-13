package ru.memorycode.userservice.service.update;

import ru.memorycode.userservice.dto.telegram.TelegramUserDto;
import ru.memorycode.userservice.model.User;


public interface UpdateTelegramUserStrategy {

    void execute(TelegramUserDto telegramUserDto, User telegramUserEntity);
}
