package com.ball.controller;

import com.ball.entity.Product;
import com.ball.service.ProductService;
import com.ball.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/thousandSale")
    public void thousandSale(HttpServletRequest request, HttpServletResponse response) {
        // 获取数据
        List<Product> thousandSale = productService.getThousandSale();
        // 标题
        String[] title = {"商品编号", "商品名称", "商品价格", "商品分类", "商品销量", "商品图片", "商品描述"};
        // 文件名
        String fileName = "Ball销量破千商品信息表" + System.currentTimeMillis() + ".xls";
        // sheet名
        String sheetName = "销量破千商品信息";
        // 数据
        String[][] content = new String[thousandSale.size()][title.length];
        // 把数据存进数组中
        for (int i = 0; i < thousandSale.size(); i++) {
            Product product = thousandSale.get(i);
            content[i][0] = product.getPid();
            content[i][1] = product.getPname();
            content[i][2] = product.getPrice() + "";
            content[i][3] = product.getCategory();
            content[i][4] = product.getSaleNum() + "";
            content[i][5] = product.getImgurl();
            content[i][6] = product.getDescription();
        }
        // 创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法（在上面的方法中被调用了）
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
