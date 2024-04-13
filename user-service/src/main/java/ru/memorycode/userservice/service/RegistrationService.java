package ru.memorycode.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.auth.RegistrationUserEntityDto;

import java.io.IOException;

public interface RegistrationService {
    ResponseEntity<HttpStatus> registerUser(RegistrationUserEntityDto registrationUserEntityDto) throws IOException, InterruptedException;
}
