package com.jxtk.mspay.common;

import com.gyf.barlibrary.ImmersionBar;
import com.jxtk.mspay.Constant;
import com.zou.fastlibrary.base.BaseLazyFragment;
import com.zou.fastlibrary.utils.Log;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 支持沉浸式Fragment懒加载基类（默认不开启沉浸式）
 */
public abstract class UILazyFragment<A extends UIActivity> extends BaseLazyFragment<A> {

    private ImmersionBar mImmersionBar; // 状态栏沉浸

    @Override
    protected void initFragment() {
        initImmersion();
        super.initFragment();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersion() {

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            statusBarConfig().init();

            // 设置标题栏
            if (getTitleId() > 0) {
                ImmersionBar.setTitleBar(mActivity, findViewById(getTitleId()));
            }
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    protected ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    public ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .navigationBarDarkIcon(true)
                .fullScreen(false)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(true);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        if (Constant.DARK_THEME == 1) {
            mImmersionBar.navigationBarColor("#2f3640")
                    .navigationBarDarkIcon(true);
        } else {
            mImmersionBar.transparentNavigationBar()
             .fullScreen(false);
        }
        return mImmersionBar;
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        //返回true表示黑色字体
        if (Constant.DARK_THEME == 1) {
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) mImmersionBar.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isStatusBarEnabled() && isLazyLoad()) {
            // 重新初始化状态栏
            Log.d("重新初始化状态栏");
            statusBarConfig().init();
        }
    }
}