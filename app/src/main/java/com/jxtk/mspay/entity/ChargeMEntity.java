package com.jxtk.mspay.entity;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/16 0016
 * description:
 */public class ChargeMEntity {

    /**
     * money : 50
     * give : 1
     * pay_money : 50.00
     */

    private String money;
    private String give;
    private String pay_money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGive() {
        return give;
    }

    public void setGive(String give) {
        this.give = give;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }
}
