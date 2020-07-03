package com.skafenko.core.repository;

import com.skafenko.core.model.User;

public interface UserRepository {
    User findByEmail(String email);

    User findById(String id);

    User saveUser(User user);
}
