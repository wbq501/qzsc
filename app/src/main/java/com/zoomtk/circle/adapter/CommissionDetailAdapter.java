package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.CommissionDetailBean;
import com.zoomtk.circle.utils.DateUtils;

import java.util.List;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class CommissionDetailAdapter extends BaseQuickAdapter<CommissionDetailBean.BwListBean,BaseViewHolder>{
    public CommissionDetailAdapter(int layoutResId, @Nullable List<CommissionDetailBean.BwListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CommissionDetailBean.BwListBean item) {
        holder.setText(R.id.money,"提现金额"+item.getTotal_amount()+"元");
        holder.setText(R.id.time, DateUtils.timeTodate(item.getCreate_time()));
        holder.setText(R.id.type,item.getBrokerage_desc());
    }
}
