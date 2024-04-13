package ru.memorycode.yandexgptservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompletionOptions {
    private Boolean stream;

    private Integer temperature;

    private Integer maxTokens;
}
