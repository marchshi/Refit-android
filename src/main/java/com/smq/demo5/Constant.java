package com.smq.demo5;

import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqian on 2017/7/19.
 */

public class Constant {

    //用于识别设备和验证请求
    public static String app = "com.smq.demo5";
    public static String uid;
    public static String xkey;

    //获取图片的请求码
    public static final int NEED_IMAGE = 1;
    //需要裁剪图片的请求码
    public static final int NEED_CROP = 2;

    //修改用户信息的请求码
    public static final int EDIT_INFO = 3;
    //修改用户信息成功的返回码
    public static final int EDIT_SUCCESS = 4;

    //修改类目信息的请求码
    public static final int EDIT_CAT = 5;
    //修改类目信息成功的返回码
    public static final int CAT_EDIT_SUCCESS = 6;

    //需要登录的请求码
    public static final int NEED_LOGIN = 87;
    //登录成功的返回码
    public static final int LOGIN_SUCCESS = 88;

    //需要创建申请类目的请求码
    public static final int NEED_APPLY_CAT = 90;

    //从类目表中选择类目的请求码
    public static final int CHOOSE_CAT = 91;

    //从类目表中进行模糊搜索类目的请求码
    public static final int SEARCH_CAT = 92;

    //保存当前用户的userId
    public static int userId = 0;

    //保存应用的登陆状态
    public static boolean isLogin = false;
    //控制登陆界面和填写个人信息界面的信号量
    public static boolean loginRestart = true;
    //访问网络服务器的请求地址前缀
//    public static final String helperUrl = "http://10.0.2.2:8080/demo5/";
    public static final String helperUrl = "http://www.youzhidg.cn:8080/demo5/";

    //保存类目表的信息
    public static List<CatNameBean> catList = new ArrayList<CatNameBean>();
    //保存首页提问的信息
    public static List<AskInfoBean> askList = new ArrayList<AskInfoBean>();
    //保存用户的个人信息
    public static UserInfoBean info = new UserInfoBean();
    //保存用户的账户信息
    public static AccountInfoBean account = new AccountInfoBean();
    //保存用户的提问信息 提问表+申请表
    public static List<AskInfoBean> asks = new ArrayList<>();
    //保存用户的类目信息
    public static List<CatInfoBean> cats = new ArrayList<>();
    //保存用户的订单信息
    public static List<OrderInfoBean> orders = new ArrayList<>();

}
