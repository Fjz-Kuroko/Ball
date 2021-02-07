package com.ball.service;

import com.ball.dao.UserDao;
import com.ball.entity.User;
import com.ball.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int register(User user) {
        User selectUserByEmail = userDao.selectUserByEmail(user.getEmail());
        if (selectUserByEmail != null) {
            return -1;
        }
        user.setUpwd(MD5Util.getMD5Pwd(user.getUpwd(), user.getEmail()));
        return userDao.register(user);
    }

    @Override
    public User login(User user) {
        user.setUpwd(MD5Util.getMD5Pwd(user.getUpwd(), user.getEmail()));
        return userDao.login(user);
    }

    @Override
    public User selectUserByEmail(String email) {
        User user = userDao.selectUserByEmail(email);
        user.setUpwd("******");
        return user;
    }
}
