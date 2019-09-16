package com.jxtk.mspay.entity;

import java.io.Serializable;

public class ShopBean  implements Serializable {


    /**
     * id : 26
     * name : 美森便利南京东路店
     * corporation_name : 涂文俊
     * tel : 15579114603
     * operating_starttime : 1566975352
     * operating_endtime : 1598597754
     * type : 便利店
     * introduce : 便利购物/网约车服务/汽车充电/销售新能源电动汽车/跨界融合新业态！
     欢迎各位光临！
     * card_head : /uploads/20190901/ece9dcd607da377d5b5c94de22d6c7d1.jpg
     * user_id : 195
     * qr_code : ./static/qrcode/shou/195.png
     * lat : 28.68096
     * lng : 115.96787
     * province : 1263
     * city : 1264
     * region : 1269
     * address : 京东大道与南京东路交叉口南100米
     * createtime : 1566975641
     * business_license :
     * updatetime : 1567385437
     * status : normal
     * distance : 1.74Km
     * avatar : /uploads/20190903/a99dd34125894fcf89378fff19421d6d.png
     * shop_address : 江西省南昌市青山湖区京东大道与南京东路交叉口南100米
     * user : {"id":195,"group_id":1,"username":"15579114603","nickname":"美森便利","password":null,"salt":"VLfzxa","email":"","mobile":"15579114603","avatar":"/uploads/20190903/a99dd34125894fcf89378fff19421d6d.png","level_id":1,"level_starttime":1566974769,"level_endtime":1767196799,"gender":"未知","birthday":null,"bio":"","money":"105.01","score":1,"successions":2,"maxsuccessions":2,"prevtime":1567046648,"logintime":1567064253,"loginip":"59.53.226.117","loginfailure":0,"joinip":"182.96.51.211","jointime":1566974769,"createtime":1566974769,"updatetime":1567582794,"token":"138286d3372304f1a897688d59d8a6a8798343e6","status":"normal","verification":{"email":0,"mobile":0},"pay_password":"0f8fad117740dc78cb2e834068b94fab","type":"商家","app_id":"oNCUp44wCFmLNiJwLIra0OYwQiOo","rechage_money":"0.00","consumption_money":"0.00","edit_version":0,"source":"mini","is_recharge":"0","url":"/u/195"}
     */

    private int id;
    private String name;
    private String corporation_name;
    private String tel;
    private int operating_starttime;
    private int operating_endtime;
    private String type;
    private String introduce;
    private String card_head;
    private int user_id;
    private String qr_code;
    private String lat;
    private String lng;
    private int province;
    private int city;
    private int region;
    private String address;
    private int createtime;
    private String business_license;
    private int updatetime;
    private String status;
    private String distance;
    private String avatar;
    private String shop_address;
    private UserBean user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorporation_name() {
        return corporation_name;
    }

