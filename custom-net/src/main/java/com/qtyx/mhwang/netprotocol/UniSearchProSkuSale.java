package com.qtyx.mhwang.netprotocol;

import java.math.BigDecimal;

/**
 * 公司：广州成林科技信息
 * 作者：王明海
 * 时间：6/28/21 2:48 PM
 * 用途：
 **/
public class UniSearchProSkuSale {
    private int id;
    private String skuCode;
    private String skuName;
    private String unit;
    private String barCode;
    private BigDecimal offLineSalePrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getOffLineSalePrice() {
        return offLineSalePrice;
    }

    public void setOffLineSalePrice(BigDecimal offLineSalePrice) {
        this.offLineSalePrice = offLineSalePrice;
    }

    @Override
    public String toString() {
        return "UniSearchProSkuSale{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", unit='" + unit + '\'' +
                ", barCode='" + barCode + '\'' +
                ", offLineSalePrice=" + offLineSalePrice +
                '}';
    }
}

