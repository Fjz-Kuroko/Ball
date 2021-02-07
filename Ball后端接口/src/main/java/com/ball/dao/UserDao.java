package com.ball.dao;

import com.ball.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("userDao")
@Mapper
public interface UserDao {
    int register(User user);
    User selectUserByEmail(String email);
    User login(User user);
}
