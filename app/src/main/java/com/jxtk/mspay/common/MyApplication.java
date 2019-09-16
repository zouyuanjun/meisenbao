package com.jxtk.mspay.common;

import android.app.Application;
import android.content.Context;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.entity.UserInfoBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

import static com.jxtk.mspay.Constant.userInfoBean;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的 Application 基类
 */
public class MyApplication extends Application {
private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        initSDK(this);
        context=this;
        ZXingLibrary.initDisplayOpinion(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
        CrashReport.initCrashReport(getApplicationContext(), "80585cdc29", false);
        Constant.TOKEN= DataKeeper.getStringKey(MyApplication.getContext(),"TOKEN_KEY");
        if (null!=Constant.TOKEN&&!Constant.TOKEN.equals("defValue")){
            Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getUserInfo(Constant.TOKEN);
            HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                @Override
                public void onSuccess(String result) {
                    Log.d(result);
                    userInfoBean = JsonUtils.stringToObject(result, UserInfoBean.class);

                }

                @Override
                public void onFault(String errorMsg) {
                }
            }));
        }

    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSDK(Application application) {
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(application, null);


        // 初始化图片加载器
        ImageLoader.init(application);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}