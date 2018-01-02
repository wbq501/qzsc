package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.DeliveryAddressModel;

import java.util.List;

/**
 * Created by wbq501 on 2017/11/14.
 */

public class AddrAdapter extends BaseQuickAdapter<DeliveryAddressModel.AddresslistBean,BaseViewHolder>{
    public AddrAdapter(int layoutResId, @Nullable List<DeliveryAddressModel.AddresslistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DeliveryAddressModel.AddresslistBean item) {
        holder.setText(R.id.tv_name,item.getTrue_name());
        if (holder.getLayoutPosition() == 0){
            holder.setVisible(R.id.line, true);
        }else {
            holder.setVisible(R.id.line, false);
        }
        holder.setText(R.id.tv_phoneNum,item.getMob_phone());
        holder.setText(R.id.tv_addr,item.getAddress()+item.getResidential());
        holder.addOnClickListener(R.id.ll_edit);
    }
}
