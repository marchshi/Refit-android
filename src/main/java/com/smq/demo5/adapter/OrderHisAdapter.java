package com.smq.demo5.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.entity.MyOrderEntity;
import com.smq.demo5.fragment.HisOrderFragment;
import com.smq.demo5.util.Utils;

import java.util.List;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class OrderHisAdapter extends BaseAdapter {

    HisOrderFragment context;
    List<MyOrderEntity> list;
    public OrderHisAdapter(HisOrderFragment context, List<MyOrderEntity> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context.activity, R.layout.item_order_his_guide,null);
        final MyOrderEntity order =  list.get(position);
        Glide.with(context).load(Constant.helperUrl+order.getTouxiang()).into((ImageView)view.findViewById(R.id.cimg_order_guider_touxiang));
        ((TextView)view.findViewById(R.id.tv_order_guider_name)).setText(order.getName());
        if("to".equals(order.getType())){
            ((TextView)view.findViewById(R.id.tv_order_guider_label)).setVisibility(View.GONE);
        }else if("get".equals(order.getType())){
            ((TextView)view.findViewById(R.id.tv_order_guider_label)).setText(order.getLabel());
        }
        CatNameBean catName = Utils.getCatNameById(order.getNameId());
        ((TextView)view.findViewById(R.id.tv_cat_FirstCatName)).setText(catName.getParentName());
        ((TextView)view.findViewById(R.id.tv_cat_SecondCatName)).setText(catName.getChildName());

        ((TextView)view.findViewById(R.id.tv_order_price1)).setText(Utils.moneyFacotry(order.getPrice()));
        ((TextView)view.findViewById(R.id.tv_order_price2)).setText(Utils.moneyFacotry(order.getPrice()));

        if(order.isCont()){
            ((TextView)view.findViewById(R.id.tv_order_cont)).setText("可追问");
        }else {
            ((TextView)view.findViewById(R.id.tv_order_cont)).setText("不追问");
        }
        switch (order.getServerModel()){
            case 1:
                ((TextView)view.findViewById(R.id.tv_order_smodel)).setText("实时回答");
                break;
            case 2:
                ((TextView)view.findViewById(R.id.tv_order_smodel)).setText("闲时回答");
                break;
            case 3:
                ((TextView)view.findViewById(R.id.tv_order_smodel)).setText("预约回答:"+order.getScheTime());
                break;
        }

        TextView tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);
        view.findViewById(R.id.img_order_delete).setVisibility(View.VISIBLE);
        view.findViewById(R.id.img_order_finish).setVisibility(View.GONE);
        view.findViewById(R.id.img_order_form).setVisibility(View.VISIBLE);
        view.findViewById(R.id.img_order_chat).setVisibility(View.VISIBLE);
        view.findViewById(R.id.img_order_comment).setVisibility(View.VISIBLE);
        switch (order.getStatus()){
            case 4:
                tv_order_status.setText("订单已完成");
                ((ImageView)view.findViewById(R.id.img_order_finish)).setImageResource(R.drawable.icon_order_finish_black);
                break;
            case 6:
                tv_order_status.setText("订单已取消");
                ((ImageView)view.findViewById(R.id.img_order_comment)).setVisibility(View.GONE);
                break;
        }

        //结束导购按钮点击事件
        view.findViewById(R.id.img_order_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //聊天按钮点击事件
        view.findViewById(R.id.img_order_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //表单按钮点击事件
        view.findViewById(R.id.img_order_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 查看表单

            }
        });
        //结束导购按钮点击事件
        view.findViewById(R.id.img_order_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //申诉按钮点击事件
        view.findViewById(R.id.tv_order_appeal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
