package ru.memorycode.yandexgptservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YandexgptServiceApplication {


    public static void main(String[] args) {
		SpringApplication.run(YandexgptServiceApplication.class, args);
	}

}
