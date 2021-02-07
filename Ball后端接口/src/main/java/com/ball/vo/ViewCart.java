package com.ball.vo;

public class ViewCart {

    private int cid;
    private String email;
    private String pid;
    private int num;
    private String pname;
    private String imgurl;
    private double price;
    private double summary;

    public ViewCart() {}

    public ViewCart(int cid, String email, String pid, int num, String pname, String imgurl, double price, double summary) {
        this.cid = cid;
        this.email = email;
        this.pid = pid;
        this.num = num;
        this.pname = pname;
        this.imgurl = imgurl;
        this.price = price;
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ViewCart{" +
                "cid=" + cid +
                ", email='" + email + '\'' +
                ", pid='" + pid + '\'' +
                ", num=" + num +
                ", pname='" + pname + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", price=" + price +
                ", summary=" + summary +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }
}
