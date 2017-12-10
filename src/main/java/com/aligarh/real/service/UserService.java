package com.aligarh.real.service;

import com.aligarh.real.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findById(Long id);
    void addUser(User user);
    void removeUser(User user);
}
