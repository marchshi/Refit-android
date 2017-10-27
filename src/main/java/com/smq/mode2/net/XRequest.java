package com.smq.mode2.net;

import android.util.Log;

import com.smq.mode2.Constant;
import com.smq.mode2.activity.BaseActivity;

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

    public XRequest(BaseActivity activity,String url,String method,Map<String,Object> params){
        this.activity = activity;
        this.url = Constant.helperUrl + url;
        this.method = method;
        Log.d("eee",params.toString());
        this.params = params;
    }

    public XRequest setOnRequestListener(HttpRequest.OnRequestListener requestListener){
        this.requestListener = requestListener;
        Log.d("ccc",url);
        return this;
    }

    public void execute(){
        HttpRequest.getInstance().execute(this);
    }

}
