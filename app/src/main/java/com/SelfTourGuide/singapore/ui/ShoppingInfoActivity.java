package com.SelfTourGuide.singapore.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.SelfTourGuide.singapore.assetsdb.ShoppingDao;
import com.SelfTourGuide.singapore.base.BaseActivity;
import com.SelfTourGuide.singapore.db.AllDatabase;
import com.SelfTourGuide.singapore.db.ShoppingDatabase;
import com.SelfTourGuide.singapore.fragment.MainTab03;
import com.SelfTourGuide.singapore.model.AllModel;
import com.SelfTourGuide.singapore.model.ShoppingModel;
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

public class ShoppingInfoActivity extends BaseActivity {
    private static final String TAG= HotelActivity.class.getSimpleName();

    @Bind(R.id.title_top)
    TextView title_top;
    @Bind(R.id.ll_back)
    LinearLayout mUILLback;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;
    @Bind(R.id.tv_intro)
    TextView tv_intro;
    //categories
    @Bind(R.id.tv_price)
    TextView tv_price;
    //营业时间
    @Bind(R.id.shop_hours)
    TextView shop_hours;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.subway)
    TextView subway;

    @Bind(R.id.rl_intro)
    RelativeLayout rl_intro;
    @Bind(R.id.rl_hotel_rating)
    RelativeLayout rl_hotel_rating;
    @Bind(R.id.rl_shop_hours)
    RelativeLayout rl_shop_hours;
    @Bind(R.id.rl_address)
    RelativeLayout rl_address;
    @Bind(R.id.rl_subway)
    RelativeLayout rl_subway;
    @Bind(R.id.iv_collection)
    ImageView iv_collection;
    //收藏
    private ArrayList<ShoppingModel> collectionlistdata;
    private ShoppingDatabase database;
    private AllDatabase alldatabase;
    private ArrayList<AllModel> alllistdata;
    //收藏

    int url;
    String titlename,id;
    private ShoppingDao shoppingDao;
    private ArrayList<ShoppingModel> listdate;
    private String rating;
    private String language;
    @Bind(R.id.adView)
    AdView mAdView;
    @Bind(R.id.scrollIndicatorUp)
    OverScrollView overScrollView;


    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.rl_phone)
    RelativeLayout rl_phone;
    @Bind(R.id.rl_website)
    RelativeLayout rl_website;
    @Bind(R.id.website)
    TextView website;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_info);
        ButterKnife.bind(this);
        titlename = getIntent().getExtras().getString("title");
        Log.e(TAG,titlename.toString().trim());
        id = getIntent().getExtras().getString("id");
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

    private void getdata(){
        if (listdate!=null&&listdate.size()>0){
            mUIllempty.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(listdate.get(0).getInstroduction())){
                rl_intro.setVisibility(View.VISIBLE);
                tv_intro.setText(listdate.get(0).getInstroduction());
            }else{
                rl_intro.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(listdate.get(0).getCategories())){
                rl_hotel_rating.setVisibility(View.VISIBLE);
                tv_price.setText(listdate.get(0).getCategories());
            }else{
                rl_hotel_rating.setVisibility(View.GONE);
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
            if (!TextUtils.isEmpty(listdate.get(0).getAddress())){
                rl_address.setVisibility(View.VISIBLE);
                address.setText(listdate.get(0).getAddress());
            }else{
                rl_address.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(listdate.get(0).getSubway())){
                rl_subway.setVisibility(View.VISIBLE);
                subway.setText(listdate.get(0).getSubway());
            }else{
                rl_subway.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(listdate.get(0).getOpentime())){
                rl_shop_hours.setVisibility(View.VISIBLE);
                shop_hours.setText(listdate.get(0).getOpentime());
            }else{
                rl_shop_hours.setVisibility(View.GONE);
            }

        }else{
            mUIllempty.setVisibility(View.VISIBLE);
        }

        mUILLback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent(MainTab03.INTENT_BROADCAST);
                LocalBroadcastManager.getInstance(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this).sendBroadcast(broadcast);
                finish();

            }
        });
        rl_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this,IntroductionActivity.class);
                intent.putExtra("content",listdate.get(0).getInstroduction());
                startActivity(intent);
            }
        });
    }

    private void init(){
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this);
        shoppingDao = new ShoppingDao();
        database = new ShoppingDatabase(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this);
        alldatabase = new AllDatabase(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this);
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
//        collectionlistdata =database.queryByUrl(language,id);
        collectionlistdata =database.queryByUrl(id);
        Log.e("tag","---"+collectionlistdata.size());

        if (collectionlistdata!=null&&collectionlistdata.size()>0){
            iv_collection.setImageResource(R.drawable.collectclick);
        }else{
            iv_collection.setImageResource(R.drawable.collectnor);
        }
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);
        listdate=new ArrayList<ShoppingModel>();
        title_top.setText(titlename.toString().trim());

        if (language.equals("zh")){
            if(country.equals("TW")){
                if (!TextUtils.isEmpty(id)){
                    listdate = shoppingDao.querybyIDLanguage("zh-tw",id);
                }else{
                    AlertToast("id为空");
                }
            }else{
                if (!TextUtils.isEmpty(id)){
                    listdate = shoppingDao.querybyIDLanguage("zh",id);
                }else{
                    AlertToast("id为空");
                }
            }
        }else{
            if (!TextUtils.isEmpty(id)){
                listdate = shoppingDao.querybyIDLanguage("en",id);
            }else{
                AlertToast("id为空");
            }
        }

        if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this) || PayStatusUtil.isSubAvailable()){
            getdata();
        }else{
            mUIllempty.setVisibility(View.VISIBLE);
        }
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this,WebViewActivity.class);
                intent.putExtra("url",listdate.get(0).getWebsite());
                startActivity(intent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_Phone_Dialog(listdate.get(0).getTelephone());
            }
        });

    }

    private Dialog dialog;

    private void call_Phone_Dialog(final String phone){
        dialog= new Dialog(this,R.style.dialog);
        View view = View.inflate(this, R.layout.delete_dialog_layout, null);
        TextView dialog_phone= (TextView)view.findViewById(R.id.dialog_phone);
        dialog_phone.setText(phone);
        view.findViewById(R.id.call_phone).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ActivityCompat.checkSelfPermission(com.SelfTourGuide.singapore.ui.ShoppingInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
//        alllistdata = alldatabase.queryByUrl(language,id,"shopping");
        collectionlistdata =database.queryByUrl(id);
        if (collectionlistdata.size()>0){
            alldatabase.deleteByUrl("shopping",id);
            iv_collection.setImageResource(R.drawable.collectnor);
            database.deleteByUrl(language,id);
            AlertToast(getString(R.string.cancecollection));
        }else{
            iv_collection.setImageResource(R.drawable.collectclick);
            AllModel amodel=new AllModel();
            amodel.setAllname(titlename);
            amodel.setType1(listdate.get(0).getCategories());
            amodel.setType2(listdate.get(0).getAddress());
            amodel.setTypeid(id);
            amodel.setType("shopping");
            amodel.setLanguage(language);
            alldatabase.insert(amodel);
            ShoppingModel model=new ShoppingModel();
            model.setShopping_name(titlename);
            model.setCategories(listdate.get(0).getCategories());
            model.setAddress(listdate.get(0).getAddress());
            model.setShoppingid(id);
            model.setLanguage(language);
            model.setType("shopping");
            database.insert(model);
            AlertToast(getString(R.string.incollection));
        }

    }


}
