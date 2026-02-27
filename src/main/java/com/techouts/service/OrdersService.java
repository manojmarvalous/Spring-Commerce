package com.techouts.service;

import com.techouts.model.Cart;
import com.techouts.model.CartProducts;
import com.techouts.model.Orders;
import com.techouts.model.ProductDetails;
import com.techouts.model.User;
import com.techouts.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrdersService {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private CartService cartService;

    @Transactional
    public void placeOrder(User user, Orders order) {


        Cart cart = cartService.getCart(user);
        List<CartProducts> cartProducts = cartService.getCartProducts(cart);

        if (cartProducts == null || cartProducts.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot place order.");
        }

        List<ProductDetails> detailsList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;


        for (CartProducts cp : cartProducts) {
            ProductDetails details = new ProductDetails();
            details.setName(cp.getProduct().getName());
            details.setQuantity(cp.getQuantity());
            details.setPrice(cp.getProduct().getPrice());

            // totalPrice is automatically calculated in ProductDetails
            details.setOrder(order);

            detailsList.add(details);

            totalAmount = totalAmount.add(cp.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cp.getQuantity())));
        }

        // Set order info
        order.setProductDetailsList(detailsList);
        order.setUser(user);
        order.setTotalAmount(totalAmount);

        // Save the order first
        ordersRepo.saveOrder(order);

        // Remove cart products AFTER saving order
        // Detach from collections to avoid cascade issues
        for (CartProducts cp : cartProducts) {
            if (cp.getCart() != null && cp.getCart().getCartProducts() != null) {
                cp.getCart().getCartProducts().remove(cp);
            }
            cartService.removeFromCart(cp.getId());
        }
    }

    public List<Orders> getOrdersByUser(User user) {
        return ordersRepo.findByUserId(user.getId());
    }

    public List<Orders> getAllOrders() {
        return ordersRepo.findAllOrders();
    }
}