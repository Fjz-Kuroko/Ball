package com.ball.dao;

import com.ball.entity.Cart;
import com.ball.vo.ViewCart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("cartDao")
@Mapper
public interface CartDao {
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
    // 根据pid和email查询
    Cart selectByPidAndEmail(Cart cart);
    // 根据email清空购物车
    int clearAll(String email);
    // 批量删除
    int batchDelete(Map<String, String> map);
}
