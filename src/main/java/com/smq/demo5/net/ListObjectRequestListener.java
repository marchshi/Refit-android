package com.smq.demo5.net;


import com.smq.demo5.activity.BaseActivity;

/**
 * Created by shimanqian on 2017/5/3.
 */

public abstract class ListObjectRequestListener implements HttpRequest.OnRequestListener {

    @Override
    public void onSuccess(BaseActivity activity, String data) {
        success(data);
    }

    @Override
    public void onFail(BaseActivity activity, String content) {
        if(content.equals(ERR_NO_NETWORK)){
            activity.showToast("无法连接网络");
        }
        fail(content);
    }

    public abstract void success(String s);

    public abstract void fail(String content);

}
