package com.jxtk.mspay.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/22 0022
 * description:
 */public class MonthBilBean implements Serializable {


    /**
     * month : 本月
     * income : 0.1
     * expenditure : 36
     * data : [{"id":603,"user_id":187,"goods_type":"便利店","money_unit":"m","goods_money":"+0.10","order_sn":"2019090910150985","consump_user_id":182,"createtime":"2019-09-09 09:27:42","memo":"","status":1,"is_apply":0,"money":"0.00","refundtime":null,"is_handle":1,"identity":2,"name":"卡西莫多","img":"/user_header/1821567564747.png","mobile":"13767981704","integral":0,"month":"9","refund_memo":"7tian wu li you","refuse_reason":null,"refuse_money":null,"refuse_apply_time":"2019-09-09 09:28:49","refuse_time":"2019-09-09 09:28:49","refund":{"id":41,"user_id":182,"order_sn":"2019090910150985","memo":"7tian wu li you","status":1,"is_apply":1,"refuse_reason":null,"goods_id":0,"consumption_id":603,"money":null,"updatetime":1567992529,"createtime":1567992529},"typeData":{"1":"充电","2":"便利店","3":"售货机","4":"充值","5":"收款","6":"升级会员","7":"提现M币","8":"用户退款","9":"提现退回"}},{"id":533,"user_id":175,"goods_type":"收款","money_unit":"m","goods_money":"-2.00","order_sn":"2019090299984851","consump_user_id":187,"createtime":"2019-09-02 14:38:36","memo":"","status":"","is_apply":0,"money":"0.00","refundtime":null,"is_handle":0,"identity":2,"name":"测试店铺","img":"/user_header/oNCUp49IwmmRITSznNsDflcEkuSY.jpg","mobile":"13177636610","integral":2,"month":"9","refund_memo":"","refuse_reason":"","refuse_money":"","refuse_apply_time":"","refuse_time":"","refund":null,"typeData":{"1":"充电","2":"便利店","3":"售货机","4":"充值","5":"收款","6":"升级会员","7":"提现M币","8":"用户退款","9":"提现退回"}},{"id":532,"user_id":175,"goods_type":"收款","money_unit":"m","goods_money":"-2.00","order_sn":"2019090297484856","consump_user_id":187,"createtime":"2019-09-02 14:38:18","memo":"","status":"","is_apply":0,"money":"0.00","refundtime":null,"is_handle":0,"identity":2,"name":"测试店铺","img":"/user_header/oNCUp49IwmmRITSznNsDflcEkuSY.jpg","mobile":"13177636610","integral":2,"month":"9","refund_memo":"","refuse_reason":"","refuse_money":"","refuse_apply_time":"","refuse_time":"","refund":null,"typeData":{"1":"充电","2":"便利店","3":"售货机","4":"充值","5":"收款","6":"升级会员","7":"提现M币","8":"用户退款","9":"提现退回"}},{"id":531,"user_id":175,"goods_type":"收款","money_unit":"m","goods_money":"-32.00","order_sn":"2019090298481029","consump_user_id":187,"createtime":"2019-09-02 14:38:03","memo":"","status":"","is_apply":0,"money":"0.00","refundtime":null,"is_handle":0,"identity":2,"name":"测试店铺","img":"/user_header/oNCUp49IwmmRITSznNsDflcEkuSY.jpg","mobile":"13177636610","integral":32,"month":"9","refund_memo":"","refuse_reason":"","refuse_money":"","refuse_apply_time":"","refuse_time":"","refund":null,"typeData":{"1":"充电","2":"便利店","3":"售货机","4":"充值","5":"收款","6":"升级会员","7":"提现M币","8":"用户退款","9":"提现退回"}}]
     */

    private String month;
    private String income;
    private String expenditure;
    private List<DataBean> data;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable{
        /**
         * id : 603
         * user_id : 187
         * goods_type : 便利店
         * money_unit : m
         * goods_money : +0.10
         * order_sn : 2019090910150985
         * consump_user_id : 182
         * createtime : 2019-09-09 09:27:42
         * memo :
         * status : 1
         * is_apply : 0
         * money : 0.00
         * refundtime : null
         * is_handle : 1
         * identity : 2
         * name : 卡西莫多
         * img : /user_header/1821567564747.png
         * mobile : 13767981704
         * integral : 0
         * month : 9
         * refund_memo : 7tian wu li you
         * refuse_reason : null
         * refuse_money : null
         * refuse_apply_time : 2019-09-09 09:28:49
         * refuse_time : 2019-09-09 09:28:49
         * refund : {"id":41,"user_id":182,"order_sn":"2019090910150985","memo":"7tian wu li you","status":1,"is_apply":1,"refuse_reason":null,"goods_id":0,"consumption_id":603,"money":null,"updatetime":1567992529,"createtime":1567992529}
         * typeData : {"1":"充电","2":"便利店","3":"售货机","4":"充值","5":"收款","6":"升级会员","7":"提现M币","8":"用户退款","9":"提现退回"}
         */

        private int id;
        private int user_id;
        private String goods_type;
        private String money_unit;
        private String goods_money;
        private String order_sn;
        private int consump_user_id;
        private String createtime;
        private String memo;
        private int status;
        private int is_apply;
        private String money;
        private Object refundtime;
        private int is_handle;
        private int identity;
        private String name;
        private String img;
        private String mobile;
        private int integral;
        private String month;
        private String refund_memo;
        private Object refuse_reason;
        private Object refuse_money;
        private String refuse_apply_time;
        private String refuse_time;
        private RefundBean refund;
        private TypeDataBean typeData;

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

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public String getMoney_unit() {
            return money_unit;
        }

        public void setMoney_unit(String money_unit) {
            this.money_unit = money_unit;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getConsump_user_id() {
            return consump_user_id;
        }

        public void setConsump_user_id(int consump_user_id) {
            this.consump_user_id = consump_user_id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(int is_apply) {
            this.is_apply = is_apply;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public Object getRefundtime() {
            return refundtime;
        }

        public void setRefundtime(Object refundtime) {
            this.refundtime = refundtime;
        }

        public int getIs_handle() {
            return is_handle;
        }

        public void setIs_handle(int is_handle) {
            this.is_handle = is_handle;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getRefund_memo() {
            return refund_memo;
        }

        public void setRefund_memo(String refund_memo) {
            this.refund_memo = refund_memo;
        }

        public Object getRefuse_reason() {
            return refuse_reason;
        }

        public void setRefuse_reason(Object refuse_reason) {
            this.refuse_reason = refuse_reason;
        }

        public Object getRefuse_money() {
            return refuse_money;
        }

        public void setRefuse_money(Object refuse_money) {
            this.refuse_money = refuse_money;
        }

        public String getRefuse_apply_time() {
            return refuse_apply_time;
        }

        public void setRefuse_apply_time(String refuse_apply_time) {
            this.refuse_apply_time = refuse_apply_time;
        }

        public String getRefuse_time() {
            return refuse_time;
        }

        public void setRefuse_time(String refuse_time) {
            this.refuse_time = refuse_time;
        }

        public RefundBean getRefund() {
            return refund;
        }

        public void setRefund(RefundBean refund) {
            this.refund = refund;
        }

        public TypeDataBean getTypeData() {
            return typeData;
        }

        public void setTypeData(TypeDataBean typeData) {
            this.typeData = typeData;
        }

        public static class RefundBean  implements Serializable{
            /**
             * id : 41
             * user_id : 182
             * order_sn : 2019090910150985
             * memo : 7tian wu li you
             * status : 1
             * is_apply : 1
             * refuse_reason : null
             * goods_id : 0
             * consumption_id : 603
             * money : null
             * updatetime : 1567992529
             * createtime : 1567992529
             */

            private int id;
            private int user_id;
            private String order_sn;
            private String memo;
            private int status;
            private int is_apply;
            private Object refuse_reason;
            private int goods_id;
            private int consumption_id;
            private Object money;
            private int updatetime;
            private int createtime;

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

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIs_apply() {
                return is_apply;
            }

            public void setIs_apply(int is_apply) {
                this.is_apply = is_apply;
            }

            public Object getRefuse_reason() {
                return refuse_reason;
            }

            public void setRefuse_reason(Object refuse_reason) {
                this.refuse_reason = refuse_reason;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getConsumption_id() {
                return consumption_id;
            }

            public void setConsumption_id(int consumption_id) {
                this.consumption_id = consumption_id;
            }

            public Object getMoney() {
                return money;
            }

            public void setMoney(Object money) {
                this.money = money;
            }

            public int getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(int updatetime) {
                this.updatetime = updatetime;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }
        }

        public static class TypeDataBean implements Serializable {
            /**
             * 1 : 充电
             * 2 : 便利店
             * 3 : 售货机
             * 4 : 充值
             * 5 : 收款
             * 6 : 升级会员
             * 7 : 提现M币
             * 8 : 用户退款
             * 9 : 提现退回
             */

            @SerializedName("1")
            private String _$1;
            @SerializedName("2")
            private String _$2;
            @SerializedName("3")
            private String _$3;
            @SerializedName("4")
            private String _$4;
            @SerializedName("5")
            private String _$5;
            @SerializedName("6")
            private String _$6;
            @SerializedName("7")
            private String _$7;
            @SerializedName("8")
            private String _$8;
            @SerializedName("9")
            private String _$9;

            public String get_$1() {
                return _$1;
            }

            public void set_$1(String _$1) {
                this._$1 = _$1;
            }

            public String get_$2() {
                return _$2;
            }

            public void set_$2(String _$2) {
                this._$2 = _$2;
            }

            public String get_$3() {
                return _$3;
            }

            public void set_$3(String _$3) {
                this._$3 = _$3;
            }

            public String get_$4() {
                return _$4;
            }

            public void set_$4(String _$4) {
                this._$4 = _$4;
            }

            public String get_$5() {
                return _$5;
            }

            public void set_$5(String _$5) {
                this._$5 = _$5;
            }

            public String get_$6() {
                return _$6;
            }

            public void set_$6(String _$6) {
                this._$6 = _$6;
            }

            public String get_$7() {
                return _$7;
            }

            public void set_$7(String _$7) {
                this._$7 = _$7;
            }

            public String get_$8() {
                return _$8;
            }

            public void set_$8(String _$8) {
                this._$8 = _$8;
            }

            public String get_$9() {
                return _$9;
            }

            public void set_$9(String _$9) {
                this._$9 = _$9;
            }
        }
    }
}
