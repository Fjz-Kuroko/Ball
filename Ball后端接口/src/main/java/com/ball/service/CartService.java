package com.ball.service;

import com.ball.entity.Cart;
import com.ball.vo.ViewCart;

import java.util.List;

public interface CartService {
    // 添加购物车
    int addCart(Cart cart);
    // 更新购物车数量
    int updateCart(Cart cart);
    // 根据cid删除购物车记录
    int deleteCart(int cid);
    // 根据cid查询
    Cart selectByCid(int cid);
    // 根据用户email查询所有的购物车记录
    List<ViewCart> selectByEmail(String email);
    // 根据email清空购物车
    int clearAll(String email);
}
