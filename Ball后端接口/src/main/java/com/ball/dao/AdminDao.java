package com.ball.dao;

import com.ball.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("adminDao")
@Mapper
public interface AdminDao {
    Admin login(Admin admin);
}
