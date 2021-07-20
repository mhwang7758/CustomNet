package com.qtyx.mhwang.netprotocol;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class ProductInfo {
    private String proCode;
    private String proName;
    private String categoryCode;
    private String barCode;
    private int amount;
    private int selfPrice;
    private int num;

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSelfPrice() {
        return selfPrice;
    }

    public void setSelfPrice(int selfPrice) {
        this.selfPrice = selfPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", barCode='" + barCode + '\'' +
                ", amount=" + amount +
                ", selfPrice=" + selfPrice +
                ", num=" + num +
                '}';
    }
}
