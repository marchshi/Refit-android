package com.smq.mode2.util;

import com.smq.mode2.Constant;
import com.smq.mode2.entity.CatTableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqian on 2017/7/5.
 */

public class CatMateUtil {

    public static boolean isContains(String text){
        return getMarch(text).isEmpty()?false:true;
    }

    public static List<CatTableEntity> getMarch(String text){
        List<CatTableEntity> list = new ArrayList<CatTableEntity>();
        for(CatTableEntity cat :Constant.category){
            if (cat.getName().contains(text))
                list.add(cat);
        }
        return list;
    }

    public static String getParentName(CatTableEntity cat){
        if("1".equals(cat.getLevel())){
            return cat.getName();
        }else{
            for(CatTableEntity entity :Constant.category){
                if("1".equals(entity.getLevel())){
                    if(entity.getId().equals(cat.getParentId())){
                        return entity.getName();
                    }
                }
            }
        }
        return null;
    }

}
