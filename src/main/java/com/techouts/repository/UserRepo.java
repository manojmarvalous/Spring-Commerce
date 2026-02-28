package com.techouts.repository;

import com.techouts.model.User;

import java.util.Optional;

public interface UserRepo {

    public void save(User user);

    User update(User user);

    public Optional<User> findByPhone(String userPhone);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserWithDetails(String email);


}
