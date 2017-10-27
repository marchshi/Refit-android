package com.smq.mode2.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.entity.UserInfoEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;
import com.smq.mode2.util.Utils;
import com.smq.mode2.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class EditActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_prof)
    EditText et_prof;
    @BindView(R.id.et_resume)
    EditText et_resume;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.ib_edit_touxiang)
    CircleImageView ib_edit_touxiang;
    @BindView(R.id.cb_sex_nan)
    CheckBox cb_sex_nan;
    @BindView(R.id.cb_sex_nv)
    CheckBox cb_sex_nv;

    private Uri touxiangUri = null;
    private boolean isUpload = false;

    Bitmap photo ;

    private UserInfoEntity info = new UserInfoEntity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit);
        bindOnClick(new int[]{R.id.tv_title_right,R.id.ib_finish,R.id.ib_edit_touxiang},this);
        init();
        initView();
        getExt();
    }
    private void getExt(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(EditActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }
    public void initView(){
        Intent intent = getIntent();
        if(intent.getSerializableExtra("info")!= null){
            info = (UserInfoEntity) intent.getSerializableExtra("info");
            et_name.setText(info.getName());
            /**
             * TODO 初始化用户的头像
             * 1，首先通过http请求获取Info信息
             * 2，判断touxiang是否为空 为空则跳过 不为空继续
             * 3，通过图片的URL发送http请求 获取图片文件
             * 4，将图片文件呈现到界面中
             */
            if(info.getTouxiang() != null){
                Glide.with(this).load(Constant.helperUrl+info.getTouxiang()).into(ib_edit_touxiang);
            }
            if("0".equals(info.getSex())){
                cb_sex_nan.setChecked(true);
                cb_sex_nv.setChecked(false);
            }else if("1".equals(info.getSex())){
                cb_sex_nan.setChecked(false);
                cb_sex_nv.setChecked(true);
            }
            et_prof.setText(info.getprofession());
            if(info.getResume()!=null)
                et_resume.setText(info.getResume());
            if(info.getCity() != null)
                et_city.setText(info.getCity());
        }
    }

    public void init(){
        TextView tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText("保存");
        tv_title_right.setVisibility(View.VISIBLE);
        ImageView ib_finish = (ImageView) findViewById(R.id.ib_finish);
        ib_finish.setVisibility(View.VISIBLE);
        cb_sex_nan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_sex_nan.setChecked(true);
                cb_sex_nv.setChecked(false);
            }
        });
        cb_sex_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_sex_nan.setChecked(false);
                cb_sex_nv.setChecked(true);
            }
        });
    }

    public void checkSave(){
        info.setName(et_name.getText().toString().trim());
        //TODO 保存头像
        info.setSex(cb_sex_nan.isChecked()? "0":"1");
        info.setprofession(et_prof.getText().toString().trim());
        info.setResume(et_resume.getText().toString().trim());
        info.setCity(et_city.getText().toString().trim());
        if(isEmpty(info)){
            showToast("保存失败，信息不能为空");
        }else {
            if(photo != null){
                String imagePath = Utils.savePhoto(photo, Environment
                        .getExternalStorageDirectory().getAbsolutePath(), String
                        .valueOf(System.currentTimeMillis()));
                //先保存其他信息 再保存头像
                File touxiang = new File(imagePath);
                Map params = new HashMap();
                params.put("touxiang",touxiang);
                new XRequest(this,"info?a=upload",XRequest.FILE,params).setOnRequestListener(new BaseRequestListener() {
                    @Override
                    protected void success(String t) {
                        try{
                            JSONObject result = new JSONObject(t);
                            if("success".equals(result.getJSONObject("message").getString("type"))){
                                isUpload = true;
                                info.setTouxiang(result.getString("data"));
                                saveOther();
                            }else{
                                showToast("头像保存失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    protected void fail(int code) {
                        showToast("头像保存失败");
                    }
                }).execute();
            }else{
                saveOther();
            }

        }
    }

    private void saveOther(){
        Map map = new HashMap();
        String json = new Gson().toJson(info);
        map.put("info", json);
        new XRequest(this,"info?a=save",XRequest.POST,map).setOnRequestListener(new BaseRequestListener(){
            @Override
            protected void success(String t) {
                try{
                    JSONObject result = new JSONObject(t);
                    if("success".equals(result.getJSONObject("message").getString("type"))){
                        setResult(2);
                        finish();
                    }else{
                        showToast("保存失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {
                showToast("保存失败");
            }
        }).execute();
    }

    public boolean isEmpty(UserInfoEntity info){
        if(TextUtils.isEmpty(info.getName()) || TextUtils.isEmpty(info.getprofession())){
            return true;
        }
        return false;
    }

    private void chooseTouxiang(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(EditActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else{
                Intent intent = new Intent();
                intent.setAction("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, Constant.NEED_IMAGE);
                return;
            }
        }
        //TODO 用户可以选择是现拍一张照片或是从相册中选一张图片
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, Constant.NEED_IMAGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_right:
                checkSave();
                break;
            case R.id.ib_finish:
                finish();
                break;
            case R.id.ib_edit_touxiang:
                chooseTouxiang();
                break;
        }
    }
    /**
     * 裁剪图片方法实现
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        Uri tempUri = uri;
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
                    photo = Utils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
                    ib_edit_touxiang.setImageBitmap(photo);

                }
            }
        }
    }
}
