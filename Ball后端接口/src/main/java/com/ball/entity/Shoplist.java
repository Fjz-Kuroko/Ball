package com.ball.entity;

public class Shoplist {

    private int slid;   // shoplist的id
    private String oid;    // 订单id
    private String pid; // 商品id
    private String email; // 用户邮箱
    private int pnum;   // 商品数量

    public Shoplist() {}

    public Shoplist(int slid, String oid, String pid, String email, int pnum) {
        this.slid = slid;
        this.oid = oid;
        this.pid = pid;
        this.email = email;
        this.pnum = pnum;
    }

    @Override
    public String toString() {
        return "Shoplist{" +
                "slid=" + slid +
                ", oid=" + oid +
                ", pid='" + pid + '\'' +
                ", email='" + email + '\'' +
                ", pnum=" + pnum +
                '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }
}
