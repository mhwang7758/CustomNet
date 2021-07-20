package com.qtyx.mhwang.netprotocol;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class CouponStatus {
    private String mchOrderNo;
    String couponId;
    String deviceInfo;
    String operaType;

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    @Override
    public String toString() {
        return "CouponStatus{" +
                "mchOrderNo='" + mchOrderNo + '\'' +
                ", couponId='" + couponId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", operaType='" + operaType + '\'' +
                '}';
    }
}
