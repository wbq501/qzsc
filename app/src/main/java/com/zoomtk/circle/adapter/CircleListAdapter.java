package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.CircleItem;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.List;

/**
 * Created by wbq501 on 2017/12/21.
 */

public class CircleListAdapter extends BaseQuickAdapter<CircleItem.JoinCircleBean,BaseViewHolder>{
    public CircleListAdapter(int layoutResId, @Nullable List<CircleItem.JoinCircleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleItem.JoinCircleBean item) {
        holder.setText(R.id.tv_name,item.getCircle_name());
        holder.setText(R.id.tv_context,"群主"+item.getCircle_mastername()+"        人数"+item.getCircle_mcount());
        holder.setText(R.id.tv_desc,item.getCircle_desc());
        ImgLoading.LoadCircle(mContext,item.getCircle_img(), (ImageView) holder.getView(R.id.iv_head));
    }
}
