package com.smq.demo5.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.widget.EditText;

/**
 * Created by shimanqian on 2017/8/11.
 */

public abstract class EditPriceHelper {

    public abstract void myTextChangeLister();

    public EditPriceHelper (final EditText et){
        //对金钱输入的数字范围进行控制
        et.setKeyListener(new DigitsKeyListener(false,false));
        et.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] numberChars={'1','2','3','4','5','6','7','8','9','0'};
                return numberChars;
            }
            @Override
            public int getInputType() {
                return android.text.InputType.TYPE_CLASS_PHONE;
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0;i<=s.length();i++){
                    if(i==s.length()){
                        if(i!=0)
                            et.setText("");
                        break;
                    }
                    if(s.charAt(i)!='0'){
                        if(i!=0)
                            et.setText(s.subSequence(i,s.length()));
                        break;
                    }
                }
                myTextChangeLister();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
