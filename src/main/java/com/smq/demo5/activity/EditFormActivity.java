package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smq.demo5.R;
import com.smq.demo5.bean.FormItemBean;

import butterknife.BindView;

public class EditFormActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_form_num)
    TextView tv_form_num;
    @BindView(R.id.et_form_title)
    EditText et_form_title;
    @BindView(R.id.cb_form_dan)
    CheckBox cb_form_dan;
    @BindView(R.id.cb_form_duo)
    CheckBox cb_form_duo;
    @BindView(R.id.cb_form_text)
    CheckBox cb_form_text;
    @BindView(R.id.ly_item_con)
    LinearLayout ly_item_con;

    FormItemBean item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_form_item);
        initview();
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_title_right,R.id.cb_form_dan,R.id.cb_form_duo,R.id.cb_form_text,R.id.bt_con_add,R.id.bt_con_minus,R.id.bt_item_del},this);
    }

    public void initview(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        TextView title_right = (TextView) findViewById(R.id.tv_title_right);
        title_right.setVisibility(View.VISIBLE);
        title_right.setText("保存");
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setTextSize(18);
        tv_title.setText("设计表单");
        Intent intent = getIntent();
        item = (FormItemBean) intent.getSerializableExtra("item");
        if(item ==null) {
            //添加一个表单项
            item = new FormItemBean();
            String id =intent.getStringExtra("id");
            item.setId(Integer.parseInt(id));
            tv_form_num.setText(id);
            checkDan();
        }else{
            tv_form_num.setText(item.getId()+"");
            et_form_title.setText(item.getTitle());
            switch (item.getType()){
                case 1:
                    cb_form_dan.setChecked(true);
                    cb_form_duo.setChecked(false);
                    cb_form_text.setChecked(false);
                    for(int i = 0;i<item.getOptions().size();i++){
                        LinearLayout view = (LinearLayout) View.inflate(this,R.layout.item_form_con_cb,null);
                        EditText option = (EditText) view.getChildAt(1);
                        option.setText(item.getOptions().get(i));
                        ly_item_con.addView(view);
                    }
                    break;
                case 2:
                    cb_form_dan.setChecked(false);
                    cb_form_duo.setChecked(true);
                    cb_form_text.setChecked(false);
                    for(int i = 0;i<item.getOptions().size();i++) {
                        LinearLayout view = (LinearLayout) View.inflate(this, R.layout.item_form_con_cb, null);
                        EditText option = (EditText) view.getChildAt(1);
                        option.setText(item.getOptions().get(i));
                        ly_item_con.addView(view);
                    }
                    break;
                case 3:
                    cb_form_dan.setChecked(false);
                    cb_form_duo.setChecked(false);
                    cb_form_text.setChecked(true);
                    for(int i =0;i<item.getInitLine();i++){
                        View view = View.inflate(this,R.layout.item_form_line,null);
                        ly_item_con.addView(view);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    //点击“单选”按钮触发的事件
    public void checkDan(){
        cb_form_dan.setChecked(true);
        cb_form_duo.setChecked(false);
        cb_form_text.setChecked(false);
        if(item.getType() == 3 || item.getType()==0){
            ly_item_con.removeAllViews();
            View view1 = View.inflate(this,R.layout.item_form_con_cb,null);
            ly_item_con.addView(view1);
            View view2 = View.inflate(this,R.layout.item_form_con_cb,null);
            ly_item_con.addView(view2);
            View view3 = View.inflate(this,R.layout.item_form_con_cb,null);
            ly_item_con.addView(view3);
        }
        item.setType(1);
    }
    //点击“多选”按钮触发的事件
    public void checkDuo(){
        cb_form_dan.setChecked(false);
        cb_form_duo.setChecked(true);
        cb_form_text.setChecked(false);
        if(item.getType() ==3 || item.getType() ==0){
            ly_item_con.removeAllViews();
            View view1 = View.inflate(this,R.layout.item_form_con_cb,null);
            ly_item_con.addView(view1);
            View view2 = View.inflate(this,R.layout.item_form_con_cb,null);
            ly_item_con.addView(view2);
            View view3 = View.inflate(this,R.layout.item_form_con_cb,null);
            ly_item_con.addView(view3);
        }
        item.setType(2);
    }
    //点击“简答”按钮触发的事件
    public void checkText(){
        cb_form_dan.setChecked(false);
        cb_form_duo.setChecked(false);
        cb_form_text.setChecked(true);
        int type = item.getType();
        if(type ==1 ||type ==2 || type ==0){
            ly_item_con.removeAllViews();
            View view1 = View.inflate(this,R.layout.item_form_line,null);
            ly_item_con.addView(view1);
            View view2 = View.inflate(this,R.layout.item_form_line,null);
            ly_item_con.addView(view2);
            View view3 = View.inflate(this,R.layout.item_form_line,null);
            ly_item_con.addView(view3);
        }
        item.setType(3);
    }

    public void add(){
        if(cb_form_text.isChecked()){
            if(ly_item_con.getChildCount() <4){
                View view = View.inflate(this,R.layout.item_form_line,null);
                ly_item_con.addView(view);
            }else {
                showToast("亲~表单超过4行就不美观了哦~");
            }
        }else if(cb_form_dan.isChecked()||cb_form_duo.isChecked()){
            if(ly_item_con.getChildCount() <8){
                View view = View.inflate(this,R.layout.item_form_con_cb,null);
                ly_item_con.addView(view);
            }else {
                showToast("亲~选项超过8个就不美观了哦~");
            }
        }

    }

    public void minus(){
        if(cb_form_text.isChecked()){
            if(ly_item_con.getChildCount() >1) {
                ly_item_con.removeViewAt(ly_item_con.getChildCount() - 1);
            }else {
                showToast("亲~最少要保留1行哦~");
            }
        }else if(cb_form_dan.isChecked()||cb_form_duo.isChecked()){
            if(ly_item_con.getChildCount() >2){
                ly_item_con.removeViewAt(ly_item_con.getChildCount() - 1);
            }else {
                showToast("亲~最少要保留两个选项哦~");
            }
        }
    }

    public void delete(){
        Intent i = new Intent();
        i.putExtra("item",item);
        setResult(2,i);
        finish();
    }

//    public void save(){
//        public void edit(){
//            final String form = new Gson().toJson(list);
//            Map params = new HashMap();
//            params.put("catId",catId);
//            params.put("form",form);
//            new XRequest(this,"form",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<String>() {
//
//                @Override
//                public void success(String s) {
//                    for(CatInfoBean cat : Constant.cats){
//                        if(cat.getCatId() == Integer.parseInt(catId)){
//                            cat.setForm(form);
//                            break;
//                        }
//                    }
//                    finish();
//                }
//                @Override
//                public void fail(String content) {
//
//                }
//
//            }).execute();
//        }
//    }

    public void save(){
        if(!TextUtils.isEmpty(et_form_title.getText().toString().trim()) && item.getType() !=0){
            item.setTitle(et_form_title.getText().toString().trim());
            item.getOptions().clear();
            if(item.getType() ==1||item.getType() ==2){
                for(int i =0;i < ly_item_con.getChildCount();i++){
                    LinearLayout option = (LinearLayout) ly_item_con.getChildAt(i);
                    EditText et = (EditText) option.getChildAt(1);
                    String text = et.getText().toString().trim();
                    if(!TextUtils.isEmpty(text)){
                        item.getOptions().add(text);
                    }else{
                        showToast("请填写完整");
                        item.getOptions().clear();
                        return;
                    }
                }
            }else if(item.getType() ==3){
                item.setInitLine(ly_item_con.getChildCount());
            }
        }else {
            showToast("请填写完整");
            return;
        }
        Intent i = new Intent();
        i.putExtra("item",item);
        //设置结果码和item数据内容，传给LookForm
        setResult(1,i);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case R.id.tv_title_right:
                save();
                break;
            case R.id.cb_form_dan:
                checkDan();
                break;
            case R.id.cb_form_duo:
                checkDuo();
                break;
            case R.id.cb_form_text:
                checkText();
                break;
            case R.id.bt_con_add:
                add();
                break;
            case R.id.bt_con_minus:
                minus();
                break;
            case R.id.bt_item_del:
                delete();
                break;
        }
    }
}
