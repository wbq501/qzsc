package com.zoomtk.circle.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoomtk.circle.R;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.List;

/**
 * Created by admin on 2017/12/28.
 */

public class TrendsImageGridAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    private String count;

    public TrendsImageGridAdapter(int layoutResId, @Nullable List<String> data,String count) {
        super(layoutResId, data);
        this.count = count;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        while (getData().size() > 3){
            getData().remove(2);
            notifyDataSetChanged();
        }
        ImgLoading.LoadCircle(mContext,item, (ImageView) holder.getView(R.id.trends_content_image));
        if (getData().size() == 3){
            holder.setVisible(R.id.fram_layout,true);
            holder.setAlpha(R.id.fram_layout,100);
            holder.setText(R.id.photo_count_tx,count);
        }
    }
}
