package com.smq.demo5.bean;

import java.util.Set;
import java.util.TreeSet;

public class AskInfoBean implements BaseBean{

	private int askId;
	private int userId;
	private String content;
	private int refPrice;
	private long askDate;
	private int nameId;
	private boolean anonymity;
	private boolean opening;
	private int lookTimes;
	private CatNameBean catName;
	private UserInfoBean user;
	private Set<ApplyInfoBean> applys = new TreeSet<ApplyInfoBean>();
	public int getAskId() {
		return askId;
	}
	public void setAskId(int askId) {
		this.askId = askId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRefPrice() {
		return refPrice;
	}
	public void setRefPrice(int refPrice) {
		this.refPrice = refPrice;
	}
	public long getAskDate() {
		return askDate;
	}
	public void setAskDate(long askDate) {
		this.askDate = askDate;
	}
	public int getNameId() {
		return nameId;
	}
	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	public boolean isAnonymity() {
		return anonymity;
	}
	public void setAnonymity(boolean anonymity) {
		this.anonymity = anonymity;
	}
	public boolean isOpening() {
		return opening;
	}
	public void setOpening(boolean opening) {
		this.opening = opening;
	}
	public int getLookTimes() {
		return lookTimes;
	}
	public void setLookTimes(int lookTimes) {
		this.lookTimes = lookTimes;
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
	@Override
	public int compareTo(Object o) {
		AskInfoBean bean = (AskInfoBean) o;
		return (int) (bean.getAskDate()-this.askDate);
	}
}
