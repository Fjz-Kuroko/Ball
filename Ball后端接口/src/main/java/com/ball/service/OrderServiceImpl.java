package com.ball.service;

import com.ball.dao.CartDao;
import com.ball.dao.OrderDao;
import com.ball.dao.ShoplistDao;
import com.ball.dto.ResponseData;
import com.ball.entity.Order;
import com.ball.entity.Shoplist;
import com.ball.utils.SequenceUtil;
import com.ball.vo.ViewCart;
import com.ball.vo.ViewOrder;
import com.ball.vo.ViewShoplist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ShoplistDao shoplistDao;
    @Autowired
    private CartDao cartDao;

    @Override
    public String submitOrder(Order order, List<ViewCart> viewCarts) {
        // 设置订单的时间以及状态
        order.setOid(SequenceUtil.getLocalTrmSeq());
        order.setOrderTime(new Timestamp(new Date().getTime()));
        order.setStatus("未付款");
        order.setAid(-1);
        // 往order表添加数据
        int addOrder = orderDao.addOrder(order);
        // 存储shoplist数据
        List<Shoplist> shoplists = new ArrayList<>();
        // 存储购物车id的字符串
        StringBuilder cartIdStr = new StringBuilder();
        for (ViewCart v: viewCarts) {
            Shoplist shoplist = new Shoplist();
            shoplist.setOid(order.getOid());
            shoplist.setEmail(order.getEmail());
            shoplist.setPid(v.getPid());
            shoplist.setPnum(v.getNum());

            shoplists.add(shoplist);
            // 组装字符串
            cartIdStr.append(v.getCid()).append(",");
        }
        int addShoplist = shoplistDao.addShoplist(shoplists);
        Map<String, String> map = new HashMap<>();
        map.put("cartIds", cartIdStr.substring(0, cartIdStr.length() - 1));
        if (addOrder > 0 || addShoplist > 0) {
            // 批量删除购物车的数据
            cartDao.batchDelete(map);
            return order.getOid();
        }
        return null;
    }

    @Override
    public Order getOrderByOid(String oid) {
        return orderDao.selectOrderByOid(oid);
    }

    @Override
    public int payOrder(Order order) {
        order.setOrderTime(new Timestamp(new Date().getTime()));
        order.setStatus("已付款");
        return orderDao.payOrder(order);
    }

    @Override
    public List<ViewShoplist> getShoplistByOid(String oid) {
        return shoplistDao.getShoplistByOid(oid);
    }

    @Override
    public List<ViewOrder> getOrderByEmail(String email) {
        return orderDao.selectAllOrderByEmail(email);
    }

    @Override
    public List<ViewOrder> getAllOrders(Map<String, Object> map) {
        return orderDao.selectAllOrder(map);
    }

    @Override
    public ViewOrder selectViewOrderByOid(String oid) {
        return orderDao.selectViewOrderByOid(oid);
    }

    @Override
    public int deleteOrderByOid(String oid) {
        Order order = orderDao.selectOrderByOid(oid);
        if (order == null) {
            return -1;
        }
        shoplistDao.deleteShoplistByOid(oid);
        return orderDao.deleteOrderByOid(oid);
    }

    @Override
    public int deliverOrder(String oid) {
        Order order = orderDao.selectOrderByOid(oid);
        if (order == null) {
            return -1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oid", oid);
        map.put("status", "已发货");
        map.put("deliveryTime", new Timestamp(new Date().getTime()));
        return orderDao.deliverOrder(map);
    }

    @Override
    public int receivingOrder(String oid) {
        Order order = orderDao.selectOrderByOid(oid);
        if (order == null) {
            return -1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oid", oid);
        map.put("status", "已收货");
        map.put("receivingTime", new Timestamp(new Date().getTime()));
        return orderDao.receivingOrder(map);
    }

    @Override
    public ResponseData getAdminOrderData() {
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("toBePaidOrder", orderDao.selectOrderByStatus("未付款").size());
        responseData.putDataValue("pendingOrder", orderDao.selectOrderByStatus("已付款").size());
        responseData.putDataValue("shipOrder", orderDao.selectOrderByStatus("已发货").size());
        responseData.putDataValue("completedOrder", orderDao.selectOrderByStatus("已收货").size());
        return responseData;
    }
}
