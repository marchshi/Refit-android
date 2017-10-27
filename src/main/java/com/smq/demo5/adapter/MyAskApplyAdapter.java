package com.smq.demo5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.activity.SubmitOrderActivity;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.UserInfoBean;

import java.util.List;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class MyAskApplyAdapter extends BaseAdapter {

    Context context;
    List<ApplyInfoBean> list;
    public MyAskApplyAdapter(Context context, List<ApplyInfoBean> list){
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
        View view = View.inflate(context, R.layout.item_apply_my,null);
        final ApplyInfoBean apply =  list.get(position);
        final CatInfoBean cat = apply.getCat();
        UserInfoBean user = cat.getUser();
        Glide.with(context).load(Constant.helperUrl+user.getTouxiang()).into((ImageView)view.findViewById(R.id.cimg_apply_my_touxiang));
        ((TextView)view.findViewById(R.id.tv_apply_my_name)).setText(user.getName());
        ((TextView)view.findViewById(R.id.tv_apply_my_label)).setText(cat.getLabel());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubmitOrderActivity.class);
                intent.putExtra("cat",cat);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
