package ru.memorycode.yandexgptservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ProducerRequest {

    private String langCode;

    private String fio;

    private String dateOfBirth;

    private String dateOfdied;

    private String placeOfBirth;

    private String placeOfDied;

    private String work;

    private String award;

    private String hobby;

    private Map<String, String> add_questions;

    private Biography biography_1;

    private Biography biography_2;

    private Biography biography_3;

    private Biography final_biography;
}
