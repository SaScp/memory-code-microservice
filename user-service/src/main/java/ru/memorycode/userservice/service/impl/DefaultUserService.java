package ru.memorycode.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;

import ru.memorycode.userservice.model.User;
import ru.memorycode.userservice.repository.UserRepository;
import ru.memorycode.userservice.service.UserService;
import ru.memorycode.userservice.service.update.UpdateTelegramUserStrategy;
import ru.memorycode.userservice.util.exception.TelegramUserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    private List<UpdateTelegramUserStrategy> userStrategies;

    public Boolean save(TelegramUserDto entity) {
        if (entity == null || userRepository.findByUserId(entity.getUserId()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUserId(entity.getUserId());
        user.setLangCode(entity.getLangCode());
        user.setLastActivity(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setFirstLangSet(false);
        userRepository.save(user);
        return true;
    }

    @Override
    public Mono<User> getUserByUserId(Long userId) {
        return Mono.just(userRepository.findByUserId(userId).orElseThrow(() ->
                new TelegramUserNotFoundException("User not found")));
    }

    @Override
    public User update(TelegramUserDto entity) {
        User user = userRepository.findByUserId(entity.getUserId()).orElseThrow(() ->
                new TelegramUserNotFoundException("User not found"));
        userStrategies.forEach(strategy -> strategy.execute(entity, user));
        user.setLastActivity(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }


}
