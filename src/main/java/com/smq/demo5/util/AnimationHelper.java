package com.smq.demo5.util;

import android.app.Activity;

import com.smq.demo5.R;

/**
 * Created by shimanqian on 2017/8/11.
 */

public class AnimationHelper {

    public static void start(Activity activity){
        activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
    }

    public static void finish(Activity activity){
        activity.overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

}
