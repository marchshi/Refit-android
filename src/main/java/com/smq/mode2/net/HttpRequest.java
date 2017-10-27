package com.smq.mode2.net;


import com.smq.mode2.Constant;
import com.smq.mode2.activity.BaseActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by shimanqian on 2017/5/3.
 */

class HttpRequest {

    static interface OnRequestListener {
        public static int ERR_NO_NETWORK = 0;
        public void onSuccess(BaseActivity activity, String result);
        public void onFail(BaseActivity activity, int code);
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
        /*if(!Utils.checkConnection(task.activity)){
            //防波空指针
            if( task.requestListener != null){
                task.requestListener.onFail(task.activity, OnRequestListener.ERR_NO_NETWORK);
            }
            return;
        }*/
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
        }else if(XRequest.FILE.equals(task.method)){
            x.http().get(params, new Callback.CommonCallback<File>(){
                @Override
                public void onSuccess(File result) {
                    
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    private Callback.CommonCallback<String> getCallback(final XRequest task) {
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                try {
//                    JSONObject object = new JSONObject(result);
//                    //获取服务器返回的type和content
//                    String type = object.optJSONObject("message") != null ?object.getJSONObject("message").getString("type") :object.getString("type");
//                    String content = object.optJSONObject("message") != null ?object.getJSONObject("message").getString("content") :object.getString("content");
//                    //判断是否登陆成功
//                    if("error".equals(type) && "session.invaild".equals(content)){
//                        if(task.activity != null){
//                            task.activity.showOffLineDialog();
//                        }
//                    }else{
//                        task.requestListener.onSuccess(task.activity,result);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                    task.requestListener.onSuccess(task.activity,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    task.requestListener.onFail(task.activity,2);
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
