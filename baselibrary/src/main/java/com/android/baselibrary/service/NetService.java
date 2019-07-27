package com.android.baselibrary.service;
import com.android.baselibrary.base.BaseApplication;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.UpdateBean;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.service.bean.find.FindBean;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeDataBean;
import com.android.baselibrary.service.bean.home.StarDataBean;
import com.android.baselibrary.service.bean.mine.ExtensionBean;
import com.android.baselibrary.service.bean.mine.MyHistoryBean;
import com.android.baselibrary.service.bean.mine.MyPromoteBean;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.service.bean.mine.PayRecharegeBean;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.android.baselibrary.service.bean.mine.WithDrawBean;
import com.android.baselibrary.service.bean.search.SearchBean;
import com.android.baselibrary.service.bean.user.ProtocolBean;
import com.android.baselibrary.service.bean.user.UserBean;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;
import com.android.baselibrary.service.http.RequestHelper;
import com.android.baselibrary.service.http.RetrofitManager;
import com.android.baselibrary.service.request.DeviceRequest;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.util.GetDevicedIDUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.schedulers.Schedulers;

import com.android.baselibrary.service.bean.user.LoginBean;
import com.google.gson.Gson;

/**
 * Created by yongqianggeng on 2017/11/28.
 * 服务端接口
 */

public class NetService {

    private NetApi netApi;
    private RequestHelper requestHelper;
    public static NetService instance;


    public static NetService getInstance() {
        synchronized (NetService.class) {
            if (instance == null) {
                instance = new NetService();
            }
        }
        return instance;
    }

    public NetService() {

        this.requestHelper = new RequestHelper(UserStorage.getInstance());
        this.netApi = RetrofitManager.getInstance().getRetrofit().create(NetApi.class);
    }

    /**
     * 1.设备信息接口-S-0930-第一个接口
     */
    public Observable<LoginBean> deviceInfo() {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("deviceCode", GetDevicedIDUtil.getUniquePsuedoID());
        paramsMap.put("fromCode", Constants.FROM_CODE);
        paramsMap.put("versionType", "2");
        paramsMap.put("version", BaseApplication.getInstance().getAppVersionName());
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.deviceInfo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 1.设备信息接口-S-0930-第一个接口
     */
    public Observable<LoginBean> deviceInfo2(String token) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("token", token);
        paramsMap.put("versionType", "2");
        paramsMap.put("version", BaseApplication.getInstance().getAppVersionName());
        paramsMap.put("deviceCode", GetDevicedIDUtil.getUniquePsuedoID());
        paramsMap.put("fromCode", Constants.FROM_CODE);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.deviceInfo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 2.个人信息接口-S
     */
    public Observable<UserBean> getMemberInfo() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getMemberInfo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 3.获取短信验证码接口-S
     */
    public Observable<BaseBean> getSmsCode(String tel,String type) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("tel",tel);
        paramsMap.put("type",type);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getSmsCode(body).subscribeOn(Schedulers.io());
    }

    /**
     * 4.注册接口-S-0928
     */
    public Observable<LoginBean>regedit(String tel,String smsCode,String pwd,String extensionCode) {
        HashMap<String,String> paramsMap = new HashMap<>();
        if (extensionCode != null) {
            paramsMap.put("extensionCode",extensionCode);
        }
        paramsMap.put("pwd",pwd);
        paramsMap.put("smsCode",smsCode);
        paramsMap.put("tel",tel);
        paramsMap.put("deviceCode", GetDevicedIDUtil.getUniquePsuedoID());
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.regedit(body).subscribeOn(Schedulers.io());
    }

