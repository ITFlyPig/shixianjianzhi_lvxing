package com.SelfTourGuide.bangkok.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SelfTourGuide.bangkok.MessageEvents;
import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.bangkok.assetsdb.OverViewDao;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.model.OverviewModel;
import com.SelfTourGuide.bangkok.util.Advertisement;
import com.SelfTourGuide.bangkok.util.MyScrollView;
import com.SelfTourGuide.bangkok.util.OverScrollView;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class OverViewActivity extends BaseActivity implements View.OnClickListener,MyScrollView.OnHeaderRefreshListener {
    private final  String TAG= OverViewActivity.class.getSimpleName();

    @Bind(R.id.content)
    TextView mUITextContent;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;

    OverViewDao database;
    private ArrayList<OverviewModel> listdate;
    @Bind(R.id.scrollView)
    OverScrollView mScrollView;
    String language,country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        ButterKnife.bind(this);
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvents event) {
        /* Do something */
        if (event.getConnect()==true || PayStatusUtil.isSubAvailable()){
            mUIllempty.setVisibility(View.GONE);
            mUITextContent.setVisibility(View.VISIBLE);
            getdata();
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
            mUITextContent.setVisibility(View.GONE);
        }
    };

   private void getdata(){
       if (language.equals("zh")){
           if(country.equals("TW")){
               listdate = database.queryLanguage("zh-tw");
           }else{
               listdate = database.queryLanguage("zh");
           }
       }else{
           listdate = database.queryLanguage("en");
           Log.e("tag","共"+listdate.size()+"条数据");
       }
       if (listdate!=null&&listdate.size()>0){
           mUIllempty.setVisibility(View.GONE);
           mUITextContent.setText(listdate.get(0).getDescription()+"\n"+"\n"+"\n");
       }
   }

    private void initData(){
       //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.bangkok.ui.OverViewActivity.this);
        database = new OverViewDao();
        listdate=new ArrayList<OverviewModel>();
        //获取本地语言
         language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
         country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
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
        getdata();
    }

    @Override
    public void onHeaderRefresh(MyScrollView view) {
        Toast.makeText(getApplicationContext(), "刷新",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:

                finish();
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
                break;
            default:
                break;
        }
    }
}
