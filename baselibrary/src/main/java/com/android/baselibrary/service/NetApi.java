package com.android.baselibrary.service;

import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.bean.UpdateBean;
import com.android.baselibrary.service.bean.channel.ChannelDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTagBean;
import com.android.baselibrary.service.bean.channel.ChannelTagDataBean;
import com.android.baselibrary.service.bean.channel.ChannelTypeBean;
import com.android.baselibrary.service.bean.channel.TagClassBean;
import com.android.baselibrary.service.bean.find.FindBean;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeDataBean;
import com.android.baselibrary.service.bean.home.StarDataBean;
import com.android.baselibrary.service.bean.mine.ExtensionBean;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.service.bean.mine.MyHistoryBean;
import com.android.baselibrary.service.bean.mine.MyPromoteBean;
import com.android.baselibrary.service.bean.mine.NotificationBean;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.service.bean.mine.PayRecharegeBean;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.android.baselibrary.service.bean.mine.WithDrawBean;
import com.android.baselibrary.service.bean.search.SearchBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.service.bean.user.ProtocolBean;
import com.android.baselibrary.service.bean.user.UserBean;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;
import com.android.baselibrary.service.request.DeviceRequest;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yongqianggeng on 2017/11/28.
 *
 */

public interface NetApi {

    /**
     * 1.设备信息接口-S-0930-第一个接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/deviceInfo")
    Observable<LoginBean> deviceInfo(@Body RequestBody route);

    /**
     * 1.设备信息接口-S-0930-第一个接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/deviceInfo")
    Observable<LoginBean> deviceInfo2(@Body RequestBody route);

    /**
     * 2.个人信息接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getMemberInfo")
    Observable<UserBean> getMemberInfo(@Body RequestBody route);

    /**
     * 3.获取短信验证码接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/getSmsCode")
    Observable<BaseBean> getSmsCode(@Body RequestBody route);

    /**
     * 4.注册接口-S-0928
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/regedit")
    Observable<LoginBean> regedit(@Body RequestBody route);

    /**
     * 5.登录接口-S-0930
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/login")
    Observable<LoginBean> login(@Body RequestBody route);

    /**
     * 6.首页接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/indexInfo")
    Observable<HomeDataBean> indexInfo(@Body RequestBody route);

    /**
     * 7.发现视频列表接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getFindVideo")
    Observable<FindBean> getFindVideo(@Body RequestBody route);

    /**
     * 8.忘记密码-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/loseTel")
    Observable<LoginBean> loseTel(@Body RequestBody route);

    /**
     * 9.用户协议-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/getProtocol")
    Observable<ProtocolBean> getProtocol(@Body RequestBody route);

    /**
     * 10.频道页面-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/channelInfo")
    Observable<ChannelDataBean> channelInfo(@Body RequestBody route);

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
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/getVideoByStarId")
    Observable<DetailListBean> getVideoByStarId(@Body RequestBody route);

    /**
     * 12.明星列表-S-0930
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/getStarPage")
    Observable<StarDataBean> getStarPage(@Body RequestBody route);

    /**
     * 13.获取搜索的关键词-S-10-1
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/searchSearchName")
    Observable<SearchBean> searchSearchName(@Body RequestBody route);

    /**
     * 14.发现搜索接口-S-10.1
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/selectOPenVideo")
    Observable<DetailListBean> selectOPenVideo(@Body RequestBody route);

    /**
     * 15.标签类型接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/selectTagType")
    Observable<TagClassBean> selectTagType(@Body RequestBody route);

    /**
     * 16.标签类型接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/selectTagsByType")
    Observable<ChannelTagDataBean> selectTagsByType(@Body RequestBody route);

    /**
     * 17.视频详情接口-S-0930
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getVideoDetail")
    Observable<VideoDetailBean> getVideoDetail(@Body RequestBody route);

    /**
     * 18.点赞、反对接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/setCareTimes")
    Observable<BaseBean> setCareTimes(@Body RequestBody route);

    /**
     * 19.提交失效次数接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/saveloseVideo")
    Observable<BaseBean> saveloseVideo(@Body RequestBody route);

    /**
     * 20.点爱心接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/setCareHistory")
    Observable<BaseBean> setCareHistory(@Body RequestBody route);

    /**
     * 21.视频评论列表接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getVideoCommon")
    Observable<VideoCommentBean> getVideoCommon(@Body RequestBody route);

    /**
     * 22.修改头像接口-S
     */
    @POST("ying/saveMemberPic")
    Call<ResponseBody> saveMemberPic(@Body MultipartBody body);

