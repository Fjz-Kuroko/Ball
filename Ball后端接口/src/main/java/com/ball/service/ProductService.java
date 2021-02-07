package com.ball.service;

import com.ball.dto.ResponseData;
import com.ball.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    int insert(Product product);
    int delete(String pid);
    int update(Product product);
    List<Product> selectAllProduct();
    Product selectByPid(String pid);
    List<Product> selectAllProductByCondition(String condition);
    List<Product> prodList(Map<String, Object> map);
    ResponseData getAdminProductData();
    int getSaleByCategory(String category);
    int getNumByCategory(String category);
    List<Product> getThousandSale();
}
