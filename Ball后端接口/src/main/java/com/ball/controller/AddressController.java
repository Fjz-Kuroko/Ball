package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.dto.Validate;
import com.ball.dto.ValidateMessage;
import com.ball.entity.Address;
import com.ball.entity.User;
import com.ball.service.AddressService;
import com.ball.service.UserService;
import com.ball.utils.JWTUtil;
import com.ball.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseData add(Address address, HttpServletRequest request) {
        ResponseData responseData = null;
        // 验证添加地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(address.getEmail(), request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        // 表单验证
        ValidateMessage validate = Validate.validate(address);
        if (!validate.getCode().equals("200")) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", validate.getMsg());
            return responseData;
        }
        // 调用service，进行添加
        int i = addressService.addAddress(address);
        if (i == -1) {
            responseData = ResponseData.notFound();
            responseData.putDataValue("msg", "邮箱不存在！");
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @PostMapping("/del")
    public ResponseData del(int aid, HttpServletRequest request) {
        ResponseData responseData = null;
        Address selectByAid = addressService.selectByAid(aid);
        if (selectByAid == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "地址不存在！");
            return responseData;
        }
        // 验证添加删除地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(selectByAid.getEmail(), request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = addressService.delAddress(aid);
        if (i > 0) {
            responseData = ResponseData.ok();
            return responseData;
        }
        responseData = ResponseData.serverInternalError();
        responseData.putDataValue("msg", "服务器错误！");
        return responseData;
    }

    @PostMapping("/update")
    public ResponseData update(Address address, HttpServletRequest request) {
        ResponseData responseData = null;
        // 验证添加地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(address.getEmail(), request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        if (address.getRecipientPhone() != null && !RegexUtils.isPhone(address.getRecipientPhone())) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "电话号码格式不正确");
            return responseData;
        }
        int i = addressService.update(address);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "地址不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
            return responseData;
        }
        responseData = ResponseData.serverInternalError();
        responseData.putDataValue("msg", "服务器错误！");
        return responseData;
    }

    @GetMapping("/getAllAddress")
    public ResponseData getAllAddress(String email, HttpServletRequest request) {
        ResponseData responseData = null;
        // 验证添加地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        User user = userService.selectUserByEmail(email);
        if (user == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "邮箱不存在！");
            return responseData;
        }
        List<Address> addresses = addressService.selectByEmail(email);
        responseData = ResponseData.ok();
        responseData.putDataValue("addresses", addresses);
        return responseData;
    }

}
