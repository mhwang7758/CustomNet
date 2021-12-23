package com.qtyx.mhwang.iface;

import com.qtyx.mhwang.netprotocol.CommonSubmitOrder;
import com.qtyx.mhwang.netprotocol.CouponStatus;
import com.qtyx.mhwang.netprotocol.MicroPay;
import com.qtyx.mhwang.netprotocol.CouponWithdraw;
import com.qtyx.mhwang.netprotocol.ErrMsg;
import com.qtyx.mhwang.netprotocol.MakeDone;
import com.qtyx.mhwang.netprotocol.OrderCreate;
import com.qtyx.mhwang.netprotocol.OrderRefund;
import com.qtyx.mhwang.netprotocol.PayStatus;
import com.qtyx.mhwang.netprotocol.UniQueryMemberInfo;
import com.qtyx.mhwang.netprotocol.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/25/21 4:04 PM
 * 用途：
 **/
public interface ApiService {
    @POST("device/microPay") // 支付
    Call<ResponseBody> microPay(@Body MicroPay verification);

    @POST("device/createOrder")  // 订单生成
    Call<ResponseBody> uniSearchProSkuSale(@Body List<OrderCreate> verification);

    @POST("device/uniStorePayOrder")
    Call<ResponseBody> uniQueryMemberInfo(@Body UniQueryMemberInfo verification);

    @POST("device/createOrder")
    Call<ResponseBody> commonSubmitOrder(@Body CommonSubmitOrder order);

    // 订单查询
    @POST("device/queryPayStatus")
    Call<ResponseBody> queryPayStatus(@Body PayStatus order);

    // 订单撤消
    @POST("device/uniFullClear")
    Call<ResponseBody> couponWithdraw(@Body CouponWithdraw couponWithdraw);

    // 电子券核销状态查询
    @POST("device/uniStorePayOrder")
//    Call<ResponseBody> couponStatus(@Header ("token") String token,@Body CouponStatus status);
    Call<ResponseBody> couponStatus(@Body CouponStatus status);

    // 订单退款
    @POST("device/uniFullClear")
    Call<ResponseBody> orderRefund(@Header ("token") String token,@Body OrderRefund status);

    // 登陆
    @POST("coffee/coffeLogin")
    Call<ResponseBody> login(@Body User user);

    // 获取产品
//    @POST("coffee/products")  旧版
    @POST("device/uniSearchProSkuSale")
    Call<ResponseBody> getProducts();

    // 心跳
    @POST("coffee/heartBeat")
    Call<ResponseBody> heartBeat(@Header ("token") String token);

    // 故障上报
    @POST("coffee/setErrMsg")
    Call<ResponseBody> setErrMsg(@Header ("token") String token,@Body ErrMsg status);

    // 制作完成
    @POST("coffee/makeDone")
    Call<ResponseBody> makeDone(@Header ("token") String token,@Body MakeDone status);
}
