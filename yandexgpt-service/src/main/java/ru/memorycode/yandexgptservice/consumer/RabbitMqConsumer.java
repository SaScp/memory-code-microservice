package ru.memorycode.yandexgptservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.memorycode.yandexgptservice.generator.GeneratorYandexGptRequest;
import ru.memorycode.yandexgptservice.model.CompletionOptions;
import ru.memorycode.yandexgptservice.model.Message;
import ru.memorycode.yandexgptservice.model.ProducerRequest;
import ru.memorycode.yandexgptservice.model.YandexGptRequest;
import ru.memorycode.yandexgptservice.util.exception.YandexGptGenerateException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
@Slf4j
@EnableRabbit
public class RabbitMqConsumer {

    private WebClient webClient;

    private GeneratorYandexGptRequest generatorYandexGptRequest;

    private final String key;

    public RabbitMqConsumer(WebClient.Builder builder, GeneratorYandexGptRequest generatorYandexGptRequest,@Value("${yandex.gpt.api.key}") String key) {
        this.webClient = builder.build();
        this.generatorYandexGptRequest = generatorYandexGptRequest;
        this.key = key;
    }

    @RabbitListener(queues = "yandexgpt-question-queue")
    public Mono<Map> questionCreateConsumer(ProducerRequest request) {
        return sendRequestToYandexGpt(request, generatorYandexGptRequest::generateQuestion);
    }

    @RabbitListener(queues = "yandexgpt-biography-queue")
    public Mono<Map> biographyCreateConsumer(ProducerRequest request) {
        return sendRequestToYandexGpt(request, generatorYandexGptRequest::generateBiography);
    }

    @RabbitListener(queues = "yandexgpt-epitaph-queue")
    public Mono<Map> epitaphCreateConsumer(ProducerRequest request) {
        return sendRequestToYandexGpt(request, generatorYandexGptRequest::generateEpitaph);
    }


    private Mono<Map> sendRequestToYandexGpt(ProducerRequest request, Function<ProducerRequest, CompletableFuture<YandexGptRequest>> generatorFunction) {
        return webClient.post()
                .uri(URI.create("https://llm.api.cloud.yandex.net/foundationModels/v1/completionAsync"))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "x-folder-id: " + key)
                .body(Mono.fromFuture(generatorFunction.apply(request)), YandexGptRequest.class)
                .retrieve().onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new YandexGptGenerateException("Error: " + clientResponse.statusCode() + " - " + clientResponse.bodyToMono(String.class).block())))
                .bodyToMono(Map.class)
                .onErrorReturn(Map.of("status", "400", "message", "YandexGpt response 4xx or 5xx"));
    }
}
