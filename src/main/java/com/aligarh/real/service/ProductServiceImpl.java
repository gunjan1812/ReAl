package com.aligarh.real.service;

import com.aligarh.real.dao.ProductDao;
import com.aligarh.real.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public void save(Product product, MultipartFile file) {
        try {
            product.setBytes(file.getBytes());
            productDao.save(product);
        } catch (IOException e) {
            System.err.println("Unable to get byte array from uploaded file.");
        }
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public void toggleFavorite(Product product) {
        if(null != product) {
            product.setFavorite(!product.isFavorite());
            productDao.save(product);
        }
        else{
            System.err.println("Unable to set Favorite on an emoty Product.");
        }
    }
}
