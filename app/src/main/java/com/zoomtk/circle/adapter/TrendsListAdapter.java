package com.zoomtk.circle.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Config.URLConst;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.CircleShowDeatilWebAct;
import com.zoomtk.circle.activity.PKActivity;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.DIanZanModle;
import com.zoomtk.circle.bean.ShareBean;
import com.zoomtk.circle.bean.TalkAboutListModel;
import com.zoomtk.circle.dialog.DelDialog;
import com.zoomtk.circle.dialog.ShareDialog;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.ConvertUtils;
import com.zoomtk.circle.utils.DateUtils;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.utils.SharePreferencesUtils;
import com.zoomtk.circle.utils.ShareUtils;
import com.zoomtk.circle.view.EmojiconTextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/12/25.
 */

public class TrendsListAdapter extends BaseQuickAdapter<TalkAboutListModel.TracelogListEntity,BaseViewHolder>{
    private int praise_count = 0;
    SpannableStringBuilder builder = null;
//    int itemPosition;
    String string1;
    private OnAddCommentCallback mCommentCallback;
    private Rect mRect = new Rect();

    public TrendsListAdapter(int layoutResId, @Nullable List<TalkAboutListModel.TracelogListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final TalkAboutListModel.TracelogListEntity trendsSurface) {
//        itemPosition = holder.getPosition();
//        itemPosition = holder.getAdapterPosition();

        if (!TextUtils.isEmpty(trendsSurface.getTrace_copycount()) && !trendsSurface.getTrace_copycount().equals("0")){
            String copycount = trendsSurface.getTrace_copycount();
            holder.setText(R.id.sharecount,copycount);
        }else {
            holder.setText(R.id.sharecount,"分享");
        }

        if (trendsSurface.getFrom_me() == 1){
            holder.setVisible(R.id.deleteshow,true);
        }else {
            holder.setGone(R.id.deleteshow,false);
        }
        holder.setOnClickListener(R.id.deleteshow, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("提示").setMessage("确定删除吗?");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        delteshow(trendsSurface,holder);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        if (trendsSurface.getTrace_attach_flag() == null || trendsSurface.getTrace_attach_flag().equals("0")){
            holder.setGone(R.id.rela_tui,false);
            holder.setGone(R.id.rela_goods,false);
        }else if (trendsSurface.getTrace_attach_flag().equals("1") && trendsSurface.getTrace_attach().getGoods_id() != null) {
            holder.setVisible(R.id.rela_goods,true);
            holder.setText(R.id.goods_name,trendsSurface.getTrace_attach().getGoods_name()+"");
            holder.setText(R.id.goods_price,trendsSurface.getTrace_attach().getGoods_price()+"元");
            ImgLoading.Loadimg(mContext,trendsSurface.getTrace_attach().getGoods_image(),(ImageView) holder.getView(R.id.goods_image));
        }else if (trendsSurface.getTrace_attach_flag().equals("2")) {
            holder.setGone(R.id.rela_goods,false);
            holder.setVisible(R.id.rela_tui,true);
            ImgLoading.Loadimg(mContext,trendsSurface.getTrace_attach().getTa_image(),(ImageView) holder.getView(R.id.tui_image));
        }
        ImgLoading.LoadCircle(mContext,trendsSurface.getTrace_avatar(),(ImageView) holder.getView(R.id.trends_potrait));
        holder.setText(R.id.trends_username,trendsSurface.getTrace_nickname());
        holder.setText(R.id.trends_time, DateUtils.timeTodate(trendsSurface.getTrace_addtime()));

        Initshow_content(trendsSurface,holder);

        Showpraise(holder,trendsSurface);

        holder.setOnClickListener(R.id.lin_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(holder,trendsSurface);
            }
        });

        holder.setText(R.id.browse_count,"浏览" + trendsSurface.getTrace_browse_count() + "次");

