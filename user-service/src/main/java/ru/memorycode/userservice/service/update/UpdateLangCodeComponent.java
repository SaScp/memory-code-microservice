package ru.memorycode.userservice.service.update;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;
import ru.memorycode.userservice.model.User;


import java.util.Optional;

@Component
public class UpdateLangCodeComponent implements UpdateTelegramUserStrategy {
    @Override
    public void execute(TelegramUserDto telegramUserDto, User telegramUserEntity) {
        if (Optional.ofNullable(telegramUserDto.getLangCode()).isPresent()) {
            telegramUserEntity.setLangCode(telegramUserDto.getLangCode());
        }
    }
}
