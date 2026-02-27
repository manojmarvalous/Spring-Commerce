package com.techouts.repositoryimpl;

import com.techouts.model.Cart;
import com.techouts.model.CartProducts;
import com.techouts.model.Product;
import com.techouts.model.User;
import com.techouts.repository.CartRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepoImpl implements CartRepo {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveCart(Cart cart) {
        getSession().save(cart);
    }

    @Override
    public void saveCartProduct(CartProducts cartProducts) {
        getSession().save(cartProducts);
    }

    @Override
    public void updateCartProduct(CartProducts cartProducts) {
        getSession().update(cartProducts);
    }

    @Override
    public Optional<CartProducts> findCartProduct(Cart cart, Product product) {
        Query<CartProducts> query = getSession().createQuery(
                "FROM CartProducts cp WHERE cp.cart = :cart AND cp.product = :product",
                CartProducts.class
        );
        query.setParameter("cart", cart);
        query.setParameter("product", product);

        return query.uniqueResultOptional();
    }

    @Override
    public Cart getCartByUser(User user) {
        Query<Cart> query = getSession().createQuery(
                "SELECT c FROM Cart c LEFT JOIN FETCH c.cartProducts WHERE c.user = :user",
                Cart.class
        );
        query.setParameter("user", user);

        return query.uniqueResult();
    }

    @Override
    public void removeCartProduct(Long cartProductId) {
        CartProducts cp = getSession().get(CartProducts.class, cartProductId);
        if (cp != null) {
            getSession().remove(cp);
        }
    }

    @Override
    public List<CartProducts> getCartProducts(Cart cart) {
        Query<CartProducts> query = getSession().createQuery(
                "FROM CartProducts cp WHERE cp.cart = :cart",
                CartProducts.class
        );
        query.setParameter("cart", cart);
        return query.list();
    }
    @Override
    public Optional<CartProducts> getCartProductsById(Long cartProductId) {
        CartProducts cp = getSession().get(CartProducts.class, cartProductId);
        return Optional.ofNullable(cp);
    }
}