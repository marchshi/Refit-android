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
import com.smq.demo5.activity.SearchOutActivity;
import com.smq.demo5.activity.SubmitOrderActivity;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.util.Utils;

import java.util.List;

/**
 * Created by shimanqian on 2017/8/4.
 */

public class SearchAdapter extends BaseAdapter {

    private SearchOutActivity context;
    private List<CatInfoBean> list;


    public SearchAdapter(SearchOutActivity context, List<CatInfoBean> list){
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
        View view = View.inflate(context, R.layout.item_apply,null);
        final CatInfoBean cat = list.get(position);
        UserInfoBean user = cat.getUser();
        CatNameBean catName = Utils.getCatNameById(cat.getNameId());
        ((TextView)view.findViewById(R.id.tv_apply_item_name)).setText(user.getName());
        Glide.with(context).load(Constant.helperUrl+user.getTouxiang()).into((ImageView)view.findViewById(R.id.img_apply_item_touxiang));
        ((TextView)view.findViewById(R.id.tv_apply_item_label)).setText(cat.getLabel());
        ((TextView)view.findViewById(R.id.tv_cat_FirstCatName)).setText(catName.getParentName());
        ((TextView)view.findViewById(R.id.tv_cat_SecondCatName)).setText(catName.getChildName());
        ((TextView)view.findViewById(R.id.tv_apply_item_desc)).setText(cat.getCatDesc());
        ((TextView)view.findViewById(R.id.tv_apply_item_price)).setText(cat.getPrice()+"");
        TextView tv_apply_status = (TextView) view.findViewById(R.id.tv_apply_status);
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