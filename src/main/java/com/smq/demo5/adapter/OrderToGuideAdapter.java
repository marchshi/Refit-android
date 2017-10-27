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
import com.smq.demo5.fragment.ToGuideFragment;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class OrderToGuideAdapter extends BaseAdapter {

    ToGuideFragment context;
    List<MyOrderEntity> list;
    public OrderToGuideAdapter(ToGuideFragment context, List<MyOrderEntity> list){
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
        View view = View.inflate(context.activity, R.layout.item_order_get_guide,null);
        final MyOrderEntity order =  list.get(position);
        Glide.with(context).load(Constant.helperUrl+order.getTouxiang()).into((ImageView)view.findViewById(R.id.cimg_order_guider_touxiang));
        ((TextView)view.findViewById(R.id.tv_order_guider_name)).setText(order.getName());
        ((TextView)view.findViewById(R.id.tv_order_guider_label)).setVisibility(View.GONE);
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
        view.findViewById(R.id.img_order_delete).setVisibility(View.GONE);
        view.findViewById(R.id.img_order_finish).setVisibility(View.VISIBLE);
        view.findViewById(R.id.img_order_form).setVisibility(View.VISIBLE);
        view.findViewById(R.id.img_order_chat).setVisibility(View.VISIBLE);
        view.findViewById(R.id.img_order_comment).setVisibility(View.GONE);
        switch (order.getStatus()){
            case 1:
                tv_order_status.setText("等待导购查看");
                ((ImageView)view.findViewById(R.id.img_order_finish)).setImageResource(R.drawable.icon_order_finish_black);
                break;
            case 2:
                tv_order_status.setText("处理中");
                ((ImageView)view.findViewById(R.id.img_order_finish)).setImageResource(R.drawable.icon_order_finish);
                break;
            case 3:
                tv_order_status.setText("等待确认交易");
                ((ImageView)view.findViewById(R.id.img_order_finish)).setImageResource(R.drawable.icon_order_finish);
                break;
            case 5:
                tv_order_status.setText("申诉中");
                ((ImageView)view.findViewById(R.id.img_order_finish)).setImageResource(R.drawable.icon_order_finish_black);
                break;
        }

        //聊天按钮点击事件
        view.findViewById(R.id.img_order_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map params = new HashMap();
                params.put("orderId",order.getOrderId());
                params.put("status",2);
                new XRequest(context.activity,"orderStatus",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<String>() {
                    @Override
                    public void success(String s) {
                    }

                    @Override
                    public void fail(String content) {

                    }
                }).execute();
            }
        });
        //表单按钮点击事件
        view.findViewById(R.id.img_order_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map params = new HashMap();
                params.put("orderId",order.getOrderId());
                params.put("status",2);
                new XRequest(context.activity,"orderStatus",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<String>() {
                    @Override
                    public void success(String s) {
                    }

                    @Override
                    public void fail(String content) {

                    }
                }).execute();

                //TODO 查看表单

            }
        });
        //结束导购按钮点击事件
        view.findViewById(R.id.img_order_finish).setOnClickListener(new View.OnClickListener() {
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
