package com.smq.mode2.net;


import com.smq.mode2.activity.BaseActivity;

/**
 * Created by shimanqian on 2017/5/3.
 */

public abstract class BaseRequestListener implements HttpRequest.OnRequestListener {

    public void onSuccess(BaseActivity activity, String result) {
            success(result);
    }

    public void onFail(BaseActivity activity, int code) {
        fail(code);
    }

    protected abstract void success(String t);

    protected abstract void fail(int code);

}
