package com.zoomtk.circle.utils;

import com.zoomtk.circle.base.BaseJson;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 存放接口地址
 * Created by win 10 on 2017/10/11.
 */

public interface RetrofitService {
    /**
     * 获取短信验证码
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/login")
    @FormUrlEncoded
    Observable<BaseJson> getLoginCode(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Msm/getCode")
    @FormUrlEncoded
    Observable<BaseJson> getYanZhengURL(@FieldMap Map<String, String> map);

    /**
     * 推荐人
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/PublicTell/checkMobile")
    @FormUrlEncoded
    Observable<BaseJson> checkMobile(@FieldMap Map<String, String> map);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/registerWshop")
    @FormUrlEncoded
    Observable<BaseJson> registerWshop(@FieldMap Map<String, String> map);

    /**
     * 重置密码
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/changeOldPasswd")
    @FormUrlEncoded
    Observable<BaseJson> changeOldPasswd(@FieldMap Map<String, String> map);

    /**
     * 获取好友信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberFriend/getFriend")
    @FormUrlEncoded
    Observable<BaseJson> getFriend(@FieldMap Map<String, String> map);

    /**
     * 搜索圈子
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/findCircle")
    @FormUrlEncoded
    Observable<BaseJson> findCircle(@FieldMap Map<String, String> map);

    /**
     * 获取圈子详情
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Circle/circleDetail")
    @FormUrlEncoded
    Observable<BaseJson> circleDetail(@FieldMap Map<String, String> map);

    /**
     * 圈子秀
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Myhome/talkAboutList")
    @FormUrlEncoded
    Observable<BaseJson> talkAboutList(@FieldMap Map<String, String> map);

    /**
     * 圈子秀删除
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Myhome/deleteTalkAbout")
    @FormUrlEncoded
    Observable<BaseJson> deleteTalkAbout(@FieldMap Map<String, String> map);

    /**
     * 删除评论
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Myhome/delComment")
    @FormUrlEncoded
    Observable<BaseJson> delComment(@FieldMap Map<String, String> map);

    /**
     * 分享次数
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Myhome/incTraceShareCount")
    @FormUrlEncoded
    Observable<BaseJson> incTraceShareCount(@FieldMap Map<String, String> map);

    /**
     * 圈子秀点赞
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Myhome/talkAboutLike")
    @FormUrlEncoded
    Observable<BaseJson> talkAboutLike(@FieldMap Map<String, String> map);

    /**
     * 同意拒绝好友请求
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberFriend/handleApply")
    @FormUrlEncoded
    Observable<BaseJson> handleApply(@FieldMap Map<String, String> map);

    /**
     * 获取请求列表
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberFriend/getApplyList")
    @FormUrlEncoded
    Observable<BaseJson> getApplyList(@FieldMap Map<String, String> map);

    /**
     * 请求添加好友
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberFriend/addFriend")
    @FormUrlEncoded
    Observable<BaseJson> addFriend(@FieldMap Map<String, String> map);

    /**
     * 获取好友列表
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberFriend/getFriends")
    @FormUrlEncoded
    Observable<BaseJson> getFriends(@FieldMap Map<String, String> map);

    /**
     * 搜索好友
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberFriend/searchPeople")
    @FormUrlEncoded
    Observable<BaseJson> searchPeople(@FieldMap Map<String, String> map);

    /**
     * 加入的圈子
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/getJoinCircle")
    @FormUrlEncoded
    Observable<BaseJson> getJoinCircle(@FieldMap Map<String, String> map);

    /**
     * 创建群聊天
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/createGroupChat")
    @FormUrlEncoded
    Observable<BaseJson> createGroupChat(@FieldMap Map<String, String> map);

    /**
     * 登录要访问的红包接口
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Redpack/sendRedpack")
    @FormUrlEncoded
    Observable<BaseJson> sendRedpack(@FieldMap Map<String, String> map);

    /**
     * 获取融云token
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/getChatToken")
    @FormUrlEncoded
    Observable<BaseJson> getChatToken(@FieldMap Map<String, String> map);

    /**
     * 获取用户信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/getMemberInfo")
    @FormUrlEncoded
    Observable<BaseJson> getMemberInfo(@FieldMap Map<String, String> map);

    /**
     * 获取所有群
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/getGroupList")
    @FormUrlEncoded
    Observable<ResponseBody> getGroupList(@FieldMap Map<String, String> map);

    /**
     * 获取附近门店
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/getOfflineOnline")
    @FormUrlEncoded
    Observable<BaseJson> getOfflineOnline(@FieldMap Map<String, String> map);

    /**
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WxPay/getPayCallBackUrl")
    @FormUrlEncoded
    Observable<BaseJson> getPayCallBackUrl(@FieldMap Map<String, String> map);

    /**
     * 获取分享信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/AppShare/getShareContent")
    @FormUrlEncoded
    Observable<BaseJson> getShareContent(@FieldMap Map<String, String> map);

    /**
     * 获取附近门店地址
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberAddress/getStoresAddressList")
    @FormUrlEncoded
    Observable<BaseJson> getStoresAddressList(@FieldMap Map<String, String> map);

    /**
     * 获取app会员所有地址
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberAddress/getAddressList")
    @FormUrlEncoded
    Observable<BaseJson> getAddressList(@FieldMap Map<String, String> map);

    /**
     * 删除地址
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberAddress/delMemberInfo")
    @FormUrlEncoded
    Observable<BaseJson> delMemberInfo(@FieldMap Map<String, String> map);

    /**
     * 新增地址
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberAddress/addMemberInfo")
    @FormUrlEncoded
    Observable<BaseJson> addMemberInfo(@FieldMap Map<String, String> map);

    /**
     * 修改地址
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/MemberAddress/editMemberInfo")
    @FormUrlEncoded
    Observable<BaseJson> editMemberInfo(@FieldMap Map<String, String> map);

    /**
     * 新的分享
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/AppShare/getShareParameters")
    @FormUrlEncoded
    Observable<BaseJson> getShareParameters(@FieldMap Map<String, String> map);

    /**
     * 邀请队员分享
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/getStoreShare")
    @FormUrlEncoded
    Observable<BaseJson> getStoreShare(@FieldMap Map<String, String> map);

    /**
     * 邀请合伙人获取分享链接
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Goods/appShareLink")
    @FormUrlEncoded
    Observable<BaseJson> appShareLink(@FieldMap Map<String, String> map);

    /**
     * 店铺二维码
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/scanCode")
    @FormUrlEncoded
    Observable<BaseJson> scanCode(@FieldMap Map<String, String> map);

    /**
     * menu 右上角的数字
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/getOpenStoreCount")
    @FormUrlEncoded
    Observable<BaseJson> getOpenStoreCount(@FieldMap Map<String, String> map);

    /**
     * 我的 头像电话等...
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/myMenu")
    @FormUrlEncoded
    Observable<BaseJson> myMenu(@FieldMap Map<String, String> map);

    /**
     * 店铺信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/user/storeManage")
    @FormUrlEncoded
    Observable<BaseJson> storeManage(@FieldMap Map<String, String> map);

    /**
     * 获取用户信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/getWshopInfo")
    @FormUrlEncoded
    Observable<BaseJson> getWshopInfo(@FieldMap Map<String, String> map);

    /**
     * 获取会员web登录授权码
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/getLoginAuthCode")
    @FormUrlEncoded
    Observable<BaseJson> getLoginAuthCode(@FieldMap Map<String, String> map);

    /**
     * 我的团队
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/myDistributor")
    @FormUrlEncoded
    Observable<BaseJson> myDistributor(@FieldMap Map<String, String> map);

    /**
     * 提示信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopInformation/getPrompt")
    @FormUrlEncoded
    Observable<BaseJson> getPrompt(@FieldMap Map<String, String> map);

    /**
     * 我的团队列表
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopInformation/getDistributorsList")
    @FormUrlEncoded
    Observable<BaseJson> getDistributorsList(@FieldMap Map<String, String> map);

    /**
     * PK榜
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/AgentTeamPk/getAgentTeamPkUrl")
    @FormUrlEncoded
    Observable<BaseJson> getAgentTeamPkUrl(@FieldMap Map<String, String> map);

    /**
     * 分销商订单列表
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopInformation/getTeamMemberOrders")
    @FormUrlEncoded
    Observable<BaseJson> getTeamMemberOrders(@FieldMap Map<String, String> map);

    /**
     * 生成子店铺
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/batchRegisterChild")
    @FormUrlEncoded
    Observable<BaseJson> batchRegisterChild(@FieldMap Map<String, String> map);

    /**
     * 本月收入
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopSeller/wshopMonthIncome")
    @FormUrlEncoded
    Observable<BaseJson> wshopMonthIncome(@FieldMap Map<String, String> map);

    /**
     * 可申请提现佣金
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/myBrokerage")
    @FormUrlEncoded
    Observable<BaseJson> myBrokerage(@FieldMap Map<String, String> map);

    /**
     * 提现申请
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/withdrawnApply")
    @FormUrlEncoded
    Observable<BaseJson> withdrawnApply(@FieldMap Map<String, String> map);

    /**
     * 佣金订单列表
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopBrokerageWithdraw/getList")
    @FormUrlEncoded
    Observable<BaseJson> getList(@FieldMap Map<String, String> map);

    /**
     * 佣金详情
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopBrokerageWithdraw/getInfo")
    @FormUrlEncoded
    Observable<BaseJson> getInfo(@FieldMap Map<String, String> map);

    /**
     * 重新发起提现申请
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopBrokerageWithdraw/restartBrokerageWithdraw")
    @FormUrlEncoded
    Observable<BaseJson> restartBrokerageWithdraw(@FieldMap Map<String, String> map);

    /**
     * 佣金详情确认
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopBrokerageWithdraw/confirm")
    @FormUrlEncoded
    Observable<BaseJson> confirm(@FieldMap Map<String, String> map);

    /**
     * 提现账号保存
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/User/operateWithdrawalAccount")
    @FormUrlEncoded
    Observable<BaseJson> operateWithdrawalAccount(@FieldMap Map<String, String> map);

    /**
     * 获取银行卡信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopBrokerageWithdraw/getBankAccountInfo")
    @FormUrlEncoded
    Observable<BaseJson> getBankAccountInfo(@FieldMap Map<String, String> map);

    /**
     * 保存银行卡信息
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/WshopBrokerageWithdraw/saveBankAccount")
    @FormUrlEncoded
    Observable<BaseJson> saveBankAccount(@FieldMap Map<String, String> map);

    /**
     * 检查更新
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/index/getSoftVersion")
    @FormUrlEncoded
    Observable<ResponseBody> getSoftVersion(@FieldMap Map<String, String> map);

    /**
     * @param map
     * @return
     */
    @POST("index.php?s=/api/user/storeManage")
    @FormUrlEncoded
    Observable<ResponseBody> userstoreManage(@FieldMap Map<String, String> map);

    /**
     * 公告 邀请队员里面
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/PublicTell/index")
    @FormUrlEncoded
    Observable<ResponseBody> GetGonggao(@FieldMap Map<String, String> map);

    /**
     * 上传头像
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/uploadAvatar")
    @Multipart
    Observable<BaseJson> uploadAvatar(@Part("fields\";filename=\"avatar.png\"") RequestBody file, @PartMap Map<String, RequestBody> map);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String downParam, @Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> downloadapk(@Url String url);

    @GET("/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D\"北京\")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
    Observable<ResponseBody> getWether();
}
