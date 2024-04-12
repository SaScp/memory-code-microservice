package ru.memorycode.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.EnableWebFlux;

import ru.memorycode.userservice.model.auth.LoginUserEntity;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@EnableWebFlux
public class UserServiceApplication  {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
