package com.jxtk.mspay.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.ui.activity.MessageCenterActivity;
import com.jxtk.mspay.ui.activity.WelcomeActivity;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/4/1 0001
 * description:
 */public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "TalkReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush 用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.d("推送的内容为", extras);
            // Push Talk messages are push down by custom message format
        //   processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");
            Log.d("推送的内容为", bundle.toString());

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            Intent intent1 = new Intent(context, MessageCenterActivity.class);
            String extras = bundle.getString(JPushInterface.EXTRA_ALERT);
              intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // intent1.putExtra(Constant.Intent_TAG,extras);
             context.startActivity(intent1);
            com.zou.fastlibrary.utils.Log.d("》》》》》》》》》");

        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d("推送的内容为", extras);
        Intent intent = new Intent(context,MessageCenterActivity.class);
        // 此处必须兼容android O设备，否则系统版本在O以上可能不展示通知栏
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "推送", NotificationManager.IMPORTANCE_HIGH);
            channel.setBypassDnd(true);    //设置绕过免打扰模式
            channel.canBypassDnd();       //检测是否绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
            channel.setDescription("接收普通消息");
            channel.setLightColor(Color.GREEN);
            channel.setName("channe1");
            channel.setShowBadge(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentText(message).setSmallIcon(R.drawable.logomeisenpay);
            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setChannelId("1");
            builder.setAutoCancel(true);
            if (intent != null) {
                PendingIntent intent2 = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(intent2);
                notification = builder.build();
            } else {
                notification = builder.build();
            }
        } else {
            // 使用notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentText(message).setSmallIcon(R.drawable.logomeisenpay);
            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setChannelId("1");
            builder.setContentTitle("美森宝");
            builder.setAutoCancel(true);
            if (intent != null) {
                PendingIntent intent2 = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(intent2);
            }
            notification = builder.build();
        }
        notificationManager.notify(1, notification);

    }

}
