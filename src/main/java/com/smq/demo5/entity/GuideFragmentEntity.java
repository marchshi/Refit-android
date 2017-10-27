package com.smq.demo5.entity;

/**
 * Created by shimanqian on 2017/8/21.
 */

public class GuideFragmentEntity {
    //订单编号用于点击后的界面请求
    private int orderId;

    private String chatName;

    private String touxiang;
    private String name;

    //订单类型 get 找人导购  to 给人导购
    private String type;

    private int status;
    private boolean cont;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCont() {
        return cont;
    }

    public void setCont(boolean cont) {
        this.cont = cont;
    }
}
