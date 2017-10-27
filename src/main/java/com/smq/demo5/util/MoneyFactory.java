package com.smq.demo5.util;

/**
 * Created by shimanqian on 2017/8/19.
 */

public class MoneyFactory {

    public static long intToLong(int i){
        return i*100;
    }

    public static String longToIntString(long l){
        return (int)Math.floor(l/100)+"";
    }
}
