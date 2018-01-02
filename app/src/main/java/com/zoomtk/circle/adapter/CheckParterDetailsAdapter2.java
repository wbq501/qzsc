package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.CheckPartnerBean;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.List;

/**
 * Created by wbq501 on 2017/11/25.
 */

public class CheckParterDetailsAdapter2 extends BaseQuickAdapter<CheckPartnerBean.OrderListBean.GoodsListBean,BaseViewHolder>{
    public CheckParterDetailsAdapter2(int layoutResId, @Nullable List<CheckPartnerBean.OrderListBean.GoodsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CheckPartnerBean.OrderListBean.GoodsListBean item) {
        ImgLoading.LoadCircle(mContext,item.getGoods_img_url(), (ImageView) holder.getView(R.id.order_image));
        holder.setText(R.id.order_name,item.getGoods_name());
        holder.setText(R.id.price,item.getGoods_price());
        holder.setText(R.id.goods_size,item.getGoods_spec());
    }
}
