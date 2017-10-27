package com.smq.demo5.util;

import java.util.Map;

public interface XmlAccessor {
	XmlAccessor put(String key, boolean value);

	XmlAccessor put(String key, int value);

	XmlAccessor put(String key, long value);

	XmlAccessor put(String key, float value);

	XmlAccessor put(String key, String value);

	void end();
	
	void removeAll();
	
	XmlAccessor remove(String key);
	
	void removeValue(String key);

	void putBoolean(String key, boolean value);

	void putInt(String key, int value);

	void putLong(String key, long value);

	void putFloat(String key, float value);

	void putString(String key, String value);

	Map<String, ?> getAll();

	boolean getBoolean(String key,boolean def);

	int getInt(String key);

	long getLong(String key);

	float getFloat(String key);

	String getString(String key);
}
