package ru.memorycode.userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import ru.memorycode.userservice.dto.auth.RegistrationUserEntityDto;
import ru.memorycode.userservice.model.auth.RegistrationUserEntity;
import ru.memorycode.userservice.service.RegistrationService;
import ru.memorycode.userservice.service.UserService;
import ru.memorycode.userservice.util.constant.RequestConstant;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

@Service
@Slf4j
public class DefaultRegistrationService implements RegistrationService {


    private ModelMapper mapper;


    private ObjectMapper objectMapper;

    @Deprecated()
    @Override
    public ResponseEntity<HttpStatus> registerUser(RegistrationUserEntityDto registrationUserEntityDto) throws IOException, InterruptedException {
        RegistrationUserEntity entity = mapper.map(registrationUserEntityDto, RegistrationUserEntity.class);
        entity.setPassword(passwordGenerate());
        entity.setPassword_confirmation(entity.getPassword());
        entity.setChecked(true);
        entity.setSendPassword(true);
        entity.setLogin("");
        int statusCode = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("https://mc.dev.rand.agency/api/register"))
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(entity)))
                .build(), HttpResponse.BodyHandlers.ofString()).statusCode();
        log.info(String.valueOf(statusCode));
        return statusCode == 201?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


    private String passwordGenerate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append((char) (new Random().nextInt(26) + 97));
        }
        return stringBuilder.toString();
    }
}
