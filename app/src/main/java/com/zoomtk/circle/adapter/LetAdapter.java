package com.zoomtk.circle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.Content;
import com.zoomtk.circle.utils.ImgLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/5.
 */
public class LetAdapter extends BaseAdapter implements SectionIndexer {
    private List<Content> list = null;
    private Context mContext;
    private SectionIndexer mIndexer;
    private HashMap<String,Boolean> sparseArray = new HashMap<String,Boolean>();
    private HashMap<String,ImageView> imageArray = new HashMap<>();

    /**显示的类型，1表示选择，2表示只是显示*/
    private int showType = 2;
    public LetAdapter(Context mContext, List<Content> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public LetAdapter(Context mContext, List<Content> list,int showType) {
        this.mContext = mContext;
        this.list = list;
        this.showType = showType;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.letadapter_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.touxiangim=(ImageView)view.findViewById(R.id.tongxunlu_img);
            viewHolder.selectStatus = (ImageView) view.findViewById(R.id.img_select_status);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();

        final Content mContent = list.get(position);
        if (position == 0) {
            if( mContent.getLetter() != null ) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getLetter());
            }
        } else {
            String lastCatalog = list.get(position - 1).getLetter();
            if (mContent.getLetter() == null || mContent.getLetter().equals(lastCatalog)) {
                viewHolder.tvLetter.setVisibility(View.GONE);
            } else {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getLetter());
            }
        }
        imageArray.put(mContent.getId(),viewHolder.selectStatus);
        if(showType == 1){
            viewHolder.selectStatus.setVisibility(View.VISIBLE);
            if(sparseArray.get(mContent.getId()) == null
                    || !sparseArray.get(mContent.getId())){
                viewHolder.selectStatus.setImageResource(R.mipmap.payee_switch_gray);
            }else{
                viewHolder.selectStatus.setImageResource(R.mipmap.payee_switch_red);
            }
        }else{
            viewHolder.selectStatus.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());
        Log.i("通讯录头像adapter姓名",""+this.list.get(position).getName());
        ImgLoading.LoadCircle(mContext,mContent.getTouxiang(),viewHolder.touxiangim);
        Log.i("通讯录头像adapter图片",""+this.list.get(position).getTouxiang());

        return view;

    }



    final static class ViewHolder {
        TextView tvTitle;
        TextView tvLetter;
        ImageView touxiangim;
        ImageView selectStatus;
    }


    public Object[] getSections() {
        return null;
    }

    public int getSectionForPosition(int position) {
        return 0;
    }

    public int getPositionForSection(int section) {
        Content mContent;
        String l;
        if (section == '#' || list == null || list.size() == 0) {
            return 0;
        } else {
            for (int i = 0; i < getCount(); i++) {
                mContent =list.get(i);
                l = mContent.getLetter();
                char firstChar = l.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i + 1;
                }

            }
        }
        mContent = null;
        l = null;
        return -1;
    }

    //设置数据
    public void setData(List<Content> datas){
        this.list = datas;
        notifyDataSetChanged();
    }

    public void changeSelectStatus(String id){
        if(sparseArray.get(id) == null || !sparseArray.get(id)){
            sparseArray.put(id,true);
            imageArray.get(id).setImageResource(R.mipmap.payee_switch_red);
        }else{
            sparseArray.put(id,false);
            imageArray.get(id).setImageResource(R.mipmap.payee_switch_gray);
        }
        //notifyDataSetChanged();
    }

    /**获取选中的项*/
    public List<String> getSelectId(){
        if(showType == 1){
            List<String> result = new ArrayList<>();
            for(Map.Entry<String,Boolean> e : sparseArray.entrySet()){
                if(e.getValue()){
                    result.add(e.getKey());
                }
            }
            return result;
        }
        return null;
    }

}
