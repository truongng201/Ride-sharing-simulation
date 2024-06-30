package me.truongng.journeymapapi.repository;

import java.util.List;

import me.truongng.journeymapapi.models.User;

public interface UserRepositoryInterface {
    boolean create(User user);

    boolean update(User user);

    List<User> findById(String id);

    boolean deleteById(String id);

    List<User> findByEmail(String email);

    List<User> findAll();

}