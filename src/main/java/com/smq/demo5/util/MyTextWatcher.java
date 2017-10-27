package com.smq.demo5.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by shimanqian on 2017/7/30.
 */

public class MyTextWatcher implements TextWatcher {

    private TextView tv;
    private int length;

    public MyTextWatcher(TextView tv ,int length){
        this.tv = tv;
        this.length = length;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tv.setText(length -s.length() +"");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
