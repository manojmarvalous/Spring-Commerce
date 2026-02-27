package com.techouts.service;

import com.techouts.model.Product;
import com.techouts.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product update(Product product) {
        return productRepo.update(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findActive() {
        return productRepo.findActive();
    }

    @Transactional(readOnly = true)
    public List<Product> findByCategory(String category) {
        return productRepo.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> search(String keyword) {
        return productRepo.search(keyword);
    }

    public void delete(Long id) {
        productRepo.delete(id);
    }

    public long count() {
        return productRepo.count();
    }

    public void reduceStock(Long productId, int quantity) {
        productRepo.findById(productId).ifPresent(product -> {
            if (product.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }
            product.setStock(product.getStock() - quantity);
            productRepo.update(product);
        });
    }
}

