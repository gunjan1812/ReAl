package com.aligarh.real.service;

import com.aligarh.real.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product, MultipartFile file);
    void delete(Product product);
    void toggleFavorite(Product product);
}
