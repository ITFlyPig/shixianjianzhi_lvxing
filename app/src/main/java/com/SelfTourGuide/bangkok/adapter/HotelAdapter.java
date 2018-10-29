package com.SelfTourGuide.bangkok.adapter;

import android.content.Context;
import android.os.Handler;
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
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.model.HotelModel;

import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class HotelAdapter extends BaseAdapter {
   private Context context;
     private LayoutInflater mLayoutInflater;
    private List<HotelModel> list;
    private Handler handler;
    public HotelAdapter(Context context, List<HotelModel> regionModelList){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = regionModelList;
    }

    @Override
    public int getCount() {
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
        private ImageView one_rating,two_rating,three_rating,four_rating,five_rating;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate( R.layout.hotel_item,null);
            holder.name = (TextView) convertView.findViewById( R.id.name);
            holder.price = (TextView) convertView.findViewById( R.id.price);
            holder.address = (TextView) convertView.findViewById( R.id.address);
            holder.imageView = (ImageView) convertView.findViewById( R.id.image);
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
        if (!TextUtils.isEmpty(list.get(position).getHotel_name())){
            holder.name.setText(list.get(position).getHotel_name());
        }
        if (!TextUtils.isEmpty(list.get(position).getAddress())){
            holder.address.setText(list.get(position).getAddress());
        }
        Log.i("tag", "getView: "+list.get(position).getStar_rating());
        if(!TextUtils.isEmpty(list.get(position).getStar_rating())){
            //判断rating是否是数字
            if(BaseActivity.isNumeric1(list.get(position).getStar_rating())){
                holder.ll_rating.setVisibility(View.VISIBLE);
                holder.price.setVisibility(View.GONE);
                if (list.get(position).getStar_rating().equals("1")){
                    holder.one_rating.setVisibility(View.VISIBLE);
                    holder.two_rating.setVisibility(View.GONE);
                    holder.three_rating.setVisibility(View.GONE);
                    holder.four_rating.setVisibility(View.GONE);
                    holder.five_rating.setVisibility(View.GONE);
                }else if (list.get(position).getStar_rating().equals("2")){
                    holder.one_rating.setVisibility(View.VISIBLE);
                    holder.two_rating.setVisibility(View.VISIBLE);
                    holder.three_rating.setVisibility(View.GONE);
                    holder.four_rating.setVisibility(View.GONE);
                    holder.five_rating.setVisibility(View.GONE);
                }else if (list.get(position).getStar_rating().equals("3")){
                    holder.one_rating.setVisibility(View.VISIBLE);
                    holder.two_rating.setVisibility(View.VISIBLE);
                    holder.three_rating.setVisibility(View.VISIBLE);
                    holder.four_rating.setVisibility(View.GONE);
                    holder.five_rating.setVisibility(View.GONE);
                } else if (list.get(position).getStar_rating().equals("4")){
                    holder.one_rating.setVisibility(View.VISIBLE);
                    holder.two_rating.setVisibility(View.VISIBLE);
                    holder.three_rating.setVisibility(View.VISIBLE);
                    holder.four_rating.setVisibility(View.VISIBLE);
                    holder.five_rating.setVisibility(View.GONE);
                }else if (list.get(position).getStar_rating().equals("5")){
                    holder.one_rating.setVisibility(View.VISIBLE);
                    holder.two_rating.setVisibility(View.VISIBLE);
                    holder.three_rating.setVisibility(View.VISIBLE);
                    holder.four_rating.setVisibility(View.VISIBLE);
                    holder.five_rating.setVisibility(View.VISIBLE);
                }
            }else{
                holder.ll_rating.setVisibility(View.GONE);
                holder.price.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(list.get(position).getStar_rating())){
                    holder.price.setText(list.get(position).getStar_rating());
                }
            }
        }



//        holder.imageView.setBackgroundResource(list.get(position).getImageUrl());


        return convertView;
    }

}
