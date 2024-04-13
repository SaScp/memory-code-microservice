package ru.memorycode.yandexgptservice.model.response;

import lombok.Data;
import ru.memorycode.yandexgptservice.model.Message;

@Data
public class Alternatives {
    private Message message;
    private String status;
}
