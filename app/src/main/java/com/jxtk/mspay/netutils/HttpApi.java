package com.jxtk.mspay.netutils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 眼神 on 2018/3/27.
 * <p>
 * 存放所有的Api
 */

public interface HttpApi {
    //获取首页资源
    @POST("api/index/index")
    Observable<ResponseBody> getbanner(@Query("token") String token);

    //获取商家首页资源
    @POST("/api/index/shopindex")
    Observable<ResponseBody> shopindex(@Query("token") String token,@Query("time") String time);
    //获取充值列表
    @POST("/api/money/money_view")
    Observable<ResponseBody> getchargelist(@Query("token") String token);

    //添加地址
    @POST("/api/user/add_address")
    @FormUrlEncoded
    Observable<ResponseBody> addAddress(@FieldMap Map<String, String> map);

    //修改地址
    @POST(" /api/user/edit_address")
    @FormUrlEncoded
    Observable<ResponseBody> updateAddress(@FieldMap Map<String, String> map);

    //获取地址
    @POST("/api/user/address")
    Observable<ResponseBody> getaddress(@Query("token") String token);

    //获取发票类型
    @POST("/api/invoice/invoice_type")
    Observable<ResponseBody> getinvoice_type(@Query("token") String token);

    //添加发票
    @POST("/api/invoice/add_invoice")
    @FormUrlEncoded
    Observable<ResponseBody> addinvoce(@FieldMap Map<String, String> map);

    //获取发票信息
    @POST("/api/invoice/list")
    Observable<ResponseBody> getinvoice(@Query("token") String token);

    //获取积分列表
    @POST("/api/integral/detailed")
    @FormUrlEncoded
    Observable<ResponseBody> getintegral(@FieldMap Map<String, String> map);

    //积分购买会员
    @POST("/api/integral/upgrade")
    Observable<ResponseBody> upgrade(@Query("token") String token);
    //M币购买会员
    @POST("/api/money/money_upgrade")
    Observable<ResponseBody> money_upgrade(@Query("token") String token);


    //获取单页面内容
    @POST("/api/setup/service")
    Observable<ResponseBody> service(@Query("token") String token,@Query("article_id") String article_id,@Query("filter") String filter);


    //发送短信验证码
    @POST("/api/login/send_message")
    @FormUrlEncoded
    Observable<ResponseBody> send_message(@FieldMap Map<String, String> map);

    //用户登陆
    @POST("/api/login/app_login")
    @FormUrlEncoded
    Observable<ResponseBody> app_login(@FieldMap Map<String, String> map);

    //设置支付密码
    @POST("/api/user/pay_password")
    @FormUrlEncoded
    Observable<ResponseBody> setpaypassword(@FieldMap Map<String, String> map);
    //设置登陆密码
    @POST("/api/user/login_password")
    @FormUrlEncoded
    Observable<ResponseBody> setpassword(@FieldMap Map<String, String> map);


    //获取M币余额
    @POST("/api/money/user_money")
    Observable<ResponseBody> getM(@Query("token") String token);

    //获取个人信息
    @POST("/api/user/index")
    Observable<ResponseBody> getUserInfo(@Query("token") String token);

    //获取未读消息数目
    @POST("/api/message/count")
    Observable<ResponseBody> getMessage(@Query("token") String token);

    //获取消息列表
    @POST("/api/message/gets")
    @FormUrlEncoded
    Observable<ResponseBody> getMessageList(@FieldMap Map<String, String> map);

    //获取账单列表
    @POST("/api/order/consumption")
    @FormUrlEncoded
    Observable<ResponseBody> getBillList(@FieldMap Map<String, String> map);

    //获取单页面内容
    @POST("/api/money/refund")
    Observable<ResponseBody> refund(@Query("token") String token,@Query("id") String id,@Query("memo") String memo);


    //获取发票列表
    @POST("/api/invoice/invoice")
    @FormUrlEncoded
    Observable<ResponseBody> getInvoiceList(@FieldMap Map<String, String> map);

    //获取未读消息数目
    @POST("/api/task/gets")
    Observable<ResponseBody> getTask(@Query("token") String token);

    //扫码获取信息
    @POST("/api/money/pay_view")
    @FormUrlEncoded
    Observable<ResponseBody> getCodeInfo(@FieldMap Map<String, String> map);


    //扫码获取第三方信息
    @POST("/mpay")
    Observable<ResponseBody> getThreeCodeInfo(@QueryMap Map<String, String> map);

