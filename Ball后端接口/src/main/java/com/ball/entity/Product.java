package com.ball.entity;

public class Product {

    private String pid;     // 商品id
    private String pname;   // 商品名称
    private double price;   // 商品价格
    private String category;    // 商品分类
    private int saleNum;    // 销量
    private String imgurl;  // 图片url
    private String description; // 商品描述

    public Product() {}

    public Product(String pid, String pname, double price, String category, int saleNum, String imgurl, String description) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.category = category;
        this.saleNum = saleNum;
        this.imgurl = imgurl;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", saleNum=" + saleNum +
                ", imgurl='" + imgurl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
