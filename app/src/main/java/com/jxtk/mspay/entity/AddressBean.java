package com.jxtk.mspay.entity;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/16 0016
 * description:
 */public class AddressBean {

    /**
     * mobile : 18601774706
     * user_name : 刘阳
     * province : 江西省
     * city : 南昌市
     * county : 高新区
     * address : 紫阳大道泰豪科技广场
     * createtime : 1563931341
     * updatetime : 1563931341
     */
private String id;
    private String mobile;
    private String user_name;
    private String province;
    private String city;
    private String county;
    private String address;
    private int createtime;
    private int updatetime;

    public String getMobile() {
        return mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }
}
