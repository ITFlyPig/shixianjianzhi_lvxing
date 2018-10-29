package com.SelfTourGuide.bangkok.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.SelfTourGuide.bangkok.MessageEvents;
import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.adapter.ShoppingAdapter;
import com.SelfTourGuide.bangkok.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.bangkok.assetsdb.ShoppingDao;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.model.ShoppingModel;
import com.SelfTourGuide.bangkok.util.Advertisement;
import com.SelfTourGuide.bangkok.util.NetUtils;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingActivity extends BaseActivity {
    private static  final String TAG= com.SelfTourGuide.bangkok.ui.ShoppingActivity.class.getSimpleName();

    @Bind(R.id.mListView)
    ListView mUIListView;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;
    @Bind(R.id.adView)
    AdView mAdView;

    private ShoppingDao shoppingDao;
    private ArrayList<ShoppingModel> listdate;
    private ShoppingAdapter adapter;
    String language,country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
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
            getdata();
        }else{
            mUIListView.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }
    };

    private void getdata(){
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = shoppingDao.queryLanguage("zh-tw");
            }else{
                listdate = shoppingDao.queryLanguage("zh");
            }
        }else{
            listdate = shoppingDao.queryLanguage("en");
        }
        Log.e("tag","共"+listdate.size()+"条数据");

            if (listdate!=null&&listdate.size()>0){
                adapter = new ShoppingAdapter(this,listdate);
                mUIllempty.setVisibility(View.GONE);
                mUIListView.setVisibility(View.VISIBLE);
                mUIListView.setAdapter(adapter);
            }

    }

    private void initData(){

        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.bangkok.ui.ShoppingActivity.this);
        shoppingDao = new ShoppingDao();
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);
        listdate=new ArrayList<ShoppingModel>();
        //获取本地语言
         language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
         country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = shoppingDao.queryLanguage("zh-tw");
            }else{
                listdate = shoppingDao.queryLanguage("zh");
            }
        }else{
            listdate = shoppingDao.queryLanguage("en");
        }
        Log.e("tag","共"+listdate.size()+"条数据");


        boolean isvalible = PayStatusUtil.isSubAvailable();
        if(NetUtils.isNetworkConnected(com.SelfTourGuide.bangkok.ui.ShoppingActivity.this) || PayStatusUtil.isSubAvailable()){
            if (listdate!=null&&listdate.size()>0){
                adapter = new ShoppingAdapter(this,listdate);
                mUIllempty.setVisibility(View.GONE);
                mUIListView.setVisibility(View.VISIBLE);
                mUIListView.setAdapter(adapter);
            }
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
        }

        mUIListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    mUIListView.setPadding(0,0,0,0);
                    Log.d(TAG, "##### 滚动到顶部 #####");
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
//                    if(Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
//                        Advertisement.getInstance().show(getString(R.string.ad_unit_id));
//                        finish();
//                    }else{
//                        finish();
//                    }
                    finish();
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
            }
        });
        mUIListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(com.SelfTourGuide.bangkok.ui.ShoppingActivity.this,ShoppingInfoActivity.class);
                intent.putExtra("title",listdate.get(position).getShopping_name());
                intent.putExtra("id",listdate.get(position).getShoppingid());
                startActivity(intent);
            }
        });
    }

}
