package com.aligarh.real.dao;

import com.aligarh.real.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(long id);
    void save(Category category);
    void delete(Category category);
}
