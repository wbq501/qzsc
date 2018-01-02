package com.zoomtk.circle.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.AdressBean;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.List;

/**
 * Created by wbq501 on 2017/11/11.
 */

public class NearStoreAdapter extends BaseQuickAdapter<AdressBean,BaseViewHolder>{
    public NearStoreAdapter(int layoutResId, @Nullable List<AdressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, AdressBean item) {
        holder.setText(R.id.tv_name,item.getW_storename());
        holder.setText(R.id.tv_addr,"地址:"+item.getArea_info()+item.getAddress());
        holder.setText(R.id.tv_num,item.getMobile()).addOnClickListener(R.id.tv_num);
        holder.setText(R.id.tv_distance,item.getDistance()/1000.0+"km");
        holder.addOnClickListener(R.id.ll_map);
        int layoutPosition = holder.getLayoutPosition();
        if (layoutPosition == 0){
            holder.setBackgroundColor(R.id.is_choose, Color.parseColor("#ee2e40"));
        }else {
            holder.setBackgroundColor(R.id.is_choose, Color.parseColor("#f4f5f7"));
        }
        ImgLoading.Loadimg(mContext,item.getAvatar(), (ImageView) holder.getView(R.id.im_img));
    }
}
