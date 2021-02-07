package com.ball.dao;

import com.ball.entity.Order;
import com.ball.vo.ViewOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("orderDao")
@Mapper
public interface OrderDao {
    // 添加订单
    int addOrder(Order order);
    // 根据订单id查询订单
    Order selectOrderByOid(String oid);
    // 订单付款
    int payOrder(Order order);
    // 根据订单号删除订单
    int deleteOrderByOid(String oid);
    // 根据email查询用户的所有订单
    List<ViewOrder> selectAllOrderByEmail(String email);
    // 查询所有订单（后台）
    List<ViewOrder> selectAllOrder(Map<String, Object> map);
    // 根据订单号查询订单和所涉及商品
    ViewOrder selectViewOrderByOid(String oid);
    // 订单发货
    int deliverOrder(Map<String, Object> map);
    // 订单收货
    int receivingOrder(Map<String, Object> map);
    // 根据状态获取订单
    List<Order> selectOrderByStatus(String status);
}
