package com.smq.mode2.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smq.mode2.R;
import com.smq.mode2.activity.EditCatActivity;
import com.smq.mode2.activity.LookFormActivity;
import com.smq.mode2.entity.CatInfoEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/6/6.
 */

public class CatAdapter extends BaseAdapter {

    private Context context;
    private List<CatInfoEntity> list;

    public CatAdapter (Context context,List<CatInfoEntity> list){
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
        Log.d("aaa", position+"");
        View view;
        if(position == list.size()){
            view = View.inflate(context,R.layout.item_cat_add,null);
            view.findViewById(R.id.ib_cat_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditCatActivity.class);
                    context.startActivity(intent);
                }
            });
        }else{
            final CatInfoEntity item = list.get(position);
            view = View.inflate(context, R.layout.item_cat,null);
            ((TextView)view.findViewById(R.id.tv_catitem_cat)).setText(item.getCategory());
            ((TextView)view.findViewById(R.id.tv_catitem_label)).setText(item.getLabel());
            ((TextView)view.findViewById(R.id.tv_catitem_price)).setText(item.getPrice());
            view.findViewById(R.id.ib_cat_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditCatActivity.class);
                    intent.putExtra("catInfo",item);
                    context.startActivity(intent);
                }
            });
            view.findViewById(R.id.ib_cat_form).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LookFormActivity.class);
                    intent.putExtra("catId",item.getCatId());
                    intent.putExtra("form",item.getForm());
                    context.startActivity(intent);
                }
            });
        }
        return view;

    }
}