    /**
     * 5.登录接口-S-0930
     */
    public Observable<LoginBean> login(String tel,String pwd) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("pwd",pwd);
        paramsMap.put("tel",tel);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.login(body).subscribeOn(Schedulers.io());
    }

    /**
     * 6.首页接口-S
     */
    public Observable<HomeDataBean> indexInfo() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.indexInfo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 7.发现视频列表接口-S
     */
    public Observable<FindBean> getFindVideo(int pageNum) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("pageNum",String.valueOf(pageNum));
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getFindVideo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 8.忘记密码-S
     */
    public Observable<LoginBean> loseTel(String tel,String smsCode,String pwd) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("pwd",pwd);
        paramsMap.put("smsCode",smsCode);
        paramsMap.put("tel",tel);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.loseTel(body).subscribeOn(Schedulers.io());
    }

    /**
     * 9.用户协议-S
     */
    public Observable<ProtocolBean> getProtocol() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getProtocol(body).subscribeOn(Schedulers.io());
    }

    /**
     * 10.频道页面-S
     */
    public Observable<ChannelDataBean> channelInfo() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.channelInfo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 11.根据明星ID、分类、标签获取影片列表-S
     * mostCare 最多喜欢
     * newVideo 最新播放
     * mostPlay 最多播放
     * starId	否	String	明星ID
     classifyId	否	String	分类ID
     tagId	否	String	标签ID
     tagIds	否	String	标签ID集合，多个标签以逗号分隔
     pageNum	否	int	页数
     tagName	否	String	标签名称
     */
    public Observable<DetailListBean> getVideoByStarId(HashMap<String,String> paramsMap) {
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getVideoByStarId(body).subscribeOn(Schedulers.io());
    }

    /**
     * 12.明星列表-S-0930
     */
    public Observable<StarDataBean> getStarPage(HashMap<String,String> paramsMap) {
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getStarPage(body).subscribeOn(Schedulers.io());
    }

    /**
     * 13.获取搜索的关键词-S-10-1
     */
    public Observable<SearchBean> searchSearchName() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.searchSearchName(body).subscribeOn(Schedulers.io());
    }

    /**
     * 14.发现搜索接口-S-10.1
     */
    public Observable<DetailListBean> selectOPenVideo(String searchName) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("searchName",searchName);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.selectOPenVideo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 15.标签类型接口-S
     */
    public Observable<TagClassBean> selectTagType() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.selectTagType(body).subscribeOn(Schedulers.io());
    }

    /**
     * 16.标签类型接口-S
     */
    public Observable<ChannelTagDataBean> selectTagsByType(int id) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("id",String.valueOf(id));
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.selectTagsByType(body).subscribeOn(Schedulers.io());
    }

    /**
     * 17.视频详情接口-S-0930
     */
    public Observable<VideoDetailBean> getVideoDetail(String videoId) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("videoId",videoId);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getVideoDetail(body).subscribeOn(Schedulers.io());
    }

    /**
     * 18.点赞、反对接口-S
     */
    public Observable<BaseBean> setCareTimes(String videoId,String careType) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("videoId",videoId);
        paramsMap.put("careType",careType);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.setCareTimes(body).subscribeOn(Schedulers.io());
    }

    /**
     * 19.提交失效次数接口-S
     */
    public Observable<BaseBean> saveloseVideo(String videoId) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("videoId",videoId);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.saveloseVideo(body).subscribeOn(Schedulers.io());
    }

    /**
     * 20.点爱心接口-S
     */
    public Observable<BaseBean> setCareHistory(String videoId) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("videoId",videoId);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.setCareHistory(body).subscribeOn(Schedulers.io());
    }

    /**
     * 21.视频评论列表接口-S
     */
    public Observable<VideoCommentBean> getVideoCommon(String videoId) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("videoId",videoId);
        paramsMap.put("lastNew","1");
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getVideoCommon(body).subscribeOn(Schedulers.io());
    }

    /**
     * 22.修改头像接口-S
     */
    public Call<ResponseBody> saveMemberPic(String path) {
        File file = new File(path);
        //多个文件上传(已此为标准)  文件的时候item.isFormField()=false
        MultipartBody.Builder form = new MultipartBody.Builder();
        form.setType(MultipartBody.FORM);
        //文件
        form.addFormDataPart("picFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        //参数
        Map<String, String> params = new HashMap<>();
        for (String key : params.keySet()) {
            form.addFormDataPart(key, params.get(key));
        }
        return netApi.saveMemberPic(form.build());
    }

    /**
     * 23.提交评论接口
     */
    public Observable<BaseBean> saveVideoCommon(String videoId,String comContent) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("videoId",videoId);
        paramsMap.put("comContent",comContent);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.saveVideoCommon(body).subscribeOn(Schedulers.io());
    }

    /**
     * 24.获取推广二维码
     */
    public Observable<ExtensionBean> getExtensionCode() {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("type","2");
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getExtensionCode(body).subscribeOn(Schedulers.io());
    }

    /**
     * 25.修改用户信息-性别
     */
    public Observable<BaseBean> saveMemberInfoSex(String sex) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("sex",sex);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.saveMemberInfoSex(body).subscribeOn(Schedulers.io());
    }

    /**
     * 26.修改用户信息-姓名
     */
    public Observable<BaseBean> saveMemberInfoName(String name) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("nickName",name);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.saveMemberInfoName(body).subscribeOn(Schedulers.io());
    }

    /**
     * 27.VIP会员卡列表-S
     */
    public Observable<VipBean> getVipList() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getVipList(body).subscribeOn(Schedulers.io());
    }

    /**
     * 28.支付方式查询接口-S
     */
    public Observable<PayBean> getPayType() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getPayType(body).subscribeOn(Schedulers.io());
    }

    /**
     * 29.支付接口，调用支付接口，返回二维码路径
     */
    public Observable<PayRecharegeBean> payRecharge(String vipId,String payType,String selectType) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("vipId",vipId);
        paramsMap.put("payType",payType);
        paramsMap.put("selectType",selectType);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.payRecharge(body).subscribeOn(Schedulers.io());
    }

    /**
     * 30.充值状态查询-S
     */
    public Observable<BaseBean> getPayStatus(String tradeNo) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("tradeNo",tradeNo);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getPayStatus(body).subscribeOn(Schedulers.io());
    }

    /**
     * 31.我的推广记录接口-S
     */
    public Observable<MyPromoteBean> getExtensionHistory() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getExtensionHistory(body).subscribeOn(Schedulers.io());
    }

    /**
     * 32.版本控制接口-S
     */
    public Observable<UpdateBean> getVersionNew() {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("type","2");
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getVersionNew(body).subscribeOn(Schedulers.io());
    }

    /**
     * 33.查看我的浏览记录-S
     */
    public Observable<MyHistoryBean> getMemberViewHistoryMore() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getMemberViewHistoryMore(body).subscribeOn(Schedulers.io());
    }

    /**
     * 34.查询我的喜欢记录数据接口-S
     */
    public Observable<MyHistoryBean> getMemberCareHistoryMore() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getMemberCareHistoryMore(body).subscribeOn(Schedulers.io());
    }

    /**
     * 35.扣减观影和缓存次数可用次数接口
     */
    public Observable<BaseBean> usedViewOrCacheNum(String type,String videoId) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("type",type);
        paramsMap.put("videoId",videoId);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.usedViewOrCacheNum(body).subscribeOn(Schedulers.io());
    }

    /**
     * 36.我的访问记录删除接口-S
     */
    public Observable<BaseBean> deleteViewHistory(String ids) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("ids",ids);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.deleteViewHistory(body).subscribeOn(Schedulers.io());
    }

    /**
     * 37.我的喜欢删除接口
     */
    public Observable<BaseBean> deleteCareHistory(String ids) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("ids",ids);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.deleteCareHistory(body).subscribeOn(Schedulers.io());
    }

    /**
     * 38.code 新增
     */
    public Observable<BaseBean> saveQrcode() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.saveQrcode(body).subscribeOn(Schedulers.io());
    }

    /**
     * 39.code 新增
     */
    public Observable<BaseBean> clickAd(String id) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("id",id);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.clickAd(body).subscribeOn(Schedulers.io());
    }

    /**
     * 40.校验缓存次数
     */
    public Observable<BaseBean> getCacheNum(String videoId) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("type","2");
        paramsMap.put("videoId",videoId);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getCacheNum(body).subscribeOn(Schedulers.io());
    }

    /**
     * 41.充值码充值
     */
    public Observable<BaseBean> useRechargeCode(String rechargeCode) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("rechargeCode",rechargeCode);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.useRechargeCode(body).subscribeOn(Schedulers.io());
    }

    /**
     * 42.获取提现信息接口
     */
    public Observable<WithDrawBean> getApplyMemberCron() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getApplyMemberCron(body).subscribeOn(Schedulers.io());
    }


    /**
     * 43.提现申请接口
     */
    public Observable<BaseBean> submitApplyCron(String name,String cardNo,String price) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("name",name);
        paramsMap.put("cardNo",cardNo);
        paramsMap.put("price",price);
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.submitApplyCron(body).subscribeOn(Schedulers.io());
    }

    /**
     * 44.我的收益
     */
    public Observable<MyPromoteBean> myincome() {
        HashMap<String,String> paramsMap = new HashMap<>();
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.myincome(body).subscribeOn(Schedulers.io());
    }

    /**
     * 45.通知列表
     */
    public Observable<NotificationBean> getNoticeList(int pageNum) {
        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("pageNum",String.valueOf(pageNum));
        RequestBody body = this.requestHelper.getHttpRequestMap(paramsMap);
        return netApi.getNoticeList(body).subscribeOn(Schedulers.io());
    }
}