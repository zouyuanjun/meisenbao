package com.jxtk.mspay.entity;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/9 0009
 * description:
 */public class ADEntity {

    public ADEntity() {
    }

    /**
     * id : 18
     * title : 首页小程序精选推荐04张图
     * imageurl : /uploads/20190723/0780b08895a4206d357a7d4b05affa88.png
     * linkurl : #
     * target : _blank
     * expiretime : 1595386863
     * weigh : 18
     */

    private int id;
    private String title;
    private String imageurl;
    private String linkurl;
    private String target;
    private int expiretime;
    private int weigh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }
}
