package com.SelfTourGuide.singapore.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SelfTourGuide.singapore.MessageEvents;
import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.adapter.CollectionAdapter;
import com.SelfTourGuide.singapore.db.AllDatabase;
import com.SelfTourGuide.singapore.db.AttractionDatabase;
import com.SelfTourGuide.singapore.db.EntertainmentDatabase;
import com.SelfTourGuide.singapore.db.HotelDatabase;
import com.SelfTourGuide.singapore.db.RestaurantDatabase;
import com.SelfTourGuide.singapore.db.ShoppingDatabase;
import com.SelfTourGuide.singapore.model.AllModel;
import com.SelfTourGuide.singapore.model.AttractionModel;
import com.SelfTourGuide.singapore.model.EntertainmentModel;
import com.SelfTourGuide.singapore.model.HotelModel;
import com.SelfTourGuide.singapore.model.RestaurantModel;
import com.SelfTourGuide.singapore.model.ShoppingModel;
import com.SelfTourGuide.singapore.ui.AttractionsInfoActivity;
import com.SelfTourGuide.singapore.ui.EntertainmentInfoActivity;
import com.SelfTourGuide.singapore.ui.HotelInfoActivity;
import com.SelfTourGuide.singapore.ui.RestaurantInfoActivity;
import com.SelfTourGuide.singapore.ui.ShoppingInfoActivity;
import com.SelfTourGuide.singapore.ui.ViewUtil;
import com.SelfTourGuide.singapore.util.LogUtil;
import com.SelfTourGuide.singapore.util.NetUtils;
import com.SelfTourGuide.singapore.util.PayStatusUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class MainTab03 extends Fragment {
	private String TAG= MainTab03.class.getSimpleName();
	private PopupWindow popupWindow;
	private View view;
	@Bind(R.id.rl_collection)
	RelativeLayout rl_collection;
	@Bind(R.id.title)
	TextView title;
	@Bind(R.id.image_arrow)
	ImageView image_arrow;
	@Bind(R.id.nodata)
	LinearLayout nodata;
	//景点
	private AttractionDatabase attractiondatabase;
	private ArrayList<AttractionModel> attractionlist;

	//娱乐
	private EntertainmentDatabase entertainmentDatabase;
	private ArrayList<EntertainmentModel> entertainmentlist;

	//酒店
	private HotelDatabase hotelDataBase;
	private ArrayList<HotelModel> hotelList;

	//餐厅
	private RestaurantDatabase restaurantDataBase;
	private ArrayList<RestaurantModel> restaurantList;

	//购物
	private ShoppingDatabase shoppingDataBase;
	private ArrayList<ShoppingModel> shoppingList;

	@Bind(R.id.mListView)
	ListView listView;
	private CollectionAdapter adapter;
	private ArrayList<AllModel>  alllist;
	private AllDatabase alldatabase;
	boolean isShow=false;
	String language,country;
	private Dialog dialog;
	@Bind(R.id.ll_empty_img)
	LinearLayout mUIllempty;
	//// TODO: 2017/5/16 接受详情页面的广播后刷新数据
	public static final String INTENT_BROADCAST = "android.intent.action.MEDICAL_BROADCAST";// 广播跳转意
	LocalBroadcastManager broadcastManager;
	@Bind(R.id.adView)
	AdView mAdView;
	private Boolean isVisible;
	private ConnectionChangeReceiver receiver;  // 广播

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.main_tab_03, container, false);
		ButterKnife.bind(this, rootView);
		if (!EventBus.getDefault().isRegistered(getActivity())) EventBus.getDefault().register(getActivity());
		init();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		filter.setPriority(1000);
		receiver=new ConnectionChangeReceiver();
		com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity().registerReceiver(receiver, filter);

		ViewUtil.checkADView(mAdView);
		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity().unregisterReceiver(receiver);
	}

	//接受广播 和网络判断
	public  class ConnectionChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(final Context context, Intent intent) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected() || !PayStatusUtil.isSubAvailable()) {
				// 断开
				Log.i("tag", "onReceive: 断开");
				EventBus.getDefault().post(new MessageEvents(false));
				mUIllempty.setVisibility(View.VISIBLE);
				nodata.setVisibility(View.GONE);
				listView.setVisibility(View.GONE);
			} else {
				Log.i("tag", "onReceive: 连接");
				EventBus.getDefault().post(new MessageEvents(true));
				mUIllempty.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				getData();
			}
		}
	}

	public void init(){
		broadcastManager = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(INTENT_BROADCAST);
		broadcastManager.registerReceiver(bordcastReceiver, intentFilter);

		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.i(TAG, "onAdLoaded: ");
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				Log.i(TAG, "onAdFailedToLoad: "+errorCode);
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

		//获取本地语言
		language = Locale.getDefault().getLanguage();
		Log.e(TAG,language);
		country= getResources().getConfiguration().locale.getCountry();
		Log.e(TAG,"country"+country);
		attractiondatabase = new AttractionDatabase(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity());
		entertainmentDatabase =  new EntertainmentDatabase(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity());
		hotelDataBase = new HotelDatabase(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity());
		restaurantDataBase = new RestaurantDatabase(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity());
		shoppingDataBase = new ShoppingDatabase(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity());

		alldatabase = new AllDatabase(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity());
		if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
			Log.i(TAG, "收藏有网");
			mUIllempty.setVisibility(View.GONE);
			getData();
		}else{
			Log.i(TAG, "收藏没网");
			mUIllempty.setVisibility(View.VISIBLE);
			nodata.setVisibility(View.GONE);
		}

		rl_collection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				image_arrow.setImageResource(R.drawable.collection_up);
				showPopupWindow(rl_collection);
			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (alllist.get(position).getType().equals("attraction")){
					Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),AttractionsInfoActivity.class);
					int i = Integer.parseInt(alllist.get(position).getTypeid());
					intent.putExtra("imageurl",imageurls[i-1]);
					intent.putExtra("title",alllist.get(position).getAllname());
					intent.putExtra("id",alllist.get(position).getTypeid());
					startActivity(intent);
				}else if (alllist.get(position).getType().equals("entertainment")){
					Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),EntertainmentInfoActivity.class);
					intent.putExtra("title",alllist.get(position).getAllname());
					intent.putExtra("id",alllist.get(position).getTypeid());
					startActivity(intent);
				}else if (alllist.get(position).getType().equals("hotel")){
					Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),HotelInfoActivity.class);
					intent.putExtra("title",alllist.get(position).getAllname());
					intent.putExtra("id",alllist.get(position).getTypeid());
					intent.putExtra("rating",alllist.get(position).getType1());
					startActivity(intent);

				}else if (alllist.get(position).getType().equals("restaurant")){
					Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),RestaurantInfoActivity.class);
					intent.putExtra("title",alllist.get(position).getAllname());
					intent.putExtra("id",alllist.get(position).getTypeid());
					startActivity(intent);
				}else if (alllist.get(position).getType().equals("shopping")){
					Intent intent=new Intent(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),ShoppingInfoActivity.class);
					intent.putExtra("title",alllist.get(position).getAllname());
					intent.putExtra("id",alllist.get(position).getTypeid());
					startActivity(intent);

				}


			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				delete_music_Dialog(position,alllist.get(position).getTypeid(),alllist.get(position).getType());

				return true;
			}
		});
	}


	BroadcastReceiver bordcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//信息处理
			getData();
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (popupWindow != null){
			popupWindow.dismiss();
			image_arrow.setImageResource(R.drawable.collection_down);
		}
		broadcastManager.unregisterReceiver(bordcastReceiver);
	}

	private void delete_music_Dialog(final int position, final String id, final String type){
		dialog= new Dialog(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),R.style.dialog);
		View view = View.inflate(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(), R.layout.delete_dialog_item_layout, null);
		view.findViewById(R.id.dialog_delete).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "onClick: type:"+title.getText().toString());
				if (title.getText().toString().equals(getString(R.string.all))){
					alldatabase.deleteByUrl(type,id);
					if (alllist.get(position).getType().equals("attraction")){
						attractiondatabase.deleteByUrl(language,id);
					}else if (alllist.get(position).getType().equals("entertainment")){
						entertainmentDatabase.deleteByUrl(language,id);
					}else if (alllist.get(position).getType().equals("hotel")){
						hotelDataBase.deleteByUrl(language,id);

					}else if (alllist.get(position).getType().equals("restaurant")){
						restaurantDataBase.deleteByUrl(language,id);

					}else if (alllist.get(position).getType().equals("shopping")){
						shoppingDataBase.deleteByUrl(language,id);
					}
					alllist.clear();
					alllist	= alldatabase.queryAll();
					LogUtil.i("收藏全部数据"+alllist.toString());
					title.setText(getString(R.string.all));
					if (alllist.size()>0){
						listView.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}else{
						listView.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
					Log.i(TAG, "onClick: "+alllist.toString());
					adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
					listView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}else if (title.getText().toString().equals(getString(R.string.attractions))){

					attractiondatabase.deleteByUrl(language,id);
					alllist.clear();
					attractionlist	=attractiondatabase.queryAll();
					for (int i = 0; i < attractionlist.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(attractionlist.get(i).getAttraction_name());
						model.setType1(attractionlist.get(i).getAddress());
						model.setType2(attractionlist.get(i).getTicket_price());
						model.setLanguage(language);
						model.setType("attraction");
						model.setTypeid(attractionlist.get(i).getAttractionid());
						alllist.add(model);
					}
					if (alllist.size()>0){
						listView.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}else{
						listView.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
					Log.i(TAG, "attractions: "+alllist.toString());
					adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
					listView.setAdapter(adapter);
				}else if (title.getText().toString().equals(getString(R.string.hotel))){
					alldatabase.deleteByUrl("hotel",id);
					hotelDataBase.deleteByUrl(language,id);
					alllist.clear();
					hotelList	=hotelDataBase.queryAll();
					for (int i = 0; i < hotelList.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(hotelList.get(i).getHotel_name());
						model.setType1(hotelList.get(i).getStar_rating());
						model.setType2(hotelList.get(i).getAddress());
						model.setLanguage(language);
						model.setType("hotel");
						model.setTypeid(hotelList.get(i).getHotelid());
						alllist.add(model);
					}
					if (alllist.size()>0){
						listView.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}else{
						listView.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
					Log.i(TAG, "hotel: "+alllist.toString());
					adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
					listView.setAdapter(adapter);

				}else if (title.getText().toString().equals(getString(R.string.restaurant))){
					alldatabase.deleteByUrl("restaurant",id);
					restaurantDataBase.deleteByUrl(language,id);
					alllist.clear();
					restaurantList	=restaurantDataBase.queryAll();
					for (int i = 0; i < restaurantList.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(restaurantList.get(i).getRestaurant_name());
						model.setType1(restaurantList.get(i).getCategories());
						model.setType2(restaurantList.get(i).getPrice_range());
						model.setLanguage(language);
						model.setType("restaurant");
						model.setTypeid(restaurantList.get(i).getRestaurantid());
						alllist.add(model);
					}
					if (alllist.size()>0){
						listView.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}else{
						listView.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
					Log.i(TAG, "restaurant: "+alllist.toString());
					adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
					listView.setAdapter(adapter);
				}else if(title.getText().toString().equals(getString(R.string.shop))){
					alldatabase.deleteByUrl("shopping",id);
					shoppingDataBase.deleteByUrl(language,id);
					alllist.clear();
					shoppingList = shoppingDataBase.queryAll();
					for (int i = 0; i < shoppingList.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(shoppingList.get(i).getShopping_name());
						model.setType1(shoppingList.get(i).getCategories());
						model.setType2(shoppingList.get(i).getAddress());
						model.setLanguage(language);
						model.setType("shopping");
						model.setTypeid(shoppingList.get(i).getShoppingid());
						alllist.add(model);
					}
					if (alllist.size()>0){
						listView.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}else{
						listView.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
					Log.i(TAG, "restaurant: "+alllist.toString());
					adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
					listView.setAdapter(adapter);
				}else if (title.getText().toString().equals(getString(R.string.entertainment))){
					alldatabase.deleteByUrl("entertainment",id);
					entertainmentDatabase.deleteByUrl(language,id);
					alllist.clear();
					entertainmentlist	=entertainmentDatabase.queryAll();
					for (int i = 0; i < entertainmentlist.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(entertainmentlist.get(i).getEntertainment_name());
						model.setType1(entertainmentlist.get(i).getAddress());
						model.setType2(entertainmentlist.get(i).getCategories());
						model.setLanguage(language);
						model.setType("entertainment");
						model.setTypeid(entertainmentlist.get(i).getEntertainmentid());
						alllist.add(model);
					}
					if (alllist.size()>0){
						listView.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}else{
						listView.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
					Log.i(TAG, "restaurant: "+alllist.toString());
					adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
					listView.setAdapter(adapter);
				}

				dialog.dismiss();
			}

		});


		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		lp.width = LinearLayout.LayoutParams.FILL_PARENT;
		lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);
		dialog.getWindow().setGravity(Gravity.CENTER);
		dialog.show();

	}


	private void  getData(){
		alllist=new ArrayList<>();
		alllist.clear();
		alllist	= alldatabase.queryAll();
		title.setText(getString(R.string.all));
		if (alllist.size()>0){
			listView.setVisibility(View.VISIBLE);
			nodata.setVisibility(View.GONE);
		}else{
			listView.setVisibility(View.GONE);
			nodata.setVisibility(View.VISIBLE);
		}

		Log.i(TAG, "getData: "+alllist.toString());
		adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}



	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden){
			isShow = false;
			Log.i(TAG, hidden+"-->1");
			if (popupWindow != null){
				popupWindow.dismiss();
				image_arrow.setImageResource(R.drawable.collection_down);
			}
		}else {
			//可见页面
			isShow = true;
			if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
				Log.i(TAG, "收藏有网");
				mUIllempty.setVisibility(View.GONE);
				getData();
			}else{
				Log.i(TAG, "收藏没网");
				mUIllempty.setVisibility(View.VISIBLE);
				nodata.setVisibility(View.GONE);
			}

			Log.i(TAG, hidden+"-->2");
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
			R.drawable.l0029 ,R.drawable.l0029, R.drawable.l0029 ,R.drawable.l0029,
			R.drawable.l0029 ,R.drawable.l0029

	};



	private void showPopupWindow(View rl_collection) {
		final 	ImageView type_iv_all,type_iv_attractions,type_iv_shopping,im_type_hotel,type_iv_restaurant,type_iv_entertainment;
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.popupwindowlayout, null);

			final RelativeLayout all=(RelativeLayout)view.findViewById(R.id.all);
			RelativeLayout rtl_attractions=(RelativeLayout)view.findViewById(R.id.rtl_attractions);
			RelativeLayout rtl_entertainment=(RelativeLayout)view.findViewById(R.id.rtl_entertainment);
			RelativeLayout rtl_hotel=(RelativeLayout)view.findViewById(R.id.rtl_hotel);
			RelativeLayout	 rtl_restaurant=(RelativeLayout)view.findViewById(R.id.rtl_restaurant);
			RelativeLayout rtl_shopping=(RelativeLayout)view.findViewById(R.id.rtl_shopping);

			type_iv_all=(ImageView)view.findViewById(R.id.type_iv_all);
			type_iv_attractions=(ImageView)view.findViewById(R.id.type_iv_attractions);
			type_iv_shopping=(ImageView)view.findViewById(R.id.type_iv_shopping);
			im_type_hotel=(ImageView)view.findViewById(R.id.im_type_hotel);
			type_iv_restaurant=(ImageView)view.findViewById(R.id.type_iv_restaurant);
			type_iv_entertainment=(ImageView)view.findViewById(R.id.type_iv_entertainment);

			popupWindow = new PopupWindow(view,  ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			type_iv_all.setVisibility(View.VISIBLE);
			Log.i(TAG, "showPopupWindow: --"+title.getText().toString());
			Log.i(TAG, "showPopupWindow: --"+getString(R.string.all));

			if (title.getText().toString().equals(getString(R.string.all))){
				type_iv_attractions.setVisibility(View.GONE);
				type_iv_all.setVisibility(View.VISIBLE);
				type_iv_entertainment.setVisibility(View.GONE);
				type_iv_shopping.setVisibility(View.GONE);
				im_type_hotel.setVisibility(View.GONE);
				type_iv_restaurant.setVisibility(View.GONE);
			}
			rtl_attractions.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					type_iv_attractions.setVisibility(View.VISIBLE);
					type_iv_all.setVisibility(View.GONE);
					type_iv_entertainment.setVisibility(View.GONE);
					type_iv_shopping.setVisibility(View.GONE);
					im_type_hotel.setVisibility(View.GONE);
					type_iv_restaurant.setVisibility(View.GONE);
					title.setText(getString(R.string.attractions));
					if (alllist!=null&&alllist.size()>0){
						alllist.clear();
					}

					attractionlist	=attractiondatabase.queryAll();
					for (int i = 0; i < attractionlist.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(attractionlist.get(i).getAttraction_name());
						model.setType1(attractionlist.get(i).getAddress());
						model.setType2(attractionlist.get(i).getTicket_price());
						model.setLanguage(language);
						model.setType("attraction");
						model.setTypeid(attractionlist.get(i).getAttractionid());
						alllist.add(model);
					}
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
						Log.i(TAG, "收藏有网");
						mUIllempty.setVisibility(View.GONE);
						if (alllist.size()>0){
							listView.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
						}else{
							listView.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						Log.i(TAG, "onClick: attraction"+alllist.toString());
						adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
						listView.setAdapter(adapter);
					}else{
						Log.i(TAG, "收藏没网");
						mUIllempty.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}

					if (popupWindow != null){
						popupWindow.dismiss();
						image_arrow.setImageResource(R.drawable.collection_down);
					}
				}
			});
			rtl_entertainment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					title.setText(getString(R.string.entertainment));
					type_iv_attractions.setVisibility(View.GONE);
					type_iv_all.setVisibility(View.GONE);
					type_iv_entertainment.setVisibility(View.VISIBLE);
					type_iv_shopping.setVisibility(View.GONE);
					im_type_hotel.setVisibility(View.GONE);
					type_iv_restaurant.setVisibility(View.GONE);
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity())){
						if (alllist!=null&&alllist.size()>0){
							alllist.clear();
						}
					}
					entertainmentlist	=entertainmentDatabase.queryAll();
					for (int i = 0; i < entertainmentlist.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(entertainmentlist.get(i).getEntertainment_name());
						model.setType1(entertainmentlist.get(i).getAddress());
						model.setType2(entertainmentlist.get(i).getCategories());
						model.setLanguage(language);
						model.setType("entertainment");
						model.setTypeid(entertainmentlist.get(i).getEntertainmentid());
						alllist.add(model);
					}
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
						Log.i(TAG, "收藏有网");
						mUIllempty.setVisibility(View.GONE);
						if (alllist.size()>0){
							listView.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
						}else{
							listView.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						Log.i(TAG, "onClick: entertainment"+alllist.toString());
						adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
						listView.setAdapter(adapter);
					}else{
						Log.i(TAG, "收藏没网");
						mUIllempty.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}

					if (popupWindow != null){
						popupWindow.dismiss();
						image_arrow.setImageResource(R.drawable.collection_down);
					}
				}
			});
			rtl_hotel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					title.setText(getString(R.string.hotel));
					type_iv_attractions.setVisibility(View.GONE);
					type_iv_all.setVisibility(View.GONE);
					type_iv_entertainment.setVisibility(View.GONE);
					type_iv_shopping.setVisibility(View.GONE);
					im_type_hotel.setVisibility(View.VISIBLE);
					type_iv_restaurant.setVisibility(View.GONE);

					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity())){
						if (alllist!=null&&alllist.size()>0){
							alllist.clear();
						}
					}
					hotelList	=hotelDataBase.queryAll();
					for (int i = 0; i < hotelList.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(hotelList.get(i).getHotel_name());
						model.setType1(hotelList.get(i).getStar_rating());
						model.setType2(hotelList.get(i).getAddress());
						model.setLanguage(language);
						model.setType("hotel");
						model.setTypeid(hotelList.get(i).getHotelid());
						alllist.add(model);
					}
					Log.i(TAG, "onClick:酒店 "+alllist.size());

					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
						Log.i(TAG, "收藏有网");
						mUIllempty.setVisibility(View.GONE);
						if (alllist.size()>0){
							listView.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
						}else{
							listView.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						Log.i(TAG, "onClick: hotel"+alllist.toString());
						adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
						listView.setAdapter(adapter);
					}else{
						Log.i(TAG, "收藏没网");
						mUIllempty.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}

					if (popupWindow != null){
						popupWindow.dismiss();
						image_arrow.setImageResource(R.drawable.collection_down);
					}
				}
			});
			rtl_restaurant.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					title.setText(getString(R.string.restaurant));
					type_iv_attractions.setVisibility(View.GONE);
					type_iv_all.setVisibility(View.GONE);
					type_iv_entertainment.setVisibility(View.GONE);
					type_iv_shopping.setVisibility(View.GONE);
					im_type_hotel.setVisibility(View.GONE);
					type_iv_restaurant.setVisibility(View.VISIBLE);
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity())){
						if (alllist!=null&&alllist.size()>0){
							alllist.clear();
						}
					}
					restaurantList	=restaurantDataBase.queryAll();
					for (int i = 0; i < restaurantList.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(restaurantList.get(i).getRestaurant_name());
						model.setType1(restaurantList.get(i).getCategories());
						model.setType2(restaurantList.get(i).getPrice_range());
						model.setLanguage(language);
						model.setType("restaurant");
						model.setTypeid(restaurantList.get(i).getRestaurantid());
						alllist.add(model);
					}
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
						Log.i(TAG, "收藏有网");
						mUIllempty.setVisibility(View.GONE);
						if (alllist.size()>0){
							listView.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
						}else{
							listView.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						Log.i(TAG, "onClick: restaurant"+alllist.toString());
						adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
						listView.setAdapter(adapter);
					}else{
						Log.i(TAG, "收藏没网");
						mUIllempty.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}



					if (popupWindow != null){
						popupWindow.dismiss();
						image_arrow.setImageResource(R.drawable.collection_down);
					}
				}
			});
			rtl_shopping.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					title.setText(getString(R.string.shop));
					type_iv_attractions.setVisibility(View.GONE);
					type_iv_all.setVisibility(View.GONE);
					type_iv_entertainment.setVisibility(View.GONE);
					type_iv_shopping.setVisibility(View.VISIBLE);
					im_type_hotel.setVisibility(View.GONE);
					type_iv_restaurant.setVisibility(View.GONE);
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity())){
						if (alllist!=null&&alllist.size()>0){
							alllist.clear();
						}
					}
					shoppingList = shoppingDataBase.queryAll();
					for (int i = 0; i < shoppingList.size(); i++) {
						AllModel model=new AllModel();
						model.setAllname(shoppingList.get(i).getShopping_name());
						model.setType1(shoppingList.get(i).getCategories());
						model.setType2(shoppingList.get(i).getAddress());
						model.setLanguage(language);
						model.setType("shopping");
						model.setTypeid(shoppingList.get(i).getShoppingid());
						alllist.add(model);
					}
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
						Log.i(TAG, "收藏有网");
						mUIllempty.setVisibility(View.GONE);
						if (alllist.size()>0){
							listView.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
						}else{
							listView.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						Log.i(TAG, "onClick: shop"+alllist.toString());

						adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
						listView.setAdapter(adapter);

					}else{
						Log.i(TAG, "收藏没网");
						mUIllempty.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}

					if (popupWindow != null){
						popupWindow.dismiss();
						image_arrow.setImageResource(R.drawable.collection_down);
					}
				}
			});
			all.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					title.setText(getString(R.string.all));
					type_iv_attractions.setVisibility(View.GONE);
					type_iv_all.setVisibility(View.VISIBLE);
					type_iv_entertainment.setVisibility(View.GONE);
					type_iv_shopping.setVisibility(View.GONE);
					im_type_hotel.setVisibility(View.GONE);
					type_iv_restaurant.setVisibility(View.GONE);
					alllist	= alldatabase.queryAll();
					LogUtil.i(alllist.toString());
					if(NetUtils.isNetworkConnected(com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity()) || PayStatusUtil.isSubAvailable()){
						Log.i(TAG, "收藏有网");
						mUIllempty.setVisibility(View.GONE);
						if (alllist.size()>0){
							listView.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
						}else{
							listView.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						Log.i(TAG, "onClick: all"+alllist.toString());
						adapter = new CollectionAdapter(language,country, com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity(),alllist);
						listView.setAdapter(adapter);

					}else{
						Log.i(TAG, "收藏没网");
						mUIllempty.setVisibility(View.VISIBLE);
						nodata.setVisibility(View.GONE);
					}


					if (popupWindow != null){
						popupWindow.dismiss();
						image_arrow.setImageResource(R.drawable.collection_down);

					}
				}
			});
			if (title.getText().toString().equals(getString(R.string.all))){
				type_iv_attractions.setVisibility(View.GONE);
				type_iv_all.setVisibility(View.VISIBLE);
				type_iv_entertainment.setVisibility(View.GONE);
				type_iv_shopping.setVisibility(View.GONE);
				im_type_hotel.setVisibility(View.GONE);
				type_iv_restaurant.setVisibility(View.GONE);
			}
		}

		image_arrow.setImageResource(R.drawable.collection_down);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) com.SelfTourGuide.singapore.fragment.MainTab03.this.getActivity().getSystemService(Context.WINDOW_SERVICE);

		// 设置好参数之后再show
		popupWindow.showAsDropDown(rl_collection);
	}



	@Override
	public void onDetach() {
		super.onDetach();

	}
}
