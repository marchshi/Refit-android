package com.smq.mode2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smq.mode2.R;
import com.smq.mode2.activity.MyAskActivity;
import com.smq.mode2.entity.AskViewEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/6/21.
 */

public class MyAskAdapter extends BaseAdapter {

    private Context context;
    private List<AskViewEntity> list;

    public MyAskAdapter (Context context,List<AskViewEntity> list){
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
        View view = View.inflate(context, R.layout.item_myask,null);
        final AskViewEntity entity = list.get(position);
        ((TextView)view.findViewById(R.id.tv_myask_cont)).setText(entity.getAskCont());
        long mins = (System.currentTimeMillis() - Long.parseLong(entity.getAskTime())) /60000;
        ((TextView)view.findViewById(R.id.tv_myask_mins)).setText(mins + "");
        ((TextView)view.findViewById(R.id.tv_myask_apply)).setText(entity.getApplyNum());
        ((TextView)view.findViewById(R.id.tv_myask_pay)).setText(entity.getPayNum());
        ((TextView)view.findViewById(R.id.tv_myask_price)).setText(entity.getRefPrice());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyAskActivity.class);
                intent.putExtra("askView",entity);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
