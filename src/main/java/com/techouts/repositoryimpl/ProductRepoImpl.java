package com.techouts.repositoryimpl;

import com.techouts.model.Product;
import com.techouts.repository.ProductRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepoImpl implements ProductRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product save(Product product) {
        sessionFactory.getCurrentSession().persist(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        return (Product) sessionFactory.getCurrentSession().merge(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Product p ORDER BY p.createdAt DESC", Product.class)
                .list();
    }

    @Override
    public List<Product> findActive() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Product p LEFT JOIN FETCH p.category WHERE p.active = true ORDER BY p.createdAt DESC", Product.class)
                .list();
    }

    @Override
    public List<Product> findByCategory(String category) {
        return sessionFactory.getCurrentSession()
                .createQuery(
                        "FROM Product WHERE category = :category AND active = true ORDER BY createdAt DESC",
                        Product.class)
                .setParameter("category", category)
                .list();
    }



    @Override
    public List<Product> search(String keyword) {
        String pattern = "%" + keyword.toLowerCase() + "%";
        return sessionFactory.getCurrentSession().
        createQuery(
                "FROM Product WHERE LOWER(name) LIKE :keyword AND active = true ORDER BY createdAt DESC", Product.class)
        .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
        .list();
    }

    @Override
    public void delete(Long id) {
        Product product = sessionFactory.getCurrentSession().get(Product.class, id);
        if (product != null) {
            product.setActive(false);
            sessionFactory.getCurrentSession().merge(product);
        }
    }

    @Override
    public long count() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(p) FROM Product p WHERE p.active = true", Long.class)
                .uniqueResult();
    }
}