    @POST("/api/message/setRead")
    @FormUrlEncoded
    Observable<ResponseBody> setRead(@FieldMap Map<String, String> map);

    //获取支付宝支付参数
    @POST("/api/app_pay/app_pay")

    Observable<ResponseBody> getpaystring(@Query("token") String token,@Query("pay_amount") String pay_password,@Query("source") String source,@Query("type") String type);


    //获取签到情况
    @POST("/api/user/sign_view")
    Observable<ResponseBody> getsignin(@Query("token") String token);

    //签到
    @POST("/api/user/sign")
    Observable<ResponseBody> signin(@Query("token") String token);


    //获取邀请好友数据
    @POST("/api/user/invitation_view")
    Observable<ResponseBody> invitation_view(@Query("token") String token);

    //修改支付密码发送短信
    @POST("/api/setup/send_message")
    Observable<ResponseBody> send_message(@Query("token") String token,@Query("mobile") String mobile);


    //验证短信验证码
    @POST("/api/user/appcode")
    Observable<ResponseBody> appcode(@Query("token") String token);

    //验证支付密码
    @POST("/api/user/verification_password")
    Observable<ResponseBody> verification_password(@Query("token") String token,@Query("pay_password") String pay_password);

    //修改支付密码
    @POST("/api/setup/edit_pay_password")
    @FormUrlEncoded
    Observable<ResponseBody> edit_pay_password(@FieldMap Map<String, String> map);

    //验证短信验证码
    @POST("/api/setup/check_captcha")
    @FormUrlEncoded
    Observable<ResponseBody> check_captcha(@FieldMap Map<String, String> map);

    //会员支付
    @POST("/api/money/user_shop")
    @FormUrlEncoded
    Observable<ResponseBody> codepay(@FieldMap Map<String, String> map);

    //会员买单
    @POST("/api/shop/store")
    @FormUrlEncoded
    Observable<ResponseBody> store(@FieldMap Map<String, String> map);
    //帮助中心首页
    @POST("/api/setup/help")
    Observable<ResponseBody> help(@Query("token") String token);
//按主题获取帮助列表
    @POST("/api/setup/help_article")
    Observable<ResponseBody> help_article(@Query("token") String token,@Query("category_id") String category_id);

    //获取商户列表
    @POST("/api/shop/shop_list")
    @FormUrlEncoded
    Observable<ResponseBody> shop_list(@FieldMap Map<String, String> map);


    //索取发票
    @POST("/api/invoice/ask_invoice")
    @FormUrlEncoded
    Observable<ResponseBody> ask_invoice(@FieldMap Map<String, String> map);
    //修改个人信息
    @POST("/api/user/edit_user")
    @FormUrlEncoded
    Observable<ResponseBody> edit_user(@FieldMap Map<String, String> map);


    //添加银行卡
    @POST("/api/shopper_account/create")
    @FormUrlEncoded
    Observable<ResponseBody> createbank(@FieldMap Map<String, String> map);


    //获取银行卡
    @POST("/api/shopper_account/get")
    Observable<ResponseBody> getbank(@Query("token") String token);

    //获取费率
    @POST("/api/shopper_account/cashOutView")
    Observable<ResponseBody> cashOutView(@Query("token") String token);

    //申请提现
    @POST("/api/shopper_account/cashOutApply")
    Observable<ResponseBody> cashOutApply(@Query("token") String token,@Query("amount") String amount);

    //
    //修改银行卡
    @POST("/api/shopper_account/update")
    @FormUrlEncoded
    Observable<ResponseBody> updatebank(@FieldMap Map<String, String> map);

    //商户处理退款
    @POST("/api/money/apply")
    @FormUrlEncoded
    Observable<ResponseBody> dealRefund(@FieldMap Map<String, String> map);

    /**
     * 律师回复咨询文字
     */
    @POST("lawyer/userToMember")
    Observable<ResponseBody> userToMember(@Query("token") String token, @Query("user_id") String user_id,
                                          @Query("chat_type") String chat_type, @Query("id") String id,
                                          @Query("content") String content);


    /**
     * 通过地址下载一个文件
     */
    @GET()
    @Streaming
    Call<ResponseBody> downloadFile(@Url String fileUrl);


    //更新用户头像
    @POST("lawyer/updateAvatr")
    @Multipart
    Observable<ResponseBody> updateAvatr(@Part MultipartBody.Part part, @Query("token") String token, @Query("user_id") String user_id);


}
