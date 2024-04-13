package ru.memorycode.yandexgptservice.model.response;

import lombok.Data;

@Data
public class YandexGptResponse {
    private Boolean done;
    private YandexResponse response;
    private String id;
    private String description;
    private String createdAt;
    private String createdBy;
    private String modifiedAt;
}
