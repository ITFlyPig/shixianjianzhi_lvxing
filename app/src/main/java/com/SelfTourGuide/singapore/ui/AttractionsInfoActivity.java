package com.SelfTourGuide.singapore.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SelfTourGuide.singapore.MessageEvents;
import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.singapore.assetsdb.AttractionsDao;
import com.SelfTourGuide.singapore.base.BaseActivity;
import com.SelfTourGuide.singapore.db.AllDatabase;
import com.SelfTourGuide.singapore.db.AttractionDatabase;
import com.SelfTourGuide.singapore.fragment.MainTab03;
import com.SelfTourGuide.singapore.model.AllModel;
import com.SelfTourGuide.singapore.model.AttractionModel;
import com.SelfTourGuide.singapore.util.LogUtil;
import com.SelfTourGuide.singapore.util.NetUtils;
import com.SelfTourGuide.singapore.util.OverScrollView;
import com.SelfTourGuide.singapore.util.PayStatusUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AttractionsInfoActivity extends BaseActivity {
    private String TAG= AttractionsInfoActivity.class.getSimpleName();

    @Bind(R.id.title_top)
    TextView title_top;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;
    @Bind(R.id.iv_attraction)
    ImageView iv_attraction;
    @Bind(R.id.tv_intro)
    TextView tv_intro;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.shop_hours)
    TextView shop_hours;
    @Bind(R.id.play_time)
    TextView play_time;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.subway)
    TextView subway;
    @Bind(R.id.website)
    TextView website;
    @Bind(R.id.rl_intro)
    RelativeLayout rl_intro;
    @Bind(R.id.rl_price)
    RelativeLayout rl_price;
    @Bind(R.id.rl_shop_hours)
    RelativeLayout rl_shop_hours;
    @Bind(R.id.rl_play_time)
    RelativeLayout rl_play_time;
    @Bind(R.id.rl_phone)
    RelativeLayout rl_phone;
    @Bind(R.id.rl_address)
    RelativeLayout rl_address;
    @Bind(R.id.rl_subway)
    RelativeLayout rl_subway;
    @Bind(R.id.rl_website)
    RelativeLayout rl_website;
    @Bind(R.id.iv_collection)
    ImageView iv_collection;
    //    @Bind(R.id.adView2)
    AdView mAdView;
    @Bind(R.id.scrollIndicatorUp)
    OverScrollView overScrollView;

    int url;
    String titlename,id;
    private AttractionsDao attractionsDao;
    //    private AttractionsHelper addressHelper;
    private ArrayList<AttractionModel> listdate;
    private Dialog dialog;
    String language;
    //收藏
    private ArrayList<AttractionModel> collectionlistdata;
    private AttractionDatabase database;
    private AllDatabase alldatabase;
    private ArrayList<AllModel> alllistdata;

    //    private OverScrollView overScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_info);
        ButterKnife.bind(this);
        url = getIntent().getExtras().getInt("imageurl");
        titlename = getIntent().getExtras().getString("title");
        id = getIntent().getExtras().getString("id");
        Log.e("tag","id"+id);
        init();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvents event) {
        /* Do something */
        if (event.getConnect()==true || PayStatusUtil.isSubAvailable()){
            overScrollView.setVisibility(View.VISIBLE);
            mUIllempty.setVisibility(View.GONE);
            getdata();
        }else{
            overScrollView.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }
    };

    private void init(){
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this);
        attractionsDao = new AttractionsDao();
        database = new AttractionDatabase(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this);
        alldatabase = new AllDatabase(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this);
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        collectionlistdata =database.queryByid(id);
        LogUtil.i("collection"+"Attraction"+collectionlistdata.size());
        if (collectionlistdata!=null&&collectionlistdata.size()>0){
            iv_collection.setImageResource(R.drawable.collectclick);
        }else{
            iv_collection.setImageResource(R.drawable.collectnor);
        }
        mAdView=(AdView)findViewById(R.id.adView2);
        //  创建AdRequest 加载广告横幅adview
        Log.i(TAG, "init: "+getString(R.string.bannerunit));
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
//                showToast(String.format("Ad failed to load with error code %d.", errorCode));
            }

            @Override
            public void onAdOpened() {
                Log.i(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdClosed() {
                Log.i(TAG, "onAdClosed: ");
//                showToast("Ad closed.");
            }

            @Override
            public void onAdLeftApplication() {
                Log.i(TAG, "onAdLeftApplication: ");
//                showToast("Ad left application.");
            }
        });
        // 最后，请求广告。
        mAdView.loadAd(adRequest);

        listdate=new ArrayList<AttractionModel>();
        title_top.setText(titlename.toString().trim());
        iv_attraction.setBackgroundResource(url);


        if (language.equals("zh")){
            if(country.equals("TW")){
                if (!TextUtils.isEmpty(id)){
                    listdate = attractionsDao.querybyIDLanguage("zh-tw",id);
                }else{
                    AlertToast("id为空");
                }
            }else{
                if (!TextUtils.isEmpty(id)){
                    listdate = attractionsDao.querybyIDLanguage("zh",id);
                }else{
                    AlertToast("id为空");
                }
            }
        }else{
            if (!TextUtils.isEmpty(id)){
                listdate = attractionsDao.querybyIDLanguage("en",id);
            }else{
                AlertToast("id为空");
            }
        }

        if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this) || PayStatusUtil.isSubAvailable()){
            mUIllempty.setVisibility(View.GONE);
            getdata();
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
        }
        if(overScrollView.isScrolledToBottom()){
            overScrollView.setPadding(0,0,0,130);
        }else{
            overScrollView.setPadding(0,0,0,0);
        }
        if(overScrollView.isScrolledToTop()){
            overScrollView.setPadding(0,0,0,0);
        }

        ViewUtil.checkADView(mAdView);

    }

    public void getdata(){
        if (listdate!=null&&listdate.size()>0){

            if (!TextUtils.isEmpty(listdate.get(0).getInstroduction())){
                rl_intro.setVisibility(View.VISIBLE);
                tv_intro.setText(listdate.get(0).getInstroduction());
            }else{
                rl_intro.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getTicket_price())){
                rl_price.setVisibility(View.VISIBLE);
                tv_price.setText(listdate.get(0).getTicket_price());
            }else{
                rl_price.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getOpentime())){
                rl_shop_hours.setVisibility(View.VISIBLE);
                shop_hours.setText(listdate.get(0).getOpentime());
            }else{
                rl_shop_hours.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getHowlong())){
                rl_play_time.setVisibility(View.VISIBLE);
                play_time.setText(listdate.get(0).getHowlong());
            }else{
                rl_play_time.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getTelephone())){
                rl_phone.setVisibility(View.VISIBLE);
                phone.setText(listdate.get(0).getTelephone());
                NoLineCllikcSpan mNoUnderlineSpan = new NoLineCllikcSpan();
                if (phone.getText() instanceof Spannable) {
                    Spannable s = (Spannable) phone.getText();
                    s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
                }

            }else{
                rl_phone.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getAddress())){
                rl_address.setVisibility(View.VISIBLE);
                address.setText(listdate.get(0).getAddress());
            }else{
                rl_address.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getSubwag())){
                rl_subway.setVisibility(View.VISIBLE);
                subway.setText(listdate.get(0).getSubwag());
            }else{
                rl_subway.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getWebsite())){
                rl_website.setVisibility(View.VISIBLE);
                website.setText(listdate.get(0).getWebsite());
                NoLineCllikcSpan mNoUnderlineSpan = new NoLineCllikcSpan();
                if (website.getText() instanceof Spannable) {
                    Spannable s = (Spannable) website.getText();
                    s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
                }
            }else{
                rl_website.setVisibility(View.GONE);
            }
        }

        mUILLback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent(MainTab03.INTENT_BROADCAST);
                LocalBroadcastManager.getInstance(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this).sendBroadcast(broadcast);
                finish();

            }
        });
        rl_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this,IntroductionActivity.class);
                intent.putExtra("content",listdate.get(0).getInstroduction());
                startActivity(intent);
            }
        });


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_Phone_Dialog(listdate.get(0).getTelephone());
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this,WebViewActivity.class);
                intent.putExtra("url",listdate.get(0).getWebsite());
                startActivity(intent);
            }
        });

    }

    public void onClickBack(View v){
        Intent broadcast = new Intent(MainTab03.INTENT_BROADCAST);
        LocalBroadcastManager.getInstance(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this).sendBroadcast(broadcast);
        finish();
    }

    private void call_Phone_Dialog(final String phone){
        dialog= new Dialog(this,R.style.dialog);
        View view = View.inflate(this, R.layout.delete_dialog_layout, null);
        TextView dialog_phone= (TextView)view.findViewById(R.id.dialog_phone);
        dialog_phone.setText(phone);
        view.findViewById(R.id.call_phone).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ActivityCompat.checkSelfPermission(com.SelfTourGuide.singapore.ui.AttractionsInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //在这里面向系统请求权限,如果没有在这里面处理,不会执行下面的方法了
                            //这里就是向系统请求权限了,这里我还做了一个判断. sdk是M(M = 23 android L)才做这个请求,否则就不做.
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
                            }
                            return;}

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.txt_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = LinearLayout.LayoutParams.FILL_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

    }


    public void onColletionClick(View v){
//        alllistdata = alldatabase.queryByUrl(language,id,"attraction");

        collectionlistdata =database.queryByid(id);
        Log.i(TAG, "onColletionClick: "+collectionlistdata.size());
        if (collectionlistdata.size()>0){
            alldatabase.deleteByUrl("attraction",id);
            iv_collection.setImageResource(R.drawable.collectnor);
            database.deleteByUrl(language,id);
            AlertToast(getString(R.string.cancecollection));
        }else{
            iv_collection.setImageResource(R.drawable.collectclick);

            AttractionModel model=new AttractionModel();
            model.setAttraction_name(titlename);
            model.setTicket_price(listdate.get(0).getTicket_price());
            model.setAddress(listdate.get(0).getAddress());
            model.setAttractionid(id);
            model.setLanguage(language);
            model.setType("attraction");
            database.insert(model);
            AllModel amodel=new AllModel();
            amodel.setAllname(titlename);
            amodel.setType1(listdate.get(0).getTicket_price());
            amodel.setType2(listdate.get(0).getAddress());
            amodel.setTypeid(id);
            amodel.setLanguage(language);
            amodel.setType("attraction");
            alldatabase.insert(amodel);
            AlertToast(getString(R.string.collectionsuccess));

        }

    }


    //下面是监听了用户的点击事件
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtil.e("ok");//代表用户同意了打电话的请求
            }else{
                LogUtil.e("no");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**输出的日志
         *   E/TAG:: ok
         E/TAG:: permissions=android.permission.CALL_PHONE
         E/TAG:: grantResults=0
         E/TAG:: requestCode=111
         */
        for (int i = 0; i < permissions.length; i++) {
            LogUtil.e("permissions=" + permissions[i]);
        }
        for (int i = 0; i < grantResults.length; i++) {
            LogUtil.e("grantResults=" + grantResults[i]);
        }
        LogUtil.e("requestCode=" + requestCode);
    }

}
