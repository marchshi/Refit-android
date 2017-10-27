package com.smq.demo5.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.google.gson.Gson;
import com.smq.demo5.Constant;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.UserInfoBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shimanqian on 2017/7/19.
 */

public class Utils {

    //判断是否连接了网络 获取此服务需要 ACCESS_NETWORK_STATE 权限
    public static boolean checkConnection(Context context){
        //获取网络连接的服务
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null == cm){
            return false;
        }
        //获取当前网络的状态信息
        NetworkInfo info = cm.getActiveNetworkInfo();
        //判断是否连接上数据网络或者无线网
        return null != info && info.isConnected() &&(
                ConnectivityManager.TYPE_MOBILE == info.getType()
                        || ConnectivityManager.TYPE_WIFI == info.getType()
        );
    }

    public static String getUuid(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString().replaceAll("-", "");
    }

    public static String getMD5(String str) {
        if (str != null && !str.equals("")) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
                }
                str = sb.toString();
            } catch (NoSuchAlgorithmException e) {
            } catch (Exception e) {
            }
        }
        return str;
    }

    public static boolean isTel(String tel){
        Pattern pattern = Pattern.compile("^[1][358]\\d{9}$");
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }

    public static String getUriPath(Context context,Uri uri) {
        String path = null;
        if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return path;
    }

    public static String bitmapToBase64(Bitmap bitmap){
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Class getActualTypeClass(Class entity) {
        ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();
        Class entityClass = (Class) type.getActualTypeArguments()[0];
        return entityClass;
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        List list = Arrays.asList(arr);
        List arrayList = new ArrayList(list);
        return arrayList;
    }

    public static void initBean(UserInfoBean userInfoBean){
        Constant.asks = new ArrayList(userInfoBean.getAsks());
        Constant.cats = new ArrayList<>(userInfoBean.getCats());
        Constant.userId = userInfoBean.getUserId();
        userInfoBean.setAsks(null);
        userInfoBean.setCats(null);
        Constant.info = userInfoBean;
    }

    public static CatNameBean getCatNameById(int name_id){
        for (CatNameBean catNameBean:Constant.catList){
            if(name_id == catNameBean.getNameId())
                return catNameBean;
        }
        return null;
    }

    public static String getTimeOut(long time){
        long now = System.currentTimeMillis();
        long out = now - time;
        int mins = (int) (out / 60000);
        int hours = mins /60;
        int days = hours /24;
        int months = days / 30;
        int years = months / 12;
        if(mins < 60){
            return mins+"分钟前";
        }else if(hours<24){
            return hours+"小时前";
        }else if(days<30){
            return days+"天以前";
        }else if(months <12){
            return months+"月以前";
        }else {
            return years+"年以前";
        }
    }

    public static String moneyFacotry(long money){
        double meneyD = money / 100.0;
        String str = String.format("%.2f", meneyD);
        return str;
    }


    public static long stringToMoney(String str){
        long money = (long) (Double.parseDouble(str) *100);
        return money;
    }

}
