package com.SelfTourGuide.bangkok.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.fragment.FragmentMainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.wearable.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;


public class StartActivity extends BaseActivity {

    private static final String TAG = com.SelfTourGuide.bangkok.ui.StartActivity.class.getSimpleName();
    private NativeExpressAdView adView;
    private TextView timeTextView;
    ImageView img_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
     /*   ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
            @Override public void onAvailable(Network network) {
                super.onAvailable(network);
            }
        });*/
        init();

    }

    public void init(){
        img_start  = (ImageView) findViewById(R.id.img_start);
        adView = (NativeExpressAdView)findViewById(R.id.nativeAdView);
        timeTextView= (TextView) findViewById(R.id.mTasksView);
        //获取本地语言
        String language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
//        if (language.equals("en")){
//            adView.setVisibility(View.GONE);
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//                    Intent intent = new Intent(com.SelfTourGuide.singapore.ui.StartActivity.this,FragmentMainActivity.class);//新建一个意图，也就是跳转的界面
//                    startActivity(intent);//开始跳转
//                    finish();
//                }
//            }, 2000);
//        }else{
//            img_start.setVisibility(View.GONE);
//            adView.setVisibility(View.VISIBLE);
//            initVariable();
//        }

        Intent intent = new Intent(com.SelfTourGuide.bangkok.ui.StartActivity.this,FragmentMainActivity.class);//新建一个意图，也就是跳转的界面
        startActivity(intent);//开始跳转
        finish();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
    };


    private void initVariable() {
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
      /*  mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {
                Log.i(TAG, "Video playback is finished.");
                super.onVideoEnd();
            }
        });*/
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i(TAG, "onAdLoaded: ");
                startNormalCountDownTime(5);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i(TAG, "onAdFailedToLoad: ");
                adView.setVisibility(View.GONE);
                img_start.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(com.SelfTourGuide.bangkok.ui.StartActivity.this,FragmentMainActivity.class);//新建一个意图，也就是跳转的界面
                        startActivity(intent);//开始跳转
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
            }
        });

    }

    private void startNormalCountDownTime(long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
        CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick  " + millisUntilFinished / 1000);
                timeTextView.setVisibility(View.VISIBLE);
                timeTextView.setText(getString(R.string.skip)+"  " + millisUntilFinished / 1000  );
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish -- 倒计时结束");
                Intent intent = new Intent(com.SelfTourGuide.bangkok.ui.StartActivity.this,FragmentMainActivity.class);//新建一个意图，也就是跳转的界面
                startActivity(intent);//开始跳转
                finish();
            }
        };
        timer.start();

    }

}
