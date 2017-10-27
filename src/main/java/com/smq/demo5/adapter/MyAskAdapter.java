package com.smq.demo5.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.demo5.R;
import com.smq.demo5.activity.BaseActivity;
import com.smq.demo5.activity.MyAskActivity;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class MyAskAdapter extends BaseAdapter {

    ListView lv_ask_list_apply;
    MyAskApplyAdapter applyAdapter;

    BaseActivity context;
    List<AskInfoBean> list;
    public MyAskAdapter(BaseActivity context, List<AskInfoBean> list){
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
        View view = View.inflate(context, R.layout.item_ask_my,null);
        lv_ask_list_apply = (ListView) view.findViewById(R.id.lv_ask_list_apply);
        final AskInfoBean bean =  list.get(position);
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
        int applyNum = bean.getApplys().size();
        ((TextView)view.findViewById(R.id.tv_ask_apply)).setText(applyNum+"");
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
                Intent intent = new Intent(context, MyAskActivity.class);
                intent.putExtra("askId",bean.getAskId());
                context.startActivity(intent);
            }
        });
        if(applyNum == 0){
            lv_ask_list_apply.setVisibility(View.GONE);
        }else {
            view.findViewById(R.id.view_ask_my).setVisibility(View.VISIBLE);
            lv_ask_list_apply.setVisibility(View.VISIBLE);
            List<ApplyInfoBean> applys = new ArrayList<>(bean.getApplys());
            applyAdapter = new MyAskApplyAdapter(context,applys);
            lv_ask_list_apply.setAdapter(applyAdapter);
        }
        return view;
    }
}
