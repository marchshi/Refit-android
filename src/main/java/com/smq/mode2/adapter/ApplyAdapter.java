package com.smq.mode2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.ApplyActivity;
import com.smq.mode2.entity.ApplyViewEntity;
import com.smq.mode2.entity.AskViewEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/6/21.
 */

public class ApplyAdapter extends BaseAdapter {

    private ApplyActivity context;
    private AskViewEntity entity;
    private List<ApplyViewEntity> list;


    public ApplyAdapter (ApplyActivity context, AskViewEntity entity, List<ApplyViewEntity> list){
        this.context = context;
        this.entity = entity;
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
            view = View.inflate(context, R.layout.item_apply_ask,null);
            if("0".equals(entity.getNoName())){
                ((TextView)view.findViewById(R.id.tv_ask_name_1)).setText(entity.getName());
                Glide.with(context).load(Constant.helperUrl+entity.getTouxiang()).into((ImageView)view.findViewById(R.id.img_touxiang_small_1));
            }
            ((TextView)view.findViewById(R.id.tv_ask_price_1)).setText(entity.getRefPrice());
            ((TextView)view.findViewById(R.id.tv_ask_cat_1)).setText(entity.getCategory());
            ((TextView)view.findViewById(R.id.tv_ask_cont_1)).setText(entity.getAskCont());
            int mins = (int)( System.currentTimeMillis() - Long.parseLong(entity.getAskTime()) ) /60000;
            ((TextView)view.findViewById(R.id.tv_apply_time)).setText(mins+"");
            ((TextView)view.findViewById(R.id.tv_apply_applynum)).setText(entity.getApplyNum());
            ((TextView)view.findViewById(R.id.tv_apply_paynum)).setText(entity.getPayNum());
            TextView apply = (TextView) view.findViewById(R.id.tv_apply_btn);
            apply.setActivated(true);
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.checkApply();
                }
            });
        }else {
            view = View.inflate(context, R.layout.item_apply,null);
            ApplyViewEntity applyViewEntity = list.get(position-1);
            ((TextView)view.findViewById(R.id.tv_apply_item_name)).setText(applyViewEntity.getName());
            Glide.with(context).load(Constant.helperUrl+applyViewEntity.getTouxiang()).into((ImageView)view.findViewById(R.id.img_apply_item_touxiang));
            ((TextView)view.findViewById(R.id.tv_apply_item_label)).setText(applyViewEntity.getLabel());
            ((TextView)view.findViewById(R.id.tv_apply_item_desc)).setText(applyViewEntity.getCatDesc());
            ((TextView)view.findViewById(R.id.tv_apply_item_price)).setText(applyViewEntity.getPrice());
            if(applyViewEntity.getStimes()!=null)
                ((TextView)view.findViewById(R.id.tv_apply_item_stimes)).setText(applyViewEntity.getStimes());
        }
        return view;
    }


}
