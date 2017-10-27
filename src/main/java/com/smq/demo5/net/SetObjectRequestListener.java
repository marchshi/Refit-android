package com.smq.demo5.net;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.demo5.activity.BaseActivity;

import java.util.Set;

/**
 * Created by shimanqian on 2017/5/3.
 */

public abstract class SetObjectRequestListener<T> implements HttpRequest.OnRequestListener {

    @Override
    public void onSuccess(BaseActivity activity, String data) {
        Set<T> list = new Gson().fromJson(data,new TypeToken<Set<T>>() {}.getType());
        success(list);
    }

    @Override
    public void onFail(BaseActivity activity, String content) {
        if(content.equals(ERR_NO_NETWORK)){
            activity.showToast("无法连接网络");
        }
        fail(content);
    }

    public abstract void success(Set<T> list);

    public abstract void fail(String content);

}
