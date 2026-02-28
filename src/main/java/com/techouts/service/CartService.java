package com.techouts.service;

import com.techouts.model.Cart;
import com.techouts.model.CartProducts;
import com.techouts.model.Product;
import com.techouts.model.User;
import com.techouts.repository.CartRepo;
import com.techouts.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    // Add product to cart
    public void addToCart(User user, Product product) {
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepo.saveCart(cart);

            user.setCart(cart);
            userRepo.update(user);
        }

        Optional<CartProducts> existingOpt = cartRepo.findCartProduct(cart, product);

        if (existingOpt.isPresent()) {
            CartProducts cp = existingOpt.get();
            cp.setQuantity(cp.getQuantity() + 1);
            cartRepo.updateCartProduct(cp);
        } else {
            CartProducts cp = new CartProducts();
            cp.setCart(cart);
            cp.setProduct(product);
            cp.setQuantity(1);
            cartRepo.saveCartProduct(cp);
        }
    }

    // Get user's cart
    public Cart getCart(User user) {
        return cartRepo.getCartByUser(user);
    }

    // Remove product from cart
    public void removeFromCart(Long cartProductId) {
        cartRepo.removeCartProduct(cartProductId);
    }

    // Update quantity
    public void updateQuantity(Long cartProductId, int quantity) {
        Optional<CartProducts> cpOpt = cartRepo.getCartProductsById(cartProductId);
        cpOpt.ifPresent(cp -> {
            cp.setQuantity(quantity);
            cartRepo.updateCartProduct(cp);
        });
    }


    public int getCartProductCount(User user) {
        return cartRepo.getCartProductCount(user);
    }

    // Get all products in a cart
    public List<CartProducts> getCartProducts(Cart cart) {
        return cartRepo.getCartProducts(cart);
    }

    public BigDecimal calculateTotal(List<CartProducts> cartProducts) {

        BigDecimal total = BigDecimal.ZERO;

        if (cartProducts != null) {
            for (CartProducts cp : cartProducts) {

                BigDecimal price = cp.getProduct().getPrice();
                BigDecimal quantity =
                        BigDecimal.valueOf(cp.getQuantity());

                BigDecimal itemTotal = price.multiply(quantity);

                total = total.add(itemTotal);
            }
        }
        return total;
    }
}