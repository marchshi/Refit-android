package com.smq.demo5.bean;

public class LoginHisBean implements BaseBean {
	
	private int loginId;
	private int userId;
	private String tel;
	private String uuid;
	private long loginDate;
	private int loginer;
	private UserInfoBean user;
	
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public long getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(long loginDate) {
		this.loginDate = loginDate;
	}
	public int getLoginer() {
		return loginer;
	}
	public void setLoginer(int loginer) {
		this.loginer = loginer;
	}
	public UserInfoBean getUser() {
		return user;
	}
	public void setUser(UserInfoBean user) {
		this.user = user;
	}
	public int compareTo(Object o) {
		return 0;
	}
}
