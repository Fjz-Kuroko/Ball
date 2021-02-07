package com.ball.service;

import com.ball.entity.User;

public interface UserService {
    int register(User user);
    User login(User user);
    User selectUserByEmail(String email);
}
