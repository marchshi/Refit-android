package com.smq.demo5.bean;

public class OrderAppealBean  implements BaseBean{
	
	private int appealId;
	private int orderId;
	private int status;
	private String appealDesc;
	private long appeanDate;
	private int result;
	private OrderInfoBean order;
	public int getAppealId() {
		return appealId;
	}
	public void setAppealId(int appealId) {
		this.appealId = appealId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAppealDesc() {
		return appealDesc;
	}
	public void setAppealDesc(String appealDesc) {
		this.appealDesc = appealDesc;
	}
	public long getAppeanDate() {
		return appeanDate;
	}
	public void setAppeanDate(long appeanDate) {
		this.appeanDate = appeanDate;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public OrderInfoBean getOrder() {
		return order;
	}
	public void setOrder(OrderInfoBean order) {
		this.order = order;
	}
	@Override
	public int compareTo(Object o) {
		OrderAppealBean bean = (OrderAppealBean) o;
		return (int) (bean.getAppeanDate() - this.appeanDate);
	}
}
