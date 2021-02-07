package com.ball.service;

import com.ball.dao.AdminDao;
import com.ball.entity.Admin;
import com.ball.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin login(Admin admin) {
        admin.setAdminPwd(MD5Util.getMD5Pwd(admin.getAdminPwd(), admin.getAdminName()));
        return adminDao.login(admin);
    }
}
