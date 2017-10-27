package com.smq.mode2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smq.mode2.R;
import com.smq.mode2.entity.CatTableEntity;
import com.smq.mode2.util.CatMateUtil;

import java.util.List;

/**
 * Created by shimanqian on 2017/7/6.
 */

public class SearchTipsAdapter  extends BaseAdapter {

    private Context context;
    private List<CatTableEntity> list;


    public SearchTipsAdapter(Context context, List<CatTableEntity> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatTableEntity cat = list.get(position);
        View view = View.inflate(context, R.layout.item_search_tips,null);
        ((TextView)view.findViewById(R.id.tv_search_string)).setText(cat.getName());
        ((TextView)view.findViewById(R.id.tv_search_cat)).setText(CatMateUtil.getParentName(cat));
        return view;
    }
}
