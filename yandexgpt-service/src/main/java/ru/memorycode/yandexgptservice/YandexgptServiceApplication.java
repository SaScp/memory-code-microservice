package ru.memorycode.yandexgptservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class YandexgptServiceApplication {

    private final Environment environment;

    public YandexgptServiceApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(YandexgptServiceApplication.class, args);
    }



    @Bean
    public DirectExchange yandexgptQuestionExchange() {
        return new DirectExchange("question-exchange");
    }

    @Bean
    public DirectExchange yandexgptBiographyExchange() {
        return new DirectExchange("biography-exchange");
    }

    @Bean
    public DirectExchange yandexgptEpitaphExchange() {
        return new DirectExchange("epitaph-exchange");
    }

    @Bean
    public Queue questionQueue() {
        return new Queue("yandexgpt-question-queue");
    }

    @Bean
    public Queue biographyQueue() {
        return new Queue("yandexgpt-biography-queue");
    }

    @Bean
    public Queue epitaphQueue() {
        return new Queue("yandexgpt-epitaph-queue");
    }

    @Bean
    public Binding bindingQuestion() {
        return BindingBuilder.bind(questionQueue()).to(yandexgptQuestionExchange()).with("yandexgpt-question-queue");
    }

    @Bean
    public Binding bindingBiography() {
        return BindingBuilder.bind(biographyQueue()).to(yandexgptBiographyExchange()).with("yandexgpt-biography-queue");
    }

    @Bean
    public Binding bindingEpitaph() {
        return BindingBuilder.bind(epitaphQueue()).to(yandexgptEpitaphExchange()).with("yandexgpt-epitaph-queue");
    }

}
