package com.crazyjiang.crazydemo.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.crazyjiang.crazydemo.app.constant.Constant;

/**
 * Created by Jiangwenjin on 2017/3/7.
 */

public class SPUtil {
    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(Constant.NAME_SP, Context.MODE_PRIVATE);
    }

    /**
     * rongcloud token
     */
    public static String getToken(Context context) {
        // TODO must get the token from server side
        return "Duc2oCzs2igWD+HunJT/5KqbVZZsuH0nmSgWgXB3mRMNhihgStzHo1H/+yw7AHgjCeafOU9cD5cXziCHO6ybhsVgzgE0iLzq";
//        return getSP(context).getString(Constant.KEY_TOKEN, "");
    }

    /**
     * rongcloud token
     */
    public static void setToken(Context context, String token) {
        getSP(context).edit().putString(Constant.KEY_TOKEN, token).apply();
    }
}
