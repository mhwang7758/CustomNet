package com.qtyx.mhwang.customnet;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qtyx.mhwang.netinterface_module.BuildConfig;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：7/8/21 4:26 PM
 * 用途：
 **/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openDebug();
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }
}
