package com.smq.mode2.entity;

/**
 * Created by shimanqian on 2017/6/21.
 */

public class ApplyViewEntity {

    private String applyId;
    private String askId;
    private String userId;
    private String applyTime;
    private String applyStatus;
    private String name;
    private String touxiang;
    private String category;
    private String label;
    private String catDesc;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String stimes;
    public String getApplyId() {
        return applyId;
    }
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    public String getAskId() {
        return askId;
    }
    public void setAskId(String askId) {
        this.askId = askId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
    public String getApplyStatus() {
        return applyStatus;
    }
    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTouxiang() {
        return touxiang;
    }
    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getCatDesc() {
        return catDesc;
    }
    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }
    public String getStimes() {
        return stimes;
    }
    public void setStimes(String stimes) {
        this.stimes = stimes;
    }
}
