package com.qtyx.mhwang.netprotocol;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class MakeDone {
    String cmd;
    String mchOrderNo;
    int success;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "MakeDone{" +
                "cmd='" + cmd + '\'' +
                ", mchOrderNo='" + mchOrderNo + '\'' +
                ", success=" + success +
                '}';
    }
}
