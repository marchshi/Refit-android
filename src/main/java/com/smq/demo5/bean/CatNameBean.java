package com.smq.demo5.bean;

public class CatNameBean implements BaseBean {
	
	private int nameId;
	private String childName;
	private String parentName;
	public int getNameId() {
		return nameId;
	}
	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Override
	public int compareTo(Object o) {
		CatNameBean bean = (CatNameBean) o;
		return this.nameId - bean.getNameId();
	}
}
