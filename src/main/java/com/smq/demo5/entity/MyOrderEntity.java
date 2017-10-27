package com.smq.demo5.entity;

/**
 * Created by shimanqian on 2017/8/18.
 */

public class MyOrderEntity {

    int orderId;
    long orderDate;
    //订单类型 get 找人导购  to 给人导购
    String type;

    String touxiang;
    String name;
    int status;
    long price;
    String label;
    int nameId;
    boolean cont;
    int serverModel;
    String scheTime;
    public String getScheTime() {
        return scheTime;
    }

    public void setScheTime(String scheTime) {
        this.scheTime = scheTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public boolean isCont() {
        return cont;
    }

    public void setCont(boolean cont) {
        this.cont = cont;
    }

    public int getServerModel() {
        return serverModel;
    }

    public void setServerModel(int serverModel) {
        this.serverModel = serverModel;
    }
}
