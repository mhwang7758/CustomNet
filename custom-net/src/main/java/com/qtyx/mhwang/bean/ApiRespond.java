package com.qtyx.mhwang.bean;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/25/21 4:16 PM
 * 用途：
 **/
public class ApiRespond {
    private String code;
    private String msg;
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
