package com.SelfTourGuide.singapore.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.ui.AttractionsActivity;
import com.SelfTourGuide.singapore.ui.EntertainmentActivity;
import com.SelfTourGuide.singapore.ui.HotelActivity;
import com.SelfTourGuide.singapore.ui.OverViewActivity;
import com.SelfTourGuide.singapore.ui.RestaurantActivity;
import com.SelfTourGuide.singapore.ui.ShoppingActivity;
import com.SelfTourGuide.singapore.ui.ViewUtil;
import com.SelfTourGuide.singapore.util.Advertisement;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class MainTab01 extends Fragment implements View.OnClickListener{
    private final String TAG= com.SelfTourGuide.singapore.fragment.MainTab01.class.getSimpleName();
	@Bind(R.id.rl_overview)
	RelativeLayout mUITabLayoutBbs;
    @Bind(R.id.rl_attractions)
	RelativeLayout mUITab_attractions;
	@Bind(R.id.rl_hotel)
	RelativeLayout rl_hotel;
	@Bind(R.id.rl_restaurant)
	RelativeLayout rl_restaurant;
	@Bind(R.id.rl_shop)
	RelativeLayout rl_shop;
	@Bind(R.id.rl_entertainment)
	RelativeLayout rl_entertainment;
	/*@Bind(R.id.rl_Air_tickets)
	RelativeLayout rl_Air_tickets;*/
	@Bind(R.id.adView)
	AdView mAdView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.main_tab_01, container, false);
		ButterKnife.bind(this, rootView);

		ViewUtil.checkADView(mAdView);
		onClick();
		return rootView;

	}


	public void onClick(){
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

		mUITabLayoutBbs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告

					if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
						Advertisement.getInstance().show(getString(R.string.ad_unit_id));
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), OverViewActivity.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), OverViewActivity.class);
						startActivity(intent);
					}

				} catch (Exception e) {
					Log.d(TAG, "setContentView: 显示广告失败");
				}

			}
		});
		mUITab_attractions.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
					if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
						Advertisement.getInstance().show(getString(R.string.ad_unit_id));
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), AttractionsActivity.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), AttractionsActivity.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.d(TAG, "setContentView: 显示广告失败");
				}


			}
		});
		rl_hotel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
					if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
						Advertisement.getInstance().show(getString(R.string.ad_unit_id));
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), HotelActivity.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), HotelActivity.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.d(TAG, "setContentView: 显示广告失败");
				}

			}
		});
		rl_restaurant.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
					if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
						Advertisement.getInstance().show(getString(R.string.ad_unit_id));
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), RestaurantActivity.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), RestaurantActivity.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.d(TAG, "setContentView: 显示广告失败");
				}
			}
		});
		rl_shop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
					if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
						Advertisement.getInstance().show(getString(R.string.ad_unit_id));
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), ShoppingActivity.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), ShoppingActivity.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.d(TAG, "setContentView: 显示广告失败");
				}

			}
		});
		rl_entertainment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
					if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
						Advertisement.getInstance().show(getString(R.string.ad_unit_id));
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), EntertainmentActivity.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab01.this.getActivity(), EntertainmentActivity.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.d(TAG, "setContentView: 显示广告失败");
				}

			}
		});
	/*	rl_Air_tickets.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainTab01.this.getActivity(), EntertainmentActivity.class);
				startActivity(intent);
			}
		});*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.rl_overview:

				break;
			default:
				break;
		}
	}
}
