package com.techouts.controller;

import com.techouts.model.User;
import com.techouts.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @GetMapping({"/","/index"})
    public String getLogin() {
        return "forward:/products";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping
    public String loginUser() {
        return "redirect:/products";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isEmpty()) {
            // No such user
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }

        User user = userOpt.get();
        // Check password
        if (!user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
        // Login successful
        session.setAttribute("user", user);

        return "redirect:/products";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,
                         RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message",
                "You have been logged out successfully.");
        return "redirect:/login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               HttpSession session,RedirectAttributes redirectAttributes) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        Optional<User> userByPhone  = userService.findByPhone(user.getPhone());
        if (existingUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            return "redirect:/register";
        }

        if (userByPhone.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Phone number already exists");
            return "redirect:/register";
        }

        // Save user into DB
        userService.save(user);

        // Create session
        session.setAttribute("user", user);

        // Redirect to products page
        return "redirect:/products";
    }

}
