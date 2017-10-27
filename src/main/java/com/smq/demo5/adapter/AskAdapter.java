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
import com.smq.demo5.activity.ApplyActivity;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;

import java.util.List;


/**
 * Created by shimanqian on 2017/6/14.
 */

public class AskAdapter extends BaseAdapter {

    private Context context;
    private List<AskInfoBean> list;

    public AskAdapter(Context context, List<AskInfoBean> list){
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_ask,null);
        final AskInfoBean bean =  list.get(position);
        UserInfoBean user = bean.getUser();
        if(!bean.isAnonymity()){
            ((TextView)view.findViewById(R.id.tv_ask_name)).setText(user.getName());
            Glide.with(context).load(Constant.helperUrl+user.getTouxiang()).into((ImageView)view.findViewById(R.id.cimg_touxiang_shouye));
        }
        ((TextView)view.findViewById(R.id.tv_ask_price)).setText(MoneyFactory.longToIntString(bean.getRefPrice()));
        CatNameBean catName;
        if(bean.getCatName() == null){
            catName = Utils.getCatNameById(bean.getNameId());
        }else {
            catName = bean.getCatName();
        }
        ((TextView)view.findViewById(R.id.tv_cat_FirstCatName)).setText(catName.getParentName());
        ((TextView)view.findViewById(R.id.tv_cat_SecondCatName)).setText(catName.getChildName());
        ((TextView)view.findViewById(R.id.tv_ask_cont)).setText(bean.getContent());
        ((TextView)view.findViewById(R.id.tv_ask_time)).setText(Utils.getTimeOut(bean.getAskDate()));
        int applys = bean.getApplys().size();
        ((TextView)view.findViewById(R.id.tv_ask_apply)).setText(applys+"");
        int pays = 0;
        for(ApplyInfoBean apply:bean.getApplys()){
            if(apply.getStatus() == 3)
                pays++;
        }
        if (pays == 0){
            view.findViewById(R.id.tv_ask_pay).setVisibility(View.GONE);
            view.findViewById(R.id.tv_ask_pay_text).setVisibility(View.GONE);
        }else {
            ((TextView)view.findViewById(R.id.tv_ask_pay)).setText(pays+"");
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApplyActivity.class);
                intent.putExtra("askInfo",bean);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
