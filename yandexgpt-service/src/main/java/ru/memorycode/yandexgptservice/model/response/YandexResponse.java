package ru.memorycode.yandexgptservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class YandexResponse {

    @JsonProperty("@type")
    private String type;
    private List<Alternatives> alternatives;
    private Map<String, String> usage;
    private String modelVersion;
}
