package com.jxtk.mspay.entity;

public class ThreePayDemo {

    /**
     * pay_amount : null
     * body : null
     * out_trade_no : null
     * shopper_name :
     * shopper_avatar : /uploads/20190815/722de6fdb46f7ccdaf54e73788fa89a0.jpg
     */

    private String pay_amount;
    private String body;
    private String out_trade_no;
    private String shopper_name;
    private String shopper_avatar;

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getShopper_name() {
        return shopper_name;
    }

    public void setShopper_name(String shopper_name) {
        this.shopper_name = shopper_name;
    }

    public String getShopper_avatar() {
        return shopper_avatar;
    }

    public void setShopper_avatar(String shopper_avatar) {
        this.shopper_avatar = shopper_avatar;
    }
}
