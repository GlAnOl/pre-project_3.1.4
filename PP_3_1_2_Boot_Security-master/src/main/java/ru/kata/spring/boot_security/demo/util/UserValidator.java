package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.Optional;


@Component
public class UserValidator implements Validator {


    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Optional<User> user_o = Optional.ofNullable(userRepository.findByName(user.getEmail()));

        if (user_o.isPresent()) {
            errors.rejectValue("email", "", "Человек с таким email уже сущетствует");
        }

    }
}
