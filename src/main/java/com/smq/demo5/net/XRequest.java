package com.smq.demo5.net;


import android.util.Log;

import com.google.gson.Gson;
import com.smq.demo5.Constant;
import com.smq.demo5.activity.BaseActivity;
import com.smq.demo5.bean.BaseBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shimanqian on 2017/5/3.
 */

public class XRequest {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String FILE = "FILE";

    BaseActivity activity;
    String url;
    String method;
    Map<String,Object> params;
    HttpRequest.OnRequestListener requestListener;

    public XRequest(BaseActivity activity,String url){
        this.activity = activity;
        this.url = Constant.helperUrl + url;
        this.method = XRequest.GET;
        params = null;
    }

    public XRequest(BaseActivity activity, String url, String method, BaseBean bean){
        this.activity = activity;
        this.url = Constant.helperUrl + url;
        this.method = method;
        params = new HashMap<String,Object>();
        params.put("bean",new Gson().toJson(bean));
    }

    public XRequest(BaseActivity activity,String url,String method,Map<String,Object> params){
        this.activity = activity;
        this.url = Constant.helperUrl + url;
        this.method = method;
        Log.d("xxx",params.toString());
        this.params = params;
    }

    public XRequest setOnRequestListener(HttpRequest.OnRequestListener requestListener){
        this.requestListener = requestListener;
        Log.d("xxx",url);
        return this;
    }

    public void execute(){
        HttpRequest.getInstance().execute(this);
    }

}
