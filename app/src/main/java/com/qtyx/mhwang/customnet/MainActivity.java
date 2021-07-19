package com.qtyx.mhwang.customnet;


import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.qtyx.mhwang.bean.MessageEvent;
import com.qtyx.mhwang.constant.NetFiled;
import com.qtyx.mhwang.service.INetService;
import com.qtyx.mhwang.service.NetServiceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    TextView tv_text;
    TextView tv_netMsg;
    INetService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_text = findViewById(R.id.tv_text);
        tv_netMsg = findViewById(R.id.tv_netMsg);
        EventBus.getDefault().register(this);
        Button btn_connect = findViewById(R.id.btn_connect);
        Button btn_heatBeat = findViewById(R.id.btn_heatBeat);
        Button btn_order = findViewById(R.id.btn_order);
        Button btn_member = findViewById(R.id.btn_member);
        Button btn_pay = findViewById(R.id.btn_pay);
        Button btn_payCancel = findViewById(R.id.btn_payCancel);
        Button btn_setErrMsg = findViewById(R.id.btn_setErrMsg);

        service = NetServiceUtil.getService();
        Map<String, Object> params = new HashMap<>();
        params.put(NetFiled.URL, "http://xxxx/api/");  //todo 替换为正式网址
        service.init(params);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("userName","admin");
                map.put("password","admin123");
                map.put("pos","123456");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean connect = service.connect(map);
                        if (connect) {
                            updateState("登陆成功");
                        }
                    }
                }).start();
            }
        });

        btn_heatBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            service.heartBeat(null);
                            SystemClock.sleep(5000);
                        }
                    }
                }).start();
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",136);
                map.put("unit","瓶");
                map.put("sdkName","浓情咖啡罐装 245ml");
                map.put("barcode","6925303733902");
                map.put("skuCode","70000325");
                map.put("offLineSalePrice",new BigDecimal("9.9"));
                service.uniSearchProSkuSale(map);
            }
        });

        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("orderId","1122334455");
                map.put("qrCode","123456");
                service.uniQueryMemberInfo(map);
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("orderId","1122334455");
                map.put("authCode","286147606827412721");
                map.put("couNo","54321");
                map.put("userId","56789");
                service.commonSubmitOrder(map);
            }
        });

        btn_payCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("orderId","1122334455");
                service.couponWithdraw(map);
            }
        });

        btn_setErrMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("orderId","11223344");
                map.put("codepool","E_2");
                service.setErrMsg(map);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveNetMessage(com.qtyx.mhwang.bean.MessageEvent messageEvent){
        Log.e("MainActivity=>","onReceiveNetMessage");
        if (messageEvent == null) return;

        if (messageEvent.getType() == MessageEvent.TYPE.UN_CONNECT){
            updateNetMsg("未连接");
        }else {
            updateNetMsg(messageEvent.getMessage());
        }
    }

    private void updateState(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_text.setText(msg);
            }
        });
    }

    private void updateNetMsg(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_netMsg.setText(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}