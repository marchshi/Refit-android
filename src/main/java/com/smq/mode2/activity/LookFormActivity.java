package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.R;
import com.smq.mode2.entity.FormItemEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by shimanqian on 2017/6/7.
 */

public class LookFormActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ly_form)
    LinearLayout ly_form;

    Intent intent = new Intent();
    String catId = null;
    List<FormItemEntity> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 在数据库端，表单是以json的形式和类目信息保存在一起
         * 而表单界面往往从类目信息的界面跳转过来
         * 所以这里接受的是intent里面的json对象
         */
        setbodyView(R.layout.activity_look_form);
        initview();
        bindOnClick(new int[]{R.id.ib_finish,R.id.ib_cat_add,R.id.tv_title_right},this);
        intent = getIntent();
        String formJson = intent.getStringExtra("form");
        catId = intent.getStringExtra("catId");
        if(formJson != null){
            list= new Gson().fromJson(formJson, new TypeToken<List<FormItemEntity>>() {}.getType());
        }
        init();
    }

    public void init(){
        ly_form.removeAllViews();
        int i = 1;
        for (FormItemEntity entity :list){
            entity.setId(i);
            addItem(entity);
            i++;
        }
    }

    public void initview(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView title_right = (TextView) findViewById(R.id.tv_title_right);
        title_right.setVisibility(View.VISIBLE);
        title_right.setText("保存");
        title.setText("我的表单");
        title.setTextSize(18);
    }

    public void addItem(final FormItemEntity item){
        LinearLayout ly_item = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ly_item.setLayoutParams(layoutParams);
        ly_item.setOrientation(LinearLayout.VERTICAL);
        View title = View.inflate(this,R.layout.item_form_title,null);
        ((TextView) title.findViewById(R.id.tv_form_num)).setText(item.getId() +"");
        switch (item.getType()) {
            case 1:
                ((TextView) title.findViewById(R.id.tv_form_title)).setText(item.getTitle()+ " (单选)");
                ly_item.addView(title);
                for(int i = 0 ;i < item.getOptions().size(); i++){
                    LinearLayout option = (LinearLayout) View.inflate(this,R.layout.item_form_con_tv,null);
                    TextView tv = (TextView) option.getChildAt(1);
                    tv.setText(item.getOptions().get(i));
                    ly_item.addView(option);
                }
                break;
            case 2:
                ((TextView) title.findViewById(R.id.tv_form_title)).setText(item.getTitle()+ " (多选)");
                ly_item.addView(title);
                for(int i = 0 ;i < item.getOptions().size(); i++){
                    LinearLayout option = (LinearLayout) View.inflate(this,R.layout.item_form_con_tv,null);
                    TextView tv = (TextView) option.getChildAt(1);
                    tv.setText(item.getOptions().get(i));
                    ly_item.addView(option);
                }
                break;
            case 3:
                ((TextView) title.findViewById(R.id.tv_form_title)).setText(item.getTitle()+ " (简答)");
                ly_item.addView(title);
                for(int i = 0 ;i < item.getInitLine() ; i++){
                    View line = View.inflate(this,R.layout.item_form_con_line,null);
                    ly_item.addView(line);
                }
                break;
            default:
                break;
        }
        ly_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookFormActivity.this,EditFormActivity.class);
                intent.putExtra("item",item);
                LookFormActivity.this.startActivityForResult(intent,2);
            }
        });
        ly_form.addView(ly_item);
    }

    public void editItem(FormItemEntity item){
        int id =item.getId();
        list.remove(id-1);
        list.add(id-1,item);
        init();
    }

    public void save(){
        String form = new Gson().toJson(list);
        Map params = new HashMap();
        params.put("catId",catId);
        params.put("form",form);
        new XRequest(this,"saveForm",XRequest.POST,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject object = new JSONObject(t);
                    if("success".equals(object.getJSONObject("message").getString("type"))){
                        showToast("保存成功");
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {

            }
        }).execute();
    }

    public void deleteItem(int id){
        list.remove(id-1);
        init();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_finish:
                finish();
                break;
            case R.id.ib_cat_add:
                Intent intent = new Intent(this,EditFormActivity.class);
                intent.putExtra("id",list.size()+1 +"");
                startActivityForResult(intent,1);
                break;
            case R.id.tv_title_right:
                save();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==1){
            FormItemEntity item = (FormItemEntity) data.getSerializableExtra("item");
            int id = item.getId();
            if(id > list.size()){
                addItem(item);
                list.add(item);
            }else{
                editItem(item);
            }
        }else if (resultCode ==2){
            FormItemEntity item = (FormItemEntity) data.getSerializableExtra("item");
            int id = item.getId();
            if(id <=  list.size()){
                deleteItem(id);
            }
        }
    }
}
