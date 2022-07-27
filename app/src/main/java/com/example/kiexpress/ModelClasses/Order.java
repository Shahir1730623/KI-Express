package com.example.kiexpress.ModelClasses;

public class Order {
    String orderId,senderName,receiverName,senderPhone,receiverPhone,senderAddress,receiverAddress,courierService,itemNames,totalWeight,date,orderStatus,orderStatusBool;

    public Order() {
    }

    public Order(String orderId, String senderName, String receiverName, String senderPhone, String receiverPhone, String senderAddress, String receiverAddress, String courierService, String itemNames, String totalWeight, String date, String orderStatus, String orderStatusBool) {
        this.orderId = orderId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.courierService = courierService;
        this.itemNames = itemNames;
        this.totalWeight = totalWeight;
        this.date = date;
        this.orderStatus = orderStatus;
        this.orderStatusBool = orderStatusBool;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getCourierService() {
        return courierService;
    }

    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusBool() {
        return orderStatusBool;
    }

    public void setOrderStatusBool(String orderStatusBool) {
        this.orderStatusBool = orderStatusBool;
    }
}
