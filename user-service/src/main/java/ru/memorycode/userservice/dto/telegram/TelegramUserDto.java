package ru.memorycode.userservice.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TelegramUserDto {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("lang_code")
    private String langCode;

    private UserAuthenticationDto userAuth;

}
