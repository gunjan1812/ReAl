package com.aligarh.real.dao;

import com.aligarh.real.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product);
    void delete(Product product);
}
