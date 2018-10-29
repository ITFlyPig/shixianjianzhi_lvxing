package com.SelfTourGuide.bangkok.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.base.BaseApplication;
import com.SelfTourGuide.bangkok.model.event.PayStatusEvent;
import com.SelfTourGuide.bangkok.ui.DownProApp;
import com.SelfTourGuide.bangkok.util.GoogleBillingUtil;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;
import com.android.billingclient.api.Purchase;
import com.google.android.gms.wearable.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;


public class FragmentMainActivity extends BaseActivity implements OnClickListener {

    String TAG = com.SelfTourGuide.bangkok.fragment.FragmentMainActivity.class.getSimpleName();
    private MainTab02 mTab02;
    private MainTab01 mTab01;
    private MainTab03 mTab03;
    private MainTab04 mTab04;

    private TextView tv_tab_bottom_weixin;
    private TextView tv_tab_bottom_friend;
    private TextView tv_tab_bottom_contact;
    private TextView tv_tab_bottom_setting;
    private LinearLayout mTabBtnWeixin;
    //地铁图
    private LinearLayout mTabBtnFrd;
    private LinearLayout mTabBtnAddress;
    private LinearLayout mTabBtnSettings;
    private FragmentManager fragmentManager;
    private String language, country;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initViews();
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.i(TAG, "内存大小：" + Long.toString(maxMemory / (1024 * 1024)));
        fragmentManager = getFragmentManager();
        setTabSelection(0);

        checkSub();

