package com.smq.demo5.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.activity.ChatActivity;
import com.smq.demo5.entity.GuideFragmentEntity;

import java.util.List;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class GuideFragmentAdapter extends BaseAdapter {

    Context context;
    List<GuideFragmentEntity> list;
    int doingNum;
    public GuideFragmentAdapter(Context context, List<GuideFragmentEntity> list,int doingNum){
        this.context = context;
        this.list = list;
        this.doingNum = doingNum;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_chat_bar,null);
        final GuideFragmentEntity entity =  list.get(position);
        if(!TextUtils.isEmpty(entity.getTouxiang())){
            Glide.with(context).load(Constant.helperUrl+entity.getTouxiang()).into((ImageView)view.findViewById(R.id.cimg_chat_touxiang));
        }
        ((TextView)view.findViewById(R.id.tv_chat_name)).setText(entity.getName());

        int unread = 0;

        //这里为什么会没有回话呢 难道要在服务器端先发送一条消息进行激活？
        //这里到底是实例空指针还是因为没有消息的空指针 没有消息就说明不存在这个会话么？
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(entity.getChatName());
        if(conversation != null){
            EMMessage last = conversation.getAllMessages().get(0);
            String lastString = "";
            if(last.getBody().toString().length()>12){
                lastString = last.getBody().toString().substring(0,10) +"...";
            }else {
                lastString = last.getBody().toString();
            }
            ((TextView)view.findViewById(R.id.tv_chat_last)).setText(lastString);

            last.getMsgTime();
            //TODO 将long类型的时间转换成字符串
            ((TextView)view.findViewById(R.id.tv_chat_time)).setText(last.getMsgTime()+"");

            unread = conversation.getUnreadMsgCount();
        }

        TextView tip = (TextView) view.findViewById(R.id.tv_chat_tip);
        if("get".equals(entity.getType())){
            if(unread==0){
                tip.setText("导购");
                tip.setTextColor(context.getResources().getColor(R.color.colorSecond));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_bg_red));
            }else if(unread<10){
                tip.setText(unread+"");
                tip.setTextColor(context.getResources().getColor(R.color.white));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_circle_red1));
            }else if(unread<99){
                tip.setText(unread+"");
                tip.setTextColor(context.getResources().getColor(R.color.white));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_circle_red2));
            }else {
                tip.setText("99+");
                tip.setTextColor(context.getResources().getColor(R.color.white));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_circle_red3));
            }
        }else if("to".equals(entity.getType())){
            if(unread==0){
                tip.setText("客户");
                tip.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_bg_blue));
            }else if(unread<10){
                tip.setText(unread+"");
                tip.setTextColor(context.getResources().getColor(R.color.white));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_circle_blue1));
            }else if(unread<99){
                tip.setText(unread+"");
                tip.setTextColor(context.getResources().getColor(R.color.white));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_circle_blue2));
            }else {
                tip.setText("99+");
                tip.setTextColor(context.getResources().getColor(R.color.white));
                tip.setBackground(context.getResources().getDrawable(R.drawable.draw_chat_circle_blue3));
            }
        }

        //TODO 添加一条分割线
        //TODO 对订单的状态进行判断

        view.findViewById(R.id.rl_chat_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(context,ChatActivity.class);
                chat.putExtra("chatUsername",entity.getChatName());
                context.startActivity(chat);
            }
        });

        return view;
    }
}
