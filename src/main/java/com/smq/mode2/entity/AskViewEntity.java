package com.smq.mode2.entity;

import java.io.Serializable;

/**
 * Created by shimanqian on 2017/6/17.
 */

public class AskViewEntity implements Serializable {

    private String userId;
    private String name;
    private String touxiang;
    private String noName;
    private String askId;
    private String askCont;
    private String refPrice;
    private String askTime;
    private String category;
    private String lookTimes;
    private String askStatus;
    private String applyNum;
    private String payNum;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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
    public String getAskId() {
        return askId;
    }
    public void setAskId(String askId) {
        this.askId = askId;
    }
    public String getAskCont() {
        return askCont;
    }
    public void setAskCont(String askCont) {
        this.askCont = askCont;
    }
    public String getRefPrice() {
        return refPrice;
    }
    public void setRefPrice(String refPrice) {
        this.refPrice = refPrice;
    }
    public String getAskTime() {
        return askTime;
    }
    public void setAskTime(String askTime) {
        this.askTime = askTime;
    }
    public String getLookTimes() {
        return lookTimes;
    }
    public void setLookTimes(String lookTimes) {
        this.lookTimes = lookTimes;
    }
    public String getAskStatus() {
        return askStatus;
    }
    public void setAskStatus(String askStatus) {
        this.askStatus = askStatus;
    }
    public String getApplyNum() {
        return applyNum;
    }
    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }
    public String getPayNum() {
        return payNum;
    }
    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }


}
