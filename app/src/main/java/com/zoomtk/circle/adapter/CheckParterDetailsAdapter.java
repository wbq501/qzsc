package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.CheckParterDetails;
import com.zoomtk.circle.bean.CheckPartnerBean;

import java.util.List;

/**
 * Created by wbq501 on 2017/11/25.
 */

public class CheckParterDetailsAdapter extends BaseQuickAdapter<CheckPartnerBean.OrderListBean,BaseViewHolder>{
    public CheckParterDetailsAdapter(int layoutResId, @Nullable List<CheckPartnerBean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CheckPartnerBean.OrderListBean item) {
        holder.setText(R.id.ordernumber,item.getOrder_sn());
        holder.setText(R.id.addtime,item.getAdd_time());
        holder.setText(R.id.buy_price,"￥"+item.getOrder_amount());
        holder.setText(R.id.buy_num,"共"+item.getGoods_count()+"件");
        holder.setText(R.id.commission,"￥"+item.getAward());

        RecyclerView recyclerView = (RecyclerView)holder.getView(R.id.orderlist_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if (item.getGoods_list().size() > 0){
            CheckParterDetailsAdapter2 adapter2 = new CheckParterDetailsAdapter2(R.layout.checkparter_item2,item.getGoods_list());
//            holder.setAdapter(R.id.orderlist_listview, (Adapter) adapter2);
            recyclerView.setAdapter(adapter2);
        }
    }
}
