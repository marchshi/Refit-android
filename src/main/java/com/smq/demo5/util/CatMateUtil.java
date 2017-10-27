package com.smq.demo5.util;

import com.smq.demo5.Constant;
import com.smq.demo5.bean.CatNameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqian on 2017/7/5.
 */

public class CatMateUtil {

    public static boolean isContains(String text){
        return getMarch(text).isEmpty()?false:true;
    }

    public static List<CatNameBean> getMarch(String text){
        List<CatNameBean> list = new ArrayList<CatNameBean>();
        List<CatNameBean> catNameBeen = Constant.catList;
        for(CatNameBean cat : catNameBeen){
            if (cat.getChildName().contains(text))
                list.add(cat);
        }
        return list;
    }
}
