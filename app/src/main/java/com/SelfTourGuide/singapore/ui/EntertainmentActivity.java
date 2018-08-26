package com.SelfTourGuide.singapore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.SelfTourGuide.singapore.MessageEvents;
import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.adapter.EntertainmentAdapter;
import com.SelfTourGuide.singapore.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.singapore.assetsdb.EntertainmentDao;
import com.SelfTourGuide.singapore.base.BaseActivity;
import com.SelfTourGuide.singapore.model.EntertainmentModel;
import com.SelfTourGuide.singapore.util.Advertisement;
import com.SelfTourGuide.singapore.util.NetUtils;
import com.SelfTourGuide.singapore.util.PayStatusUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EntertainmentActivity extends BaseActivity {

    private final String TAG= com.SelfTourGuide.singapore.ui.EntertainmentActivity.class.getSimpleName();

    @Bind(R.id.mListView)
    ListView mUIListView;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;

    private String language,country;
    private EntertainmentDao entertainmentDao;
    private ArrayList<EntertainmentModel> listdate;
    private EntertainmentAdapter adapter;
    @Bind(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        ButterKnife.bind(this);

        initData();

        ViewUtil.checkADView(mAdView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvents event) {
        /* Do something */
        if (event.getConnect()==true || PayStatusUtil.isSubAvailable()){
            mUIListView.setVisibility(View.VISIBLE);
            mUIllempty.setVisibility(View.GONE);
            getData();
        }else{
            mUIListView.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }
    };

    private void getData() {
        AssetsDatabaseManager.initManager(com.SelfTourGuide.singapore.ui.EntertainmentActivity.this);
        entertainmentDao = new EntertainmentDao();
        if (language.equals("zh")) {
            if (country.equals("TW")) {
                listdate = entertainmentDao.queryLanguage("zh-tw");
            } else {
                listdate = entertainmentDao.queryLanguage("zh");
            }
        } else {
            listdate = entertainmentDao.queryLanguage("en");
        }
        if (listdate != null && listdate.size() > 0) {
            adapter = new EntertainmentAdapter(this, listdate);
            mUIllempty.setVisibility(View.GONE);
            mUIListView.setAdapter(adapter);
        }
    }

    private void initData() {

        listdate = new ArrayList<EntertainmentModel>();
        //获取本地语言
         language = Locale.getDefault().getLanguage();
        Log.e("tag", language);
         country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag", "country" + country);
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.singapore.ui.EntertainmentActivity.this);
        entertainmentDao = new EntertainmentDao();
        if (language.equals("zh")) {
            if (country.equals("TW")) {
                listdate = entertainmentDao.queryLanguage("zh-tw");
            } else {
                listdate = entertainmentDao.queryLanguage("zh");
            }
        } else {
            listdate = entertainmentDao.queryLanguage("en");
        }
        if (language.equals("zh")) {
            if (country.equals("TW")) {
                listdate = entertainmentDao.queryLanguage("zh-tw");
            } else {
                listdate = entertainmentDao.queryLanguage("zh");
            }
        } else {
            listdate = entertainmentDao.queryLanguage("en");
        }
        if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.ui.EntertainmentActivity.this) || PayStatusUtil.isSubAvailable()) {
            if (listdate != null && listdate.size() > 0) {
                adapter = new EntertainmentAdapter(this, listdate);
                mUIllempty.setVisibility(View.GONE);
                mUIListView.setAdapter(adapter);
            }
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
            mUIListView.setVisibility(View.GONE);
        }
        Log.e("tag", "共" + listdate.size() + "条数据");
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);
        if (NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.ui.EntertainmentActivity.this) || PayStatusUtil.isSubAvailable()) {
            if (listdate != null && listdate.size() > 0) {

                adapter = new EntertainmentAdapter(this, listdate);
                mUIllempty.setVisibility(View.GONE);
                mUIListView.setAdapter(adapter);
            }

        } else {
//            AlertUtil.toast(this, "网络连接失败");
            mUIllempty.setVisibility(View.VISIBLE);
        }

        mUIListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    Log.d(TAG, "##### 滚动到顶部 #####");
                    mUIListView.setPadding(0,0,0,0);
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    Log.d(TAG, "##### 滚动到底部 ######");
                    mUIListView.setPadding(0,0,0,130);
                }
            }
        });

        mUILLback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
                        Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                        finish();
                    }else{
                        finish();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
            }
        });
        mUIListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(com.SelfTourGuide.singapore.ui.EntertainmentActivity.this,EntertainmentInfoActivity.class);
                intent.putExtra("title",listdate.get(position).getEntertainment_name());
                intent.putExtra("id",listdate.get(position).getEntertainmentid());
                startActivity(intent);
            }
        });
    }
}
