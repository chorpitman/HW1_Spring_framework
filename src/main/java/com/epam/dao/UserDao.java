package com.epam.dao;

import com.epam.model.User;

import java.util.List;

public interface UserDao {
    User createUser(User user);

    User getUserById(long userId);

    User getUserByEmail(String email);

    User update(User user);

    boolean deleteUser(long userId);

    List<User> getUsersByName(String name, int pageSize, int pageNum);
}
