package com.smq.demo5.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by shimanqian on 2017/7/20.
 */

public class ResponseParser {

    public static BaseJson toBaseResponse(String jsonString) {
        try {
            return new Gson().fromJson(jsonString, new TypeToken<BaseJson>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
