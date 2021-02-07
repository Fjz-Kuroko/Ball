package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.dto.Validate;
import com.ball.dto.ValidateMessage;
import com.ball.entity.Product;
import com.ball.service.ProductService;
import com.ball.utils.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController("productController")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseData add(Product product, String adminName, HttpServletRequest request) {
        ResponseData responseData = null;
        // 验证添加地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        // 获取随机的uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        product.setPid(uuid);
        // 表单验证
        ValidateMessage validate = Validate.validate(product);
        if (!validate.getCode().equals("200")) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", validate.getMsg());
            return responseData;
        }
        int i = productService.insert(product);
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
            responseData.putDataValue("msg", "服务器错误！");
        }
        return responseData;
    }

    @PostMapping("/del")
    public ResponseData del(String pid, String adminName, HttpServletRequest request) {
        return getDelUpdateResponseData(adminName, request, productService.delete(pid));
    }

    @PostMapping("/update")
    public ResponseData update(Product product, String adminName, HttpServletRequest request) {
        return getDelUpdateResponseData(adminName, request, productService.update(product));
    }

    private ResponseData getDelUpdateResponseData(String adminName, HttpServletRequest request, int i) {
        ResponseData responseData;
        // 验证请求头的token中的adminName和操作的adminName是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        // 判断调用service进行数据库操作后的返回值情况
        if (i == -1) {
            responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "该商品不存在！");
            return responseData;
        }
        if (i > 0) {
            responseData = ResponseData.ok();
        } else {
            responseData = ResponseData.serverInternalError();
            responseData.putDataValue("msg", "服务器错误！");
        }
        return responseData;
    }

    @RequestMapping("/all")
    public ResponseData add(int pageNum, int pageSize, String pid, String pname, String category) {
        ResponseData responseData = ResponseData.ok();
        //1.通过调用 PageHelper 的静态方法开始获取分页数据
        //指定当前第几页以及每页显示的条数
        PageHelper.startPage(pageNum, pageSize);
        // 创建map，添加条件
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        map.put("pname", pname);
        map.put("category", category);

        //2.获得所有的商品记录
        List<Product> products = productService.prodList(map);
//        List<Product> products = productService.selectAllProduct();
        //3.获得当前分页对象
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        responseData.putDataValue("pageInfo", pageInfo);
        return responseData;
    }

    @RequestMapping("/selectByPid")
    public ResponseData selectByPid(String pid) {
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("product", productService.selectByPid(pid));
        return responseData;
    }

    @RequestMapping("/list")
    public ResponseData list(String condition) {
        ResponseData responseData = ResponseData.ok();
        List<Product> products = productService.selectAllProductByCondition(condition);
        responseData.putDataValue("products", products);
        return responseData;
    }

    @RequestMapping("/admin/getProductData")
    public ResponseData getProductData() {
        return productService.getAdminProductData();
    }

    @RequestMapping("/admin/getEchartData")
    public ResponseData getEchartData() {
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("badmintonNum", productService.getNumByCategory("羽毛球"));
        responseData.putDataValue("basketballNum", productService.getNumByCategory("篮球"));
        responseData.putDataValue("tableTennisNum", productService.getNumByCategory("乒乓球"));
        responseData.putDataValue("volleyballNum",productService.getNumByCategory("排球"));

        responseData.putDataValue("badmintonSale", productService.getSaleByCategory("羽毛球"));
        responseData.putDataValue("basketballSale", productService.getSaleByCategory("篮球"));
        responseData.putDataValue("tableTennisSale", productService.getSaleByCategory("乒乓球"));
        responseData.putDataValue("volleyballSale",productService.getSaleByCategory("排球"));
        return responseData;
    }

}
