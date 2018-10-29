package com.SelfTourGuide.bangkok.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.MessageEvents;
import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.model.event.PayStatusEvent;
import com.SelfTourGuide.bangkok.ui.DownProApp;
import com.SelfTourGuide.bangkok.ui.ViewUtil;
import com.SelfTourGuide.bangkok.util.KMD1;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class MainTab02 extends BaseFragment implements View.OnClickListener {
    private static String TAG = MainTab02.class.getSimpleName();
    SubsamplingScaleImageView imageView;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;
    @Bind(R.id.adView)
    AdView mAdView;
    @Bind(R.id.LL)
    LinearLayout LL;
    @Bind(R.id.tv_remove_id)
    TextView tvRemoveId;


    private ConnectionChangeReceiver receiver;  // 广播

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_02, container, false);
        if (!EventBus.getDefault().isRegistered(getActivity()))
            EventBus.getDefault().register(getActivity());
        ButterKnife.bind(this, rootView);
        init(rootView);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.setPriority(1000);
        receiver = new ConnectionChangeReceiver();
        MainTab02.this.getActivity().registerReceiver(receiver, filter);
        tvRemoveId.setOnClickListener(this);

        ViewUtil.checkADView(mAdView);
        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainTab02.this.getActivity().unregisterReceiver(receiver);
        ButterKnife.unbind(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_remove_id:
                Intent intent = new Intent(getContext(), DownProApp.class);
                startActivity(intent);
                break;
        }
    }

    //接受广播 和网络判断
    public class ConnectionChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (PayStatusUtil.shouldShowEmpty(context)) {
                // 断开
                Log.i("wyl", "onReceive: 断开显示空uI");
                EventBus.getDefault().post(new MessageEvents(false));
                LL.setVisibility(View.GONE);
                mUIllempty.setVisibility(View.VISIBLE);
            } else {
                Log.i("wyl", "onReceive: 连接不显示空UI");
                EventBus.getDefault().post(new MessageEvents(true));
                LL.setVisibility(View.VISIBLE);
                mUIllempty.setVisibility(View.GONE);
                getData();
            }
        }
    }

    boolean isShow = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            isShow = false;
            Log.i(TAG, hidden + "-->1");
        } else {
            //可见页面
            isShow = true;
            if (!PayStatusUtil.shouldShowEmpty(getContext())) {
                Log.i(TAG, "有网");
                LL.setVisibility(View.VISIBLE);
                mUIllempty.setVisibility(View.GONE);
                getData();
            } else {
                Log.i(TAG, "没网");
                LL.setVisibility(View.GONE);
                mUIllempty.setVisibility(View.VISIBLE);
            }

            Log.i(TAG, hidden + "-->2");
        }
    }


    private void init(View v) {
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i(TAG, "onAdFailedToLoad: " + errorCode);
            }

            @Override
            public void onAdOpened() {
                Log.i(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdClosed() {
                Log.i(TAG, "onAdClosed: ");
            }

            @Override
            public void onAdLeftApplication() {
                Log.i(TAG, "onAdLeftApplication: ");
            }
        });
        // 最后，请求广告。
        mAdView.loadAd(adRequest);

        imageView = (SubsamplingScaleImageView) v.findViewById(R.id.imageView);
        if (!PayStatusUtil.shouldShowEmpty(getContext())) {
            Log.i(TAG, "有网");
            LL.setVisibility(View.VISIBLE);
            mUIllempty.setVisibility(View.GONE);
            getData();
        } else {
            Log.i(TAG, "没网");
            imageView.setVisibility(View.GONE);
            LL.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }

    }

    public void getData() {
        imageView.setVisibility(View.VISIBLE);
        Bitmap bitmap = KMD1.getImageFromAssets(MainTab02.this.getActivity(), "bangkokSubWay.png");
        if (bitmap != null) {
            imageView.setImage(ImageSource.bitmap(bitmap));
        } else {
            Log.i(TAG, "图片为空");
            System.out.println("图片为空");
        }
    }


    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onPayStatusChange(PayStatusEvent payStatusEvent) {
        Log.d("wyl", "订阅的状态改变了:" + PayStatusUtil.isSubAvailable());
        refreshUI();
        ViewUtil.checkADView(mAdView);
        Log.d("wylad", "MainTab02");

    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        if (mUIllempty == null) {
            return;
        }
        if (!PayStatusUtil.shouldShowEmpty(getContext())) {
            LL.setVisibility(View.VISIBLE);
            mUIllempty.setVisibility(View.GONE);
            getData();
        } else {
            LL.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }
    }

}
