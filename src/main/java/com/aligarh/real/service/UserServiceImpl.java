package com.aligarh.real.service;

import com.aligarh.real.dao.UserDao;
import com.aligarh.real.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void addUser(User user) {
        if (null != user) {
            userDao.addUser(user);
        } else {
            System.err.println("Unable to add null user.");
        }
    }

    @Override
    public void removeUser(User user) {
        if (null != user) {
            userDao.removeUser(user);
        }
        else{
            System.err.println("Unable to remove null user.");
        }
    }

}
