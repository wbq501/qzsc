package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;

import com.baidu.mapapi.search.core.PoiInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;

import java.util.List;

/**
 * Created by wbq501 on 2017/11/17.
 */

public class SelectMapItemAdapter extends BaseQuickAdapter<PoiInfo,BaseViewHolder>{
    public SelectMapItemAdapter(int layoutResId, @Nullable List<PoiInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, PoiInfo item) {
        holder.setText(R.id.tv_addr,item.address);
        holder.setText(R.id.tv_name,item.name);
    }
}
