package ru.memorycode.yandexgptservice.model;

import lombok.Data;

@Data
public class CompletionOptions {
    private Boolean stream;

    private String temperature;

    private String maxTokens;
}
