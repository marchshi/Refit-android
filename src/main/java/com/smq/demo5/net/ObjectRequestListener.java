package com.smq.demo5.net;


import com.google.gson.Gson;
import com.smq.demo5.activity.BaseActivity;
import com.smq.demo5.util.Utils;

/**
 * Created by shimanqian on 2017/5/3.
 */

public abstract class ObjectRequestListener<T> implements HttpRequest.OnRequestListener {

    @Override
    public void onSuccess(BaseActivity activity, String data) {
        Class cls = Utils.getActualTypeClass(getClass());
        T t = (T) new Gson().fromJson(data,cls);
        success(t);

    }

    @Override
    public void onFail(BaseActivity activity, String content) {
        if(content.equals(ERR_NO_NETWORK)){
            activity.showToast("无法连接网络");
        }
        fail(content);
    }

    public abstract void success(T t);

    public abstract void fail(String content);

}
