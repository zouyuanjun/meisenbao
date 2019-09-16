package com.jxtk.mspay.entity;

public class MyInvoiceTitleBean {
    /**
     * id : 3
     * user_id : 187
     * corporate_name : zouyuanjun
     * duty_paragraph : null
     * corporate_address : null
     * tel_phone : null
     * bank : null
     * bank_number : null
     * type : person
     * invoice_type : null
     * createtime : 1567499207
     * updatetime : 1567499207
     * typetext : 个人
     */
    private int id;
    private int user_id;
    private String corporate_name;
    private String duty_paragraph;
    private String corporate_address;
    private String tel_phone;
    private String bank;
    private String bank_number;
    private String type;
    private String invoice_type;
    private int createtime;
    private int updatetime;
    private String typetext;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCorporate_name() {
        return corporate_name;
    }

    public void setCorporate_name(String corporate_name) {
        this.corporate_name = corporate_name;
    }

    public String getDuty_paragraph() {
        return duty_paragraph;
    }

    public void setDuty_paragraph(String duty_paragraph) {
        this.duty_paragraph = duty_paragraph;
    }

    public String getCorporate_address() {
        return corporate_address;
    }

    public void setCorporate_address(String corporate_address) {
        this.corporate_address = corporate_address;
    }

    public String getTel_phone() {
        return tel_phone;
    }

    public void setTel_phone(String tel_phone) {
        this.tel_phone = tel_phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
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

    public String getTypetext() {
        return typetext;
    }

    public void setTypetext(String typetext) {
        this.typetext = typetext;
    }
}
