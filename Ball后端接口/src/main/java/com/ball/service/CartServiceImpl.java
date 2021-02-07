package com.ball.service;

import com.ball.dao.CartDao;
import com.ball.dao.ProductDao;
import com.ball.dao.UserDao;
import com.ball.entity.Cart;
import com.ball.vo.ViewCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public int addCart(Cart cart) {
        if (userDao.selectUserByEmail(cart.getEmail()) == null) {
            return -1;
        }
        if (productDao.selectByPid(cart.getPid()) == null) {
            return -2;
        }
        // 查询购物车是否放有该用户的该商品
        Cart selectByPidAndEmail = cartDao.selectByPidAndEmail(cart);
        // 如果存在，那把购物车记录的商品数量增加就行
        if (selectByPidAndEmail != null) {
            selectByPidAndEmail.setNum(selectByPidAndEmail.getNum() + cart.getNum());
            return cartDao.updateCart(selectByPidAndEmail);
        }
        // 如果不存在，那就新增
        return cartDao.addCart(cart);
    }

    @Override
    public int updateCart(Cart cart) {
        if (cartDao.selectByCid(cart.getCid()) == null) {
            return -1;
        }
        return cartDao.updateCart(cart);
    }

    @Override
    public int deleteCart(int cid) {
        if (cartDao.selectByCid(cid) == null) {
            return -1;
        }
        return cartDao.deleteCart(cid);
    }

    @Override
    public Cart selectByCid(int cid) {
        return cartDao.selectByCid(cid);
    }

    @Override
    public List<ViewCart> selectByEmail(String email) {
        List<ViewCart> viewCarts = cartDao.selectByEmail(email);
        for (ViewCart v : viewCarts) {
            v.setSummary(v.getNum() * v.getPrice());
        }
        return viewCarts;
    }

    @Override
    public int clearAll(String email) {
        if (userDao.selectUserByEmail(email) == null) {
            return -1;
        }
        return cartDao.clearAll(email);
    }
}
