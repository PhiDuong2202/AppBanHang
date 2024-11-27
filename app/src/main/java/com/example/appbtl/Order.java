package com.example.appbtl;

public class Order {
    public String orderId;
    public String name;
    public String gender;
    public String address;
    public String phone;
    public String paymentMethod;

    public Order() {
        // Constructor mặc định cho Firebase
    }

    public Order(String orderId, String name, String gender, String address, String phone, String paymentMethod) {
        this.orderId = orderId;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
    }
}
