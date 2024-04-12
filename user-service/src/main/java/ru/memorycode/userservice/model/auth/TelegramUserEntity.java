package ru.memorycode.userservice.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TelegramUserEntity {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("lang_code")
    private String langCode;
}
