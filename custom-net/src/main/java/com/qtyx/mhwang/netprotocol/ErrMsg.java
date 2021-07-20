package com.qtyx.mhwang.netprotocol;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class ErrMsg {
    private String codepool;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCodepool() {
        return codepool;
    }

    public void setCodepool(String codepool) {
        this.codepool = codepool;
    }

    @Override
    public String toString() {
        return "ErrMsg{" +
                "codepool='" + codepool + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
