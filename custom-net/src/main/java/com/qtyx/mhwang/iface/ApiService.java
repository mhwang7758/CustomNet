package com.qtyx.mhwang.iface;

import com.qtyx.mhwang.netprotocol.CommonSubmitOrder;
import com.qtyx.mhwang.netprotocol.CouponStatus;
import com.qtyx.mhwang.netprotocol.CouponVerification;
import com.qtyx.mhwang.netprotocol.CouponWithdraw;
import com.qtyx.mhwang.netprotocol.ErrMsg;
import com.qtyx.mhwang.netprotocol.MakeDone;
import com.qtyx.mhwang.netprotocol.OrderRefund;
import com.qtyx.mhwang.netprotocol.PayStatus;
import com.qtyx.mhwang.netprotocol.UniQueryMemberInfo;
import com.qtyx.mhwang.netprotocol.UniSearchProSkuSale;
import com.qtyx.mhwang.netprotocol.User;

import java.util.Map;

import okhttp3.ResponseBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/25/21 4:04 PM
 * 用途：
 **/
public interface ApiService {
    @POST("orders/couponVerification")
    Call<ResponseBody> couponVerification(@Header ("token") String token, @Body CouponVerification verification);

    @POST("orders/uniSearchProSkuSale")
    Call<ResponseBody> uniSearchProSkuSale(@Header ("token") String token, @Body UniSearchProSkuSale verification);

    @POST("orders/uniQueryMemberInfo")
    Call<ResponseBody> uniQueryMemberInfo(@Header ("token") String token, @Body UniQueryMemberInfo verification);

    @POST("orders/commonSubmitOrder")
    Call<ResponseBody> commonSubmitOrder(@Header ("token") String token,@Body CommonSubmitOrder order);

    // 订单查询
    @POST("orders/queryPayStatus")
    Call<ResponseBody> queryPayStatus(@Header ("token") String token,@Body PayStatus order);

    // 电子券核销
    @POST("orders/couponWithdraw")
    Call<ResponseBody> couponWithdraw(@Header ("token") String token,@Body CouponWithdraw couponWithdraw);

    // 电子券查询
    @POST("orders/couponStatus")
    Call<ResponseBody> couponStatus(@Header ("token") String token,@Body CouponStatus status);

    // 订单退款
    @POST("orders/orderRefund")
    Call<ResponseBody> orderRefund(@Header ("token") String token,@Body OrderRefund status);

    // 登陆
    @POST("coffee/coffeLogin")
    Call<ResponseBody> login(@Body User user);

    // 登陆
    @POST("coffee/products")
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
