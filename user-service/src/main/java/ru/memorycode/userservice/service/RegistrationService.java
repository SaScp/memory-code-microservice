package ru.memorycode.userservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.auth.RegistrationUserEntityDto;

public interface RegistrationService {
    Mono<ResponseEntity<HttpStatus>> registerUser(RegistrationUserEntityDto registrationUserEntityDto);
}
