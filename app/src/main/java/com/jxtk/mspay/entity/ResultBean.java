package com.jxtk.mspay.entity;

/**
 * @author 邹远君
 * @date 2020/1/8 0008.
 * email：2948992828@qq.com
 * description：
 */
public class ResultBean<T> {
    Integer code;
    T data;
    String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
