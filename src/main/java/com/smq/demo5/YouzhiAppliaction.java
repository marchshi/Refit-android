package com.smq.demo5;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.x;


/**
 * Created by shimanqian on 2017/7/19.
 */

public class YouzhiAppliaction extends Application {

    private static YouzhiAppliaction instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);

        EMClient.getInstance().init(this.getApplicationContext(),new EMOptions());

    }

    public static YouzhiAppliaction getInstance() {
        return instance;
    }


}
