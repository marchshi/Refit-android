package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.EditPriceHelper;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;

import butterknife.BindView;

public class EditAskActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_ask_desc)
    EditText et_ask_desc;
    @BindView(R.id.tv_cat_choose)
    TextView tv_cat_choose;
    @BindView(R.id.ly_cat_name)
    LinearLayout ly_cat_name;
    @BindView(R.id.tv_cat_FirstCatName)
    TextView tv_cat_FirstCatName;
    @BindView(R.id.tv_cat_SecondCatName)
    TextView tv_cat_SecondCatName;
    @BindView(R.id.sw_ask_ano)
    Switch sw_ask_ano;
    @BindView(R.id.sw_ask_opening)
    Switch sw_ask_opening;
    @BindView(R.id.img_title_back)
    ImageView img_title_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_ask_ref_price)
    EditText et_ask_ref_price;

    AskInfoBean askNew = new AskInfoBean();
    AskInfoBean askOld;
    CatNameBean catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_ask);
        init();
        bindOnClick(new int[]{R.id.img_title_back,R.id.fl_ask_cat,R.id.tv_ask_add},this);
        initview();
    }

    public void initview(){
        askOld = (AskInfoBean) getIntent().getSerializableExtra("askInfo");
        if(askOld!=null){
            et_ask_desc.setText(askOld.getContent());
            if(askOld.getCatName() == null){
                catName = Utils.getCatNameById(askOld.getNameId());
            }else{
                catName = askOld.getCatName();
            }
            tv_cat_choose.setVisibility(View.GONE);
            ly_cat_name.setVisibility(View.VISIBLE);
            tv_cat_FirstCatName.setText(catName.getParentName());
            tv_cat_SecondCatName.setText(catName.getChildName());
            et_ask_ref_price.setText(MoneyFactory.longToIntString(askOld.getRefPrice()));
            if(askOld.isAnonymity()){
                sw_ask_ano.setChecked(true);
            }else{
                sw_ask_ano.setChecked(false);
            }
            askNew.setAskId(askOld.getAskId());
        }
    }

    public void init(){
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("添加");
        tv_title.setTextSize(18);
        img_title_back.setVisibility(View.VISIBLE);
        //对金钱输入的数字范围进行控制
        et_ask_ref_price.setSelection(et_ask_ref_price.getText().toString().trim().length());
        new EditPriceHelper(et_ask_ref_price) {
            @Override
            public void myTextChangeLister() {

            }
        };
    }

    public void save(){
        String askCont = et_ask_desc.getText().toString();
        String refPrice = et_ask_ref_price.getText().toString();
        Boolean ano = sw_ask_ano.isChecked();
        Boolean opening = sw_ask_opening.isChecked();
        if(!TextUtils.isEmpty(askCont) && !TextUtils.isEmpty(refPrice) && catName != null){
            int nameId = catName.getNameId();
            askNew.setUserId(Constant.userId);
            askNew.setContent(askCont);
            askNew.setRefPrice(Integer.parseInt(refPrice)*100);
            askNew.setNameId(nameId);
            askNew.setAskDate(System.currentTimeMillis());
            askNew.setAnonymity(ano);
            askNew.setOpening(opening);
            new XRequest(this,"ask",XRequest.POST,askNew).setOnRequestListener(new ListObjectRequestListener(){
                @Override
                public void success(String t) {
                    showToast("保存成功");
                    Constant.asks = Utils.stringToArray(t,AskInfoBean[].class);
                    finish();
                }
                @Override
                public void fail(String content) {
                    showToast("保存失败");
                }
            }).execute();
        }else {
            showToast("请填写完整");
        }
    }

    @Override
    public void onClick(View v) {
        //保存发布的信息
        switch (v.getId()){
            case R.id.tv_ask_add:
                save();
                break;
            case R.id.img_title_back:
                finish();
                break;
            case R.id.fl_ask_cat:
                Intent intent = new Intent(this,SearchActivity.class);
                intent.putExtra("mode","choose");
                startActivityForResult(intent, Constant.CHOOSE_CAT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.CHOOSE_CAT && resultCode == 200){
            catName = (CatNameBean) data.getSerializableExtra("catName");
            tv_cat_choose.setVisibility(View.GONE);
            ly_cat_name.setVisibility(View.VISIBLE);
            tv_cat_FirstCatName.setText(catName.getParentName());
            tv_cat_SecondCatName.setText(catName.getChildName());
        }
    }
}
