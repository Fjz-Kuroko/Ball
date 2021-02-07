package com.ball.vo;

import java.sql.Timestamp;
import java.util.List;

public class ViewOrder {

    private String oid;    // 订单id
    private String email; // 用户email
    private int aid;    // 地址id
    private String address; // 订单的地址
    private String recipient;   // 收货人
    private String recipientPhone;  // 收货人电话
    private Timestamp orderTime;    // 下单时间
    private double orderAmount; // 订单金额
    private String status;  // 订单状态
    private Timestamp deliveryTime; // 发货时间
    private Timestamp receivingTime;    // 收货时间
    private List<ViewShoplist> shoplists; // 订单涉及的商品

    public ViewOrder() {}

    public ViewOrder(String oid, String email, int aid, String address, String recipient,
                     String recipientPhone, Timestamp orderTime, double orderAmount,
                     String status, Timestamp deliveryTime, Timestamp receivingTime, List<ViewShoplist> shoplists) {
        this.oid = oid;
        this.email = email;
        this.aid = aid;
        this.address = address;
        this.recipient = recipient;
        this.recipientPhone = recipientPhone;
        this.orderTime = orderTime;
        this.orderAmount = orderAmount;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.receivingTime = receivingTime;
        this.shoplists = shoplists;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
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

    public List<ViewShoplist> getShoplists() {
        return shoplists;
    }

    public void setShoplists(List<ViewShoplist> shoplists) {
        this.shoplists = shoplists;
    }
}
