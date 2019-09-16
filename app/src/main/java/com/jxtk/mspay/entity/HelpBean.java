package com.jxtk.mspay.entity;

public class HelpBean {

    /**
     * article_id : 20
     * title : 如何获得积分
     * content : 积分可通过消费(商家、充电桩、售货机)、签到、邀请好友等方式获得积分

     */

    private int article_id;
    private String title;
    private String content;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
