package com.smq.demo5.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.smq.demo5.activity.BaseActivity;

/**
 * Created by shimanqian on 2017/7/18.
 */

public class BaseFragment extends Fragment {

    //所有Fragment的上下文对象
    public BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

}
