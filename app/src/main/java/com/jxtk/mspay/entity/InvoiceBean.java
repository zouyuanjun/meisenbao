package com.jxtk.mspay.entity;

public class InvoiceBean {

    /**
     * title : 开票中
     * id : 12
     * amount : 500.00
     * createtime : 2019-08-06 14:52:45
     */

    private String title;
    private int id;
    private String amount;
    private String createtime;
    private boolean isselect=false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }
}
