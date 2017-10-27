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
import com.smq.mode2.activity.ApplyActivity;
import com.smq.mode2.entity.AskViewEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/6/14.
 */

public class AskAdapter extends BaseAdapter {

    private Context context;
    private List<AskViewEntity> list;

    public AskAdapter (Context context,List<AskViewEntity> list){
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
        View view = View.inflate(context, R.layout.item_ask,null);
        final AskViewEntity entity =  list.get(position);
        if("0".equals(entity.getNoName())){
            ((TextView)view.findViewById(R.id.tv_ask_name)).setText(entity.getName());
            Glide.with(context).load(Constant.helperUrl+entity.getTouxiang()).into((ImageView)view.findViewById(R.id.img_touxiang_small));
        }
        ((TextView)view.findViewById(R.id.tv_ask_price)).setText(entity.getRefPrice());
        ((TextView)view.findViewById(R.id.tv_ask_cont)).setText(entity.getAskCont());
        int mins = (int)( System.currentTimeMillis() - Long.parseLong(entity.getAskTime()) ) /60000;
        ((TextView)view.findViewById(R.id.tv_ask_time)).setText(mins+"");
        ((TextView)view.findViewById(R.id.tv_ask_apply)).setText(entity.getApplyNum());
        ((TextView)view.findViewById(R.id.tv_ask_pay)).setText(entity.getPayNum());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApplyActivity.class);
                intent.putExtra("askView",entity);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
