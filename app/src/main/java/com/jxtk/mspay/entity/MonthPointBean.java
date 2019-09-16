package com.jxtk.mspay.entity;

import java.util.List;

public class MonthPointBean {

    /**
     * total : 3
     * month : 本月
     * data : [{"id":329,"integral":1,"instructions":"连续签到1天","type":"签到","user_id":187,"createtime":"2019-09-04 13:50:20","deletetime":null,"month":"9"},{"id":313,"integral":1,"instructions":"连续签到1天","type":"签到","user_id":187,"createtime":"2019-09-02 09:24:27","deletetime":null,"month":"9"}]
     */

    private int total;
    private String month;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 329
         * integral : 1
         * instructions : 连续签到1天
         * type : 签到
         * user_id : 187
         * createtime : 2019-09-04 13:50:20
         * deletetime : null
         * month : 9
         */

        private int id;
        private int integral;
        private String instructions;
        private String type;
        private int user_id;
        private String createtime;
        private Object deletetime;
        private String month;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getDeletetime() {
            return deletetime;
        }

        public void setDeletetime(Object deletetime) {
            this.deletetime = deletetime;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
