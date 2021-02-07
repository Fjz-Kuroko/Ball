package com.ball.service;

import com.ball.dao.ProductDao;
import com.ball.dto.ResponseData;
import com.ball.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public int insert(Product product) {
        return productDao.insert(product);
    }

    @Override
    public int delete(String pid) {
        Product product = productDao.selectByPid(pid);
        if (product == null) {
            return -1;
        }
        return productDao.delete(pid);
    }

    @Override
    public int update(Product product) {
        Product selectByPid = productDao.selectByPid(product.getPid());
        if (selectByPid == null) {
            return -1;
        }
        return productDao.update(product);
    }

    @Override
    public List<Product> selectAllProduct() {
        return productDao.selectAllProduct();
    }

    @Override
    public Product selectByPid(String pid) {
        return productDao.selectByPid(pid);
    }

    @Override
    public List<Product> selectAllProductByCondition(String condition) {
        return productDao.selectAllProductByCondition(condition);
    }

    @Override
    public List<Product> prodList(Map<String, Object> map) {
        return productDao.prodList(map);
    }

    @Override
    public ResponseData getAdminProductData() {
        ResponseData responseData = ResponseData.ok();
        int size = productDao.selectAllProduct().size();
        responseData.putDataValue("total", size);
        int thousandSale = productDao.getThousandSale().size();
        responseData.putDataValue("thousandSale", thousandSale);
        return responseData;
    }

    @Override
    public int getSaleByCategory(String category) {
        return productDao.getSaleByCategory(category);
    }

    @Override
    public int getNumByCategory(String category) {
        return productDao.getNumByCategory(category);
    }

    @Override
    public List<Product> getThousandSale() {
        return productDao.getThousandSale();
    }
}
