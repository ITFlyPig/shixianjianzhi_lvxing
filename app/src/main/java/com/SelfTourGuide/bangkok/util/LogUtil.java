package com.SelfTourGuide.bangkok.util;

import android.util.Log;

public class LogUtil {

    public static boolean isShowLog = true;//true:打印log，false:不打印log

    public static void e(String message) {
        if (!isShowLog) return;
        Log.e("Info", message);
    }

    public static void i(String message) {
        if (!isShowLog) return;
        Log.i("Info", message);
    }

    public static void v(String message) {
        if (!isShowLog) return;
        Log.v("Info", message);
    }

    /**
     * 打印请求参数
     *
     * @param message
     */
    public static void req_rep(String message) {
        if (!isShowLog) return;
        if (message.startsWith("request")) {
            Log.i("req_rep", message);
        } else {
            Log.e("req_rep", message);
        }
    }

}
