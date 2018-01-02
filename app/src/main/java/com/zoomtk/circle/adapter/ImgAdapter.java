package com.zoomtk.circle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.LookImgAct;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.ArrayList;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class ImgAdapter extends BaseAdapter{

    private ArrayList<String> lists;
    private Context context;

    public ImgAdapter(Context context, ArrayList<String> lists){
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.commission_img_item,null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ImgLoading.LoadCircle(context,lists.get(position),holder.imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,LookImgAct.class);
                intent.putExtra("imgurl",lists.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void setData(ArrayList<String> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        ImageView imageView;
    }
}
