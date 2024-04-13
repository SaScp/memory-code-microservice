package ru.memorycode.yandexgptservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.memorycode.yandexgptservice.service.YandexGptService;
import ru.memorycode.yandexgptservice.model.Message;
import ru.memorycode.yandexgptservice.model.ProducerRequest;

import java.util.Map;

@Slf4j
@RestController
public class YandexController {

    private final YandexGptService yandexGptService;

    public YandexController(YandexGptService yandexGptService) {
        this.yandexGptService = yandexGptService;
    }

    @PostMapping("/question")
    public Mono<Map> generateQuestion(@RequestBody ProducerRequest producerRequest) {
        return yandexGptService.questionCreateConsumer(producerRequest);
    }

    @PostMapping("/epitaph")
    public Mono<Map> generateEpitaph(@RequestBody ProducerRequest producerRequest) {
        return yandexGptService.epitaphCreateConsumer(producerRequest);
    }

    @PostMapping("/viography")
    public Mono<Map> generateBiography(@RequestBody ProducerRequest producerRequest) {
        return yandexGptService.biographyCreateConsumer(producerRequest);
    }

    @GetMapping("/get/{id}")
    public Mono<Message> getData(@PathVariable String id) {
        return yandexGptService.getData(id);
    }
}
