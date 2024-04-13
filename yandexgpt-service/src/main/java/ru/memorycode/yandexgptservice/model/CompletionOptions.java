package ru.memorycode.yandexgptservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompletionOptions {
    private Boolean stream;

    private String temperature;

    private String maxTokens;
}
