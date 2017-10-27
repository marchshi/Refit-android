package com.smq.demo5.bean;

public class ApplyInfoBean implements BaseBean{
	
	private int applyId;
	private int askId;
	private int catId;
	private long applyDate;
	private int status;
	private long lookDate;
	private long payDate;
	private AskInfoBean ask;
	private CatInfoBean cat;
	
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public int getAskId() {
		return askId;
	}
	public void setAskId(int askId) {
		this.askId = askId;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public long getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(long applyDate) {
		this.applyDate = applyDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getLookDate() {
		return lookDate;
	}
	public void setLookDate(long lookDate) {
		this.lookDate = lookDate;
	}
	public long getPayDate() {
		return payDate;
	}
	public void setPayDate(long payDate) {
		this.payDate = payDate;
	}
	public AskInfoBean getAsk() {
		return ask;
	}
	public void setAsk(AskInfoBean ask) {
		this.ask = ask;
	}
	public CatInfoBean getCat() {
		return cat;
	}
	public void setCat(CatInfoBean cat) {
		this.cat = cat;
	}
	@Override
	public int compareTo(Object o) {
		ApplyInfoBean bean = (ApplyInfoBean) o;
		return (int) (bean.getApplyDate() - this.applyDate);
	}
}
