package com.ball.dao;

import com.ball.entity.Shoplist;
import com.ball.vo.ViewShoplist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shoplistDao")
@Mapper
public interface ShoplistDao {
    // 批量添加shoplist
    int addShoplist(List<Shoplist> shoplists);
    // 根据订单id查询shoplist
    List<ViewShoplist> getShoplistByOid(String oid);
    // 根据订单id删除shoplist
    int deleteShoplistByOid(String oid);
}
