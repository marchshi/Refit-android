package com.smq.demo5.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public class SimpleXmlAccessor implements XmlAccessor {
	SharedPreferences sp;
	private Editor editor;

	public SimpleXmlAccessor(Context ctx, String name, int mode) {
		sp = ctx.getSharedPreferences(name, mode);
		editor = sp.edit();
	}

	public XmlAccessor put(String key, boolean value) {
		editor.putBoolean(key, value);
		return this;
	}

	public XmlAccessor put(String key, int value) {
		editor.putInt(key, value);
		return this;
	}

	public XmlAccessor put(String key, long value) {
		editor.putLong(key, value);
		return this;
	}

	public XmlAccessor put(String key, float value) {
		editor.putFloat(key, value);
		return this;
	}

	public XmlAccessor put(String key, String value) {
		editor.putString(key, value);
		return this;
	}

	public void end() {
		editor.commit();
	}

	public void removeAll() {
		editor.clear().commit();
	}

	public XmlAccessor remove(String key) {
		editor.remove(key);
		editor.commit();
		return this;
	}

	public void removeValue(String key) {
		editor.remove(key).commit();
	}

	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value).commit();
	}

	public void putInt(String key, int value) {
		editor.putInt(key, value).commit();
	}

	public void putLong(String key, long value) {
		editor.putLong(key, value).commit();
	}

	public void putFloat(String key, float value) {
		editor.putFloat(key, value).commit();
	}

	public void putString(String key, String value) {
		editor.putString(key, value).commit();
	}

	public void putDouble(String key, double value) {
		editor.putString(key, String.valueOf(value)).commit();
	}

	public Map<String, ?> getAll() {
		return sp.getAll();
	}

	public boolean getBoolean(String key,Boolean def) {
		return sp.getBoolean(key, def);
	}

	public int getInt(String key) {
		return sp.getInt(key, 0);
	}

	public long getLong(String key) {
		return sp.getLong(key, 0);
	}

	public float getFloat(String key) {
		return sp.getFloat(key, 0);
	}

	public String getString(String key) {
		return sp.getString(key, null);
	}

	public boolean getBoolean(String key,boolean value) {
		return sp.getBoolean(key, value);
	}

	public double getDouble(String key) {
		String tmp = sp.getString(key, "");
		if(tmp.equals("")){
			return 0.0;
		}
		double result = 0.0;
		try{
			result = Double.parseDouble(tmp);
		}catch(Exception e){
			result = 0.0;
		}
		return result;
	}
}
