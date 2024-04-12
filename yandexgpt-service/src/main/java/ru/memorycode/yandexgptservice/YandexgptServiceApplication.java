package ru.memorycode.yandexgptservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YandexgptServiceApplication {


    public static void main(String[] args) {
		SpringApplication.run(YandexgptServiceApplication.class, args);
	}

}