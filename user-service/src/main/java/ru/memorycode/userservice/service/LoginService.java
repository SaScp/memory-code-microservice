package ru.memorycode.userservice.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface LoginService {

    Mono<Map> login(String email, String password);
}
