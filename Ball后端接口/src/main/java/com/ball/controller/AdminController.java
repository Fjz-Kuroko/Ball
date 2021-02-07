package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.dto.Validate;
import com.ball.dto.ValidateMessage;
import com.ball.entity.Admin;
import com.ball.service.AdminService;
import com.ball.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("adminController")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseData login(Admin admin) {
        ResponseData responseData = ResponseData.ok();
        ValidateMessage validate = Validate.validate(admin);
        if (!(validate.getCode().equals("200"))) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", validate.getMsg());
            return responseData;
        }
        Admin login = adminService.login(admin);
        if (login == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "用户名或密码错误！");
            return responseData;
        }
        // 生成token
        String token = JWTUtil.generateToken(login.getAdminName(), "BallShopIssuer", login.getAdminPwd());
        login.setAdminPwd("******");
        responseData.putDataValue("token", token);
        responseData.putDataValue("admin", login);
        return responseData;
    }

    @RequestMapping("/index")
    public ResponseData index(String adminName, HttpServletRequest request) {
        ResponseData responseData = ResponseData.ok();
        // 验证添加地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        return responseData;
    }

}
