package com.zoomtk.circle.utils;

import android.util.Log;

import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.Interface.ResponseBack;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wbq501 on 2017/10/27.
 */

public class HttpUtils {
    private static String TAG = "com.zoomtk.circle.utils.HttpUtils";

    /**
     * 登录
     *
     * @param parms
     * @param requestBack
     */
    public static void Login(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getLoginCode(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取验证码
     *
     * @param parms
     * @param requestBack
     */
    public static void getYanZhengURL(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getYanZhengURL(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 推荐人
     *
     * @param parms
     * @param requestBack
     */
    public static void checkMobile(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().checkMobile(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 注册
     *
     * @param parms
     * @param requestBack
     */
    public static void registerWshop(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().registerWshop(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 重置密码
     *
     * @param parms
     * @param requestBack
     */
    public static void changeOldPasswd(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().changeOldPasswd(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取好友信息
     *
     * @param parms
     * @param requestBack
     */
    public static void getFriend(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getFriend(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 搜索圈子
     *
     * @param parms
     * @param requestBack
     */
    public static void findCircle(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().findCircle(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取圈子详情
     *
     * @param parms
     * @param requestBack
     */
    public static void circleDetail(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().circleDetail(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 圈子秀
     *
     * @param parms
     * @param requestBack
     */
    public static void talkAboutList(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().talkAboutList(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 圈子秀删除
     *
     * @param parms
     * @param requestBack
     */
    public static void deleteTalkAbout(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().deleteTalkAbout(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 删除评论
     *
     * @param parms
     * @param requestBack
     */
    public static void delComment(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().delComment(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 分享次数
     *
     * @param parms
     * @param requestBack
     */
    public static void incTraceShareCount(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().incTraceShareCount(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 圈子秀点赞
     *
     * @param parms
     * @param requestBack
     */
    public static void talkAboutLike(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().talkAboutLike(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 同意拒绝好友请求
     *
     * @param parms
     * @param requestBack
     */
    public static void handleApply(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().handleApply(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取请求列表
     *
     * @param parms
     * @param requestBack
     */
    public static void getApplyList(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getApplyList(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 请求添加好友
     *
     * @param parms
     * @param requestBack
     */
    public static void addFriend(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().addFriend(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取好友列表
     *
     * @param parms
     * @param requestBack
     */
    public static void getFriends(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getFriends(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 搜索好友
     *
     * @param parms
     * @param requestBack
     */
    public static void searchPeople(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().searchPeople(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 加入的圈子
     *
     * @param parms
     * @param requestBack
     */
    public static void getJoinCircle(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getJoinCircle(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 创建群聊天
     *
     * @param parms
     * @param requestBack
     */
    public static void createGroupChat(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().createGroupChat(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取所有群
     *
     * @param parms
     * @param requestBack
     */
//    public static void getGroupList(Map<String, String> parms, final RequestBack requestBack) {
//        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getGroupList(getRequestparams(parms));
//        POST(loginCode, requestBack);
//    }

    /**
     * 登录要访问的红包接口
     *
     * @param parms
     * @param requestBack
     */
    public static void sendRedpack(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().sendRedpack(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取融云token
     *
     * @param parms
     * @param requestBack
     */
    public static void getChatToken(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getChatToken(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取用户信息
     *
     * @param parms
     * @param requestBack
     */
    public static void getMemberInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getMemberInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取附近门店
     *
     * @param parms
     * @param requestBack
     */
    public static void getOfflineOnline(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getOfflineOnline(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * @param parms
     * @param requestBack
     */
    public static void getPayCallBackUrl(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getPayCallBackUrl(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取分享信息
     *
     * @param parms
     * @param requestBack
     */
    public static void getShareContent(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getShareContent(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取附近门店
     *
     * @param parms
     * @param requestBack
     */
    public static void getStoresAddressList(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getStoresAddressList(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取app会员地址
     *
     * @param parms
     * @param requestBack
     */
    public static void getAddressList(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getAddressList(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 删除地址
     *
     * @param parms
     * @param requestBack
     */
    public static void delMemberInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().delMemberInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 新增地址
     *
     * @param parms
     * @param requestBack
     */
    public static void addMemberInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().addMemberInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 修改地址
     *
     * @param parms
     * @param requestBack
     */
    public static void editMemberInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().editMemberInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 新的分享
     *
     * @param parms
     * @param requestBack
     */
    public static void getShareParameters(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getShareParameters(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 邀请队员 分享
     *
     * @param parms
     * @param requestBack
     */
    public static void getStoreShare(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getStoreShare(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 邀请合伙人 获取分享链接
     *
     * @param parms
     * @param requestBack
     */
    public static void appShareLink(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().appShareLink(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 我的团队
     *
     * @param parms
     * @param requestBack
     */
    public static void getDistributorsList(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getDistributorsList(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * menu 右上角的数字
     *
     * @param parms
     * @param requestBack
     */
    public static void getOpenStoreCount(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getOpenStoreCount(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 我的头像电话等...
     *
     * @param parms
     * @param requestBack
     */
    public static void myMenu(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().myMenu(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 店铺信息
     * @param parms
     * @param requestBack
     */
    public static void storeManage(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().storeManage(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取用户信息
     *
     * @param parms
     * @param requestBack
     */
    public static void getWshopInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getWshopInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取web登录授权码
     *
     * @param parms
     * @param requestBack
     */
    public static void getLoginAuthCode(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getLoginAuthCode(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 我的团队
     *
     * @param parms
     * @param requestBack
     */
    public static void myDistributor(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().myDistributor(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 提示信息
     *
     * @param parms
     * @param requestBack
     */
    public static void getPrompt(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getPrompt(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * PK榜
     *
     * @param parms
     * @param requestBack
     */
    public static void getAgentTeamPkUrl(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getAgentTeamPkUrl(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 分销商订单列表
     *
     * @param parms
     * @param requestBack
     */
    public static void getTeamMemberOrders(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getTeamMemberOrders(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 生成子店铺
     *
     * @param parms
     * @param requestBack
     */
    public static void batchRegisterChild(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().batchRegisterChild(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 店铺二维码
     *
     * @param parms
     * @param requestBack
     */
    public static void scanCode(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().scanCode(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 本月收入
     *
     * @param parms
     * @param requestBack
     */
    public static void wshopMonthIncome(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().wshopMonthIncome(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 可申请提现佣金
     *
     * @param parms
     * @param requestBack
     */
    public static void myBrokerage(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().myBrokerage(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 提现申请
     *
     * @param parms
     * @param requestBack
     */
    public static void withdrawnApply(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().withdrawnApply(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 佣金订单列表
     *
     * @param parms
     * @param requestBack
     */
    public static void getList(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getList(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 佣金详情
     *
     * @param parms
     * @param requestBack
     */
    public static void getInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 重新发起提现申请
     *
     * @param parms
     * @param requestBack
     */
    public static void restartBrokerageWithdraw(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().restartBrokerageWithdraw(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 佣金详情确定
     *
     * @param parms
     * @param requestBack
     */
    public static void confirm(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().confirm(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 提现账号保存
     *
     * @param parms
     * @param requestBack
     */
    public static void operateWithdrawalAccount(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().operateWithdrawalAccount(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 获取银行卡信息
     *
     * @param parms
     * @param requestBack
     */
    public static void getBankAccountInfo(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getBankAccountInfo(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 保存银行卡信息
     *
     * @param parms
     * @param requestBack
     */
    public static void saveBankAccount(Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().saveBankAccount(getRequestparams(parms));
        POST(loginCode, requestBack);
    }

    /**
     * 检查更新
     *
     * @param parms
     * @param requestBack
     */
    public static void getSoftVersion(Map<String, String> parms, final ResponseBack requestBack) {
        Observable<ResponseBody> loginCode = RetrofitFactory.getInstence().API().getSoftVersion(getRequestparams(parms));
        POSTS(loginCode, requestBack);
    }

    /**
     *
     *
     * @param parms
     * @param responseBack
     */
    public static void userstoreManage(Map<String, String> parms, final ResponseBack responseBack) {
        Observable<ResponseBody> userstoreManage = RetrofitFactory.getInstence().API().userstoreManage(getRequestparams(parms));
        POSTS(userstoreManage, responseBack);
    }

    /**
     *
     *
     * @param parms
     * @param responseBack
     */
    public static void getGroupList(Map<String, String> parms, final ResponseBack responseBack) {
        Observable<ResponseBody> groupList = RetrofitFactory.getInstence().API().getGroupList(getRequestparams(parms));
        POSTS(groupList, responseBack);
    }

    /**
     * 公告 邀请队员
     *
     * @param parms
     * @param requestBack
     */
    public static void GetGonggao(Map<String, String> parms, final RequestBack requestBack) {

    }

    /**
     * 上传头像
     *
     * @param parms
     * @param requestBack
     */
    public static void uploadAvatar(RequestBody file, Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().uploadAvatar(file, getFileRequestparams(parms));
        POST(loginCode, requestBack);
    }



    private static void POST(Observable<BaseJson> loginCode, final RequestBack requestBack) {
        loginCode.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseJson>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                BaseLog.LogD("onSubscribe", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull BaseJson result) {
                try {
                    requestBack.success(result);
                } catch (Exception e) {
                    requestBack.error(e.getMessage() + "");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (BaseConfig.isError) {
                    requestBack.error(e.getMessage() + "");
                } else {
                    requestBack.error(BaseConfig.TOEAST);
                }
            }

            @Override
            public void onComplete() {
                BaseLog.LogD("onComplete", "onComplete");
            }
        });
    }

    private static void POSTS(Observable<ResponseBody> loginCode, final ResponseBack responseBack) {
        loginCode.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                BaseLog.LogD("onSubscribe", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull ResponseBody result) {
                try {
                    responseBack.success(result.string());
                } catch (Exception e) {
                    responseBack.error(e.getMessage() + "");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (BaseConfig.isError) {
                    responseBack.error(e.getMessage() + "");
                } else {
                    responseBack.error(BaseConfig.TOEAST);
                }
            }

            @Override
            public void onComplete() {
                BaseLog.LogD("onComplete", "onComplete");
            }
        });
    }

    public static Map<String, String> getRequestparams(Map<String, String> parm) {
        Map<String, String> parms = AjaxParamUtil.getSuitParam(parm);
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(parms.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().toString().compareTo(rhs.getKey());
            }
        });
        final TreeMap<String, String> requestParams = new TreeMap<String, String>();
        requestParams.put("sign", parms.get("sign"));
        for (int i = 0; i < infoIds.size(); i++) {
            if (!infoIds.get(i).getKey().equals("sign")) {
                requestParams.put(infoIds.get(i).getKey(), infoIds.get(i).getValue());
            }
        }
        return requestParams;
    }

    private static Map<String, RequestBody> getFileRequestparams(Map<String, String> parm) {
        Map<String, String> parms = AjaxParamUtil.getSuitParam(parm);
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(parms.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().toString().compareTo(rhs.getKey());
            }
        });
        final TreeMap<String, RequestBody> requestParams = new TreeMap<>();
        requestParams.put("sign", RequestBody.create(MediaType.parse("text/plain"), parms.get("sign")));
        for (int i = 0; i < infoIds.size(); i++) {
            if (!infoIds.get(i).getKey().equals("sign")) {
                requestParams.put(infoIds.get(i).getKey(), RequestBody.create(MediaType.parse("text/plain"), infoIds.get(i).getValue()));
            }
        }
        return requestParams;
    }

    public static void getWether() {
        Observable<ResponseBody> loginCode = RetrofitFactory.getInstence().API().getWether();
        loginCode.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    Log.e(TAG, "onNext: " + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onNext: " );
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
