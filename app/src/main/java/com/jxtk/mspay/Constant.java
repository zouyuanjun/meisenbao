package com.jxtk.mspay;

import com.jxtk.mspay.common.MyApplication;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.entity.BankBean;
import com.jxtk.mspay.entity.MessageNum;
import com.jxtk.mspay.entity.UserInfoBean;
import com.zou.fastlibrary.utils.DataKeeper;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/9 0009
 * description:
 */public class Constant {
    public static String TOKEN_KEY="TOKEN_KEY";
    public static String ISSHOPER_KEY="ISSHOPER_KEY";
    public static String KEY_FINGER_PAY="KEY_FINGER_PAY";
    public static String KEY_DARK_THEME="KEY_DARK_THEME";
    public static int ISSHOPER=DataKeeper.getIntKey(MyApplication.getContext(),"ISSHOPER_KEY");
public static String QRcode; //收款码


    public static String Intent_TAG = "A";
    public static String Intent_TAG2 = "B";
    public static int Remark_Requestcode = 2;
    public static int ScanCode = 1;
    public static int Remark_Backcode = 2;
    public static String BASE_URL = "http://app.meisenpay.com/";
    public static String TOKEN= DataKeeper.getStringKey(MyApplication.getContext(),"TOKEN_KEY");
    public static String WEBSOCKET="ws://122.112.182.112:2347";
    public static AddressBean addressBean;
    public static BankBean bankBean;
    public static String FIRST_OPEN="FIRST_OPEN";
    public static String defValue="defValue";

    public static String M_MONEY="";
    public static UserInfoBean userInfoBean;
    public static MessageNum messageNum;
    public static String WECHAT_ID="wxa32eb20e28d88d8c";
 //   public static String WEBSOCKET="ws://123.207.167.163:9010/ajaxchattest";
    public static int FINGER_PAY=DataKeeper.getIntKey(MyApplication.getContext(),KEY_FINGER_PAY);
    public static int DARK_THEME=DataKeeper.getIntKey(MyApplication.getContext(),KEY_DARK_THEME);  //主题颜色，1是黑暗，0是亮色

}
