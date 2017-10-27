package com.smq.demo5.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqian on 2017/6/7.
 */

public class FormItemBean implements BaseBean {

    private int id;
    private String title;
    private int type;
    private List<String> options = new ArrayList<String>();
    private int[] num;
    private int initLine;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int[] getNum() {
        return num;
    }

    public void setNum(int[] num) {
        this.num = num;
    }

    public int getInitLine() {
        return initLine;
    }

    public void setInitLine(int initLine) {
        this.initLine = initLine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public int compareTo(Object o) {
		FormItemBean bean = (FormItemBean) o;
		return this.id - bean.id;
	}
}
