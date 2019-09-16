package com.jxtk.mspay.utils;

import android.content.Context;

import com.zou.fastlibrary.utils.Log;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/4/1 0001
 * description:
 */public class MyJPushMessageReceiver extends JPushMessageReceiver {
    public MyJPushMessageReceiver() {
        super();
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        //tag增删查改的操作会在此方法中回调结果。
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        Log.d("设置标签");
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        //设置手机号码会在此方法中回调结果。
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

}
