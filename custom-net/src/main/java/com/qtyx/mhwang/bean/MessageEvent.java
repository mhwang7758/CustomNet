package com.qtyx.mhwang.bean;

import static com.qtyx.mhwang.bean.MessageEvent.TYPE.NORMAL;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：2020-03-03 10:35
 * 用途：eventbuss消息类
 **/
public class MessageEvent {
    private String message;
    private String extra;
    private int type = NORMAL;
    private String cmd;
    private int ret;

    public MessageEvent(){}

    public MessageEvent(String message, String cmd){
        this.cmd = cmd;
        this.message = message;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class TYPE{
        public static final int NORMAL = 0;
        public static final int UN_CONNECT = 1;
        public static final int LOGIN = 2;
        public static final int UN_AUTHORITY = 3;
        public static final int ERR = 4;
    }
}

