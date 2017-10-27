package com.smq.demo5.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.EditPriceHelper;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;

import butterknife.BindView;

public class EditCatActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ly_cat_name)
    LinearLayout ly_cat_name;
    @BindView(R.id.tv_cat_FirstCatName)
    TextView tv_cat_FirstCatName;
    @BindView(R.id.tv_cat_SecondCatName)
    TextView tv_cat_SecondCatName;
    @BindView(R.id.et_cat_label)
    EditText et_cat_label;
    @BindView(R.id.et_cat_desc)
    EditText et_cat_desc;
    @BindView(R.id.sw_cat_cont)
    Switch sw_cat_cont;
    @BindView(R.id.tv_cat_answer)
    TextView tv_cat_answer;
    @BindView(R.id.et_cat_price)
    EditText et_cat_price;
    @BindView(R.id.tv_cat_release)
    TextView tv_cat_release;
    @BindView(R.id.et_cat_time)
    EditText et_cat_time;
    @BindView(R.id.ly_cat_time)
    LinearLayout  ly_cat_time;
    @BindView(R.id.tv_cat_choose)
    TextView tv_cat_choose;

    TextView title;

    int smodel = 1;

    private CatInfoBean catNew=new CatInfoBean();
    CatInfoBean catOld;
    CatNameBean catName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_cat);
        //进行通用的初始化
        init();
        //根据来源判断当前界面的显示
        initview();
    }

    private void init(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        title = (TextView) findViewById(R.id.tv_title);
        title.setVisibility(View.VISIBLE);
        title.setTextSize(18);
        new EditPriceHelper(et_cat_price) {
            @Override
            public void myTextChangeLister() {

            }
        };
        tv_cat_choose.setVisibility(View.VISIBLE);
        ly_cat_name.setVisibility(View.GONE);
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_cat_release,R.id.tv_cat_answer,R.id.ly_edit_name},this);
    }

    private void initview() {
        catOld=(CatInfoBean)getIntent().getSerializableExtra("catInfo");
        int nameId = getIntent().getIntExtra("category",0);
        catName = Utils.getCatNameById(nameId);
        if(catOld!=null){
            title.setText("修改");
            catName = catOld.getCatName();
            tv_cat_choose.setVisibility(View.GONE);
            ly_cat_name.setVisibility(View.VISIBLE);
            if(catOld.getCatName() != null){
                catName = catOld.getCatName();
            }else {
                catName = Utils.getCatNameById(catOld.getNameId());
            }
            tv_cat_FirstCatName.setText(catName.getParentName());
            tv_cat_SecondCatName.setText(catName.getChildName());
            et_cat_label.setText(catOld.getLabel());
            et_cat_desc.setText(catOld.getCatDesc());
            if(catOld.isCont()){
                sw_cat_cont.setChecked(true);
            }else {
                sw_cat_cont.setChecked(false);
            }
            et_cat_price.setText(MoneyFactory.longToIntString(catOld.getPrice()));
            switch (catOld.getServerModel()){
                case 1:
                    smodel=1;
                    tv_cat_answer.setText("实时回答");
                    ly_cat_time.setVisibility(View.GONE);
                    break;
                case 2:
                    smodel=2;
                    tv_cat_answer.setText("闲时回答");
                    ly_cat_time.setVisibility(View.GONE);
                    break;
                case 3:
                    smodel=3;
                    tv_cat_answer.setText("预约回答");
                    ly_cat_time.setVisibility(View.VISIBLE);
                    et_cat_time.setText(catOld.getScheTime());
                    break;
                case 4:
                    smodel=4;
                    break;
            }
            catNew.setCatId(catOld.getCatId());
        }else if(catName!=null){
            title.setText("添加");
            tv_cat_choose.setVisibility(View.GONE);
            ly_cat_name.setVisibility(View.VISIBLE);
            tv_cat_FirstCatName.setText(catName.getParentName());
            tv_cat_SecondCatName.setText(catName.getChildName());
            et_cat_label.requestFocus();
        }
    }

    private void catRelease() {
        String label=et_cat_label.getText().toString().trim();
        String catDesc=et_cat_desc.getText().toString();
        Boolean catCont=true;
        if(!sw_cat_cont.isChecked()){
            catCont=false;
        }
        String price= et_cat_price.getText().toString();
        if(catName==null || TextUtils.isEmpty(label) || TextUtils.isEmpty(catDesc) || TextUtils.isEmpty(price)){
            showToast("请将信息输入完整");
            return;
        }
        catNew.setUserId(Constant.userId);
        catNew.setNameId(catName.getNameId());
        catNew.setLabel(label);
        catNew.setCatDesc(catDesc);
        catNew.setCont(catCont);
        catNew.setPrice(Integer.parseInt(price)*100);
        catNew.setServerModel(smodel);
        if (catOld!=null){
            catNew.setForm(catOld.getForm());
        }
        if (smodel==3){
            catNew.setScheTime(et_cat_time.getText().toString().trim());
        }
        new XRequest(this,"category", XRequest.POST,catNew).setOnRequestListener(new ListObjectRequestListener(){

            @Override
            public void success(String s) {
                showToast("保存成功");
                Constant.cats = Utils.stringToArray(s,CatInfoBean[].class);
                finish();
            }
            @Override
            public void fail(String content) {
                showToast("保存失败");
            }
        }).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case R.id.tv_cat_release:
                catRelease();
                break;
            case R.id.tv_cat_answer:
                showSingleChoseDialog();
                break;
            case R.id.ly_edit_name:
                Intent intent=new Intent(this,SearchActivity.class);
                intent.putExtra("mode","choose");
                startActivityForResult(intent,Constant.CHOOSE_CAT);
        }
    }

    //弹出单选对话框
    private void showSingleChoseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置单选对话框的标题
        builder.setTitle("回答方式");
        //创建字符串数组,用来设置单选对话框中的条目
        final String[] items = {"实时回答", "闲时回答","预约回答"};
        //创建单选对话框
        //第一个参数:单选对话框中显示的条目所在的字符串数组
        //第二个参数:默认选择的条目的下标(-1表示默认没有选择任何条目)
        //第三个参数:设置事件监听
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            //which:用户所选的条目的下标
            //dialog:触发这个方法的对话框
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    dialog.dismiss();
                    tv_cat_answer.setText("实时回答");
                    smodel=1;
                    findViewById(R.id.ly_cat_time).setVisibility(View.GONE);
                }
                if (which==1){
                    dialog.dismiss();
                    tv_cat_answer.setText("闲时回答");
                    findViewById(R.id.ly_cat_time).setVisibility(View.GONE);
                    smodel=2;
                }
                if (which==2){
                    dialog.dismiss();
                    tv_cat_answer.setText("预约回答");
                    findViewById(R.id.ly_cat_time).setVisibility(View.VISIBLE);
                    smodel=3;
                }
            }
        });
        //显示单选对话框
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CHOOSE_CAT && resultCode==200){
            catName = (CatNameBean) data.getSerializableExtra("catName");
            tv_cat_choose.setVisibility(View.GONE);
            ly_cat_name.setVisibility(View.VISIBLE);
            tv_cat_FirstCatName.setText(catName.getParentName());
            tv_cat_SecondCatName.setText(catName.getChildName());
        }
    }
}
