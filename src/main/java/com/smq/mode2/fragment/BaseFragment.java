package com.smq.mode2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.smq.mode2.activity.BaseActivity;


public class BaseFragment extends Fragment {
    public BaseActivity activity;//所有Fragment的上下文对象

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }
}
