package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.dto.Validate;
import com.ball.dto.ValidateMessage;
import com.ball.entity.Cart;
import com.ball.service.CartService;
import com.ball.utils.JWTUtil;
import com.ball.vo.ViewCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseData add(Cart cart, HttpServletRequest request) {
        System.out.println(cart);
        ResponseData responseData;
        // 验证添加购物车的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(cart.getEmail(), request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        // 表单验证
        ValidateMessage validate = Validate.validate(cart);
        if (!validate.getCode().equals("200")) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", validate.getMsg());
            return responseData;
        }
        int i = cartService.addCart(cart);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "邮箱不存在！");
            return responseData;
        }
        if (i == -2) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "商品不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @PostMapping("/update")
    public ResponseData update(Cart cart, HttpServletRequest request) {
        ResponseData responseData;
        // 验证添加购物车的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(cart.getEmail(), request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = cartService.updateCart(cart);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "购物车不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @PostMapping("/del")
    public ResponseData del(int cid, String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证添加购物车的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = cartService.deleteCart(cid);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "购物车不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @RequestMapping("/getCarts")
    public ResponseData getCarts(String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证购物车的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        List<ViewCart> viewCarts = cartService.selectByEmail(email);
        responseData = ResponseData.ok();
        responseData.putDataValue("viewCarts", viewCarts);
        return responseData;
    }

    @RequestMapping("/clearCart")
    public ResponseData clearCart(String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证购物车的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = cartService.clearAll(email);
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

}
