package com.qtyx.mhwang.util;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qtyx.mhwang.bean.MessageEvent;
import com.qtyx.mhwang.iface.ApiService;
import com.qtyx.mhwang.netprotocol.CommonSubmitOrder;
import com.qtyx.mhwang.netprotocol.CouponStatus;
import com.qtyx.mhwang.netprotocol.CouponWithdraw;
import com.qtyx.mhwang.netprotocol.ErrMsg;
import com.qtyx.mhwang.netprotocol.MakeDone;
import com.qtyx.mhwang.netprotocol.OrderRefund;
import com.qtyx.mhwang.netprotocol.UniQueryMemberInfo;
import com.qtyx.mhwang.netprotocol.UniSearchProSkuSale;
import com.qtyx.mhwang.netprotocol.User;
import com.qtyx.mhwang.service.INetService;
import com.qtyx.mhwang.service.NetServiceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.qtyx.mhwang.constant.NetFiled.AUTH_CODE;
import static com.qtyx.mhwang.constant.NetFiled.BAR_CODE;
import static com.qtyx.mhwang.constant.NetFiled.CODE_POOL;
import static com.qtyx.mhwang.constant.NetFiled.COMMON_SUBMIT_ORDER;
import static com.qtyx.mhwang.constant.NetFiled.COUPON_STATUS;
import static com.qtyx.mhwang.constant.NetFiled.COUPON_VERIFICATION;
import static com.qtyx.mhwang.constant.NetFiled.COUPON_WITHDRAW;
import static com.qtyx.mhwang.constant.NetFiled.COU_NO;
import static com.qtyx.mhwang.constant.NetFiled.GET_ERR_MSG;
import static com.qtyx.mhwang.constant.NetFiled.HEART_BEAT;
import static com.qtyx.mhwang.constant.NetFiled.ID;
import static com.qtyx.mhwang.constant.NetFiled.LOGIN;
import static com.qtyx.mhwang.constant.NetFiled.MAKE_DONE;
import static com.qtyx.mhwang.constant.NetFiled.OFF_LINE_SALE_PRICE;
import static com.qtyx.mhwang.constant.NetFiled.ORDER_ID;
import static com.qtyx.mhwang.constant.NetFiled.ORDER_REFUND;
import static com.qtyx.mhwang.constant.NetFiled.PASSWORD;
import static com.qtyx.mhwang.constant.NetFiled.POS;
import static com.qtyx.mhwang.constant.NetFiled.QR_CODE;
import static com.qtyx.mhwang.constant.NetFiled.QUERY_PAY_STATUS;
import static com.qtyx.mhwang.constant.NetFiled.SKU_CODE;
import static com.qtyx.mhwang.constant.NetFiled.SKU_NAME;
import static com.qtyx.mhwang.constant.NetFiled.UNIT;
import static com.qtyx.mhwang.constant.NetFiled.URL;
import static com.qtyx.mhwang.constant.NetFiled.USER_ID;
import static com.qtyx.mhwang.constant.NetFiled.USER_NAME;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/25/21 3:58 PM
 * 用途：
 **/
@Route(path = NetServiceUtil.PATH_SERVICE)
public class ServiceHelper implements INetService {
    private Retrofit retrofit;
    private ApiService apiService;

    private Queue<String> mStatesQueue;
    private String mLastReport = "";
    private String mTryToReportMsg = "";
    private boolean login = false;
    private static final int MAX_QUEUE_SIZE = 20;
    private static final int MAX_REPORT_COUNT = 8;
    public static String TOKEN;
    private static int brokenCounts = 0;
    private boolean init = false;

    private boolean debug = true;

    public void showLog(boolean show){
        debug = show;
    }

    private void showLog(String msg) {
        if (!debug) return;
        Log.e("--ServerHelper-->", msg);
    }

    public ServiceHelper(){

    }

    private void dispatchMessage(String msg, String cmd, int type){
        dispatchMessage(msg, cmd, type,null);
    }

    private void dispatchMessage(String msg, String cmd, int type, String extra){
        brokenCounts = 0;
        MessageEvent messageEvent = new MessageEvent(msg, cmd);
        messageEvent.setType(type);
        messageEvent.setExtra(extra);
        messageEvent.setMessage(msg);
        EventBus.getDefault().post(messageEvent);
    }

    private void disconnect(){
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setType(MessageEvent.TYPE.UN_CONNECT);
        EventBus.getDefault().post(messageEvent);
    }

    private void msgError(String msg){
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setType(MessageEvent.TYPE.ERR);
        messageEvent.setMessage(msg);
        EventBus.getDefault().post(messageEvent);
    }

