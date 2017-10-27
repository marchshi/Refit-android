package com.smq.demo5.bean;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CatInfoBean implements BaseBean {
	
	private int catId;
	private int userId;
	private int nameId;
	private String label;
	private String catDesc;
	private int price;
	private boolean cont;
	private int serverModel;
	private String scheTime;
	private int lookTimes;
	private int newLook;
	private String form;
	private CatNameBean catName;
	private UserInfoBean user;
	private Set<ApplyInfoBean> applys = new TreeSet<ApplyInfoBean>();
	private Set<OrderInfoBean> orders = new TreeSet<OrderInfoBean>();
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNameId() {
		return nameId;
	}
	public void setNameId(int nameId) {
		this.nameId = nameId;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public String getScheTime() {
		return scheTime;
	}
	public void setScheTime(String scheTime) {
		this.scheTime = scheTime;
	}
	public int getLookTimes() {
		return lookTimes;
	}
	public void setLookTimes(int lookTimes) {
		this.lookTimes = lookTimes;
	}
	public int getNewLook() {
		return newLook;
	}
	public void setNewLook(int newLook) {
		this.newLook = newLook;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public CatNameBean getCatName() {
		return catName;
	}
	public void setCatName(CatNameBean catName) {
		this.catName = catName;
	}
	public UserInfoBean getUser() {
		return user;
	}
	public void setUser(UserInfoBean user) {
		this.user = user;
	}
	public Set<ApplyInfoBean> getApplys() {
		return applys;
	}
	public void setApplys(Set<ApplyInfoBean> applys) {
		this.applys = applys;
	}
	public Set<OrderInfoBean> getOrders() {
		return orders;
	}
	public void setOrders(Set<OrderInfoBean> orders) {
		this.orders = orders;
	}
	@Override
	public int compareTo(Object o) {
		CatInfoBean bean = (CatInfoBean) o;
		return this.catId - bean.getCatId();
	}
	
}
