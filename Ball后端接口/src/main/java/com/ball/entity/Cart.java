package com.ball.entity;

public class Cart {

    private int cid;    // 购物车id
    private String email; // 用户email
    private String pid; // 商品id
    private int num;    // 商品数量

    public Cart() {}

    public Cart(int cid, String email, String pid, int num) {
        this.cid = cid;
        this.email = email;
        this.pid = pid;
        this.num = num;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", email='" + email + '\'' +
                ", pid='" + pid + '\'' +
                ", num=" + num +
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
}
