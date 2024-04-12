package ru.memorycode.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.memorycode.userservice.dto.telegram.TelegramUserDto;
import ru.memorycode.userservice.model.auth.TelegramUserEntity;
import ru.memorycode.userservice.model.User;
import ru.memorycode.userservice.repository.UserRepository;
import ru.memorycode.userservice.service.UserService;
import ru.memorycode.userservice.util.exception.TelegramUserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private UserRepository userRepository;
    private List<Validator> validators;


    public Boolean save(TelegramUserEntity entity) {
        if (entity == null || userRepository.findByUserId(entity.getUserId()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUserId(entity.getUserId());
        user.setLangCode(entity.getLangCode());
        user.setLastActivity(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() ->
                new TelegramUserNotFoundException("User not found"));
    }

    @Override
    public User update(TelegramUserEntity entity) {
        User user = userRepository.findByUserId(entity.getUserId()).orElseThrow(() ->
                new TelegramUserNotFoundException("User not found"));
        user.setLangCode(entity.getLangCode());
        user.setLastActivity(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }


}
