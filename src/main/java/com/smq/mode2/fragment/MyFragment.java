package com.smq.mode2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.EditActivity;
import com.smq.mode2.activity.LoginActivity;
import com.smq.mode2.entity.UserInfoEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;
import com.smq.mode2.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFragment extends BaseFragment implements View.OnClickListener {

    //TODD 在fragment内使用注解
    @BindView(R.id.tv_info_name)
    TextView tv_info_name;
    @BindView(R.id.tv_info_prof)
    TextView tv_info_prof;
    @BindView(R.id.tv_info_resume)
    TextView tv_info_resume;
    @BindView(R.id.icon_sex)
    ImageView icon_sex;
    @BindView(R.id.img_touxiang)
    CircleImageView img_touxiang;

    private UserInfoEntity infoEntity;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_pager,container,false );
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    public void refresh(){
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void getInfo(){
        Map params = new HashMap();
        params.put("a","get");
        new XRequest(activity,"info",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        initView(json.getString("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {

            }
        }).execute();
    }
    public void initView(String info){
        Gson gson = new Gson();
        infoEntity = gson.fromJson(info, UserInfoEntity.class);
        tv_info_name.setText(infoEntity.getName());
        if(infoEntity.getTouxiang() != null){
            Glide.with(this).load(Constant.helperUrl+infoEntity.getTouxiang()).into(img_touxiang);
        }else{
            img_touxiang.setImageResource(R.drawable.icon_touxiang_default);
        }
        if("0".equals(infoEntity.getSex())){
            icon_sex.setImageResource(R.drawable.icon_sex_nan);
        }else if("1".equals(infoEntity.getSex())){
            icon_sex.setImageResource(R.drawable.icon_sex_nv);
        }

        tv_info_prof.setText(infoEntity.getprofession());
        tv_info_resume.setText(infoEntity.getResume());
    }

    public void init(){
        TextView title = (TextView) activity.findViewById(R.id.tv_title);
        title.setVisibility(View.VISIBLE);
        title.setText("我");
        title.setTextSize(20);
        if(Constant.isLogin){//如果用户已经登录了，则显示个人信息
            getInfo();
            view.findViewById(R.id.rl_login).setVisibility(View.GONE);
            view.findViewById(R.id.ly_info).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ib_login_exit).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ib_login_exit).setOnClickListener(this);
            view.findViewById(R.id.ly_edit_info).setOnClickListener(this);
        }else{//如果没有登录则将个人信息隐藏，显示匿名登录界面
            view.findViewById(R.id.rl_login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ly_info).setVisibility(View.GONE);
            view.findViewById(R.id.ib_login_exit).setVisibility(View.GONE);
            view.findViewById(R.id.rl_login).setOnClickListener(this);
        }
    }
    public void exit(){
        infoEntity = null;
        Constant.isLogin = false;
        init();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_login:
                Intent loginIntent = new Intent(activity, LoginActivity.class);
                startActivityForResult(loginIntent,Constant.NEED_LOGIN);
                break;
            case R.id.ly_edit_info:
                Intent editIntent = new Intent(activity, EditActivity.class);
                editIntent.putExtra("info",infoEntity);
                startActivityForResult(editIntent,2);
                break;
            case R.id.ib_login_exit:
                exit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.NEED_LOGIN && resultCode == Constant.LOGIN_SUCCESS){
            init();
        }else if(resultCode == 2) {
            init();
        }
    }
}
