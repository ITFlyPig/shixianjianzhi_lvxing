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
import com.SelfTourGuide.bangkok.adapter.HotelAdapter;
import com.SelfTourGuide.bangkok.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.bangkok.assetsdb.HotelDao;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.model.HotelModel;
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

public class HotelActivity extends BaseActivity {

    private static final String TAG= com.SelfTourGuide.bangkok.ui.HotelActivity.class.getSimpleName();

    @Bind(R.id.mListView)
    ListView mUIListView;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;

    private String country,language;
    private HotelDao hotelDao;
    private ArrayList<HotelModel> listdate;
    private HotelAdapter adapter;
    @Bind(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
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


    private void getData(){
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = hotelDao.queryLanguage("zh-tw");
            }else{
                listdate = hotelDao.queryLanguage("zh");
            }
        }else{
            listdate = hotelDao.queryLanguage("en");
        }
        if (listdate!=null&&listdate.size()>0){
            adapter = new HotelAdapter(this,listdate);
            mUIllempty.setVisibility(View.GONE);
            mUIListView.setAdapter(adapter);
        }

        Log.i(TAG,"共"+listdate.size()+"条数据");
    }

    private void initData(){

        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.bangkok.ui.HotelActivity.this);
        hotelDao = new HotelDao();
        listdate=new ArrayList<HotelModel>();
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = hotelDao.queryLanguage("zh-tw");
            }else{
                listdate = hotelDao.queryLanguage("zh");
            }
        }else{
            listdate = hotelDao.queryLanguage("en");
        }
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
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
                Intent intent=new Intent(com.SelfTourGuide.bangkok.ui.HotelActivity.this,HotelInfoActivity.class);
                intent.putExtra("title",listdate.get(position).getHotel_name());
                intent.putExtra("id",listdate.get(position).getHotelid());
                intent.putExtra("rating",listdate.get(position).getStar_rating());
                Log.i(TAG,listdate.get(position).getHotel_name()+"酒店");
                startActivity(intent);
            }
        });


        if(NetUtils.isNetworkConnected(com.SelfTourGuide.bangkok.ui.HotelActivity.this) || PayStatusUtil.isSubAvailable()){
            if (listdate!=null&&listdate.size()>0){
                adapter = new HotelAdapter(this,listdate);
                mUIllempty.setVisibility(View.GONE);
                mUIListView.setAdapter(adapter);
            }
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
        }
    }

    private int[] imageurls={
            R.drawable.l0001, R.drawable.l0002, R.drawable.l0003,
            R.drawable.l0004, R.drawable.l0005, R.drawable.l0006,
            R.drawable.l0007, R.drawable.l0008, R.drawable.l0009,
            R.drawable.l0010, R.drawable.l0011, R.drawable.l0012,
            R.drawable.l0013, R.drawable.l0014, R.drawable.l0015,
            R.drawable.l0016, R.drawable.l0017, R.drawable.l0018,
            R.drawable.l0019, R.drawable.l0020, R.drawable.l0022,
            R.drawable.l0023, R.drawable.l0024, R.drawable.l0025,
            R.drawable.l0026, R.drawable.l0027, R.drawable.l0028,
            R.drawable.l0029 ,R.drawable.l0029,
            R.drawable.l0029 ,R.drawable.l0029
            ,
            R.drawable.l0029 ,R.drawable.l0029

    };
}
