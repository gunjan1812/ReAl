package com.aligarh.real.dao;

import com.aligarh.real.model.Product;
import com.aligarh.real.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> findAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createCriteria(User.class).list();
        session.close();
        return users;
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class,id);
        session.close();
        return user;
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
