package com.smq.mode2;

import com.smq.mode2.entity.CatTableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqian on 2017/5/3.
 */

public class Constant {
    //用于识别设备和验证请求
    public static String app = "com.tiaohuo.assistant";
    public static String uid;
    public static String xkey;

    //需要登录的请求码
    public static final int NEED_LOGIN = 87;
    //登录成功的返回码
    public static final int LOGIN_SUCCESS = 88;

    //开启相册请求图片的请求码
    public static final int NEED_IMAGE = 89;

    //需要创建申请类目的请求码
    public static final int NEED_APPLY_CAT = 90;

    //从类目表中选择类目的请求码
    public static final int CHOOSE_CAT = 91;

    //从类目表中进行模糊搜索类目的请求码
    public static final int SEARCH_CAT = 92;

    //保存应用的登陆状态
    public static String userId = "0";

    //保存应用的登陆状态
    public static boolean isLogin = false;

    //保存用户的搜索记录
    public static List<CatTableEntity> search_his = new ArrayList<>();

    //保存全部的类目信息
    public static List<CatTableEntity> category = new ArrayList<>();

//    public static final String helperUrl = "http://www.youzhidg.cn:8080/mode1/";
//    public static final String helperUrl = "http://10.0.2.2:8080/mode2/";
    public static final String helperUrl = "http://www.youzhidg.cn:8080/mode2/";
}
