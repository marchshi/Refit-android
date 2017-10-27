package com.smq.demo5.bean;

public class CommentBean implements BaseBean {
	
	private int commentId;
	private int orderId;
	private int catId;
	private int payUserId;
	private int serverUserId;
	private String remark;
	private int type;
	private int appTimes;
	private int supTimes;
	private int oppTimes;
	private OrderInfoBean order;
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getPayUserId() {
		return payUserId;
	}
	public void setPayUserId(int payUserId) {
		this.payUserId = payUserId;
	}
	public int getServerUserId() {
		return serverUserId;
	}
	public void setServerUserId(int serverUserId) {
		this.serverUserId = serverUserId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAppTimes() {
		return appTimes;
	}
	public void setAppTimes(int appTimes) {
		this.appTimes = appTimes;
	}
	public int getSupTimes() {
		return supTimes;
	}
	public void setSupTimes(int supTimes) {
		this.supTimes = supTimes;
	}
	public int getOppTimes() {
		return oppTimes;
	}
	public void setOppTimes(int oppTimes) {
		this.oppTimes = oppTimes;
	}
	public OrderInfoBean getOrder() {
		return order;
	}
	public void setOrder(OrderInfoBean order) {
		this.order = order;
	}
	@Override
	public int compareTo(Object o) {
		
		return 0;
	}
}
