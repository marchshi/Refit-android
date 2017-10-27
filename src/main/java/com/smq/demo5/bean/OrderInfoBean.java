package com.smq.demo5.bean;

import java.util.Set;
import java.util.TreeSet;

public class OrderInfoBean  implements BaseBean{

	private int orderId;
	private int payUserId;
	private int catId;
	private int nameId;
	private int price;
	private boolean cont;
	private int serverModel;
	private String scheTime;
	private int orderFrom;
	private int status;
	private long switch1;
	private long switch2;
	private long switch3;
	private long switch4;
	private long switch5;
	private long switch6;
    private String form;
	private CatNameBean catName;
	private UserInfoBean user;
	private CatInfoBean cat;
	private CommentBean comment;
	private Set<OrderAppealBean> appeals = new TreeSet<OrderAppealBean>();
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getPayUserId() {
		return payUserId;
	}
	public void setPayUserId(int payUserId) {
		this.payUserId = payUserId;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getNameId() {
		return nameId;
	}
	public void setNameId(int nameId) {
		this.nameId = nameId;
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
	public int getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(int orderFrom) {
		this.orderFrom = orderFrom;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getSwitch1() {
		return switch1;
	}
	public void setSwitch1(long switch1) {
		this.switch1 = switch1;
	}
	public long getSwitch2() {
		return switch2;
	}
	public void setSwitch2(long switch2) {
		this.switch2 = switch2;
	}
	public long getSwitch3() {
		return switch3;
	}
	public void setSwitch3(long switch3) {
		this.switch3 = switch3;
	}
	public long getSwitch4() {
		return switch4;
	}
	public void setSwitch4(long switch4) {
		this.switch4 = switch4;
	}
	public long getSwitch5() {
		return switch5;
	}
	public void setSwitch5(long switch5) {
		this.switch5 = switch5;
	}
	public long getSwitch6() {
		return switch6;
	}
	public void setSwitch6(long switch6) {
		this.switch6 = switch6;
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
	public CatInfoBean getCat() {
		return cat;
	}
	public void setCat(CatInfoBean cat) {
		this.cat = cat;
	}
	public CommentBean getComment() {
		return comment;
	}
	public void setComment(CommentBean comment) {
		this.comment = comment;
	}
	public Set<OrderAppealBean> getAppeals() {
		return appeals;
	}
	public void setAppeals(Set<OrderAppealBean> appeals) {
		this.appeals = appeals;
	}
	@Override
	public int compareTo(Object o) {
		OrderInfoBean bean = (OrderInfoBean) o;
		return (int) (bean.getSwitch1() -this.switch1);
	}
}
