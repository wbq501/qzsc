package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/12/25.
 */

public class TalkAboutListModel implements Serializable{

    private int page_count;
    private String curr_page;
    private int has_next;

    private List<TracelogListEntity> tracelog_list;

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public void setCurr_page(String curr_page) {
        this.curr_page = curr_page;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public void setTracelog_list(List<TracelogListEntity> tracelog_list) {
        this.tracelog_list = tracelog_list;
    }

    public int getPage_count() {
        return page_count;
    }

    public String getCurr_page() {
        return curr_page;
    }

    public int getHas_next() {
        return has_next;
    }

    public List<TracelogListEntity> getTracelog_list() {
        return tracelog_list;
    }

    public static class TracelogListEntity implements Serializable {
        private String trace_id;
        private String trace_memberid;
        private String trace_title;
        private String trace_content;
        private String trace_addtime;
        private String trace_privacy;
        private String trace_commentcount;
        private int trace_browse_count;
        private String trace_attach_id;
        private String trace_activityid;
        private String trace_nickname;
        private String trace_avatar;
        private String trace_timefromnow;
        private String trace_attach_flag;
        private int from_me;
        private String show_detail_url;
        private String trace_copycount;

        public String getTrace_copycount() {
            return trace_copycount;
        }

        public void setTrace_copycount(String trace_copycount) {
            this.trace_copycount = trace_copycount;
        }

        public String getShow_detail_url() {
            return show_detail_url;
        }

        public void setShow_detail_url(String show_detail_url) {
            this.show_detail_url = show_detail_url;
        }

        public int getFrom_me() {
            return from_me;
        }

        public void setFrom_me(int from_me) {
            this.from_me = from_me;
        }

        private TraceAlbumpicEntity trace_albumpic;

        private TraceLikeEntity trace_like;

        private TraceCommentEntity trace_comment;
        private TraceAttach trace_attach;

        private TraceActivityBean trace_activity;


        public TraceAttach getTrace_attach() {
            return trace_attach;
        }

        public void setTrace_attach(TraceAttach trace_attach) {
            this.trace_attach = trace_attach;
        }

        public void setTrace_id(String trace_id) {
            this.trace_id = trace_id;
        }

        public void setTrace_memberid(String trace_memberid) {
            this.trace_memberid = trace_memberid;
        }


        public void setTrace_content(String trace_content) {
            this.trace_content = trace_content;
        }

        public void setTrace_addtime(String trace_addtime) {
            this.trace_addtime = trace_addtime;
        }

        public void setTrace_privacy(String trace_privacy) {
            this.trace_privacy = trace_privacy;
        }

        public void setTrace_commentcount(String trace_commentcount) {
            this.trace_commentcount = trace_commentcount;
        }

        public void setTrace_browse_count(int trace_browse_count) {
            this.trace_browse_count = trace_browse_count;
        }

        public void setTrace_attach_flag(String trace_attach_flag) {
            this.trace_attach_flag = trace_attach_flag;
        }

        public void setTrace_attach_id(String trace_attach_id) {
            this.trace_attach_id = trace_attach_id;
        }

        public void setTrace_title(String trace_title) {
            this.trace_title = trace_title;
        }

        public String getTrace_activityid() {
            return trace_activityid;
        }

        public void setTrace_activityid(String trace_activityid) {
            this.trace_activityid = trace_activityid;
        }

        public void setTrace_nickname(String trace_nickname) {
            this.trace_nickname = trace_nickname;
        }

        public void setTrace_avatar(String trace_avatar) {
            this.trace_avatar = trace_avatar;
        }

        public void setTrace_timefromnow(String trace_timefromnow) {
            this.trace_timefromnow = trace_timefromnow;
        }

        public void setTrace_albumpic(TraceAlbumpicEntity trace_albumpic) {
            this.trace_albumpic = trace_albumpic;
        }

        public void setTrace_like(TraceLikeEntity trace_like) {
            this.trace_like = trace_like;
        }

        public void setTrace_comment(TraceCommentEntity trace_comment) {
            this.trace_comment = trace_comment;
        }

        public String getTrace_id() {
            return trace_id;
        }

        public String getTrace_memberid() {
            return trace_memberid;
        }

        public String getTrace_title() {
            return trace_title;
        }

        public String getTrace_content() {
            return trace_content;
        }

        public String getTrace_addtime() {
            return trace_addtime;
        }

        public String getTrace_privacy() {
            return trace_privacy;
        }

        public String getTrace_commentcount() {
            return trace_commentcount;
        }

        public int getTrace_browse_count() {
            return trace_browse_count;
        }

        public String getTrace_attach_flag() {
            return trace_attach_flag;
        }

        public String getTrace_attach_id() {
            return trace_attach_id;
        }

        public String getTrace_nickname() {
            return trace_nickname;
        }

        public String getTrace_avatar() {
            return trace_avatar;
        }

        public String getTrace_timefromnow() {
            return trace_timefromnow;
        }

        public TraceAlbumpicEntity getTrace_albumpic() {
            return trace_albumpic;
        }

        public TraceLikeEntity getTrace_like() {
            return trace_like;
        }

        public TraceCommentEntity getTrace_comment() {
            return trace_comment;
        }

        public TraceActivityBean getTrace_activity() {
            return trace_activity;
        }

        public void setTrace_activity(TraceActivityBean trace_activity) {
            this.trace_activity = trace_activity;
        }


        public static class TraceAlbumpicEntity implements Serializable {
            private String count;

            private List<String> list;

            public List<String> getList() {
                return list;
            }

            public void setList(List<String> list) {
                this.list = list;
            }

            public void setCount(String count) {
                this.count = count;
            }


            public String getCount() {
                return count;
            }


        }

        public static class TraceLikeEntity implements Serializable {
            private String count;
            private int is_like;

            private List<ListEntity> list;

            public void setCount(String count) {
                this.count = count;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public String getCount() {
                return count;
            }

            public int getIs_like() {
                return is_like;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Serializable {
                private String member_id;
                private String member_nickname = "";
                private String member_avatar;


                public String getMember_nickname() {
                    return member_nickname;
                }

                public void setMember_nickname(String member_nickname) {
                    this.member_nickname = member_nickname;
                }

                public void setMember_id(String member_id) {
                    this.member_id = member_id;
                }


                public void setMember_avatar(String member_avatar) {
                    this.member_avatar = member_avatar;
                }

                public String getMember_id() {
                    return member_id;
                }


                public String getMember_avatar() {
                    return member_avatar;
                }
            }
        }

        public static class TraceCommentEntity implements Serializable {
            private String count;

            private List<ListEntity> list;

            public void setCount(String count) {
                this.count = count;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public String getCount() {
                return count;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Serializable {
                private String comment_id;
                private String comment_memberid;
                private String comment_state;
                private String trace_id;
                private String comment_replyid;
                private String comment_avatar;
                private String comment_nickname;
                private String comment_receiver_nickname;
                private String comment_content;
                private int is_self;

                public String getComment_content() {
                    return comment_content;
                }

                public void setComment_content(String comment_content) {
                    this.comment_content = comment_content;
                }

                public String getComment_receiver_nickname() {
                    return comment_receiver_nickname;
                }

                public void setComment_receiver_nickname(String comment_receiver_nickname) {
                    this.comment_receiver_nickname = comment_receiver_nickname;
                }

                public void setComment_id(String comment_id) {
                    this.comment_id = comment_id;
                }

                public void setComment_memberid(String comment_memberid) {
                    this.comment_memberid = comment_memberid;
                }

                public void setComment_state(String comment_state) {
                    this.comment_state = comment_state;
                }

                public void setTrace_id(String trace_id) {
                    this.trace_id = trace_id;
                }

                public void setComment_replyid(String comment_replyid) {
                    this.comment_replyid = comment_replyid;
                }

                public void setComment_avatar(String comment_avatar) {
                    this.comment_avatar = comment_avatar;
                }

                public void setComment_nickname(String comment_nickname) {
                    this.comment_nickname = comment_nickname;
                }


                public String getComment_id() {
                    return comment_id;
                }

                public String getComment_memberid() {
                    return comment_memberid;
                }

                public String getComment_state() {
                    return comment_state;
                }

                public String getTrace_id() {
                    return trace_id;
                }

                public String getComment_replyid() {
                    return comment_replyid;
                }

                public String getComment_avatar() {
                    return comment_avatar;
                }

                public String getComment_nickname() {
                    return comment_nickname;
                }

                public int getIs_self() {
                    return is_self;
                }

                public void setIs_self(int is_self) {
                    this.is_self = is_self;
                }
            }
        }

        public static class TraceAttach implements Serializable {
            private String ta_id;
            private String ta_image;
            private String ta_url;

            private String goods_id;
            private String goods_name;
            private String goods_image;
            private String store_id;
            private String goods_price;
            private String goods_url;


            public String getTa_url() {
                return ta_url;
            }

            public void setTa_url(String ta_url) {
                this.ta_url = ta_url;
            }

            public String getTa_image() {
                return ta_image;
            }

            public void setTa_image(String ta_image) {
                this.ta_image = ta_image;
            }

            public String getTa_id() {
                return ta_id;
            }

            public void setTa_id(String ta_id) {
                this.ta_id = ta_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setGoods_url(String goods_url) {
                this.goods_url = goods_url;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public String getStore_id() {
                return store_id;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public String getGoods_url() {
                return goods_url;
            }
        }


        public static class TraceActivityBean {
            private String act_id;
            private String act_title;
            private String act_addtime;
            private String act_starttime;
            private String act_applyendtime;
            private String act_endtime;
            private String act_address;
            /**
             * af_id : 7
             * act_id : 8
             * af_name : 32
             * af_fee : 0.00
             * af_limit : 0
             * af_addtime : 1458095242
             */

            private List<FeesBean> fees;

            public String getAct_id() {
                return act_id;
            }

            public void setAct_id(String act_id) {
                this.act_id = act_id;
            }

            public String getAct_title() {
                return act_title;
            }

            public void setAct_title(String act_title) {
                this.act_title = act_title;
            }

            public String getAct_addtime() {
                return act_addtime;
            }

            public void setAct_addtime(String act_addtime) {
                this.act_addtime = act_addtime;
            }

            public String getAct_starttime() {
                return act_starttime;
            }

            public void setAct_starttime(String act_starttime) {
                this.act_starttime = act_starttime;
            }

            public String getAct_applyendtime() {
                return act_applyendtime;
            }

            public void setAct_applyendtime(String act_applyendtime) {
                this.act_applyendtime = act_applyendtime;
            }

            public String getAct_endtime() {
                return act_endtime;
            }

            public void setAct_endtime(String act_endtime) {
                this.act_endtime = act_endtime;
            }

            public String getAct_address() {
                return act_address;
            }

            public void setAct_address(String act_address) {
                this.act_address = act_address;
            }

            public List<FeesBean> getFees() {
                return fees;
            }

            public void setFees(List<FeesBean> fees) {
                this.fees = fees;
            }

            public static class FeesBean implements Serializable {
                private String af_id;
                private String act_id;
                private String af_name;
                private String af_fee;
                private String af_limit;
                private String af_addtime;

                public String getAf_id() {
                    return af_id;
                }

                public void setAf_id(String af_id) {
                    this.af_id = af_id;
                }

                public String getAct_id() {
                    return act_id;
                }

                public void setAct_id(String act_id) {
                    this.act_id = act_id;
                }

                public String getAf_name() {
                    return af_name;
                }

                public void setAf_name(String af_name) {
                    this.af_name = af_name;
                }

                public String getAf_fee() {
                    return af_fee;
                }

                public void setAf_fee(String af_fee) {
                    this.af_fee = af_fee;
                }

                public String getAf_limit() {
                    return af_limit;
                }

                public void setAf_limit(String af_limit) {
                    this.af_limit = af_limit;
                }

                public String getAf_addtime() {
                    return af_addtime;
                }

                public void setAf_addtime(String af_addtime) {
                    this.af_addtime = af_addtime;
                }
            }
        }
    }
}
