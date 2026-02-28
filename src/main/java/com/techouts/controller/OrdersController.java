package com.techouts.controller;

import com.techouts.model.Orders;
import com.techouts.model.User;
import com.techouts.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // Place the order
    @PostMapping
    public String placeOrder(@ModelAttribute Orders order, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        order.setUser(user);
        order.setName(user.getName());
        order.setEmail(user.getEmail());
        order.setPhoneNumber(user.getPhone());

        ordersService.placeOrder(user, order);

        // Optionally, you can add order summary to session or model
        session.setAttribute("lastOrder", order);

        return "redirect:/orders/confirmation";
    }

    // Order confirmation page
    @GetMapping("/confirmation")
    public String orderConfirmation(HttpSession session, Model model) {
        Orders order = (Orders) session.getAttribute("lastOrder");
        if (order == null) {
            return "redirect:/products";
        }

        model.addAttribute("order", order);
        return "order-confirmation"; // JSP page name
    }

    // Show all orders for logged-in user
    @GetMapping("/myorders")
    public String viewMyOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Orders> ordersList = ordersService.getOrdersByUser(user);
        model.addAttribute("ordersList", ordersList);
        return "myorders";
    }
}