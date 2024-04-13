package ru.memorycode.userservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.model.auth.LoginUserEntity;
import ru.memorycode.userservice.service.LoginService;
import ru.memorycode.userservice.util.constant.RequestConstant;
import ru.memorycode.userservice.util.exception.UserNotFoundException;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class DefaultLoginService implements LoginService {

    private WebClient webClient;


    public DefaultLoginService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public Mono<Map> login(String email, String password) {
        return webClient.post()
                .uri(URI.create(RequestConstant.GET_ACCESS_TOKEN))
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new LoginUserEntity(email, password, RequestConstant.DEVICE)), LoginUserEntity.class)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new UserNotFoundException("User not found")))
                .bodyToMono(Map.class)
                .handle((a, sink) -> {
                    if (Optional.ofNullable(a.get("access_token")).isPresent()) {
                        sink.next(a);
                    } else {
                        sink.error(new UserNotFoundException("User not found"));
                    }
                });
    }
}
