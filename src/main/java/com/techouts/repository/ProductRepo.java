package com.techouts.repository;

import com.techouts.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepo {

    Product save(Product product);
    Product update(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findActive();
    List<Product> findByCategory(String category);
    List<Product> search(String keyword);
    void delete(Long id);
    long count();


}
