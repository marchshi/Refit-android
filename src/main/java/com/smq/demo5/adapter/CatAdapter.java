package com.smq.demo5.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.activity.EditCatActivity;
import com.smq.demo5.activity.LookFormActivity;
import com.smq.demo5.activity.LoginActivity;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;

import java.util.List;


/**
 * Created by shimanqian on 2017/6/6.
 */

public class CatAdapter extends BaseAdapter {

    private Context context;
    private List<CatInfoBean> list;

    public CatAdapter(Context context, List<CatInfoBean> list){
        this.context = context;
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
        View view;
        if(!Constant.isLogin || list.isEmpty()){
            view = View.inflate(context, R.layout.item_cat_admin_null,null);
            view.findViewById(R.id.tv_admin_add_null).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Constant.isLogin){
                        Intent intent = new Intent(context,EditCatActivity.class);
                        context.startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }else if(position == list.size()){
            view = View.inflate(context, R.layout.item_cat_add,null);
            view.findViewById(R.id.img_cat_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditCatActivity.class);
                    context.startActivity(intent);
                }
            });
        }else{
            final CatInfoBean item = list.get(position) ;
            view = View.inflate(context, R.layout.item_cat_info,null);
            CatNameBean catName;
            if(item.getCatName() == null){
                catName = Utils.getCatNameById(item.getNameId());
            }else {
                catName = item.getCatName();
            }
            ((TextView)view.findViewById(R.id.tv_cat_item_FirstCatName)).setText(catName.getParentName());
            ((TextView)view.findViewById(R.id.tv_cat_item_SecondCatName)).setText(catName.getChildName());
            ((TextView)view.findViewById(R.id.tv_cat_item_label)).setText(item.getLabel());
            ((TextView)view.findViewById(R.id.tv_cat_item_price)).setText(MoneyFactory.longToIntString(item.getPrice()));
            //设置回答模式
            String str="";
            switch(item.getServerModel()){
                case 1:
                    str="实时";
                    break;
                case 2:
                    str="闲时";
                    break;
                case 3:
                    str="18:00-23:00";
                    break;
                case 4:
                    str="停止导购";
                    break;
            }
            ((TextView)view.findViewById(R.id.tv_cat_item_smodel)).setText(str);

            final DrawerLayout dl_cat_item = (DrawerLayout) view.findViewById(R.id.dl_cat_item);
            dl_cat_item.setScrimColor(0x33000000);
            view.findViewById(R.id.ll_cat_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dl_cat_item.isDrawerOpen(Gravity.RIGHT)){
                        dl_cat_item.closeDrawer(Gravity.RIGHT);
                    }else {
                        dl_cat_item.openDrawer(Gravity.RIGHT);
                    }
                }
            });

            view.findViewById(R.id.ll_cat_form_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LookFormActivity.class);
                    intent.putExtra("catId",item.getCatId()+"");
                    intent.putExtra("form",item.getForm());
                    context.startActivity(intent);
                }
            });
            view.findViewById(R.id.ll_cat_item_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditCatActivity.class);
                    intent.putExtra("catInfo",item);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }
}
