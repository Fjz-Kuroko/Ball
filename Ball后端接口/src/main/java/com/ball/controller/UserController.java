package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.dto.Validate;
import com.ball.dto.ValidateMessage;
import com.ball.entity.User;
import com.ball.service.UserService;
import com.ball.utils.EhcacheUtil;
import com.ball.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseData register(User user, String code) {
        ResponseData responseData = ResponseData.ok();
        String emailCode = EhcacheUtil.getEmailCode(user.getEmail());
        if (emailCode == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "邮箱验证码已过期，请重新获取.");
            return responseData;
        } else if (!emailCode.equals(code)) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "邮箱验证码错误！");
            return responseData;
        }
        // 验证表单信息
        ValidateMessage validate = Validate.validate(user);
        if (!(validate.getCode().equals("200"))) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", validate.getMsg());
            return responseData;
        }
        int i = userService.register(user);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "该邮箱已经存在！");
        }
        if (i > 0) {
            responseData.putDataValue("msg", "注册成功");
            return responseData;
        }
        responseData = ResponseData.serverInternalError();
        responseData.putDataValue("msg", "注册失败");
        return responseData;
    }

    @PostMapping("/login")
    public ResponseData login(User user, String uuid, String checkCode) {
        ResponseData responseData = ResponseData.ok();
        String captchaCode = EhcacheUtil.getCaptchaCode(uuid);
        if (captchaCode == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "验证码已过期");
            return responseData;
        } else if (!captchaCode.equals(checkCode.toLowerCase())){
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "验证码错误");
            return responseData;
        }
        // 验证表单信息
        ValidateMessage validate = Validate.validate(user);
        if ((validate.getCode().equals("401")) || (validate.getCode().equals("402"))) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", validate.getMsg());
            return responseData;
        }
        User login = userService.login(user);
        if (login == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "邮箱或密码错误");
            return responseData;
        }
        // 生成token
        String token = JWTUtil.generateToken(login.getEmail(), "BallShopIssuer", login.getUname());
        login.setUpwd("******");
        responseData.putDataValue("token", token);
        responseData.putDataValue("user", login);
        return responseData;
    }

    @PostMapping("/getUser")
    public ResponseData getUser(String email) {
        ResponseData responseData = ResponseData.ok();
        if (email == null || "".equals(email)) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "邮箱不能为空!");
            return responseData;
        }
        User user = userService.selectUserByEmail(email);
        responseData.putDataValue("user", user);
        return responseData;
    }

}