    public void setCorporation_name(String corporation_name) {
        this.corporation_name = corporation_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getOperating_starttime() {
        return operating_starttime;
    }

    public void setOperating_starttime(int operating_starttime) {
        this.operating_starttime = operating_starttime;
    }

    public int getOperating_endtime() {
        return operating_endtime;
    }

    public void setOperating_endtime(int operating_endtime) {
        this.operating_endtime = operating_endtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCard_head() {
        return card_head;
    }

    public void setCard_head(String card_head) {
        this.card_head = card_head;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
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

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean  implements Serializable {
        /**
         * id : 195
         * group_id : 1
         * username : 15579114603
         * nickname : 美森便利
         * password : null
         * salt : VLfzxa
         * email :
         * mobile : 15579114603
         * avatar : /uploads/20190903/a99dd34125894fcf89378fff19421d6d.png
         * level_id : 1
         * level_starttime : 1566974769
         * level_endtime : 1767196799
         * gender : 未知
         * birthday : null
         * bio :
         * money : 105.01
         * score : 1
         * successions : 2
         * maxsuccessions : 2
         * prevtime : 1567046648
         * logintime : 1567064253
         * loginip : 59.53.226.117
         * loginfailure : 0
         * joinip : 182.96.51.211
         * jointime : 1566974769
         * createtime : 1566974769
         * updatetime : 1567582794
         * token : 138286d3372304f1a897688d59d8a6a8798343e6
         * status : normal
         * verification : {"email":0,"mobile":0}
         * pay_password : 0f8fad117740dc78cb2e834068b94fab
         * type : 商家
         * app_id : oNCUp44wCFmLNiJwLIra0OYwQiOo
         * rechage_money : 0.00
         * consumption_money : 0.00
         * edit_version : 0
         * source : mini
         * is_recharge : 0
         * url : /u/195
         */

        private int id;
        private int group_id;
        private String username;
        private String nickname;
        private Object password;
        private String salt;
        private String email;
        private String mobile;
        private String avatar;
        private int level_id;
        private int level_starttime;
        private int level_endtime;
        private String gender;
        private Object birthday;
        private String bio;
        private String money;
        private int score;
        private int successions;
        private int maxsuccessions;
        private int prevtime;
        private int logintime;
        private String loginip;
        private int loginfailure;
        private String joinip;
        private int jointime;
        private int createtime;
        private int updatetime;
        private String token;
        private String status;
        private VerificationBean verification;
        private String pay_password;
        private String type;
        private String app_id;
        private String rechage_money;
        private String consumption_money;
        private int edit_version;
        private String source;
        private String is_recharge;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getLevel_id() {
            return level_id;
        }

        public void setLevel_id(int level_id) {
            this.level_id = level_id;
        }

        public int getLevel_starttime() {
            return level_starttime;
        }

        public void setLevel_starttime(int level_starttime) {
            this.level_starttime = level_starttime;
        }

        public int getLevel_endtime() {
            return level_endtime;
        }

        public void setLevel_endtime(int level_endtime) {
            this.level_endtime = level_endtime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSuccessions() {
            return successions;
        }

        public void setSuccessions(int successions) {
            this.successions = successions;
        }

        public int getMaxsuccessions() {
            return maxsuccessions;
        }

        public void setMaxsuccessions(int maxsuccessions) {
            this.maxsuccessions = maxsuccessions;
        }

        public int getPrevtime() {
            return prevtime;
        }

        public void setPrevtime(int prevtime) {
            this.prevtime = prevtime;
        }

        public int getLogintime() {
            return logintime;
        }

        public void setLogintime(int logintime) {
            this.logintime = logintime;
        }

        public String getLoginip() {
            return loginip;
        }

        public void setLoginip(String loginip) {
            this.loginip = loginip;
        }

        public int getLoginfailure() {
            return loginfailure;
        }

        public void setLoginfailure(int loginfailure) {
            this.loginfailure = loginfailure;
        }

        public String getJoinip() {
            return joinip;
        }

        public void setJoinip(String joinip) {
            this.joinip = joinip;
        }

        public int getJointime() {
            return jointime;
        }

        public void setJointime(int jointime) {
            this.jointime = jointime;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public VerificationBean getVerification() {
            return verification;
        }

        public void setVerification(VerificationBean verification) {
            this.verification = verification;
        }

        public String getPay_password() {
            return pay_password;
        }

        public void setPay_password(String pay_password) {
            this.pay_password = pay_password;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getRechage_money() {
            return rechage_money;
        }

        public void setRechage_money(String rechage_money) {
            this.rechage_money = rechage_money;
        }

        public String getConsumption_money() {
            return consumption_money;
        }

        public void setConsumption_money(String consumption_money) {
            this.consumption_money = consumption_money;
        }

        public int getEdit_version() {
            return edit_version;
        }

        public void setEdit_version(int edit_version) {
            this.edit_version = edit_version;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getIs_recharge() {
            return is_recharge;
        }

        public void setIs_recharge(String is_recharge) {
            this.is_recharge = is_recharge;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class VerificationBean   implements Serializable{
            /**
             * email : 0
             * mobile : 0
             */

            private int email;
            private int mobile;

            public int getEmail() {
                return email;
            }

            public void setEmail(int email) {
                this.email = email;
            }

            public int getMobile() {
                return mobile;
            }

            public void setMobile(int mobile) {
                this.mobile = mobile;
            }
        }
    }
}
