package com.smq.demo5.net;


import android.util.Log;

import com.smq.demo5.Constant;
import com.smq.demo5.activity.BaseActivity;
import com.smq.demo5.json.BaseJson;
import com.smq.demo5.json.ResponseParser;
import com.smq.demo5.util.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by shimanqian on 2017/5/3.
 */

class HttpRequest {

    static interface OnRequestListener {
        public static String ERR_NO_NETWORK = "0";
        public void onSuccess(BaseActivity activity, String result);
        public void onFail(BaseActivity activity, String content);
    }

    private static class HttpFetcherFactory{
        private static HttpRequest instance = instance();
    }

    private static HttpRequest instance(){
        return new HttpRequest();
    }

    public static HttpRequest getInstance(){
        return HttpFetcherFactory.instance;
    }

    public void execute(XRequest task){
        //每次发送网络请求前都要判断网络连接情况
        if(!Utils.checkConnection(task.activity)){
            if( task.requestListener != null){
                task.requestListener.onFail(task.activity, OnRequestListener.ERR_NO_NETWORK);
            }
            return;
        }
        //创建一个回调方法
        Callback.CommonCallback<String> callback = getCallback(task);
        RequestParams params = new RequestParams(task.url);
        //获取task中的参数
        if (task.params != null && !task.params.isEmpty()) {
            for (String key : task.params.keySet()) {
                Object value = task.params.get(key);
                if (value == null) {
                    continue;
                }
                params.addParameter(key, value);
            }
        }

        params.addHeader("x-uid", Constant.uid);
        params.addHeader("x-app", Constant.app);
        params.addHeader("x-key", Constant.xkey);
        if (XRequest.GET.equals(task.method)) {
            x.http().get(params,callback);
        } else if (XRequest.POST.equals(task.method) || XRequest.FILE.equals(task.method)) {
            x.http().post(params, callback);
        }
    }

    private Callback.CommonCallback<String> getCallback(final XRequest task) {
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result",result);
                //解耦和 对返回数据进行判断
                BaseJson response = ResponseParser.toBaseResponse(result);
                if (response == null) {
                    task.requestListener.onFail(task.activity,"2");
                } else if ("success".equals(response.getType())) {
                    task.requestListener.onSuccess(task.activity,response.getData());
                } else if ("error".equals(response.getType())) {
                    task.requestListener.onFail(task.activity,response.getContent());
                    task.activity.showToast(response.getContent());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                task.requestListener.onFail(task.activity,"1");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        };
        return callback;
    }

}
