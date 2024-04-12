package ru.memorycode.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.resource.HttpResource;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.RegistrationUserEntityDto;
import ru.memorycode.userservice.model.auth.RegistrationUserEntity;
import ru.memorycode.userservice.service.RegistrationService;
import ru.memorycode.userservice.util.constant.RequestConstant;

import java.net.URI;
import java.util.Map;
import java.util.Random;

@Service
public class DefaultRegistrationService implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(DefaultRegistrationService.class);
    private WebClient webClient;
    private ModelMapper mapper;
    public DefaultRegistrationService(WebClient.Builder builder, ModelMapper mapper) {
        this.mapper = mapper;
        this.webClient = WebClient.builder().build();
    }

    @Override
    public  Mono<ResponseEntity<HttpStatus>>  registerUser(RegistrationUserEntityDto registrationUserEntityDto) {
        RegistrationUserEntity entity = mapper.map(registrationUserEntityDto, RegistrationUserEntity.class);
        entity.setPassword(passwordGenerate());
        entity.setPassword_confirmation(entity.getPassword());
        entity.setChecked(true);
        entity.setSendPassword(true);
        entity.setLogin("");

        Mono<ResponseEntity<HttpStatus>> response =  webClient.post()
                .uri(URI.create(RequestConstant.REGISTRATION))
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(entity), RegistrationUserEntity.class)
                .exchangeToMono(responseA -> {
                    log.info(responseA.statusCode().toString());
                    if (responseA.statusCode().is2xxSuccessful()) {
                        return Mono.just(ResponseEntity.created(URI.create(RequestConstant.REGISTRATION)).build());
                    } else {
                        return Mono.just(ResponseEntity.badRequest().build());
                    }
                });

        return response;
    }

    private String passwordGenerate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append((char) (new Random().nextInt(26) + 97));
        }
        return stringBuilder.toString();
    }
}
