package ru.memorycode.yandexgptservice.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class YandexGptRequest {

    private String modelUri;

    private CompletionOptions completionOptions;

    private List<Message> messages;
}
