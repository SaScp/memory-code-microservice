package ru.memorycode.yandexgptservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.memorycode.yandexgptservice.generator.GeneratorYandexGptRequest;
import ru.memorycode.yandexgptservice.model.Message;
import ru.memorycode.yandexgptservice.model.ProducerRequest;
import ru.memorycode.yandexgptservice.model.YandexGptRequest;
import ru.memorycode.yandexgptservice.model.response.YandexGptResponse;
import ru.memorycode.yandexgptservice.util.exception.YandexGptGenerateException;
import ru.memorycode.yandexgptservice.util.exception.YandexGptProcessingException;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
@Slf4j
public class YandexGptService {

    private final WebClient webClient;

    private GeneratorYandexGptRequest generatorYandexGptRequest;

    private String key;



    public YandexGptService(WebClient.Builder builder, GeneratorYandexGptRequest generatorYandexGptRequest, @Value("${yandex.gpt.api.key}") String key) {
        this.webClient = builder.build();
        this.generatorYandexGptRequest = generatorYandexGptRequest;
        this.key = key;
    }

    public Mono<Map> questionCreateConsumer(ProducerRequest request) {
        return sendRequestToYandexGpt(request, generatorYandexGptRequest::generateQuestion);
    }

    public Mono<Map> biographyCreateConsumer(ProducerRequest request) {
        return sendRequestToYandexGpt(request, generatorYandexGptRequest::generateBiography);
    }

    public Mono<Map> epitaphCreateConsumer(ProducerRequest request) {
        return sendRequestToYandexGpt(request, generatorYandexGptRequest::generateEpitaph);
    }

    public Mono<Map> sendRequestToYandexGpt(ProducerRequest request, Function<ProducerRequest, CompletableFuture<YandexGptRequest>> generatorFunction) {
        return webClient.post()
                .uri(URI.create("https://llm.api.cloud.yandex.net/foundationModels/v1/completionAsync"))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Api-Key " + key)
                .body(Mono.fromFuture(generatorFunction.apply(request)), YandexGptRequest.class)
                .retrieve().onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse ->
                                Mono.error(new YandexGptGenerateException("Error: " + clientResponse.statusCode() + " - " + clientResponse.bodyToMono(String.class).block())))
                .bodyToMono(Map.class)
                .onErrorReturn(Map.of("status", "400", "message", "YandexGpt response 4xx or 5xx"))
                .handle((map, sink) -> {
                    log.info("YandexGpt response: " + map);
                    if (Optional.ofNullable(map).isEmpty() || !map.containsKey("id")) {
                        sink.error(new YandexGptGenerateException("map is empty or doesn't contain key id"));
                        return;
                    }
                    sink.next(Map.of("id", map.get("id")));
                });
    }

    public Mono<Message> getData(String id) {
        return webClient.get()
                .uri(URI.create("https://operation.api.cloud.yandex.net/operations/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Api-Key " + key)
                .retrieve().onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse ->
                                Mono.error(new YandexGptGenerateException("error")))
                .bodyToMono(YandexGptResponse.class)
                .onErrorMap(throwable -> new YandexGptGenerateException("error"))
                .handle((map, sink) -> {
                    log.info("YandexGpt response: " + map);
                    if (Optional.ofNullable(map).isPresent() && map.getDone().equals(true))
                        sink.next(map.getResponse().getAlternatives().get(0).getMessage());
                    else
                        sink.error(new YandexGptProcessingException());
                });
    }

}


