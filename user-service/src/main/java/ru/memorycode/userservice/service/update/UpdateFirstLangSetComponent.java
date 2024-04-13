package ru.memorycode.userservice.service.update;

import org.springframework.stereotype.Component;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;
import ru.memorycode.userservice.model.User;

import java.util.Optional;

@Component
public class UpdateFirstLangSetComponent implements UpdateTelegramUserStrategy {
    @Override
    public void execute(TelegramUserDto telegramUserDto, User telegramUserEntity) {
        if (Optional.ofNullable(telegramUserDto.getFirstLangSet()).isPresent()) {
            telegramUserEntity.setFirstLangSet(telegramUserDto.getFirstLangSet());
        }
    }
}
