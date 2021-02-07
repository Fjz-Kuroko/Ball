package com.ball.dao;

import com.ball.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("productDao")
@Mapper
public interface ProductDao {
    int insert(Product product);
    int delete(String pid);
    int update(Product product);
    List<Product> selectAllProduct();
    Product selectByPid(String pid);
    List<Product> selectAllProductByCondition(@Param("condition") String condition);
    List<Product> prodList(Map<String, Object> map);
    List<Product> getThousandSale();
    int getSaleByCategory(String category);
    int getNumByCategory(String category);
}
