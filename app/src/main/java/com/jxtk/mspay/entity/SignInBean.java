package com.jxtk.mspay.entity;

public class SignInBean {


    /**
     * date : 08-29
     * is_sigin : 0
     * integral : 1
     */

    private String date;
    private int is_sigin;
    private String integral;
    boolean islast = false;

    public boolean isIslast() {
        return islast;
    }

    public void setIslast(boolean islast) {
        this.islast = islast;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIs_sigin() {
        return is_sigin;
    }

    public void setIs_sigin(int is_sigin) {
        this.is_sigin = is_sigin;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

}
