package com.ball.entity;

public class Address {

    private int aid;    // 地址id
    private String email; // 用户email
    private String address; // 地址
    private String recipient;   // 收件人
    private String recipientPhone;  // 收件人电话号码

    public Address() {}

    public Address(int aid, String email, String address, String recipient, String recipientPhone) {
        this.aid = aid;
        this.email = email;
        this.address = address;
        this.recipient = recipient;
        this.recipientPhone = recipientPhone;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid=" + aid +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientPhone='" + recipientPhone + '\'' +
                '}';
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
