package ru.memorycode.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.memorycode.userservice.dto.auth.LoginUserEntityDto;
import ru.memorycode.userservice.model.User;
import ru.memorycode.userservice.model.UserAuthentication;
import ru.memorycode.userservice.repository.UserAuthenticationRepository;
import ru.memorycode.userservice.repository.UserRepository;
import ru.memorycode.userservice.service.UserAuthenticationService;
import ru.memorycode.userservice.util.exception.TelegramUserNotFoundException;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultUserAuthenticationService implements UserAuthenticationService {

    private UserAuthenticationRepository userAuthenticationRepository;

    private UserRepository userRepository;

    @Override
    public Boolean saveAuthData(LoginUserEntityDto entity) {

        User user = userRepository.findByUserId(entity.getUserId())
                .orElseThrow(() -> new TelegramUserNotFoundException("User not found"));

        UserAuthentication authentication = new UserAuthentication();
        Optional<UserAuthentication> userAuthentication =
                userAuthenticationRepository.findByLogin(entity.getLogin());

        if (userAuthentication.isEmpty()) {
            authentication.setPassword(entity.getPassword());
            authentication.setLogin(entity.getLogin());
        } else {
            authentication = userAuthentication.get();
        }

        authentication.addUser(user);
        user.setUserAuth(authentication);
        userAuthenticationRepository.save(authentication);
        userRepository.save(user);

        log.info("successful save auth data id: {}", authentication.getId());
        return true;
    }

}
