package com.smq.demo5.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.smq.demo5.R;

import butterknife.BindView;

import static com.smq.demo5.R.id.tv_chat_send;

public class ChatActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.et_chat_msg)
    EditText et_chat_msg;

    String chatUsername ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_chat);
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tv_title)).setText("正在开发-敬请期待");
        ((TextView)findViewById(R.id.tv_title)).setTextSize(18);
        bindOnClick(new int[]{R.id.img_title_back, tv_chat_send},this);
        chatUsername = getIntent().getStringExtra("chatUsername");
//        EaseChatFragment easeChatFragment = new EaseChatFragment();
//        easeChatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.ll_chat_content,easeChatFragment).commit();

    }

    public void send(){
        String centent = et_chat_msg.getText().toString();
        if (!TextUtils.isEmpty(centent)){
            EMMessage message = EMMessage.createTxtSendMessage(centent, chatUsername);
            //设置chattype，默认是单聊
            message.setChatType(EMMessage.ChatType.GroupChat);
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);
            et_chat_msg.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case tv_chat_send:
                send();
                break;
        }
    }
}
