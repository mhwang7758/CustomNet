package com.qtyx.mhwang.bean;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/25/21 4:16 PM
 * 用途：
 **/
public class ApiRespond {
    private String errNo;
    private String errMsg;
    private Object data;

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiRespond{" +
                "errNo='" + errNo + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
