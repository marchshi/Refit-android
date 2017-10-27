package com.smq.demo5.util;

import com.smq.demo5.bean.OrderInfoBean;

import java.util.List;

/**
 * Created by shimanqian on 2017/7/28.
 */

public class AcountHelper {

    public List<OrderInfoBean> orders;

    public AcountHelper(List<OrderInfoBean> orders){
        this.orders=orders;
    }

    public int getServerTimes(){
        int i =0;
        for(OrderInfoBean order:orders){
            if(order.getStatus() == 4){
                i++;
            }
        }
        return i;
    }

    public int getTotalMoney(){
        int i =0;
        for(OrderInfoBean order:orders){
            if(order.getStatus() == 4){
                i+=order.getPrice();
            }
        }
        return i;
    }

}
