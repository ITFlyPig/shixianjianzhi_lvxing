package com.SelfTourGuide.singapore.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;


public class Advertisement {
    private static final String TAG = Advertisement.class.getSimpleName();
    private HashMap<String, InterstitialAd> AdvertisementList;


    private static class AdvertisementInstance {
        private static final Advertisement INSTANCE = new Advertisement();
    }

    public static final Advertisement getInstance() {
        return AdvertisementInstance.INSTANCE;
    }

    public Advertisement() {

    }

    /**
     * @param context
     * @param key
     * @throws Exception
     */
    public void init(Context context, String... key) throws Exception {

        if (null != AdvertisementList) {
            Log.e(TAG, "init:已初始化·········无需再次初始化");
            return;
        }
        AdvertisementList = new HashMap<>();

        if (null == context || null == key) {
            throw new Exception("参数错误");
        }

        for (int i = 0; i < key.length; i++) {
            if (TextUtils.isEmpty(key[i])) {
                Log.e(TAG, "init: 请检察KEY是否符合规格");
                continue;
            }
            // Initialize the Mobile Ads SDK.
            MobileAds.initialize(context, key[i]);
            // Create the InterstitialAd and set the adUnitId.
            final InterstitialAd interstitial = new InterstitialAd(context);
            // Defined in res/values/strings.xml
            interstitial.setAdUnitId(key[i]);
            AdvertisementList.put(key[i], interstitial);
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    setLoadAd(interstitial);
                }
            });
        }
    }

    private void setLoadAd(InterstitialAd interstitial) {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (PayStatusUtil.isSubAvailable()) {//是会员的话，直接不请求
            return;
        }

        if (!interstitial.isLoading() && !interstitial.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitial.loadAd(adRequest);
        }
    }

    public boolean show(String key) throws Exception {
        if (PayStatusUtil.isSubAvailable()) {//是会员的话，直接不请求
            return false;
        }

        if (TextUtils.isEmpty(key)) {
            throw new Exception("getInterstitialAd：参数错误·······");
        }
        if (null == AdvertisementList) {
            throw new Exception("getInterstitialAd：未初始化代码·······");
        }
        InterstitialAd interstitial = AdvertisementList.get(key);
        if (null == interstitial) {
            throw new Exception("getInterstitialAd：请检察KEY是否符合规格");
        }
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitial != null && interstitial.isLoaded()) {
            interstitial.show();
            Log.w(TAG, "showInterstitial: 弹出广告");
            return true;
        } else {
            Log.w(TAG, "showInterstitial: 广告未就绪 请稍后   Ad did not load");
            setLoadAd(interstitial);
            return false;
        }
    }
}
