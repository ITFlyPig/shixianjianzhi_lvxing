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
import com.SelfTourGuide.bangkok.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.bangkok.assetsdb.AttractionsDao;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.model.AttractionModel;
import com.SelfTourGuide.bangkok.adapter.AttractionsAdapter;
import com.SelfTourGuide.bangkok.util.Advertisement;
import com.SelfTourGuide.bangkok.util.NetUtils;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AttractionsActivity extends BaseActivity {
    private int netMobile;
    private static final String TAG= com.SelfTourGuide.bangkok.ui.AttractionsActivity.class.getSimpleName();

    @Bind(R.id.mListView)
    ListView mUIListView;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;
    private AttractionsDao attractionsDao;
//    private AttractionsHelper addressHelper;
    private ArrayList<AttractionModel> listdate,adapterlistdata;
    private AttractionsAdapter adapter;
    @Bind(R.id.adView)
    AdView mAdView;
    private String language,country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        ButterKnife.bind(this);
        initData();

        ViewUtil.checkADView(mAdView);
    }

    private void initData(){
        Log.i(TAG, "initData: "+getString(R.string.bannerunit));
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.bangkok.ui.AttractionsActivity.this);
        attractionsDao = new AttractionsDao();
        adapterlistdata = new ArrayList<AttractionModel>();
        listdate=new ArrayList<AttractionModel>();
        //获取本地语言
         language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
         country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        if(listdate!=null){
            listdate.clear();
        }
        if(adapterlistdata!=null){
            adapterlistdata.clear();
        }
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = attractionsDao.queryLanguage("zh-tw");
            }else{
                listdate = attractionsDao.queryLanguage("zh");
            }
        }else{
            listdate = attractionsDao.queryLanguage("en");
        }
        Log.i(TAG, "initData: "+listdate.size());
        mUIListView.setVisibility(View.VISIBLE);
        if(NetUtils.isNetworkConnected(com.SelfTourGuide.bangkok.ui.AttractionsActivity.this) || PayStatusUtil.isSubAvailable()){
            if (listdate!=null&&listdate.size()>0){
                for (int i = 0; i < listdate.size(); i++) {
                    AttractionModel attractionModel=new AttractionModel();
                    attractionModel.setImageUrl(imageurls[i]);
                    attractionModel.setAttractionid(listdate.get(i).getAttractionid());
                    attractionModel.setAttraction_name(listdate.get(i).getAttraction_name());
                    attractionModel.setLanguage(listdate.get(i).getLanguage());
                    attractionModel.setOpentime(listdate.get(i).getOpentime());
                    attractionModel.setHowlong(listdate.get(i).getHowlong());
                    attractionModel.setTicket_price(listdate.get(i).getTicket_price());
                    attractionModel.setAddress(listdate.get(i).getAddress());
                    attractionModel.setTelephone(listdate.get(i).getTelephone());
                    attractionModel.setInstroduction(listdate.get(i).getInstroduction());
                    attractionModel.setSubwag(listdate.get(i).getSubwag());
                    attractionModel.setWebsite(listdate.get(i).getWebsite());
                    attractionModel.setLogoid(listdate.get(i).getLogoid());
                    adapterlistdata.add(attractionModel);
                }
                adapter = new AttractionsAdapter(this,adapterlistdata);
                mUIllempty.setVisibility(View.GONE);
                mUIListView.setAdapter(adapter);
                Log.e(TAG,"共"+listdate.size()+"条数据");
                Log.e(TAG,"共"+adapterlistdata.size()+"条数据");
                Log.e(TAG,"共"+imageurls.length+"条图片");
            }
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
            mUIListView.setVisibility(View.GONE);
        }



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
//                AlertToast("onAdFailedToLoad: "+errorCode);
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
                Intent intent=new Intent(com.SelfTourGuide.bangkok.ui.AttractionsActivity.this,AttractionsInfoActivity.class);
                intent.putExtra("imageurl",adapterlistdata.get(position).getImageUrl());
                intent.putExtra("title",adapterlistdata.get(position).getAttraction_name());
                intent.putExtra("id",adapterlistdata.get(position).getAttractionid());
                Log.i(TAG, "onItemClick: "+adapterlistdata.get(position).getAttraction_name());
                startActivity(intent);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvents event) {
        /* Do something */
        if (event.getConnect()==true || PayStatusUtil.isSubAvailable()){
            getDate();
        }else{
            mUIListView.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }
    };


    public void getDate(){
        if(listdate!=null){
            listdate.clear();
        }
        if(adapterlistdata!=null){
            adapterlistdata.clear();
        }
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = attractionsDao.queryLanguage("zh-tw");
            }else{
                listdate = attractionsDao.queryLanguage("zh");
            }
        }else{
            listdate = attractionsDao.queryLanguage("en");
        }
        mUIListView.setVisibility(View.VISIBLE);
        if (listdate!=null&&listdate.size()>0){
            for (int i = 0; i < listdate.size(); i++) {
                AttractionModel attractionModel=new AttractionModel();
                attractionModel.setImageUrl(imageurls[i]);
                attractionModel.setAttractionid(listdate.get(i).getAttractionid());
                attractionModel.setAttraction_name(listdate.get(i).getAttraction_name());
                attractionModel.setLanguage(listdate.get(i).getLanguage());
                attractionModel.setOpentime(listdate.get(i).getOpentime());
                attractionModel.setHowlong(listdate.get(i).getHowlong());
                attractionModel.setTicket_price(listdate.get(i).getTicket_price());
                attractionModel.setAddress(listdate.get(i).getAddress());
                attractionModel.setTelephone(listdate.get(i).getTelephone());
                attractionModel.setInstroduction(listdate.get(i).getInstroduction());
                attractionModel.setSubwag(listdate.get(i).getSubwag());
                attractionModel.setWebsite(listdate.get(i).getWebsite());
                attractionModel.setLogoid(listdate.get(i).getLogoid());
                adapterlistdata.add(attractionModel);
            }
            adapter = new AttractionsAdapter(this,adapterlistdata);
            mUIllempty.setVisibility(View.GONE);
            mUIListView.setAdapter(adapter);
        }
    }

    private int[] imageurls={
            R.drawable.l0001, R.drawable.l0002, R.drawable.l0003,
            R.drawable.l0004, R.drawable.l0005, R.drawable.l0006,
            R.drawable.l0007, R.drawable.l0008, R.drawable.l0009,
            R.drawable.l0010, R.drawable.l0011, R.drawable.l0012,
            R.drawable.l0013, R.drawable.l0014, R.drawable.l0015,
            R.drawable.l0016, R.drawable.l0017, R.drawable.l0018,
            R.drawable.l0019, R.drawable.l0020,R.drawable.l0021,
            R.drawable.l0022, R.drawable.l0023,R.drawable.l0024,
            R.drawable.l0025, R.drawable.l0026,R.drawable.l0027,
            R.drawable.l0028, R.drawable.l0029,R.drawable.l0030,
            R.drawable.l0031, R.drawable.l0032,R.drawable.l0033,
            R.drawable.l0034, R.drawable.l0035

    };


}
