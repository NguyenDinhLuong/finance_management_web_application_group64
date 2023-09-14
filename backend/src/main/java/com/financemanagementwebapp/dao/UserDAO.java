package com.financemanagementwebapp.dao;

import com.financemanagementwebapp.entity.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    User getUser(int userId);
    List<User> getAllUser();
    void updateUser(User newUser);
    void deleteUser(int userId);
}
