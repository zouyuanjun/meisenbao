package com.jxtk.mspay.entity;

public class BankBean {

    /**
     * id : 3
     * type : 1
     * realname : sd
     * account : 6217002020024749954
     * bank : sdfsdf
     */

    private int id;
    private int type;
    private String realname;
    private String account;
    private String bank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
