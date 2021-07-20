package com.qtyx.mhwang.netprotocol;

import java.util.List;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class CommonSubmitOrder {
    private String orderId;
    private String authCode;
    private String userId;
    private String couNo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouNo() {
        return couNo;
    }

    public void setCouNo(String couNo) {
        this.couNo = couNo;
    }

    @Override
    public String toString() {
        return "CommonSubmitOrder{" +
                "orderId='" + orderId + '\'' +
                ", authCode='" + authCode + '\'' +
                ", userId='" + userId + '\'' +
                ", couNo='" + couNo + '\'' +
                '}';
    }
}
