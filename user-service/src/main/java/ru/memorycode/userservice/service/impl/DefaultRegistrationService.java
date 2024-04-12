package ru.memorycode.userservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.auth.RegistrationUserEntityDto;
import ru.memorycode.userservice.model.auth.RegistrationUserEntity;
import ru.memorycode.userservice.service.RegistrationService;
import ru.memorycode.userservice.service.UserService;
import ru.memorycode.userservice.util.constant.RequestConstant;

import java.net.URI;
import java.util.Random;

@Service
@Slf4j
public class DefaultRegistrationService implements RegistrationService {

    private WebClient webClient;
    private ModelMapper mapper;

    private UserService userService;

    public DefaultRegistrationService(WebClient.Builder builder, ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.webClient = builder.build();
        this.userService = userService;
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
                    if (responseA.statusCode().is2xxSuccessful()) {
                        log.info(responseA.statusCode().toString());
                        return Mono.just(ResponseEntity.created(URI.create(RequestConstant.REGISTRATION)).build());
                    } else {
                        log.error(responseA.statusCode().toString());
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
