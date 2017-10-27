package com.smq.mode2.entity;

import java.io.Serializable;

/**
 * Created by shimanqian on 2017/6/3.
 */

public class CatInfoEntity implements Serializable {

    private String catId;
    private String userId;
    private String category;
    private String label;
    private String catDesc;
    private String price;
    private String cont;
    private String smodel;
    private String looktimes;
    private String stimes;
    private String handling;
    private String income;
    private String form;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getSmodel() {
        return smodel;
    }

    public void setSmodel(String smodel) {
        this.smodel = smodel;
    }

    public String getLooktimes() {
        return looktimes;
    }

    public void setLooktimes(String looktimes) {
        this.looktimes = looktimes;
    }

    public String getStimes() {
        return stimes;
    }

    public void setStimes(String stimes) {
        this.stimes = stimes;
    }

    public String getHandling() {
        return handling;
    }

    public void setHandling(String handling) {
        this.handling = handling;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}
