package ru.memorycode.yandexgptservice.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.memorycode.yandexgptservice.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class GeneratorYandexGptRequest {

    private final String indignity_key;

    public GeneratorYandexGptRequest( @Value("${yandex.gpt.api.indignity-key}") String indignityKey) {
        this.indignity_key = indignityKey;
    }

    @Async
    public CompletableFuture<YandexGptRequest> generateEpitaph(ProducerRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            List<Message> messages = new ArrayList<>();
            messages.add(new Message("system", "Вы писатель и пишете эпитафию об умершем человеке. Я даю вам исходные данные. Проанализируйте их и напишите текст"));
            messages.add(new Message("user", "Имя человека - Александр Александрович Иванов. Он родился 21.01.2000 и умер 12.11.2024. Ему нравились такие хобби, как игра в шахматы, чтение книг и плавание. По характеру был холериком. Он был трудолюбив и работал на заводе Светлана"));
            messages.add(new Message("assistant", "Александр Александрович навсегда останется в наших сердцах. Он был добрым, отзывчивым человеком. Верный муж, любящий отец, хороший сын - все это про Александра. Всю жизнь он старался поступать по совести и учил всех не унывать даже в трудных ситуациях. Он и сейчас смотрит на нас, и считает, что все будет хорошо!"));
            messages.add(new Message("user", "Теперь напиши эпитафию о другом человеке. " + generateTextEpitaph(request)));
            return generateRequest(new CompletionOptions(true  ,1, 123123), messages);
        });
    }

    @Async
    public CompletableFuture<YandexGptRequest> generateBiography(ProducerRequest producerRequest) {
        return CompletableFuture.supplyAsync(() -> {
            List<Message> messages = new ArrayList<>();
            messages.add(new Message("system", "Вы писатель и пишете  биографию умершего человека, основываясь на предоставленных данных. В первую очередь основывайся на заголовке биографии."));
            messages.add(new Message("user","Вот данные для биографии умершего " + generateTextForBiography(producerRequest)));
            messages.add(new Message("system", "напиши только биографию, без комментариев"));
            return generateRequest(new CompletionOptions(true  ,1, 123123), messages);
        });
    }
    @Async
    public CompletableFuture<YandexGptRequest> generateQuestion(ProducerRequest producerRequest) {
        return CompletableFuture.supplyAsync(() -> {
            List<Message> messages = new ArrayList<>();
            messages.add(new Message("system", "ты должен сгенерировать 5 вопросов о человеке, который умер. Я дам тебе данные о нем, а ты сгенерируешь новые простые вопросы, не относящиеся к этим данным, но основывающиеся на них. "));
            messages.add(new Message("user", "Вот данные о умершем: " + generateTextForQuestion(producerRequest)));
            messages.add(new Message("system", "напиши только вопросы, без комментариев, разделяя новой строчкой"));
            return generateRequest(new CompletionOptions(true, 1, 123123), messages);
        });
    }

    private YandexGptRequest generateRequest(CompletionOptions completionOptions, List<Message> messages) {
        YandexGptRequest yandexGptRequest = new YandexGptRequest("gpt://b1g5og37bgh1ghh2s2qc/yandexgpt/latest", completionOptions, messages);
        yandexGptRequest.setModelUri("gpt://" + indignity_key + "/yandexgpt/latest");
        yandexGptRequest.setCompletionOptions(completionOptions);
        yandexGptRequest.setMessages(messages);
        return yandexGptRequest;
    }

    private String generateTextEpitaph(ProducerRequest producerRequest) {
        return new StringBuilder().append(producerRequest.getFio()).append(" родился ")
                .append(producerRequest.getDateOfBirth()).append(" и умер ").append(producerRequest.getDateOfdied())
                .append(". ").append("Он родился в ").append(producerRequest.getPlaceOfBirth()).append(" и умер в " + producerRequest.getPlaceOfDied()).append(". ")
                .append("Он работал " + producerRequest.getWork()).append(". ").append("Он получил награды " + producerRequest.getAward()).append(". ").append( "Его любимым хобби было: ")
                .append(producerRequest.getHobby()).append(". также же он ").append(decoderFromMap(producerRequest.getAdd_questions())).toString();
    }


    private String generateTextForBiography(ProducerRequest producerRequest) {
        Biography biography = getBiography(producerRequest);
        return new StringBuilder().append("заголовок биографии ").append(biography.getHeader()).append(producerRequest.getFio()).append(" родился ")
                .append(producerRequest.getDateOfBirth()).append(" и умер ").append(producerRequest.getDateOfdied())
                .append(". ").append("Он родился в ").append(producerRequest.getPlaceOfBirth()).append(" и умер в " + producerRequest.getPlaceOfDied()).append(". ")
                .append("Он работал " + producerRequest.getWork()).append(". ").append("Он получил награды " + producerRequest.getAward()).append(". ").append( "Его любимым хобби было: ")
                .append(producerRequest.getHobby()).append(". также же он ").append(decoderFromMap(producerRequest.getAdd_questions())).toString();
    }


    private Biography getBiography(ProducerRequest producerRequest) {
        if (producerRequest.getBiography_1().getActive()){
            return producerRequest.getBiography_1();
        } else if (producerRequest.getBiography_2().getActive()){
            return producerRequest.getBiography_2();
        } else if (producerRequest.getBiography_3().getActive()){
            return producerRequest.getBiography_3();
        } else {
            return new Biography();
        }
    }
    private String decoderFromMap(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        if (map == null || map.isEmpty()) {
            return "";
        }
        for (var i : map.entrySet()) {
            stringBuilder.append(i.getValue()).append(". ");
        }
        return stringBuilder.toString();
    }
    private String generateTextForQuestion(ProducerRequest producerRequest) {
        return new StringBuilder().append(producerRequest.getFio()).append(" родился ")
                .append(producerRequest.getDateOfBirth()).append(" и умер ").append(producerRequest.getDateOfdied())
                .append(". ").append("Он родился в ").append(producerRequest.getPlaceOfBirth()).append(" и умер в " + producerRequest.getPlaceOfDied()).append(". ")
                .append("Он работал " + producerRequest.getWork()).append(". ").append("Он получил награды " + producerRequest.getAward()).append(". ").append( "Его любимым хобби было: ")
                .append(producerRequest.getHobby()).append(".").toString();
    }
}
