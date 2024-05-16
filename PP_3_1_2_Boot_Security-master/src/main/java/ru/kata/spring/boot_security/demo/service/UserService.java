package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getOneUser(long id);

    User getOneThisUser();

    void save(User user);

    void update(User user);

    void delete(long id);


}
