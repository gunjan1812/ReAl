package com.aligarh.real.dao;

import com.aligarh.real.model.Like;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeDaoImpl implements LikeDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Like> findAllLikers() {
        Session session = sessionFactory.openSession();
        List<Like> likers = session.createCriteria(Like.class).list();
        session.close();
        return likers;
    }

    @Override
    public void addLiker(Like user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeLiker(Like user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
