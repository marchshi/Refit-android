package com.smq.demo5.bean;

public class AccountInfoBean implements BaseBean {
	
	private int accountId;
	private int balance;
	private int expense;
	private int asset;
	private UserInfoBean user;
	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getAsset() {
		return asset;
	}
	public void setAsset(int asset) {
		this.asset = asset;
	}
	public UserInfoBean getUser() {
		return user;
	}
	public void setUser(UserInfoBean user) {
		this.user = user;
	}
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
