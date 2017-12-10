package com.aligarh.real.dao;

import com.aligarh.real.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findAll() {
        Session session = sessionFactory.openSession();
        List<Product> products = session.createCriteria(Product.class).list();

        session.close();
        return products;
    }

    @Override
    public Product findById(Long id) {
        Session session = sessionFactory.openSession();
        Product product = session.get(Product.class,id);
        session.close();
        return product;
    }

    @Override
    public void save(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(product);
        session.getTransaction().commit();
        session.close();
    }
}
