package com.techouts.service;

import com.techouts.model.User;
import com.techouts.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public User update(User user) {
        return userRepo.update(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }

    public User getProfileDetails(String email) {
        return userRepo.findUserWithDetails(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateBasicDetails(String currentEmail, String newName, String newEmail) {

        User user = userRepo.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(newName);
        user.setEmail(newEmail);

        userRepo.update(user);
    }
}
