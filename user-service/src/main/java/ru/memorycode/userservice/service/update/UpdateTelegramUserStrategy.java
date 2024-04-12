package ru.memorycode.userservice.service.update;

import ru.memorycode.userservice.model.auth.TelegramUserEntity;

public interface UpdateTelegramUserStrategy {

    void execute(TelegramUserEntity telegramUserDto, TelegramUserEntity telegramUserEntity);
}
