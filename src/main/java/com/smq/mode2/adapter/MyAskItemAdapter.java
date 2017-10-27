package com.smq.mode2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.EditAskActivity;
import com.smq.mode2.entity.ApplyViewEntity;
import com.smq.mode2.entity.AskViewEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/6/22.
 */

public class MyAskItemAdapter extends BaseAdapter {

    private Context context;
    private List<ApplyViewEntity> list;
    private AskViewEntity askView;
    public MyAskItemAdapter (Context context,AskViewEntity askView,List<ApplyViewEntity> list){
        this.context = context;
        this.askView = askView;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size()+1;
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
        View view = null;
        if(position ==0){
            view = View.inflate(context, R.layout.item_myask,null);
            ((TextView)view.findViewById(R.id.tv_myask_cont)).setText(askView.getAskCont());
            long mins = (System.currentTimeMillis() - Long.parseLong(askView.getAskTime())) /60000;
            ((TextView)view.findViewById(R.id.tv_myask_mins)).setText(mins + "");
            ((TextView)view.findViewById(R.id.tv_myask_apply)).setText(askView.getApplyNum());
            ((TextView)view.findViewById(R.id.tv_myask_pay)).setText(askView.getPayNum());
            ((TextView)view.findViewById(R.id.tv_myask_price)).setText(askView.getRefPrice());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditAskActivity.class);
                    intent.putExtra("askView",askView);
                    context.startActivity(intent);
                }
            });
        }else{
            view = View.inflate(context, R.layout.item_apply,null);
            ApplyViewEntity entity = list.get(position-1);
            ((TextView)view.findViewById(R.id.tv_apply_item_name)).setText(entity.getName());
            Glide.with(context).load(Constant.helperUrl+entity.getTouxiang()).into((ImageView)view.findViewById(R.id.img_apply_item_touxiang));
            ((TextView)view.findViewById(R.id.tv_apply_item_label)).setText(entity.getLabel());
            ((TextView)view.findViewById(R.id.tv_apply_item_desc)).setText(entity.getCatDesc());
            ((TextView)view.findViewById(R.id.tv_apply_item_price)).setText(entity.getPrice());
            if(entity.getStimes()!=null)
                ((TextView)view.findViewById(R.id.tv_apply_item_stimes)).setText(entity.getStimes());
        }
        return view;
    }
}
