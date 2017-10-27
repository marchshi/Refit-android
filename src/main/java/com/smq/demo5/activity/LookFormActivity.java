package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.FormItemBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LookFormActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;
    @BindView(R.id.img_title_back)
    ImageView img_title_back;

    @BindView(R.id.ly_form)
    LinearLayout ly_form;
    Intent intent=new Intent();
    String catId = null;
    List<FormItemBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_form);
        initview();
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_title_right},this);
        intent=getIntent();
        String formjson=intent.getStringExtra("form");
        catId = intent.getStringExtra("catId");
        if(formjson != null){
            list= new Gson().fromJson(formjson, new TypeToken<List<FormItemBean>>() {}.getType());
        }
        init();
    }
    //初始化布局
    private void initview() {
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("保存");
        img_title_back.setVisibility(View.VISIBLE);
        tv_title.setText("查看表单");
        tv_title.setTextSize(18);
    }

    private void init() {
        ly_form.removeAllViews();
        int i = 1;
        for (FormItemBean formitem :list){
            formitem.setId(i);
            addItem(formitem);
            i++;
        }
    }

    private void addItem(final FormItemBean formitem) {
        LinearLayout ly_item = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(12,5,12,0);
        ly_item.setLayoutParams(layoutParams);
        ly_item.setBackgroundResource(R.drawable.draw_form_background);
        ly_item.setOrientation(LinearLayout.VERTICAL);
        View title = View.inflate(this,R.layout.item_form_title,null);
        //确定表单序号
        ((TextView) title.findViewById(R.id.tv_form_number)).setText(formitem.getId() +"");
        TextView type =(TextView) title.findViewById(R.id.tv_form_type);
        ((TextView) title.findViewById(R.id.tv_form_title)).setText(formitem.getTitle());
        //类型：单选：1； 多选：2；简答：3
        switch (formitem.getType()) {
            case 1:
                type.setText(" (单选)");
                ly_item.addView(title);
                for(int i = 0 ;i < formitem.getOptions().size(); i++){
                    LinearLayout option = (LinearLayout) View.inflate(this,R.layout.item_form_content,null);
                    TextView tv = (TextView) option.getChildAt(1);
                    tv.setText(formitem.getOptions().get(i));
                    ly_item.addView(option);
                }
                break;
            case 2:
                type.setText(" (多选)");
                ly_item.addView(title);
                for(int i = 0 ;i < formitem.getOptions().size(); i++){
                    LinearLayout option = (LinearLayout) View.inflate(this,R.layout.item_form_content,null);
                    TextView tv = (TextView) option.getChildAt(1);
                    tv.setText(formitem.getOptions().get(i));
                    ly_item.addView(option);
                }
                break;
            case 3:
                type.setText(" (简答)");
                ly_item.addView(title);
                View line = View.inflate(this,R.layout.item_form_line,null);
                ly_item.addView(line);
                break;
            default:
                break;
        }
        ly_form.addView(ly_item);
    }

    public void editItem(FormItemBean item){
        int id =item.getId();
        list.remove(id-1);
        list.add(id-1,item);
        init();
    }

    public void deleteItem(int id){
        list.remove(id-1);
        init();
    }

    public void edit(){
        final String form = new Gson().toJson(list);
        Map params = new HashMap();
        params.put("catId",catId);
        params.put("form",form);
        new XRequest(this,"form",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<String>() {

            @Override
            public void success(String s) {
                for(CatInfoBean cat :Constant.cats){
                    if(cat.getCatId() == Integer.parseInt(catId)){
                        cat.setForm(form);
                        break;
                    }
                }
                finish();
            }
            @Override
            public void fail(String content) {

            }

        }).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case R.id.tv_title_right:
                edit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==1){
            //修改或者添加表单项
            FormItemBean item = (FormItemBean) data.getSerializableExtra("item");
            int id = item.getId();
            if(id > list.size()){
                addItem(item);
                list.add(item);
            }else{
                editItem(item);
            }
        }else if (resultCode ==2){
            //删除表单项
            FormItemBean item = (FormItemBean) data.getSerializableExtra("item");
            int id = item.getId();
            if(id <=  list.size()){
                deleteItem(id);
            }
        }
    }

}
