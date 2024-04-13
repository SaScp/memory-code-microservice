package ru.memorycode.yandexgptservice.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.memorycode.yandexgptservice.model.CompletionOptions;
import ru.memorycode.yandexgptservice.model.Message;
import ru.memorycode.yandexgptservice.model.ProducerRequest;
import ru.memorycode.yandexgptservice.model.YandexGptRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class GeneratorYandexGptRequest {

    private final String indignity_key;

    public GeneratorYandexGptRequest( @Value("${yandex.gpt.api.indignity-key}") String indignityKey) {
        this.indignity_key = indignityKey;
    }

    @Async
    public CompletableFuture<YandexGptRequest> generateEpitaph(ProducerRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            return generateRequest(new CompletionOptions(null  ,null, null), new ArrayList<>());
        });
    }

    @Async
    public CompletableFuture<YandexGptRequest> generateBiography(ProducerRequest producerRequest) {
        return CompletableFuture.supplyAsync(() -> {
            return generateRequest(new CompletionOptions(null  ,null, null), new ArrayList<>());
        });
    }
    @Async
    public CompletableFuture<YandexGptRequest> generateQuestion(ProducerRequest producerRequest) {
        return CompletableFuture.supplyAsync(() -> {
            Message message = new Message();
            message.setRole("user");
            message.setText("Какой смысл жизни?");
            return generateRequest(new CompletionOptions(true, "1", "1"), List.of(message));
        });
    }

    private YandexGptRequest generateRequest(CompletionOptions completionOptions, List<Message> messages) {
        YandexGptRequest yandexGptRequest = new YandexGptRequest("gpt://b1g5og37bgh1ghh2s2qc/yandexgpt/latest", completionOptions, messages);
        yandexGptRequest.setModelUri("gpt://" + indignity_key + "/yandexgpt/latest");
        yandexGptRequest.setCompletionOptions(completionOptions);
        yandexGptRequest.setMessages(messages);
        return yandexGptRequest;
    }
}
