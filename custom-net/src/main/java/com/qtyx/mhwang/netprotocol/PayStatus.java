package com.qtyx.mhwang.netprotocol;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class PayStatus {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PayStatus{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
