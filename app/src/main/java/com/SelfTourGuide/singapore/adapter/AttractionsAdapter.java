package com.SelfTourGuide.singapore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.model.AttractionModel;

import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class AttractionsAdapter extends BaseAdapter {
   private Context context;
     private LayoutInflater mLayoutInflater;
    private List<AttractionModel> list;
    public AttractionsAdapter(Context context, List<AttractionModel> regionModelList){
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

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate( R.layout.attraction_item,null);
            holder.name = (TextView) convertView.findViewById( R.id.name);
            holder.price = (TextView) convertView.findViewById( R.id.price);
            holder.address = (TextView) convertView.findViewById( R.id.address);
            holder.imageView = (ImageView) convertView.findViewById( R.id.image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if (!TextUtils.isEmpty(list.get(position).getAttraction_name())){
            holder.name.setText(list.get(position).getAttraction_name());
        }
        if (!TextUtils.isEmpty(list.get(position).getAddress())){
            holder.address.setText(list.get(position).getAddress());
        }
        if (!TextUtils.isEmpty(list.get(position).getTicket_price())){
            holder.price.setText(list.get(position).getTicket_price());
        }


        holder.imageView.setBackgroundResource(list.get(position).getImageUrl());


        return convertView;
    }

}
