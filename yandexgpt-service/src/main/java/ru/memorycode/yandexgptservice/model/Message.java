package ru.memorycode.yandexgptservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Message {
    private String role;
    private String text;
}
