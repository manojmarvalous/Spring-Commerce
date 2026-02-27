package com.techouts.repository;

import com.techouts.model.Cart;
import com.techouts.model.CartProducts;
import com.techouts.model.Product;
import com.techouts.model.User;

import java.util.List;
import java.util.Optional;

public interface CartRepo {

    void saveCart(Cart cart);

    void saveCartProduct(CartProducts cartProducts);

    void updateCartProduct(CartProducts cartProducts);

    Optional<CartProducts> findCartProduct(Cart cart, Product product);

    Cart getCartByUser(User user);

    void removeCartProduct(Long cartProductId);

    List<CartProducts> getCartProducts(Cart cart);
    Optional<CartProducts> getCartProductsById(Long cartProductId);
}