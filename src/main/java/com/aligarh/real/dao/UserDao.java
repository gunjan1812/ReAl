package com.aligarh.real.dao;

import com.aligarh.real.model.Product;
import com.aligarh.real.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers();
    User findById(Long id);
    void addUser(User username);
    void removeUser(User username);
}
