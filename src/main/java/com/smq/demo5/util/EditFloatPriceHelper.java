package com.smq.demo5.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.widget.EditText;

/**
 * Created by shimanqian on 2017/8/11.
 */

public abstract class EditFloatPriceHelper {

    public abstract void myTextChangeLister();

    public EditFloatPriceHelper(final EditText et){
        //对金钱输入的数字范围进行控制
        et.setKeyListener(new DigitsKeyListener(false,false));
        et.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] numberChars={'1','2','3','4','5','6','7','8','9','0','.'};
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
                //0 将最前面的0删去 !! 0.除外
                //1 如果第一位为小数点的话 则再前面加一个0
                //2 第一个小数点后最多保留两位小数
                //3 最多出现一个小数点
                int dot = -1;
                for(int i=0;i<s.length();i++){
                    if(s.charAt(i)=='.'){
                        if(dot == -1){
                            dot = i;
                        }else {
                            et.setText(s.subSequence(0,i));
                            et.setSelection(et.getText().toString().length());
                            break;
                        }
                        if(dot==0 && count>before){
                            et.setText("0"+s.subSequence(i,s.length()));
                            et.setSelection(et.getText().toString().length());
                            break;
                        }
                    }
                    //最多输入两位小数
                    if(dot != -1 && i>=dot+3){
                        et.setText(s.subSequence(0,dot+3));
                        et.setSelection(et.getText().toString().length());
                        break;
                    }
                    if (s.charAt(0)=='0' && s.length() >=2 && s.charAt(1)!='.'){
                        et.setText(s.subSequence(1,s.length()));
                        et.setSelection(et.getText().toString().length());
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
