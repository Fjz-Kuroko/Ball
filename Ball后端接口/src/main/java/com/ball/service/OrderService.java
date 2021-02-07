package com.ball.service;

import com.ball.dto.ResponseData;
import com.ball.entity.Order;
import com.ball.vo.ViewCart;
import com.ball.vo.ViewOrder;
import com.ball.vo.ViewShoplist;

import java.util.List;
import java.util.Map;

public interface OrderService {

    // 提交订单
    String submitOrder(Order order, List<ViewCart> viewCarts);
    // 根据订单id获取订单
    Order getOrderByOid(String oid);
    // 订单付款
    int payOrder(Order order);
    // 根据订单id获取shoplist
    List<ViewShoplist> getShoplistByOid(String oid);
    // 根据email获取所有订单信息
    List<ViewOrder> getOrderByEmail(String email);
    // 获取所有订单
    List<ViewOrder> getAllOrders(Map<String, Object> map);
    // 根据订单号查询订单和所涉及商品
    ViewOrder selectViewOrderByOid(String oid);
    // 根据订单id删除订单
    int deleteOrderByOid(String oid);
    // 订单发货
    int deliverOrder(String oid);
    // 订单收货
    int receivingOrder(String oid);
    // 获取订单的数据
    ResponseData getAdminOrderData();

}
