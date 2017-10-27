package com.smq.demo5.bean;


public class LoginVerifyBean implements BaseBean {
	
	private int userId;
	private String tel;
	private String uuid;
	private String verify;
	private long verifyDate;
	private int failTimes;
	
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
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public long getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(long verifyDate) {
		this.verifyDate = verifyDate;
	}
	public int getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(int failTimes) {
		this.failTimes = failTimes;
	}
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
