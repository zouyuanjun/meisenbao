package com.jxtk.mspay.entity;

public class ShopIndexBean {

    /**
     * income : 0.02
     * sum : 2
     * max : 0.01
     * qr_code : ./static/qrcode/shou/73.png
     */

    private String income;
    private int sum;
    private String max;
    private String qr_code;

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
}
