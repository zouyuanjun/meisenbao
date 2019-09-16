package com.jxtk.mspay.entity;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/22 0022
 * description:
 */public class MessageNum {

    /**
     * service : 2
     * account : 1
     * system : 0
     * all : 3
     */

    private int service;
    private int account;
    private int system;
    private int all;

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
