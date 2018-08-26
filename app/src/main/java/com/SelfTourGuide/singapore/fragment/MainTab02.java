package com.SelfTourGuide.singapore.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.SelfTourGuide.singapore.MessageEvents;
import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.ui.ViewUtil;
import com.SelfTourGuide.singapore.util.KMD1;
import com.SelfTourGuide.singapore.util.NetUtils;
import com.SelfTourGuide.singapore.util.PayStatusUtil;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class MainTab02 extends Fragment {
	private static String TAG= com.SelfTourGuide.singapore.fragment.MainTab02.class.getSimpleName();
	SubsamplingScaleImageView imageView;
    @Bind(R.id.ll_empty_img)
	LinearLayout mUIllempty;
	@Bind(R.id.adView)
	AdView mAdView;
    @Bind(R.id.LL)
	LinearLayout LL;
	private ConnectionChangeReceiver receiver;  // 广播
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.main_tab_02, container, false);
		if (!EventBus.getDefault().isRegistered(getActivity())) EventBus.getDefault().register(getActivity());
		ButterKnife.bind(this, rootView);
		init(rootView);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		filter.setPriority(1000);
		receiver=new ConnectionChangeReceiver();
		MainTab02.this.getActivity().registerReceiver(receiver, filter);

		ViewUtil.checkADView(mAdView);
		return rootView;

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		MainTab02.this.getActivity().unregisterReceiver(receiver);
	}



	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	//接受广播 和网络判断
	public  class ConnectionChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(final Context context, Intent intent) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected() || !PayStatusUtil.isSubAvailable()) {
				// 断开
				Log.i("tag", "onReceive: 断开");
				EventBus.getDefault().post(new MessageEvents(false));
				LL.setVisibility(View.GONE);
				mUIllempty.setVisibility(View.VISIBLE);
			} else {
				Log.i("tag", "onReceive: 连接");
				EventBus.getDefault().post(new MessageEvents(true));
				LL.setVisibility(View.VISIBLE);
				mUIllempty.setVisibility(View.GONE);
				getData();
			}
		}
	}
	boolean isShow=false;

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden){
			isShow = false;
			Log.i(TAG, hidden+"-->1");
		}else {
			//可见页面
			isShow = true;
			if(NetUtils.isNetworkConnected(MainTab02.this.getActivity()) || PayStatusUtil.isSubAvailable()){
				Log.i(TAG, "有网");
				LL.setVisibility(View.VISIBLE);
				mUIllempty.setVisibility(View.GONE);
				getData();
			}else{
				Log.i(TAG, "没网");
				LL.setVisibility(View.GONE);
				mUIllempty.setVisibility(View.VISIBLE);
			}

			Log.i(TAG, hidden+"-->2");
		}
	}


	private void init(View v){
		//  创建AdRequest 加载广告横幅adview
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.i(TAG, "onAdLoaded: ");
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				Log.i(TAG, "onAdFailedToLoad: "+errorCode);
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

		imageView = (SubsamplingScaleImageView)v.findViewById(R.id.imageView);
		if(NetUtils.isNetworkConnected(MainTab02.this.getActivity()) || PayStatusUtil.isSubAvailable()){
			Log.i(TAG, "有网");
			LL.setVisibility(View.VISIBLE);
			mUIllempty.setVisibility(View.GONE);
			getData();
		}else{
			Log.i(TAG, "没网");
			imageView.setVisibility(View.GONE);
			LL.setVisibility(View.GONE);
			mUIllempty.setVisibility(View.VISIBLE);
		}

	}

	public void getData(){
		imageView.setVisibility(View.VISIBLE);
		Bitmap	 bitmap= KMD1.getImageFromAssets(MainTab02.this.getActivity(),"beijing2.png");
		if(bitmap != null) {
			imageView.setImage(ImageSource.bitmap(bitmap));
		} else {
			Log.i(TAG,"图片为空");
			System.out.println("图片为空");
		}
	}





}