        showTip();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
    }

    ;

    private void initViews() {
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag", language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag", "country" + country);

        mTabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_weixin);
        mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_friend);
        mTabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
        mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_setting);
        tv_tab_bottom_weixin = (TextView) findViewById(R.id.tv_tab_bottom_weixin);
        tv_tab_bottom_friend = (TextView) findViewById(R.id.tv_tab_bottom_friend);
        tv_tab_bottom_contact = (TextView) findViewById(R.id.tv_tab_bottom_contact);
        tv_tab_bottom_setting = (TextView) findViewById(R.id.tv_tab_bottom_setting);

        mTabBtnWeixin.setOnClickListener(this);
        mTabBtnFrd.setOnClickListener(this);
        mTabBtnAddress.setOnClickListener(this);
        mTabBtnSettings.setOnClickListener(this);
        if (language.equals("en")) {

            if (country.equals("AS")) {
                mTabBtnFrd.setVisibility(View.GONE);
            } else {
                mTabBtnFrd.setVisibility(View.VISIBLE);
            }

        } else {
            mTabBtnFrd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_bottom_weixin:

                setTabSelection(0);
                break;
            case R.id.id_tab_bottom_friend:
			/*try {
				//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
				Advertisement.getInstance().show(getString(R.string.ad_unit_id));
			} catch (Exception e) {
				Log.d(TAG, "setContentView: 显示广告失败");
			}*/
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_contact:

                setTabSelection(2);
                break;
            case R.id.id_tab_bottom_setting:

                setTabSelection(3);
                break;

            default:
                break;
        }
    }


    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        resetBtn();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                ((ImageView) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
                        .setImageResource(R.drawable.strategyclick);
                tv_tab_bottom_weixin.setTextColor(getResources().getColor(R.color.red_normal));
                if (mTab01 == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTab01 = new MainTab01();
                    transaction.add(R.id.id_content, mTab01);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTab01);
                }
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                ((ImageView) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
                        .setImageResource(R.drawable.subwayclick);
                tv_tab_bottom_friend.setTextColor(getResources().getColor(R.color.red_normal));
                if (mTab02 == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTab02 = new MainTab02();
                    transaction.add(R.id.id_content, mTab02);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTab02);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                ((ImageView) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
                        .setImageResource(R.drawable.collect_click);
                tv_tab_bottom_contact.setTextColor(getResources().getColor(R.color.red_normal));
                if (mTab03 == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mTab03 = new MainTab03();
                    transaction.add(R.id.id_content, mTab03);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mTab03);
                }
                break;
            case 3:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                ((ImageView) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
                        .setImageResource(R.drawable.moreclick);
                tv_tab_bottom_setting.setTextColor(getResources().getColor(R.color.red_normal));
                if (mTab04 == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    mTab04 = new MainTab04();
                    transaction.add(R.id.id_content, mTab04);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(mTab04);
                }
                break;
        }
        transaction.commit();
    }

    private void resetBtn() {
        ((ImageView) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
                .setImageResource(R.drawable.strategynor);
        ((ImageView) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
                .setImageResource(R.drawable.subwaynor);
        ((ImageView) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
                .setImageResource(R.drawable.collect_nor);
        ((ImageView) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
                .setImageResource(R.drawable.morenor);
        tv_tab_bottom_weixin.setTextColor(getResources().getColor(R.color.black));
        tv_tab_bottom_friend.setTextColor(getResources().getColor(R.color.black));
        tv_tab_bottom_contact.setTextColor(getResources().getColor(R.color.black));
        tv_tab_bottom_setting.setTextColor(getResources().getColor(R.color.black));
    }

    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }

    }

    private static GoogleBillingUtil googleBillingUtil;

    /**
     * 检查有效订阅
     */
    public static void checkSub() {


        GoogleBillingUtil.cleanListener();
        if (GoogleBillingUtil.getInstance().isReady()) {
            handleSubSize();
        } else {
            googleBillingUtil = GoogleBillingUtil.getInstance()
                    .setOnStartSetupFinishedListener(mOnStartSetupFinishedListener)
                    .setOnPurchaseFinishedListener(mOnPurchaseFinishedListener)
                    .build();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        checkSub();
    }

    private static MyOnStartSetupFinishedListener mOnStartSetupFinishedListener = new MyOnStartSetupFinishedListener();//启动结果回调接口
    private static MyOnPurchaseFinishedListener mOnPurchaseFinishedListener = new MyOnPurchaseFinishedListener();//购买回调接口

    //服务初始化结果回调接口
    private static class MyOnStartSetupFinishedListener implements GoogleBillingUtil.OnStartSetupFinishedListener {
        @Override
        public void onSetupSuccess() {
            Log.d("wyl", "服务初始化结果回调接口 onSetupSuccess");


            Log.d("wyl", "开始查询已经购买商品");
            List<Purchase> inapps = googleBillingUtil.queryPurchasesInApp();
            if (inapps != null) {
                for (Purchase inapp : inapps) {
                    Log.d("wyl", "已经购买的商品：" + inapp.getSku());
                }
            }
            Log.d("wyl", "开始查询已经订阅商品");
            List<Purchase> subs = googleBillingUtil.queryPurchasesSubs();
            Log.d("wyl", "已经订阅的商品：" + (subs == null ? 0 : subs.size()));

            if (subs != null) {
                for (Purchase sub : subs) {
                    Log.d("wyl", "已经订阅的商品：" + sub.getSku());
                }
            }

            handleSubSize();

        }

        @Override
        public void onSetupFail(int responseCode) {
            Log.d("wyl", "服务初始化结果回调接口 onSetupFail");

        }

        @Override
        public void onSetupError() {
            Log.d("wyl", "服务初始化结果回调接口 onSetupError");

        }
    }

    //购买商品回调接口
    private static class MyOnPurchaseFinishedListener implements GoogleBillingUtil.OnPurchaseFinishedListener {
        @Override
        public void onPurchaseSuccess(List<Purchase> list) {
            //内购或者订阅成功,可以通过purchase.getSku()获取suk进而来判断是哪个商品
            Log.d("wyl", "购买商品回调接口 onPurchaseSuccess");

        }

        @Override
        public void onPurchaseFail(int responseCode) {
            Log.d("wyl", "购买商品回调接口 onPurchaseFail：" + responseCode);

        }

        @Override
        public void onPurchaseError() {
            Log.d("wyl", "购买商品回调接口 onPurchaseError");

        }

    }

    private static void handleSubSize() {
        int size = googleBillingUtil.getPurchasesSizeSubs();

//        Toast.makeText(BaseApplication.getInstance(), "有效订阅的数量：" + size, Toast.LENGTH_LONG).show();

        if (size > 0) {
            PayStatusUtil.savePaySubStatus(true);
        } else {
            PayStatusUtil.savePaySubStatus(false);
        }

        EventBus.getDefault().post(new PayStatusEvent());
    }

    private Dialog dialog;
    private View rating_but;
    private View watch_video_ad_but;
    private void showDialog(){
        dialog= new Dialog(this,R.style.dialog);
        View view = View.inflate(this, R.layout.dialogunlock, null);
        rating_but = (TextView)view.findViewById(R.id.rating_but);
        watch_video_ad_but = (TextView)view.findViewById(R.id.watch_video_ad_but);
        rating_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(getBaseContext(), DownProApp.class);
                startActivity(intent);

            }
        });
        watch_video_ad_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }


            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = LinearLayout.LayoutParams.FILL_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        //点击dialog之外的区域禁止取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    /**
     * 显示提示
     */
    private void showTip() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog();
            }
        }, 1000 * 60);
    }

}
