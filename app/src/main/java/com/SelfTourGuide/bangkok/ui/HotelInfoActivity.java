package com.SelfTourGuide.bangkok.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.MessageEvents;
import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.bangkok.assetsdb.HotelDao;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.db.AllDatabase;
import com.SelfTourGuide.bangkok.db.HotelDatabase;
import com.SelfTourGuide.bangkok.fragment.MainTab03;
import com.SelfTourGuide.bangkok.model.AllModel;
import com.SelfTourGuide.bangkok.model.HotelModel;
import com.SelfTourGuide.bangkok.util.LogUtil;
import com.SelfTourGuide.bangkok.util.NetUtils;
import com.SelfTourGuide.bangkok.util.OverScrollView;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotelInfoActivity extends BaseActivity {
    private static final String TAG= HotelActivity.class.getSimpleName();

    @Bind(R.id.title_top)
    TextView title_top;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;

    @Bind(R.id.tv_intro)
    TextView tv_intro;
    @Bind(R.id.tv_price)
    TextView tv_price;

    @Bind(R.id.hotel_phone)
    TextView phone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.subway)
    TextView subway;
    @Bind(R.id.website)
    TextView website;
    @Bind(R.id.rl_intro)
    RelativeLayout rl_intro;
    @Bind(R.id.ll_rating)
    LinearLayout ll_rating;
    @Bind(R.id.one_rating)
    ImageView one_rating;
    @Bind(R.id.two_rating)
    ImageView two_rating;
    @Bind(R.id.three_rating)
    ImageView three_rating;
    @Bind(R.id.four_rating)
    ImageView four_rating;
    @Bind(R.id.five_rating)
    ImageView five_rating;
    @Bind(R.id.rl_hotel_rating)
    RelativeLayout rl_hotel_rating;
    @Bind(R.id.rl_phone)
    RelativeLayout rl_phone;
    @Bind(R.id.rl_address)
    RelativeLayout rl_address;
    @Bind(R.id.rl_subway)
    RelativeLayout rl_subway;
    @Bind(R.id.rl_website)
    RelativeLayout rl_website;
    String language;

    int url;
    String titlename,id;
    private ArrayList<HotelModel> listdate,adapterlistdata;
    private String rating;
    private HotelDao hotelDao;
    @Bind(R.id.iv_collection)
    ImageView iv_collection;
    //收藏
    private ArrayList<HotelModel> collectionlistdata;
    private HotelDatabase database;
    private AllDatabase alldatabase;
    private ArrayList<AllModel> alllistdata;
    //收藏
    private Dialog dialog;
    @Bind(R.id.adView)
    AdView mAdView;
    @Bind(R.id.scrollIndicatorUp)
    OverScrollView overScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        ButterKnife.bind(this);
        titlename = getIntent().getExtras().getString("title");
        Log.e(TAG,titlename.toString().trim());
        id = getIntent().getExtras().getString("id");
        Log.e(TAG,id+"  ");
        rating = getIntent().getExtras().getString("rating");
        init();

        ViewUtil.checkADView(mAdView);
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

    private void getdata() {
        if (listdate != null && listdate.size() > 0) {
            mUIllempty.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(listdate.get(0).getInstroduction())) {
                rl_intro.setVisibility(View.VISIBLE);
                tv_intro.setText(listdate.get(0).getInstroduction());
            } else {
                rl_intro.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(rating)) {
                rl_hotel_rating.setVisibility(View.VISIBLE);
                //判断rating是否是数字
                if (BaseActivity.isNumeric1(rating)) {
                    ll_rating.setVisibility(View.VISIBLE);
                    tv_price.setVisibility(View.GONE);
                    if (rating.equals("1")) {
                        one_rating.setVisibility(View.VISIBLE);
                        two_rating.setVisibility(View.GONE);
                        three_rating.setVisibility(View.GONE);
                        four_rating.setVisibility(View.GONE);
                        five_rating.setVisibility(View.GONE);
                    } else if (rating.equals("2")) {
                        one_rating.setVisibility(View.VISIBLE);
                        two_rating.setVisibility(View.VISIBLE);
                        three_rating.setVisibility(View.GONE);
                        four_rating.setVisibility(View.GONE);
                        five_rating.setVisibility(View.GONE);
                    } else if (rating.equals("3")) {
                        one_rating.setVisibility(View.VISIBLE);
                        two_rating.setVisibility(View.VISIBLE);
                        three_rating.setVisibility(View.VISIBLE);
                        four_rating.setVisibility(View.GONE);
                        five_rating.setVisibility(View.GONE);
                    } else if (rating.equals("4")) {
                        one_rating.setVisibility(View.VISIBLE);
                        two_rating.setVisibility(View.VISIBLE);
                        three_rating.setVisibility(View.VISIBLE);
                        four_rating.setVisibility(View.VISIBLE);
                        five_rating.setVisibility(View.GONE);
                    } else if (rating.equals("5")) {
                        one_rating.setVisibility(View.VISIBLE);
                        two_rating.setVisibility(View.VISIBLE);
                        three_rating.setVisibility(View.VISIBLE);
                        four_rating.setVisibility(View.VISIBLE);
                        five_rating.setVisibility(View.VISIBLE);
                    }
                } else {
                    ll_rating.setVisibility(View.GONE);
                    tv_price.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(rating)) {
                        tv_price.setText(rating);
                    }
                }
            } else {
                rl_hotel_rating.setVisibility(View.GONE);
                LogUtil.i("酒店星际为空");
            }

            if (!TextUtils.isEmpty(listdate.get(0).getTelephone())) {
                rl_phone.setVisibility(View.VISIBLE);
                phone.setText(listdate.get(0).getTelephone());
            } else {
                rl_phone.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getAddress())) {
                rl_address.setVisibility(View.VISIBLE);
                address.setText(listdate.get(0).getAddress());
            } else {
                rl_address.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getSubway())) {
                rl_subway.setVisibility(View.VISIBLE);
                subway.setText(listdate.get(0).getSubway());
            } else {
                rl_subway.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getWebsite())) {
                rl_website.setVisibility(View.VISIBLE);
                website.setText(listdate.get(0).getWebsite());
            } else {
                rl_website.setVisibility(View.GONE);
            }
        }
    }

    private void init(){
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this);
        hotelDao = new HotelDao();
        database = new HotelDatabase(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this);
        alldatabase = new AllDatabase(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this);
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
//        collectionlistdata =database.queryByUrl(language,id);
        collectionlistdata =database.queryByUrl(id);
        Log.e("tag","---"+collectionlistdata.size());

        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);

        if (collectionlistdata!=null&&collectionlistdata.size()>0){
            iv_collection.setImageResource(R.drawable.collectclick);
        }else{
            iv_collection.setImageResource(R.drawable.collectnor);
        }
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_Phone_Dialog(listdate.get(0).getTelephone());
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this,WebViewActivity.class);
                intent.putExtra("url",listdate.get(0).getWebsite());
                startActivity(intent);
            }
        });


        listdate=new ArrayList<HotelModel>();
        title_top.setText(titlename.toString().trim());
        if (language.equals("zh")){
            if(country.equals("TW")){
                if (!TextUtils.isEmpty(id)){
                    listdate = hotelDao.querybyIDLanguage("zh-tw",id);
                }else{
                    AlertToast("id为空");
                }
            }else{
                if (!TextUtils.isEmpty(id)){
                    listdate = hotelDao.querybyIDLanguage("zh",id);
                }else{
                    AlertToast("id为空");
                }
            }
        }else{
            if (!TextUtils.isEmpty(id)){
                listdate = hotelDao.querybyIDLanguage("en",id);
            }else{
                AlertToast("id为空");
            }
        }

        if(NetUtils.isNetworkConnected(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this) || PayStatusUtil.isSubAvailable()){
            getdata();
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
        }

        mUILLback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent(MainTab03.INTENT_BROADCAST);
                LocalBroadcastManager.getInstance(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this).sendBroadcast(broadcast);
                finish();

            }
        });
        rl_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this,IntroductionActivity.class);
                intent.putExtra("content",listdate.get(0).getInstroduction());
                startActivity(intent);
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this,WebViewActivity.class);
                intent.putExtra("url",listdate.get(0).getWebsite());
                startActivity(intent);
            }
        });
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

                        if (ActivityCompat.checkSelfPermission(com.SelfTourGuide.bangkok.ui.HotelInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        collectionlistdata =database.queryByUrl(id);
        Log.e("tag","---"+collectionlistdata.size());
        if (collectionlistdata.size()>0){
            iv_collection.setImageResource(R.drawable.collectnor);
            database.deleteByUrl(id);
            alldatabase.deleteByUrl("hotel",id);
            AlertToast(getString(R.string.cancecollection));
        }else{
            iv_collection.setImageResource(R.drawable.collectclick);
            AllModel amodel=new AllModel();
            amodel.setAllname(titlename);
            amodel.setType1(listdate.get(0).getStar_rating());
            amodel.setType2(listdate.get(0).getAddress());
            amodel.setTypeid(id);
            amodel.setType("hotel");
            amodel.setLanguage(language);
            alldatabase.insert(amodel);
            HotelModel model=new HotelModel();
            model.setHotel_name(titlename);
            model.setStar_rating(listdate.get(0).getStar_rating());
            model.setAddress(listdate.get(0).getAddress());
            model.setHotelid(id);
            model.setLanguage(language);
            model.setType("hotel");
            database.insert(model);

            AlertToast(getString(R.string.collectionsuccess));
        }

    }


}
