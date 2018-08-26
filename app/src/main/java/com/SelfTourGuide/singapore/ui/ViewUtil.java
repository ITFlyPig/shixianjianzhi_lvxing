package com.SelfTourGuide.singapore.ui;

import android.view.View;

import com.SelfTourGuide.singapore.util.PayStatusUtil;

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
        } else {
            adView.setVisibility(View.VISIBLE);
        }

    }
}
