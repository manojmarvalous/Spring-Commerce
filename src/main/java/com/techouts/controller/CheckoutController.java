package com.techouts.controller;

import com.techouts.model.Cart;
import com.techouts.model.CartProducts;
import com.techouts.model.User;
import com.techouts.repository.CartRepo;
import com.techouts.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    CartService cartService;
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCart(user);

        if (cart == null) {
            return "redirect:/cart/view";
        }

        List<CartProducts> cartProducts = cartService.getCartProducts(cart);

        BigDecimal total = cartService.calculateTotal(cartProducts);

        model.addAttribute("user", user);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("total", total);

        return "checkout";
    }

}
