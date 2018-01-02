package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.DistriButorModelBean;
import com.zoomtk.circle.utils.DateUtils;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.List;

/**
 * Created by wbq501 on 2017/11/24.
 */

public class CheckPartnerListAdapter extends BaseQuickAdapter<DistriButorModelBean.ContentBean,BaseViewHolder>{

    public CheckPartnerListAdapter(int layoutResId, @Nullable List<DistriButorModelBean.ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DistriButorModelBean.ContentBean item) {
        ImgLoading.LoadCircle(mContext,item.getAvatar(),(ImageView) holder.getView(R.id.item_head));
        ImgLoading.LoadCircle(mContext,item.getWshop_level_icon(),(ImageView) holder.getView(R.id.iv_level));
        holder.setText(R.id.item_name,item.getW_storename());
        holder.setText(R.id.tv_level,item.getWshop_level_desc());
        if (TextUtils.isEmpty(item.getMember_login_time())){
            holder.setText(R.id.item_time,"暂无时间信息");
        }else {
            holder.setText(R.id.item_time, DateUtils.timeTodate(item.getMember_login_time()));
        }
        holder.setText(R.id.item_phone,"手机："+item.getMember_mobile());
        holder.setText(R.id.item_man,"合伙人："+item.getDistributors_num());
        holder.setText(R.id.item_register_time,"注册日期："+item.getCreate_time());
        holder.setText(R.id.my_order,""+item.getOrder_total());

        holder.addOnClickListener(R.id.callphone);
        holder.addOnClickListener(R.id.prompting5);
        holder.addOnClickListener(R.id.my_order);
    }
}
