package com.ball.vo;

public class ViewShoplist {

    private int slid;
    private String oid;
    private String pid;
    private int pnum;
    private String pname;
    private double price;
    private String imgurl;
    private double summary;

    public ViewShoplist() {}

    public ViewShoplist(int slid, String oid, String pid, int pnum, String pname,
                        double price, String imgurl, double summary) {
        this.slid = slid;
        this.oid = oid;
        this.pid = pid;
        this.pnum = pnum;
        this.pname = pname;
        this.price = price;
        this.imgurl = imgurl;
        this.summary = summary;
    }

    public int getSlid() {
        return slid;
    }

    public void setSlid(int slid) {
        this.slid = slid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }
}
