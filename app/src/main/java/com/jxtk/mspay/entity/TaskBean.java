package com.jxtk.mspay.entity;

public class TaskBean {

    /**
     * title : 首次充值赠送积分
     * point : 50
     * path : pages/recharge/recharge
     * finish : 0
     */

    private String title;
    private int point;
    private String path;
    private int finish;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
