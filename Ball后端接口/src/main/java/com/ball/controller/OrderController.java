package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.entity.Order;
import com.ball.entity.Product;
import com.ball.service.OrderService;
import com.ball.utils.JWTUtil;
import com.ball.vo.ViewCart;
import com.ball.vo.ViewOrder;
import com.ball.vo.ViewShoplist;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submitOrder")
    public ResponseData submitOrder(String viewCartsStr, String email, double orderAmount, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token的email和传来的email是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        // 处理前端传递的数据
        ObjectMapper objectMapper = new ObjectMapper();
        List<ViewCart> viewCarts;
        try {
            viewCarts = objectMapper.readValue(viewCartsStr, new TypeReference<List<ViewCart>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            responseData = ResponseData.serverInternalError();
            responseData.putDataValue("exception", e);
            return responseData;
        }
        Order order = new Order();
        order.setEmail(email);
        order.setOrderAmount(orderAmount);
        String oid = orderService.submitOrder(order, viewCarts);
        if (oid != null) {
            responseData = ResponseData.ok();
            responseData.putDataValue("oid", oid);
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @PostMapping("/payOrder")
    public ResponseData payOrder(Order order, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token的email和传来的email是否一致
        if (!JWTUtil.verifyId(order.getEmail(), request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = orderService.payOrder(order);
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @RequestMapping("/getAllOrders")
    public ResponseData getAllOrders(int pageNum, int pageSize, String oid, String status, String adminName, HttpServletRequest request) {
        ResponseData responseData;
        //1.通过调用 PageHelper 的静态方法开始获取分页数据
        //指定当前第几页以及每页显示的条数
        PageHelper.startPage(pageNum, pageSize);
        // 创建map，添加条件
        Map<String, Object> map = new HashMap<>();
        map.put("oid", oid);
        map.put("status", status);
        // 验证请求头的token中的adminName和操作的adminName是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        responseData = ResponseData.ok();
        //2.获得所有的订单记录
        List<ViewOrder> orders = orderService.getAllOrders(map);
        //3.获得当前分页对象
        PageInfo<ViewOrder> pageInfo = new PageInfo<>(orders);
        responseData.putDataValue("pageInfo", pageInfo);
        return responseData;
    }

    @RequestMapping("/getShoplistByOid")
    public ResponseData getShoplistByOid(String oid, String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token的email和传来的email是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        List<ViewShoplist> shoplist = orderService.getShoplistByOid(oid);
        responseData = ResponseData.ok();
        responseData.putDataValue("shoplist", shoplist);
        return responseData;
    }

    @RequestMapping("/getOrderByOid")
    public ResponseData getOrderByOid(String oid, String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token中的adminName和操作的adminName是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        Order order = orderService.getOrderByOid(oid);
        if (order == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "订单不存在！");
            return responseData;
        }
        responseData = ResponseData.ok();
        responseData.putDataValue("order", order);
        return responseData;
    }

    @RequestMapping("/getViewOrderByOid")
    public ResponseData getViewOrderByOid(String oid, String adminName, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token中的adminName和操作的adminName是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        if (orderService.selectViewOrderByOid(oid) == null) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "订单不存在！");
            return responseData;
        }
        responseData = ResponseData.ok();
        responseData.putDataValue("order", orderService.selectViewOrderByOid(oid));
        return responseData;
    }

    @PostMapping("/getViewOrdersByEmail")
    public ResponseData getViewOrdersByEmail(String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token的email和传来的email是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        List<ViewOrder> orderList = orderService.getOrderByEmail(email);
        responseData = ResponseData.ok();
        responseData.putDataValue("orderList", orderList);
        return responseData;
    }

    @PostMapping("/cancelOrderByOid")
    public ResponseData cancelOrderByOid(String oid, String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token的email和传来的email是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = orderService.deleteOrderByOid(oid);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "订单不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @PostMapping("/deliverOrder")
    public ResponseData deliverOrder(String oid, String adminName, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token中的adminName和操作的adminName是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = orderService.deliverOrder(oid);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "订单不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @PostMapping("/receivingOrder")
    public ResponseData receivingOrder(String oid, String email, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token的email和传来的email是否一致
        if (!JWTUtil.verifyId(email, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        int i = orderService.receivingOrder(oid);
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "订单不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
        }
        return responseData;
    }

    @RequestMapping("/admin/getOrderData")
    public ResponseData getOrderData() {
        return orderService.getAdminOrderData();
    }

}
