package ru.memorycode.yandexgptservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.memorycode.yandexgptservice.service.YandexGptService;
import ru.memorycode.yandexgptservice.model.Message;
import ru.memorycode.yandexgptservice.model.ProducerRequest;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", value = "*")
@Tag(name = "Yandex GPT Service", description = "Yandex GPT Service")
public class YandexController {

    private final YandexGptService yandexGptService;

    public YandexController(YandexGptService yandexGptService) {
        this.yandexGptService = yandexGptService;
    }

    @PostMapping(value = "/question", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Question biography",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProducerRequest.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Question generated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    )
    public Mono<Map> generateQuestion(@RequestBody ProducerRequest producerRequest) {
        return yandexGptService.questionCreateConsumer(producerRequest);
    }

    @PostMapping(value = "/epitaph", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate epitaph",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProducerRequest.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Epitaph generated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    )
    public Mono<Map> generateEpitaph(@RequestBody ProducerRequest producerRequest) {
        return yandexGptService.epitaphCreateConsumer(producerRequest);
    }

    @PostMapping(value = "/biography", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate biography",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProducerRequest.class))
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Biography generated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    )
    public Mono<Map> generateBiography(@RequestBody ProducerRequest producerRequest) {
        return yandexGptService.biographyCreateConsumer(producerRequest);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "get data by id",
            parameters = @Parameter(
                    name = "id",
                    description = "id",
                    required = true,
                    schema = @Schema(type = "string")
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "get data by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    )
    public Mono<Message> getData(@PathVariable String id) {
        return yandexGptService.getData(id);
    }
}
