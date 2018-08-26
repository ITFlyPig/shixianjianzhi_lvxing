package com.SelfTourGuide.singapore.fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.base.BaseActivity;
import com.SelfTourGuide.singapore.util.GoogleBillingUtil;
import com.SelfTourGuide.singapore.util.PayStatusUtil;
import com.google.android.gms.wearable.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;


public class FragmentMainActivity extends BaseActivity implements OnClickListener {

	String TAG= com.SelfTourGuide.singapore.fragment.FragmentMainActivity.class.getSimpleName();
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
    private String language,country;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initViews();
		Runtime rt=Runtime.getRuntime();
		long maxMemory=rt.maxMemory();
		Log.i(TAG,"内存大小："+Long.toString(maxMemory/(1024*1024)));
		fragmentManager = getFragmentManager();
		setTabSelection(0);

		checkSub();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MessageEvent event) {
        /* Do something */
	};

	private void initViews() {
		//获取本地语言
		language = Locale.getDefault().getLanguage();
		Log.e("tag",language);
		country = getResources().getConfiguration().locale.getCountry();
		Log.e("tag","country"+country);

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
		if(language.equals("en")){

			if (country.equals("AS")){
				mTabBtnFrd.setVisibility(View.GONE);
			}else{
				mTabBtnFrd.setVisibility(View.VISIBLE);
			}

		}else{
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
			if (mTab01 == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab01 = new MainTab01();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab01);
			}
			break;
			case 1:
				// 当点击了消息tab时，改变控件的图片和文字颜色
				((ImageView) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
						.setImageResource(R.drawable.subwayclick);
				tv_tab_bottom_friend.setTextColor(getResources().getColor(R.color.red_normal));
				if (mTab02 == null)
				{
					// 如果MessageFragment为空，则创建一个并添加到界面上
					mTab02 = new MainTab02();
					transaction.add(R.id.id_content, mTab02);
				} else
				{
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(mTab02);
				}
				break;
			case 2:
				// 当点击了动态tab时，改变控件的图片和文字颜色
				((ImageView) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
						.setImageResource(R.drawable.collect_click);
				tv_tab_bottom_contact.setTextColor(getResources().getColor(R.color.red_normal));
				if (mTab03 == null)
				{
					// 如果NewsFragment为空，则创建一个并添加到界面上
					mTab03 = new MainTab03();
					transaction.add(R.id.id_content, mTab03);
				} else
				{
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
				} else
				{
					// 如果SettingFragment不为空，则直接将它显示出来
					transaction.show(mTab04);
				}
				break;
		}
		transaction.commit();
	}

	private void resetBtn()
	{
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
	private void hideFragments(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
		if (mTab04 != null)
		{
			transaction.hide(mTab04);
		}
		
	}

	private GoogleBillingUtil googleBillingUtil;
	/**
	 * 检查有效订阅
	 */
	private void checkSub() {

		googleBillingUtil = GoogleBillingUtil.getInstance()
				.build();
		int size = googleBillingUtil.getPurchasesSizeSubs();
		Toast.makeText(this, "谷歌查询有效订阅返回：" + size, Toast.LENGTH_LONG).show();
		if (size > 0) {
			PayStatusUtil.savePaySubStatus(true);
		} else if (size == 0){
			PayStatusUtil.savePaySubStatus(false);
		}
	}

}
