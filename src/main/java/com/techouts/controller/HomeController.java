package com.techouts.controller;

import com.techouts.model.User;
import com.techouts.service.CartService;
import com.techouts.service.OrdersService;
import com.techouts.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
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
        return "redirect:/products";
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

    @Autowired
    CartService cartService;
    @Autowired
    OrdersService ordersService;
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        int cartCount = cartService.getCartProductCount(user);
        int orderCount = ordersService.getOrderCount(user);

        model.addAttribute("user", user);
        model.addAttribute("cartCount", cartCount);
        model.addAttribute("orderCount", orderCount);

        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phone,
                                @RequestParam("address") String address,
                                @RequestParam("imageFile") MultipartFile file,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) throws IOException {

        User user = (User) session.getAttribute("user");


        if (!user.getEmail().equals(email)) {
            Optional<User> existingEmailUser = userService.findByEmail(email);
            if (existingEmailUser.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Email already exists!");
                return "redirect:/profile";
            }
        }


        if (!user.getPhone().equals(phone)) {
            Optional<User> existingPhoneUser = userService.findByPhone(phone);
            if (existingPhoneUser.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Phone number already exists!");
                return "redirect:/profile";
            }
        }

        // Update values
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);

        // Image upload
        if (!file.isEmpty()) {
            String uploadDir = "C:/uploads/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            file.transferTo(new File(uploadDir + fileName));
            user.setProfileImage(fileName);
        }

        userService.update(user);
        session.setAttribute("user", user);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }

}
