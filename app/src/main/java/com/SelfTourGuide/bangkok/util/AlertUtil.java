package com.SelfTourGuide.bangkok.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Frank on 2016/5/28.
 */
public final class AlertUtil {
    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void toast(Context context, Integer message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
