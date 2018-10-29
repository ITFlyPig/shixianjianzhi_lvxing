package com.SelfTourGuide.bangkok.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.model.event.PayStatusEvent;
import com.SelfTourGuide.bangkok.ui.DownProApp;
import com.SelfTourGuide.bangkok.ui.ViewUtil;
import com.SelfTourGuide.bangkok.ui.WebViewActivity;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class MainTab04 extends BaseFragment {
    private final String TAG = MainTab04.class.getSimpleName();
    @Bind(R.id.tv_privacy)
    TextView tvPrivacy;
    @Bind(R.id.tv_use)
    TextView tvUse;
    private TextView tv_feedback, tv_comment;
    @Bind(R.id.adView)
    AdView mAdView;
    @Bind(R.id.tv_Get_PRO)
    TextView tv_Get_PRO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newsLayout = inflater.inflate(R.layout.main_tab_04, container, false);
        ButterKnife.bind(this, newsLayout);
        init(newsLayout);
        return newsLayout;

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

//		SQLiteDatabase.loadLibs(MainTab04.this.getActivity());
        tv_feedback = (TextView) v.findViewById(R.id.tv_feedback);
        tv_feedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:rekindleteam@gmail.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
                data.putExtra(Intent.EXTRA_TEXT, "这是内容");
                startActivity(data);

            }
        });
        tv_comment = (TextView) v.findViewById(R.id.tv_comment);
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.SelfTourGuide.singapore")));
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.SelfTourGuide.bangkok")));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.SelfTourGuide.singapore")));
                }

			/*	//这里开始执行一个应用市场跳转逻辑，默认this为Context上下文对象
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=" + "com.SelfTourGuide.singapore")); //跳转到应用市场，非Google Play市场一般情况也实现了这个接口
				//存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
				if (intent.resolveActivity(MainTab04.this.getActivity().getPackageManager()) != null) { //可以接收
					startActivity(intent);
				} else { //没有应用市场，我们通过浏览器跳转到Google Play
					intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.SelfTourGuide.singapore"));
					//这里存在一个极端情况就是有些用户浏览器也没有，再判断一次
					if (intent.resolveActivity(MainTab04.this.getActivity().getPackageManager()) != null) { //有浏览器
						startActivity(intent);
					} else { //天哪，这还是智能手机吗？
						Toast.makeText(MainTab04.this.getActivity(), "您没安装应用市场，连浏览器也没有", Toast.LENGTH_SHORT).show();
					}
				}*/

            }
        });


        tv_Get_PRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTab04.this.getActivity(), DownProApp.class);
                startActivity(intent);
            }
        });

        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("url","https://cityguides.kuaizhan.com/59/0/p437768400c952b");
                startActivity(intent);
            }
        });

        tvUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("url","https://cityguides.kuaizhan.com/77/57/p43776854184062");
                startActivity(intent);

            }
        });

        ViewUtil.checkADView(mAdView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onPayStatusChange(PayStatusEvent payStatusEvent) {
        ViewUtil.checkADView(mAdView);

    }
}
