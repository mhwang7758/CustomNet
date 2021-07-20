package com.qtyx.mhwang.netprotocol;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class OrderRefund {
    private String mchOrderNo;
    String deviceInfo;
    String posTimeStamp;

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getPosTimeStamp() {
        return posTimeStamp;
    }

    public void setPosTimeStamp(String posTimeStamp) {
        this.posTimeStamp = posTimeStamp;
    }

    @Override
    public String toString() {
        return "OrderRefund{" +
                "mchOrderNo='" + mchOrderNo + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", posTimeStamp='" + posTimeStamp + '\'' +
                '}';
    }
}
