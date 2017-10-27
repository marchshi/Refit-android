package com.smq.mode2.entity;

/**
 * Created by shimanqian on 2017/6/17.
 */

public class AskInfoEntity {

    private String askId;
    private String userId;
    private String askCont;
    private String refPrice;
    private String askTime;
    private String category;
    private String noName;
    private String askStatus;
    private String lookTimes;

    public String getLookTimes() {
        return lookTimes;
    }
    public void setLookTimes(String lookTimes) {
        this.lookTimes = lookTimes;
    }
    public String getNoName() {
        return noName;
    }
    public void setNoName(String noName) {
        this.noName = noName;
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
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getAskStatus() {
        return askStatus;
    }
    public void setAskStatus(String askStatus) {
        this.askStatus = askStatus;
    }

}
