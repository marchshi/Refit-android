package com.smq.demo5.adapter;

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
import com.smq.demo5.activity.SubmitOrderActivity;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;

import java.util.List;

/**
 * Created by shimanqian on 2017/8/2.
 */

public class ApplyAdapter extends BaseAdapter {

    private ApplyActivity context;
    private AskInfoBean bean;
    private List<ApplyInfoBean> list;


    public ApplyAdapter (ApplyActivity context, AskInfoBean bean, List<ApplyInfoBean> list){
        this.context = context;
        this.bean = bean;
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
            UserInfoBean user = bean.getUser();
            if(!bean.isAnonymity()){
                ((TextView)view.findViewById(R.id.tv_ask_name_1)).setText(user.getName());
                Glide.with(context).load(Constant.helperUrl+user.getTouxiang()).into((ImageView)view.findViewById(R.id.img_touxiang_small_1));
            }
            ((TextView)view.findViewById(R.id.tv_ask_price_1)).setText(MoneyFactory.longToIntString(bean.getRefPrice()));
            CatNameBean catName;
            if(bean.getCatName() == null){
                catName = Utils.getCatNameById(bean.getNameId());
            }else {
                catName = bean.getCatName();
            }
            ((TextView)view.findViewById(R.id.tv_cat_FirstCatName)).setText(catName.getParentName());
            ((TextView)view.findViewById(R.id.tv_cat_SecondCatName)).setText(catName.getChildName());
            ((TextView)view.findViewById(R.id.tv_ask_cont_1)).setText(bean.getContent());
            ((TextView)view.findViewById(R.id.tv_apply_time)).setText(Utils.getTimeOut(bean.getAskDate()));
            int applys = bean.getApplys().size();
            ((TextView)view.findViewById(R.id.tv_apply_apply_num)).setText(applys+"");
            int pays = 0;
            for(ApplyInfoBean apply:bean.getApplys()){
                if(apply.getStatus() == 3)
                    pays++;
            }
            ((TextView)view.findViewById(R.id.tv_apply_pay_num)).setText(pays+"");
            TextView apply = (TextView) view.findViewById(R.id.tv_apply_btn);
            apply.setActivated(false);
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.checkApply();
                }
            });
        }else {
            view = View.inflate(context, R.layout.item_apply,null);
            ApplyInfoBean apply = list.get(position-1);
            final CatInfoBean cat = apply.getCat();
            UserInfoBean user = cat.getUser();
            CatNameBean catName = Utils.getCatNameById(cat.getNameId());
            ((TextView)view.findViewById(R.id.tv_apply_item_name)).setText(user.getName());
            Glide.with(context).load(Constant.helperUrl+user.getTouxiang()).into((ImageView)view.findViewById(R.id.img_apply_item_touxiang));
            ((TextView)view.findViewById(R.id.tv_apply_item_label)).setText(cat.getLabel());

            ((TextView)view.findViewById(R.id.tv_cat_FirstCatName)).setText(catName.getParentName());
            ((TextView)view.findViewById(R.id.tv_cat_SecondCatName)).setText(catName.getChildName());

            ((TextView)view.findViewById(R.id.tv_apply_item_desc)).setText(cat.getCatDesc());
            ((TextView)view.findViewById(R.id.tv_apply_item_price)).setText(MoneyFactory.longToIntString(cat.getPrice()));
            TextView tv_apply_status = (TextView) view.findViewById(R.id.tv_apply_status);
            switch (apply.getStatus()){
                case 1:
                    tv_apply_status.setVisibility(View.VISIBLE);
                    tv_apply_status.setText("新申请");
                    break;
                case 2:
                    tv_apply_status.setVisibility(View.GONE);
                    break;
                case 3:
                    tv_apply_status.setVisibility(View.VISIBLE);
                    tv_apply_status.setText("已支付");
                    break;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SubmitOrderActivity.class);
                    intent.putExtra("cat",cat);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }
}