        //评论
        holder.setTag(R.id.i_say,holder.getAdapterPosition());
        holder.setTag(R.id.lin_comment,holder.getAdapterPosition());
        holder.setOnClickListener(R.id.i_say, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_comment(holder,trendsSurface,v);
            }
        });
        holder.setOnClickListener(R.id.lin_comment, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_comment(holder,trendsSurface,v);
            }
        });

        //点赞相关
        int status = trendsSurface.getTrace_like().getIs_like();
        final int[] like = new int[1];
        if (status == 0){
            like[0] = 1;
            holder.setBackgroundRes(R.id.praise,R.mipmap.circleshow_zan);
        }else if (status == 1){
            holder.setBackgroundRes(R.id.praise,R.mipmap.circleshow_zan1);
            like[0] = 0;
        }
        holder.setOnClickListener(R.id.lin_praise, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumb(like,holder,trendsSurface);
            }
        });

        List<String> imagelist = trendsSurface.getTrace_albumpic().getList();
        if (imagelist != null && imagelist.size() > 0){
            holder.setVisible(R.id.trends_gridview,true);
            RecyclerView recyclerView = holder.getView(R.id.trends_gridview);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(new TrendsImageGridAdapter(R.layout.griditem_contentoftrends,imagelist,trendsSurface.getTrace_albumpic().getCount()));
        }else {
            holder.setGone(R.id.trends_gridview,false);
        }

        if (trendsSurface.getTrace_comment() != null){
            List<TalkAboutListModel.TracelogListEntity.TraceCommentEntity.ListEntity> list = trendsSurface.getTrace_comment().getList();
            if (list.size() > 0){
                int count = Integer.valueOf(trendsSurface.getTrace_comment().getCount()).intValue();
                holder.setText(R.id.commmentcount,""+count);
                holder.setVisible(R.id.llCommentLayout,true);
                showCommentInfo(holder,trendsSurface,count,list);
            }else {
                holder.setText(R.id.commmentcount,"评论");
                showCommentInfo(holder,trendsSurface,0,list);
            }
        }else {
            holder.setText(R.id.commmentcount,"评论");
            holder.setGone(R.id.llCommentLayout,false);
            showCommentInfo(holder,trendsSurface,0,null);
        }
    }

    /**
     * 评论相关
     * @param holder
     * @param trendsSurface
     * @param count 最大显示数量
     * @param commentList
     */
    private void showCommentInfo(final BaseViewHolder holder, final TalkAboutListModel.TracelogListEntity trendsSurface, int count, List<TalkAboutListModel.TracelogListEntity.TraceCommentEntity.ListEntity> commentList) {
        LinearLayout linearLayout = holder.getView(R.id.llCommentLayout);
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, CommonUtil.dip2px(6),0,0);
        linearLayout.setLayoutParams(params);
        if (commentList == null || commentList.size() == 0){
            return;
        }
        for (int i = 0; i < commentList.size(); i++){
            final EmojiconTextView textView = new EmojiconTextView(mContext);
            textView.setTextColor(Color.parseColor("#999999"));
            textView.setTextSize(13);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.comment_item_selector));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams
                    .WRAP_CONTENT);

            layoutParams.setMargins(ConvertUtils.dip2px(mContext, 10), 0, ConvertUtils.dip2px(mContext, 10), 0);
            textView.setLayoutParams(layoutParams);
            final TalkAboutListModel.TracelogListEntity.TraceCommentEntity.ListEntity commentInfo = commentList.get(i);
            SpannableStringBuilder sb = new SpannableStringBuilder();
            if (commentInfo.getComment_nickname() != null) {
                sb.append(createSpannableString(commentInfo.getComment_nickname()));
            }
            if (commentInfo.getComment_receiver_nickname() != null && !commentInfo.getComment_receiver_nickname().equals("")) {
                sb.append("回复").append(createSpannableString(commentInfo.getComment_receiver_nickname()));
            }
            try {
                sb.append(createSpannableString("：")).append(commentInfo.getComment_content());
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setText(sb);
            textView.setTag(R.id.tag_first, commentInfo);
            textView.setTag(R.id.tag_second, holder.getAdapterPosition());
            textView.setTag(R.id.tag_third, i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {//点击评论回复
                    if (mCommentCallback != null) {
                        TalkAboutListModel.TracelogListEntity.TraceCommentEntity.ListEntity currentCommentInfo = (TalkAboutListModel.TracelogListEntity.TraceCommentEntity.ListEntity) v.getTag(R.id.tag_first);
                        int currentPosition = (int) v.getTag(R.id.tag_second);
                        String name = currentCommentInfo.getComment_nickname();
                        textView.getGlobalVisibleRect(mRect);
                        mCommentCallback.addComment(currentPosition, (int) v.getTag(R.id.tag_third), name, false, mRect
                                .bottom+v.getHeight(), trendsSurface.getTrace_id(), commentInfo.getComment_id()
                        );
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DelDialog delDialog = new DelDialog(mContext, commentInfo.getComment_content(), commentInfo.getIs_self(), new DelDialog.Del() {
                        @Override
                        public void del() {
                            delComment(commentInfo.getComment_id(),commentInfo.getTrace_id());
                        }
                    });
                    delDialog.show();
                    return true;
                }
            });
            linearLayout.addView(textView);
        }
        if (count > 5){
            TextView textView = new TextView(mContext);
            textView.setText("还有" + (count - 5) + "条评论>");
            textView.setTextSize(13);
            textView.setTextColor(Color.parseColor("#285A97"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, ConvertUtils.dip2px(mContext, 10), 0, 0);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showdetailWeb(trendsSurface, holder);
                }
            });
            linearLayout.addView(textView);
        }
    }

    private void delComment(String comment_id, String trace_id) {
        Map<String,String> prams = new HashMap<>();
        prams.put("token",SharePreferencesUtils.getToken(mContext));
        prams.put("comment_id",comment_id);
        prams.put("trace_id",trace_id);
        HttpUtils.delComment(prams, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    BaseToast.YToastS(mContext,"删除成功");
                }else {
                    BaseToast.YToastS(mContext,"删除失败");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(mContext,errormsg);
            }
        });
    }

    /**
     * 详情页初始化
     * @param trendsSurface
     * @param holder
     */
    private void showdetailWeb(TalkAboutListModel.TracelogListEntity trendsSurface, BaseViewHolder holder) {
        if (trendsSurface.getShow_detail_url() == null){
            return;
        }
        Bundle bundle = new Bundle();
        ShareBean shareBean = new ShareBean();
        shareBean.setShare_title(string1.substring(1,string1.length()-1));
        if (TextUtils.isEmpty(trendsSurface.getTrace_content())){
            shareBean.setShare_content(string1);
        }else {
            shareBean.setShare_content(trendsSurface.getTrace_content());
        }
        if (trendsSurface.getTrace_attach_flag().equals("1")){
            shareBean.setShare_image(trendsSurface.getTrace_attach().getGoods_image());
        }else if (trendsSurface.getTrace_albumpic() != null && trendsSurface.getTrace_albumpic().getList().size() > 0){
            shareBean.setShare_image(trendsSurface.getTrace_albumpic().getList().get(0));
        }else if (!TextUtils.isEmpty(trendsSurface.getTrace_activityid()) && trendsSurface.getTrace_activityid().equals("0")){
            shareBean.setShare_image("http://show"+ URLConst.showurl+"/static/img/icon-activity.png");
        }else {
            shareBean.setShare_image("http://show"+URLConst.showurl+"/static/img/icon-show.png");
        }
        shareBean.setShare_url(trendsSurface.getShow_detail_url() + "/sid/" + AppApplication.sid);
        bundle.putSerializable("mode",shareBean);
        Intent intent = new Intent(mContext,CircleShowDeatilWebAct.class);
        intent.putExtra("trace_id",trendsSurface.getTrace_id());
        intent.putExtra("url",trendsSurface.getShow_detail_url());
        intent.putExtra("title",string1);
        intent.putExtra("activityid",trendsSurface.getTrace_activityid());
        intent.putExtra("isme",trendsSurface.getFrom_me());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public interface OnAddCommentCallback {
        /**
         * @param position
         * @param name      对谁进行评论或者回复
         * @param isComment ture为评论
         * @param y         需要滚动到某个位置
         */
        public void addComment(int position, int childPosition, String name, boolean isComment, int y, String geval_id, String reply_id);

    }

    private SpannableString createSpannableString(String name) {
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new MyClickSpan(name), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    private class MyClickSpan extends ClickableSpan {
        private String name;

        public MyClickSpan(final String name) {
            this.name = name;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#999999"));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            BaseToast.YToastS(mContext,name);
        }
    }

    /**
     * 点赞操作
     * @param like
     * @param holder
     * @param trendsSurface
     */
    private void thumb(final int[] like, final BaseViewHolder holder, final TalkAboutListModel.TracelogListEntity trendsSurface) {
        if (trendsSurface.getTrace_id() == null){
            return;
        }
        Map<String,String> parms = new HashMap<>();
        parms.put("trace_id",trendsSurface.getTrace_id());
        parms.put("is_like",like[0]+"");
        parms.put("token",SharePreferencesUtils.getToken(mContext));
        HttpUtils.talkAboutLike(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    Gson gson = new Gson();
                    String result = gson.toJson(msg.getResult());
                    DIanZanModle dIanZanModle = gson.fromJson(result, DIanZanModle.class);
                    if (like[0] == 0){
                        like[0] = 1;
                        holder.setBackgroundRes(R.id.praise,R.mipmap.circleshow_zan);
                        for (int i = 0; i < trendsSurface.getTrace_like().getList().size(); i++){
                            if (trendsSurface.getTrace_like().getList().get(i).getMember_nickname().contains(dIanZanModle.getMember_nickname())){
                                trendsSurface.getTrace_like().getList().remove(i);
                                trendsSurface.getTrace_like().setCount(Integer.valueOf(trendsSurface.getTrace_like().getCount())-1 +"");
                            }
                        }
                        Showpraise(holder,trendsSurface);
                    }else if (like[0] == 1){
                        like[0] = 0;
                        holder.setBackgroundRes(R.id.praise,R.mipmap.circleshow_zan1);
                        TalkAboutListModel.TracelogListEntity.TraceLikeEntity.ListEntity listEntity = new TalkAboutListModel.TracelogListEntity.TraceLikeEntity.ListEntity();
                        listEntity.setMember_id(dIanZanModle.getMember_id());
                        listEntity.setMember_avatar(dIanZanModle.getMember_avatar());
                        listEntity.setMember_nickname(dIanZanModle.getMember_nickname());
                        trendsSurface.getTrace_like().getList().add(0,listEntity);
                        trendsSurface.getTrace_like().setCount(Integer.valueOf(trendsSurface.getTrace_like().getCount())+1 +"");
                        Showpraise(holder,trendsSurface);
                    }
                }else {
                    BaseToast.YToastS(mContext,"点赞失败");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(mContext,errormsg);
            }
        });
    }

    /**
     * 评论
     * @param holder
     * @param trendsSurface
     * @param v
     */
    private void go_comment(BaseViewHolder holder, TalkAboutListModel.TracelogListEntity trendsSurface, View v) {
        if (trendsSurface.getTrace_id() == null){
            return;
        }
        int currPosition = (int) v.getTag();
        List<TalkAboutListModel.TracelogListEntity.TraceCommentEntity.ListEntity> currTrendsSurface = trendsSurface.getTrace_comment().getList();
        int childPosition;
        if (currTrendsSurface == null || currTrendsSurface.size() == 0) {
            childPosition = 0;
        } else {
            childPosition = currTrendsSurface.size() - 1;
        }
    }


    /**
     * 分享
     * @param holder
     * @param trendsSurface
     */
    private void share(final BaseViewHolder holder, final TalkAboutListModel.TracelogListEntity trendsSurface) {
        Map<String,String> parms = new HashMap<String, String>();
        parms.put("token",SharePreferencesUtils.getToken(mContext));
        parms.put("type",5+"");//每页的分享类型
        parms.put("id",trendsSurface.getTrace_id());
        HttpUtils.getShareParameters(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    final Gson gson = new Gson();
                    ShareBean shareBean = gson.fromJson(gson.toJson(msg.getResult()), ShareBean.class);
                    ShareUtils.ShareAdress((Activity) mContext, new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            Toast.makeText(mContext,"请稍等...",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            Toast.makeText(mContext,"分享成功",Toast.LENGTH_LONG).show();
                            Map<String,String> parms = new HashMap<>();
                            parms.put("token",SharePreferencesUtils.getToken(mContext));
                            parms.put("trace_id",trendsSurface.getTrace_id());
                            HttpUtils.incTraceShareCount(parms, new RequestBack() {
                                @Override
                                public void success(BaseJson msg) throws Exception {
                                    if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                                        String result = gson.toJson(msg.getResult());
                                        JSONObject object = new JSONObject(result);
                                        holder.setText(R.id.sharecount,object.getString("trace_copycount"));
                                    }else {
                                        BaseToast.YToastS(mContext,"数据错误");
                                    }
                                }

                                @Override
                                public void error(String errormsg) {
                                    BaseToast.YToastS(mContext,errormsg);
                                }
                            });
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable t) {
                            Toast.makeText(mContext,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            Toast.makeText(mContext,"取消分享",Toast.LENGTH_LONG).show();
                        }
                    }, shareBean);
                }else {
                    BaseToast.YToastS(mContext,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(mContext,errormsg);
            }
        });
    }

    /**
     * 点赞
     * @param holder
     * @param trendsSurface
     */
    private void Showpraise(BaseViewHolder holder, TalkAboutListModel.TracelogListEntity trendsSurface) {
        if (trendsSurface.getTrace_like() != null && trendsSurface.getTrace_like().getList().size() > 0){
            praise_count = Integer.valueOf(trendsSurface.getTrace_like().getCount()).intValue();
            holder.setText(R.id.praisecount,praise_count+"");
            StringBuffer praise = new StringBuffer();
            for (int i = 0; i < trendsSurface.getTrace_like().getList().size(); i++) {
                praise.append(trendsSurface.getTrace_like().getList().get(i).getMember_nickname());
                if (i < trendsSurface.getTrace_like().getList().size() - 1) {
                    praise.append("、");
                }
            }
            builder = new SpannableStringBuilder(praise);
            ForegroundColorSpan bluespan = new ForegroundColorSpan(Color.parseColor("#285A97"));
            builder.setSpan(bluespan, 0, praise.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.setVisible(R.id.praise_name1,true);
            holder.setText(R.id.praise_name1,builder);
        }else {
            holder.setGone(R.id.praise_name1,false);
            holder.setText(R.id.praisecount,"赞");
        }
    }

    /**
     * 标题栏
     * @param trendsSurface
     * @param holder
     */
    private void Initshow_content(final TalkAboutListModel.TracelogListEntity trendsSurface, final BaseViewHolder holder) {
        StringBuilder stringBuilder = new StringBuilder();
        string1 = trendsSurface.getTrace_title();
        if (TextUtils.isEmpty(string1)){
            string1 = "#" + trendsSurface.getTrace_nickname() + "的秀#";
        }else {
            string1 = "#" + string1 + "#";
        }
        stringBuilder.append(string1);
        stringBuilder.append(trendsSurface.getTrace_content());
        String s3 = stringBuilder.toString();
        if (stringBuilder.length() > 100) {
            s3 = stringBuilder.toString().substring(0, 100);
            s3 = s3 + "...全文";
        } else {
            s3 = s3 + "...全文";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(s3);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#285a97"));
        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.parseColor("#285a97"));
        builder.setSpan(span1, 0, string1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        try {
            if (builder.length() > 100) {
                builder.setSpan(span, 100, 105, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                builder.setSpan(span, builder.length() - 5, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setText(R.id.trends_content,builder);
        holder.setOnClickListener(R.id.trends_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetailWeb(trendsSurface, holder);
            }
        });
    }

    /**
     * 删除秀
     * @param trendsSurface
     * @param holder
     */
    private void delteshow(TalkAboutListModel.TracelogListEntity trendsSurface, final BaseViewHolder holder) {
        Map<String, String> parms = new HashMap<>();
        parms.put("token", SharePreferencesUtils.getToken(mContext));
        parms.put("trace_id", trendsSurface.getTrace_id());
        HttpUtils.deleteTalkAbout(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    remove(holder.getAdapterPosition());
                    BaseToast.YToastS(mContext,"删除成功");
                }else {
                    BaseToast.YToastS(mContext,"删除失败");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(mContext,errormsg);
            }
        });
    }

}