    private boolean connect(User user){
        Response<ResponseBody> call = login(user);
        if (call == null){
            return false;
        }
        try {
            String msg = call.body().string();
            showLog("connect-> token:"+msg);
            JsonElement jsonObject = new JsonParser().parse(msg);
            JsonObject object = jsonObject.getAsJsonObject();
            int errNo = object.get("errNo").getAsInt();
//            JSONObject object = new JSONObject(msg);
//            int errNo = object.getInt("errNo");
            if (errNo == 0){
                TOKEN = object.get("data").getAsString();
                showLog("登陆服务器成功-> token:"+TOKEN);
                login = true;
                dispatchMessage(msg, LOGIN, MessageEvent.TYPE.LOGIN);
                startStateReport();
                return true;
            }else{
                showLog(msg);
                msgError(msg);
            }
        }catch (Exception e){
            showLog(e.toString());
            return false;
        }
        return false;
    }

    public boolean isLogin(){
        return login;
    }

    private void dealCallBack(Response<ResponseBody> response, String cmd, int type){
        try {
            String body = response.body().string();
            showLog("receive=>"+response.body().string());
            dispatchMessage(body, cmd, type);
        }catch (Exception e){
            showLog(e.toString());
        }
    }

    private void dealCallBack(Response<ResponseBody> response, String cmd){
        dealCallBack(response, cmd, MessageEvent.TYPE.NORMAL);
    }

    public Response<ResponseBody> login(User user){
        Response<ResponseBody> call;
        try {
            call = apiService.login(user).execute();
        }catch (Exception e){
            Log.d("login=>", e.toString());
            return null;
        }
        return call;

    }

