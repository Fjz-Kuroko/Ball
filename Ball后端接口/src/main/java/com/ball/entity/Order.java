package com.ball.entity;

import java.sql.Timestamp;

public class Order {

    private String oid;    // 订单id
    private String email; // 用户邮箱
    private int aid;    // 地址id
    private Timestamp orderTime;    // 下单时间
    private double orderAmount; // 订单金额
    private String status;  // 订单状态
    private Timestamp deliveryTime; // 发货时间
    private Timestamp receivingTime;    // 收货时间

    public Order() {}

    public Order(String oid, String email, int aid, Timestamp orderTime, double orderAmount,
                 String status, Timestamp deliveryTime, Timestamp receivingTime) {
        this.oid = oid;
        this.email = email;
        this.aid = aid;
        this.orderTime = orderTime;
        this.orderAmount = orderAmount;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.receivingTime = receivingTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", email='" + email + '\'' +
                ", aid=" + aid +
                ", orderTime=" + orderTime +
                ", orderAmount=" + orderAmount +
                ", status='" + status + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", receivingTime=" + receivingTime +
                '}';
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Timestamp getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(Timestamp receivingTime) {
        this.receivingTime = receivingTime;
    }
}