    /**
     * 23.提交评论接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/saveVideoCommon")
    Observable<BaseBean> saveVideoCommon(@Body RequestBody route);

    /**
     * 24.获取推广二维码
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getExtensionCode")
    Observable<ExtensionBean> getExtensionCode(@Body RequestBody route);

    /**
     * 25.修改用户信息-性别
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/saveMemberInfo")
    Observable<BaseBean> saveMemberInfoSex(@Body RequestBody route);

    /**
     * 26.修改用户信息-姓名
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/saveMemberInfo")
    Observable<BaseBean> saveMemberInfoName(@Body RequestBody route);

    /**
     * 27.VIP会员卡列表-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getVipList")
    Observable<VipBean> getVipList(@Body RequestBody route);

    /**
     * 28.支付方式查询接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/getPayType")
    Observable<PayBean> getPayType(@Body RequestBody route);

    /**
     * 29.支付接口，调用支付接口，返回二维码路径
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/payRecharge")
    Observable<PayRecharegeBean> payRecharge(@Body RequestBody route);

    /**
     * 30.充值状态查询-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getPayStatus")
    Observable<BaseBean> getPayStatus(@Body RequestBody route);

    /**
     * 31.我的推广记录接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getExtensionHistory")
    Observable<MyPromoteBean> getExtensionHistory(@Body RequestBody route);

    /**
     * 32.版本控制接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("openapi/getVersionNew")
    Observable<UpdateBean> getVersionNew(@Body RequestBody route);

    /**
     * 33.查看我的浏览记录-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getMemberViewHistoryMore")
    Observable<MyHistoryBean> getMemberViewHistoryMore(@Body RequestBody route);

    /**
     * 34.查询我的喜欢记录数据接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getMemberCareHistoryMore")
    Observable<MyHistoryBean> getMemberCareHistoryMore(@Body RequestBody route);

    /**
     * 35.扣减观影和缓存次数可用次数接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/usedViewOrCacheNum")
    Observable<BaseBean> usedViewOrCacheNum(@Body RequestBody route);

    /**
     * 36.我的访问记录删除接口-S
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/deleteViewHistory")
    Observable<BaseBean> deleteViewHistory(@Body RequestBody route);

    /**
     * 37.我的喜欢删除接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/deleteCareHistory")
    Observable<BaseBean> deleteCareHistory(@Body RequestBody route);

    /**
     * 38.code 新增
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/saveQrcode")
    Observable<BaseBean> saveQrcode(@Body RequestBody route);


    /**
     * 39.code 新增
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/clickAd")
    Observable<BaseBean> clickAd(@Body RequestBody route);

    /**
     * 40.校验缓存次数
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/valiateViewNum")
    Observable<BaseBean> getCacheNum(@Body RequestBody route);


    /**
     * 41.充值码充值
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/useRechargeCode")
    Observable<BaseBean> useRechargeCode(@Body RequestBody route);

    /**
     * 42.获取提现信息接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/getApplyMemberCron")
    Observable<WithDrawBean> getApplyMemberCron(@Body RequestBody route);


    /**
     * 43.提现申请接口
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/submitApplyCron")
    Observable<BaseBean> submitApplyCron(@Body RequestBody route);

    /**
     * 44.我的收益
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("ying/myincome")
    Observable<MyPromoteBean> myincome(@Body RequestBody route);

    /**
     * 45.通知列表
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(" openapi/getNoticeList")
    Observable<NotificationBean> getNoticeList(@Body RequestBody route);
}

