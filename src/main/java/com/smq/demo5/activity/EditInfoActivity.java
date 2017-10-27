package com.smq.demo5.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.MyTextWatcher;
import com.smq.demo5.util.Utils;
import com.smq.demo5.view.CircleImageView;

import butterknife.BindView;


public class EditInfoActivity extends BaseActivity implements View.OnClickListener {

    ImageView img_title_back;
    TextView tv_title_right;
    TextView tv_title;
    @BindView(R.id.cimg_info_touxiang)
    CircleImageView cimg_info_touxiang;
    @BindView(R.id.et_info_name)
    EditText et_info_name;
    @BindView(R.id.tv_info_name_num)
    TextView tv_info_name_num;
    @BindView(R.id.et_info_prof)
    EditText et_info_prof;
    @BindView(R.id.tv_info_prof_num)
    TextView tv_info_prof_num;
    @BindView(R.id.et_info_resume)
    EditText et_info_resume;
    @BindView(R.id.tv_info_resume_num)
    TextView tv_info_resume_num;
    @BindView(R.id.et_info_city)
    EditText et_info_city;

    private Bitmap photo;
    UserInfoBean info = Constant.info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_info);
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_title_right,R.id.cimg_info_touxiang},this);
        init();
        initView();
    }
    private void init(){
        img_title_back = (ImageView) findViewById(R.id.img_title_back);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("修改我的信息");
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("保存");
        img_title_back.setVisibility(View.VISIBLE);
        et_info_name.addTextChangedListener(new MyTextWatcher(tv_info_name_num,16));
        et_info_prof.addTextChangedListener(new MyTextWatcher(tv_info_prof_num,18));
        et_info_resume.addTextChangedListener(new MyTextWatcher(tv_info_resume_num,99));
    }

    private void initView(){
        if(info.getTouxiang() != null){
            Glide.with(this).load(Constant.helperUrl+info.getTouxiang()).into(cimg_info_touxiang);
        }
        et_info_name.setText(info.getName());
        et_info_prof.setText(info.getProfession());
        et_info_resume.setText(info.getResume());
        et_info_city.setText(info.getCity());
    }

    private void checkInfo(){
        String name = et_info_name.getText().toString().trim();
        String prof = et_info_prof.getText().toString().trim();
        if(TextUtils.isEmpty( name) ||TextUtils.isEmpty( prof)){
            showToast("姓名和职业不能为空");
            return;
        }else {
            final UserInfoBean newInfo = info;
            if(photo == null){
                newInfo.setTouxiang(info.getTouxiang());
            }else {
                newInfo.setTouxiang(Utils.bitmapToBase64(photo));
            }
            newInfo.setName(name);
            newInfo.setProfession(prof);
            String resume = et_info_resume.getText().toString();
            String city = et_info_city.getText().toString().trim();
            newInfo.setResume(resume);
            newInfo.setCity(city);
            new XRequest(this,"info",XRequest.POST,newInfo).setOnRequestListener(new ObjectRequestListener<UserInfoBean>() {
                @Override
                public void success(UserInfoBean userInfoBean) {
                    Constant.info = userInfoBean;
                    setResult(Constant.EDIT_SUCCESS);
                    finish();
                }

                @Override
                public void fail(String content) {

                }
            }).execute();
        }
    }

    private void chooseTouxiang(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, Constant.NEED_IMAGE);
    }

    /**
     * 裁剪图片方法实现
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高 以1080为最高像素
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case R.id.tv_title_right:
                checkInfo();
                break;
            case R.id.cimg_info_touxiang:
                chooseTouxiang();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.NEED_IMAGE){
            if(data !=null){
                startPhotoZoom(data.getData());
            }
        }else if(requestCode == 300){
            if(data!=null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                    cimg_info_touxiang.setImageBitmap(photo);
                }
            }
        }
    }


}
