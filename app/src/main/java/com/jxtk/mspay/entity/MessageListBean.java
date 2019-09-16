package com.jxtk.mspay.entity;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/22 0022
 * description:
 */public class MessageListBean {
     int id;
     String title;
     String content;
     String create_time;
     int  is_read;

    public MessageListBean() {
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }
}
