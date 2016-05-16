package com.epam.service;

import com.epam.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(long userId);

    User getUserByEmail(String email);

    User updateUser(User user);

    boolean deleteUser(long userId);

    List<User> getUsersByName(String name, int pageSize, int pageNum);
}
