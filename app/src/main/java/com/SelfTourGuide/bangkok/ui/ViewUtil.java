package com.SelfTourGuide.bangkok.ui;

import android.util.Log;
import android.view.View;

import com.SelfTourGuide.bangkok.util.PayStatusUtil;

public class ViewUtil {

    /**
     * 检查广告
     * @param adView
     */
    public static void checkADView(View adView) {
        if (adView == null) {
            return;
        }
        if (PayStatusUtil.isSubAvailable()) {
            adView.setVisibility(View.GONE);
            Log.d("wylad", "ad设置为不可见");
        } else {
            adView.setVisibility(View.VISIBLE);
            Log.d("wylad", "ad设置为可见");
        }

    }
}
