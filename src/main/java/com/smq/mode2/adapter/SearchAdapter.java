package com.smq.mode2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.SearchOutActivity;
import com.smq.mode2.entity.CatViewEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/6/21.
 */

public class SearchAdapter extends BaseAdapter {

    private SearchOutActivity context;
    private List<CatViewEntity> list;


    public SearchAdapter(SearchOutActivity context, List<CatViewEntity> list){
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
        CatViewEntity entity = list.get(position);
        ((TextView)view.findViewById(R.id.tv_apply_item_name)).setText(entity.getName());
        Glide.with(context).load(Constant.helperUrl+entity.getTouxiang()).into((ImageView)view.findViewById(R.id.img_apply_item_touxiang));
        ((TextView)view.findViewById(R.id.tv_apply_item_label)).setText(entity.getLabel());
        ((TextView)view.findViewById(R.id.tv_apply_item_desc)).setText(entity.getCatDesc());
        ((TextView)view.findViewById(R.id.tv_apply_item_price)).setText(entity.getPrice());
        if(entity.getStimes()!=null)
            ((TextView)view.findViewById(R.id.tv_apply_item_stimes)).setText(entity.getStimes());
        return view;
    }


}
