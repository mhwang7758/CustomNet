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
        Button btn_getProducts = findViewById(R.id.btn_getProducts);

        service = NetServiceUtil.getService();
        Map<String, Object> params = new HashMap<>();
//        params.put(NetFiled.URL, "http://xx/api/");  //todo 替换为正式网址
        params.put(NetFiled.URL, "http://49.234.58.201:18080/");  //todo 替换为正式网址
        params.put(NetFiled.DEBUG, true);
        service.init(params);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Map<String,Object> map = new HashMap<>();
//                map.put("userName","admin");
//                map.put("password","admin123");
//                map.put("pos","123456");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        boolean connect = service.connect(map);
//                        if (connect) {
//                            updateState("登陆成功");
//                        }
//                    }
//                }).start();

                Map<String,Object> map = new HashMap<>();
                map.put(NetFiled.ORDER_ID,"ced3fd7048b440c186fc103cbdfdc78e");
                map.put(NetFiled.QR_CODE,"134953427875884561");
                map.put(NetFiled.MCH_SPBILL_IP,"192.168.137.73");
                service.barCode(map);
            }
        });

        btn_heatBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true){
//                            service.heartBeat(null);
//                            SystemClock.sleep(5000);
//                        }
//                    }
//                }).start();

                Map<String,Object> map = new HashMap<>();
                map.put(NetFiled.ORDER_ID,"06924020b2bc40c994f66c6f2a969293");
                service.commonSubmitOrder(map);
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put(NetFiled.SALE_COUNT,"1");
                map.put(NetFiled.SKU_CODE,"70000106");
                service.uniSearchProSkuSale(map);
            }
        });

        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put(NetFiled.ORDER_ID,"dc89073ff90341378299d6f6a91282dd");
                map.put(NetFiled.QR_CODE,"0065031522924072300200");
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
                map.put(NetFiled.ORDER_ID,"e458b5bfd91b472d82417acb7058f827");
                service.couponWithdraw(map);
            }
        });

        btn_setErrMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Map<String,Object> map = new HashMap<>();
//                map.put("orderId","11223344");
//                map.put("codepool","E_2");
//                service.setErrMsg(map);

                Map<String,Object> map = new HashMap<>();
                map.put(NetFiled.ORDER_ID,"c1bf0aec04c640e2bd219f5031199d6b");
                service.login(map);
            }
        });

        btn_getProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.getProducts(null);
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