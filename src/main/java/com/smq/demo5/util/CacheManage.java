package com.smq.demo5.util;

import android.content.Context;

import com.smq.demo5.YouzhiAppliaction;

public class CacheManage {
    private static SimpleXmlAccessor accessor;
    private static final String NAME = "Cache";

    private static SimpleXmlAccessor getAccessor() {
        if (accessor == null) {
            accessor = new SimpleXmlAccessor(YouzhiAppliaction.getInstance(), NAME, Context.MODE_APPEND);
        }
        return accessor;
    }
    // 保存字符串
    public static void save(String name, String content) {
        getAccessor().putString(name, content);
    }
    // 读取字符串
    public static String get(String name, String defaultName) {
        return getAccessor().getString(name);
    }
    public static void remove(String key) {
        getAccessor().remove(key);
    }
    public static void removeAll() {
        getAccessor().removeAll();
    }
    public static boolean isFirstLoad() {
        return getAccessor().getBoolean("firstLoad",true);
    }

    public static void setFirstLoad(boolean firstLoad) {
        getAccessor().putBoolean("firstLoad", firstLoad);
    }

    public static void setAuto(boolean auto) {
        getAccessor().putBoolean("auto", auto);
    }

    public static boolean isAuto() {
        return getAccessor().getBoolean("auto",false);
    }

    public static int getUserId() {
        return getAccessor().getInt("userId");
    }

    public static void setUserId(int userId) {
        getAccessor().putInt("userId", userId);
    }

    public static String getTel() {
        return getAccessor().getString("tel");
    }

    public static void setTel(String tel) {
        getAccessor().putString("tel", tel);
    }

    public static String getVerify() {
        return getAccessor().getString("verify");
    }

    public static void setVerify(String verify) {
        getAccessor().putString("verify", verify);
    }

    public static String getUuid() {
        return getAccessor().getString("uuid");
    }

    public static void setUuid(String uuid) {
        getAccessor().putString("uuid", uuid);
    }

    public static String getSearchHis() {
        return getAccessor().getString("searchHis");
    }

    public static void setSearchHis(String searchHis) {
        getAccessor().putString("searchHis", searchHis);
    }
}