    public void heartBeat(){
        apiService.heartBeat(TOKEN).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, HEART_BEAT);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void uniSearchProSkuSale(UniSearchProSkuSale verification){
        apiService.uniSearchProSkuSale(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, COUPON_VERIFICATION);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void commonSubmitOrder(CommonSubmitOrder verification){
        apiService.commonSubmitOrder(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, COMMON_SUBMIT_ORDER);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void uniQueryMemberInfo(UniQueryMemberInfo verification){
        apiService.uniQueryMemberInfo(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, QUERY_PAY_STATUS);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void couponWithdraw(CouponWithdraw verification){
        apiService.couponWithdraw(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, COUPON_WITHDRAW);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void couponStatus(CouponStatus verification){
        apiService.couponStatus(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, COUPON_STATUS);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void orderRefund(OrderRefund verification){
        apiService.orderRefund(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, ORDER_REFUND);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void setErrMsg(ErrMsg verification){
        apiService.setErrMsg(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, GET_ERR_MSG);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public void makeDone(MakeDone verification){
        apiService.makeDone(TOKEN, verification).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dealCallBack(response, MAKE_DONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                disconnect();
            }
        });
    }

    public synchronized void reportState(String msg) {
        if (mLastReport.equals(msg)) {
            return;
        }
        mLastReport = msg;
        if (mStatesQueue.size() > MAX_QUEUE_SIZE) {
            showLog("more than queue max size->");
            return;
        }

        showLog("in queue->" + msg);
        mStatesQueue.add(msg);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onReceiveNetMessage(MessageEvent messageEvent) {
    }

    public void unRegister() {
        EventBus.getDefault().unregister(this);
    }

    private StateReportThread mStateReportThread = null;
    private void startStateReport(){
        if (mStateReportThread == null) {
            mStateReportThread = new StateReportThread();
            mStateReportThread.start();
        }
    }

    @Override
    public boolean connect(Map<String, Object> params) {
        if (!init){
            throw new IllegalArgumentException("please call init() first");
        }
        String userName = (String) params.get(USER_NAME);
        String password = (String) params.get(PASSWORD);
        String pos = (String) params.get(POS);
        User user = new User();
        user.setPassword(password);
        user.setUserName(userName);
        user.setPos(pos);
        return connect(user);
    }

    @Override
    public void init(Map<String, Object> params) {
        String url = (String) params.get(URL);
        if (TextUtils.isEmpty(url)){
            throw new IllegalArgumentException("init URL error");
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        mStatesQueue = new LinkedBlockingDeque<>(MAX_QUEUE_SIZE);
        EventBus.getDefault().register(this);
        init = true;
    }

    @Override
    public void login(Map<String, Object> params) {

    }

    @Override
    public void logout(Map<String, Object> params) {

    }

    @Override
    public void heartBeat(Map<String, Object> params) {
        heartBeat();
    }

    @Override
    public void auth(Map<String, Object> params) {

    }

    @Override
    public void register(Map<String, Object> params) {

    }

    @Override
    public void qrCode(Map<String, Object> params) {

    }

    @Override
    public void cardCode(Map<String, Object> params) {

    }

    @Override
    public void barCode(Map<String, Object> params) {
    }

    @Override
    public void productDone(Map<String, Object> params) {

    }

    @Override
    public void stateReport(Map<String, Object> params) {

    }

    @Override
    public void statusReport(Map<String, Object> params) {

    }

    @Override
    public void supplyReport(Map<String, Object> params) {

    }

    @Override
    public void eventReport(Map<String, Object> params) {

    }

    @Override
    public void uniSearchProSkuSale(Map<String, Object> params) {
        int id = (int)params.get(ID);
        String skuCode = (String) params.get(SKU_CODE);
        String skuName = (String) params.get(SKU_NAME);
        String unit = (String) params.get(UNIT);
        String barCode = (String) params.get(BAR_CODE);
        BigDecimal offLineSalePrice = (BigDecimal) params.get(OFF_LINE_SALE_PRICE);
        UniSearchProSkuSale searchProSkuSale = new UniSearchProSkuSale();
        searchProSkuSale.setId(id);
        searchProSkuSale.setBarCode(barCode);
        searchProSkuSale.setOffLineSalePrice(offLineSalePrice);
        searchProSkuSale.setUnit(unit);
        searchProSkuSale.setSkuCode(skuCode);
        searchProSkuSale.setSkuName(skuName);
        uniSearchProSkuSale(searchProSkuSale);
    }

    @Override
    public void commonSubmitOrder(Map<String, Object> params) {
        String orderId = (String) params.get(ORDER_ID);
        String authCode = (String) params.get(AUTH_CODE);
        String userId = (String) params.get(USER_ID);
        String couNo = (String) params.get(COU_NO);
        CommonSubmitOrder order = new CommonSubmitOrder();
        order.setOrderId(orderId);
        order.setAuthCode(authCode);
        order.setUserId(userId);
        order.setCouNo(couNo);
        commonSubmitOrder(order);
    }

    @Override
    public void uniQueryMemberInfo(Map<String, Object> params) {
        String orderId = (String) params.get(ORDER_ID);
        String qrCode = (String) params.get(QR_CODE);
        UniQueryMemberInfo info = new UniQueryMemberInfo();
        info.setOrderId(orderId);
        info.setQrCode(qrCode);
        uniQueryMemberInfo(info);
    }

    @Override
    public void couponWithdraw(Map<String, Object> params) {
        String orderId = (String) params.get(ORDER_ID);
        CouponWithdraw couponWithdraw = new CouponWithdraw();
        couponWithdraw.setOrderId(orderId);
        couponWithdraw(couponWithdraw);
    }

    @Override
    public void setErrMsg(Map<String, Object> params) {
        String orderId = (String) params.get(ORDER_ID);
        String codePool = (String) params.get(CODE_POOL);
        ErrMsg errMsg = new ErrMsg();
        errMsg.setCodepool(codePool);
        errMsg.setOrderId(orderId);
        setErrMsg(errMsg);
    }

    @Override
    public void getErrMsg(Map<String, Object> params) {

    }

    @Override
    public void init(Context context) {

    }

    /**
     * 状态上报线程
     */
    class StateReportThread extends Thread {
        private boolean stop = false;
        private int mReportCount = 0;

        private void stopThread() {
            stop = true;
        }

        @Override
        public void run() {
            showLog("StateReportThread start");
            while (!stop) {

                if (!login) {
                    SystemClock.sleep(3000);
                    continue;
                }

                String element = mStatesQueue.peek();
                if (TextUtils.isEmpty(element)) {
                    SystemClock.sleep(3000);
                    continue;
                }

                // 上报超过8次的则自动出列，防止一直上报
                if (mTryToReportMsg.equals(element)) {
                    mReportCount++;
                } else {
                    mTryToReportMsg = element;
                    mReportCount = 0;
                }

                if (mReportCount >= MAX_REPORT_COUNT) {
                    String abortMsg = mStatesQueue.poll();
                    showLog("queue msg->" + abortMsg);
                    SystemClock.sleep(3000);
                    continue;
                }

                if (mReportCount >= 1) {    // 门开关只报一次
                    if (element.contains("doorclose")
                            || element.contains("dooropen")) {
                        String abortMsg = mStatesQueue.poll();
                        showLog("abort net message->" + abortMsg);
                        SystemClock.sleep(10000);
                        continue;
                    }
                }

                ErrMsg msg = new ErrMsg();
                msg.setCodepool(element);
                setErrMsg(msg);
                SystemClock.sleep(10000);
            }
        }
    }

}
