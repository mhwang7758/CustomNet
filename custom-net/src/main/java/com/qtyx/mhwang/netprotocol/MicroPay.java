package com.qtyx.mhwang.netprotocol;

import java.util.List;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class MicroPay {
    private String orderId;
    private String qrCode;
    private String mchSpbillIp;

    public String getMchSpbillIp() {
        return mchSpbillIp;
    }

    public void setMchSpbillIp(String mchSpbillIp) {
        this.mchSpbillIp = mchSpbillIp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "CouponVerification{" +
                "orderId='" + orderId + '\'' +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}
