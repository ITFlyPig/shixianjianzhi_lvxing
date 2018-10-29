package com.SelfTourGuide.bangkok.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.assetsdb.AssetsDatabaseManager;
import com.SelfTourGuide.bangkok.assetsdb.AttractionsDao;
import com.SelfTourGuide.bangkok.assetsdb.EntertainmentDao;
import com.SelfTourGuide.bangkok.assetsdb.HotelDao;
import com.SelfTourGuide.bangkok.assetsdb.RestaurantDao;
import com.SelfTourGuide.bangkok.assetsdb.ShoppingDao;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.model.AllModel;
import com.SelfTourGuide.bangkok.model.AttractionModel;
import com.SelfTourGuide.bangkok.model.EntertainmentModel;
import com.SelfTourGuide.bangkok.model.HotelModel;
import com.SelfTourGuide.bangkok.model.RestaurantModel;
import com.SelfTourGuide.bangkok.model.ShoppingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class CollectionAdapter extends BaseAdapter {
    String  TAG= com.SelfTourGuide.bangkok.adapter.CollectionAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<AllModel> list;

    //景点
//    private AttractionsHelper addressHelper;
    private AttractionsDao attractionsDao;
    private ArrayList<AttractionModel> attractionlist;

    //娱乐
    private EntertainmentDao addressHelperenter;
    private ArrayList<EntertainmentModel> entertainmentlist;

    //酒店
    private HotelDao hotelHelper;
    private ArrayList<HotelModel> hotelList;

    //餐厅
    private RestaurantDao resaddressHelper;
    private ArrayList<RestaurantModel> restaurantList;

    //购物
    private ShoppingDao shopaddressHelper;
    private ArrayList<ShoppingModel> shoppingList;

    private String language,country;

    public CollectionAdapter(String language,String country,Context context, List<AllModel> listdata){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = listdata;
        this.language=language;
        this.country=country;
        //获取本地语言
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(context);
        shopaddressHelper = new ShoppingDao();
        attractionsDao = new AttractionsDao();
        addressHelperenter = new EntertainmentDao();
        hotelHelper = new HotelDao();
        resaddressHelper = new RestaurantDao();
        shopaddressHelper = new ShoppingDao();


    }

    @Override
    public int getCount() {
        Log.i("collection", "getCount: "+list.size());
        return list.size();

    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        private TextView name,price,address;
        private ImageView imageView;
        private LinearLayout ll_rating;
        private ImageView address_iv;
        private ImageView one_rating,two_rating,three_rating,four_rating,five_rating;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate( R.layout.collection_item,null);
            holder.name = (TextView) convertView.findViewById( R.id.name);
            holder.price = (TextView) convertView.findViewById( R.id.price);
            holder.address = (TextView) convertView.findViewById( R.id.address);
            holder.imageView = (ImageView) convertView.findViewById( R.id.image);
            holder.address_iv = (ImageView) convertView.findViewById(R.id.address_iv);
            holder.ll_rating = (LinearLayout)convertView.findViewById(R.id.ll_rating);
            holder.one_rating = (ImageView)convertView.findViewById(R.id.one_rating);
            holder.two_rating = (ImageView)convertView.findViewById(R.id.two_rating);
            holder.three_rating = (ImageView)convertView.findViewById(R.id.three_rating);
            holder.four_rating = (ImageView)convertView.findViewById(R.id.four_rating);
            holder.five_rating = (ImageView)convertView.findViewById(R.id.five_rating);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Log.i("collection", "getView: type"+list.get(position).getType());

        if (!TextUtils.isEmpty(list.get(position).getType())){
            if (list.get(position).getType().equals("attraction")){
                holder.ll_rating.setVisibility(View.GONE);
                holder.address_iv.setVisibility(View.VISIBLE);
                //景点
                if (language.equals("zh")){
                    if(country.equals("TW")){
                        attractionlist = attractionsDao.querybyIDLanguage("zh-tw",list.get(position).getTypeid());
                    }else{
                        attractionlist = attractionsDao.querybyIDLanguage("zh",list.get(position).getTypeid());
                    }
                }else{
                    attractionlist = attractionsDao.querybyIDLanguage("en",list.get(position).getTypeid());
                }

                Log.i(TAG, "attraction: "+attractionlist.toString());
                if (!TextUtils.isEmpty(list.get(position).getAllname())){
                    if (attractionlist!=null&&attractionlist.size()>0){
                        holder.name.setText(attractionlist.get(0).getAttraction_name());
                    }
                }

                int i = Integer.parseInt(list.get(position).getTypeid());
                holder.imageView.setImageResource(imageurls[i-1]);
                if (!TextUtils.isEmpty(list.get(position).getType1())){
                    holder.price.setVisibility(View.VISIBLE);
                    if (attractionlist!=null&&attractionlist.size()>0){
                        holder.price.setText(attractionlist.get(0).getTicket_price());
                    }

                }
                if (!TextUtils.isEmpty(list.get(position).getType2())) {
                    if (attractionlist.size()>0){
                        holder.address.setVisibility(View.VISIBLE);
                        holder.address.setText(attractionlist.get(0).getAddress());
                    }
                }
            }else if (list.get(position).getType().equals("entertainment")){

                holder.ll_rating.setVisibility(View.GONE);
                holder.address_iv.setVisibility(View.VISIBLE);
                if (language.equals("zh")){
                    if(country.equals("TW")){
                        entertainmentlist =  addressHelperenter.querybyIDLanguage("zh-tw",list.get(position).getTypeid());
                    }else{
                        entertainmentlist =  addressHelperenter.querybyIDLanguage(language,list.get(position).getTypeid());
                    }
                }else{
                    entertainmentlist =  addressHelperenter.querybyIDLanguage("en",list.get(position).getTypeid());
                }


                Log.i(TAG, "entertainment娱乐: "+entertainmentlist.toString());
                if (!TextUtils.isEmpty(list.get(position).getAllname())){
                    if (entertainmentlist.size()>0){
                        holder.name.setText(entertainmentlist.get(0).getEntertainment_name());
                    }
                }

                if (!TextUtils.isEmpty(list.get(position).getType1())){
                    holder.price.setVisibility(View.VISIBLE);
                    if (entertainmentlist!=null&&entertainmentlist.size()>0){
                        holder.price.setText(entertainmentlist.get(0).getCategories());
                    }

                }
                if (!TextUtils.isEmpty(list.get(position).getType2())) {
                    if (entertainmentlist!=null&&entertainmentlist.size()>0){
                        holder.address.setVisibility(View.VISIBLE);
                        holder.address.setText(entertainmentlist.get(0).getAddress());
                    }
                }
                holder.imageView.setImageResource(R.drawable.entertainmentlist);
            }else if (list.get(position).getType().equals("hotel")){
                holder.imageView.setImageResource(R.drawable.hotel);
                if (language.equals("zh")){
                    if(country.equals("TW")){
                        hotelList= hotelHelper.querybyIDLanguage("zh-tw",list.get(position).getTypeid());
                    }else{
                        hotelList= hotelHelper.querybyIDLanguage(language,list.get(position).getTypeid());
                    }
                }else{
                    hotelList= hotelHelper.querybyIDLanguage("en",list.get(position).getTypeid());
                }

                if (!TextUtils.isEmpty(list.get(position).getAllname())){
                    if (hotelList!=null&&hotelList.size()>0){
                        holder.name.setText(hotelList.get(0).getHotel_name());
                    }
                }
                if (!TextUtils.isEmpty(list.get(position).getType2())) {
                    if (hotelList!=null&&hotelList.size()>0){
                        holder.address.setVisibility(View.VISIBLE);
                        holder.address.setText(hotelList.get(0).getAddress());
                    }
                }
                holder.price.setVisibility(View.GONE);
                holder.address_iv.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(list.get(position).getType1())){
                    //判断rating是否是数字
                    if(BaseActivity.isNumeric1(list.get(position).getType1())){
                        holder.ll_rating.setVisibility(View.VISIBLE);
                        if (list.get(position).getType1().equals("1")){
                            holder.one_rating.setVisibility(View.VISIBLE);
                            holder.two_rating.setVisibility(View.GONE);
                            holder.three_rating.setVisibility(View.GONE);
                            holder.four_rating.setVisibility(View.GONE);
                            holder.five_rating.setVisibility(View.GONE);
                        }else if (list.get(position).getType1().equals("2")){
                            holder.one_rating.setVisibility(View.VISIBLE);
                            holder.two_rating.setVisibility(View.VISIBLE);
                            holder.three_rating.setVisibility(View.GONE);
                            holder.four_rating.setVisibility(View.GONE);
                            holder.five_rating.setVisibility(View.GONE);
                        }else if (list.get(position).getType1().equals("3")){
                            holder.one_rating.setVisibility(View.VISIBLE);
                            holder.two_rating.setVisibility(View.VISIBLE);
                            holder.three_rating.setVisibility(View.VISIBLE);
                            holder.four_rating.setVisibility(View.GONE);
                            holder.five_rating.setVisibility(View.GONE);
                        } else if (list.get(position).getType1().equals("4")){
                            holder.one_rating.setVisibility(View.VISIBLE);
                            holder.two_rating.setVisibility(View.VISIBLE);
                            holder.three_rating.setVisibility(View.VISIBLE);
                            holder.four_rating.setVisibility(View.VISIBLE);
                            holder.five_rating.setVisibility(View.GONE);
                        }else if (list.get(position).getType1().equals("5")){
                            holder.one_rating.setVisibility(View.VISIBLE);
                            holder.two_rating.setVisibility(View.VISIBLE);
                            holder.three_rating.setVisibility(View.VISIBLE);
                            holder.four_rating.setVisibility(View.VISIBLE);
                            holder.five_rating.setVisibility(View.VISIBLE);
                        }
                    }else{
                        holder.ll_rating.setVisibility(View.GONE);
                        holder.price.setVisibility(View.VISIBLE);
                        holder.price.setText(list.get(position).getType1());
                    }
                }


            }else if (list.get(position).getType().equals("restaurant")){
                holder.ll_rating.setVisibility(View.GONE);
                Log.i("collection", "getView: restaurant"+list.get(position).getType1());
                holder.imageView.setImageResource(R.drawable.restaruantlist);
                holder.address_iv.setVisibility(View.GONE);
                if (language.equals("zh")){
                    if(country.equals("TW")){
                        restaurantList =  resaddressHelper.querybyIDLanguage("zh-tw",list.get(position).getTypeid());
                    }else{
                        restaurantList =  resaddressHelper.querybyIDLanguage(language,list.get(position).getTypeid());
                    }
                }else{
                    restaurantList =  resaddressHelper.querybyIDLanguage("en",list.get(position).getTypeid());
                }


                if (!TextUtils.isEmpty(list.get(position).getAllname())){
                    if (restaurantList!=null&&restaurantList.size()>0){
                        holder.name.setText(restaurantList.get(0).getRestaurant_name());
                    }
                }
                if (!TextUtils.isEmpty(list.get(position).getType1())){
                    holder.price.setVisibility(View.VISIBLE);
                    if (restaurantList!=null&&restaurantList.size()>0){
                        holder.price.setText(restaurantList.get(0).getCategories());
                    }

                }
                if (!TextUtils.isEmpty(list.get(position).getType2())) {
                    if (restaurantList!=null&&restaurantList.size()>0){
                        holder.address.setVisibility(View.VISIBLE);
                        holder.address.setText(restaurantList.get(0).getPrice_range());
                    }
                }
            }else if (list.get(position).getType().equals("shopping")){
                holder.ll_rating.setVisibility(View.GONE);
                if (language.equals("zh")){
                    if(country.equals("TW")){
                        shoppingList =  shopaddressHelper.querybyIDLanguage("zh-tw",list.get(position).getTypeid());
                    }else{
                        shoppingList =  shopaddressHelper.querybyIDLanguage(language,list.get(position).getTypeid());
                    }
                }else{
                    shoppingList =  shopaddressHelper.querybyIDLanguage("en",list.get(position).getTypeid());
                }

                if (!TextUtils.isEmpty(list.get(position).getAllname())){
                    if (shoppingList!=null&&shoppingList.size()>0){
                        holder.name.setText(shoppingList.get(0).getShopping_name());
                    }
                }
                if (!TextUtils.isEmpty(list.get(position).getType2())) {
                    if (shoppingList!=null&&shoppingList.size()>0){
                        holder.address.setVisibility(View.VISIBLE);
                        holder.address.setText(shoppingList.get(0).getAddress());
                    }
                }
                holder.address_iv.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(R.drawable.shoppinglist);

                if (!TextUtils.isEmpty(list.get(position).getType1())){
                    holder.price.setVisibility(View.VISIBLE);
                    if (shoppingList!=null&&shoppingList.size()>0) {
                        holder.price.setText(shoppingList.get(0).getCategories());
                    }
                }
            }
        }

        return convertView;
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
}
