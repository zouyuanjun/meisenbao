package com.jxtk.mspay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.blankj.utilcode.util.AppUtils;
import com.hjq.permissions.OnPermission;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.ui.fragment.HomeFragment;
import com.jxtk.mspay.ui.fragment.Home_Shop_Fragment;
import com.jxtk.mspay.ui.fragment.MFragment;
import com.jxtk.mspay.ui.fragment.MallFragment;
import com.jxtk.mspay.ui.fragment.MyCentreFragment;
import com.zou.fastlibrary.base.BaseFragmentAdapter;
import com.zou.fastlibrary.helper.ActivityStackManager;
import com.zou.fastlibrary.helper.DoubleClickHelper;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.widget.NoScrollViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends MyActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener, OnPermission {

    @BindView(R.id.vp_home_pager)
    NoScrollViewPager vpHomePager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView bvHomeNavigation;
    private BaseFragmentAdapter<MyLazyFragment> mPagerAdapter;

    Home_Shop_Fragment home_shop_fragment=new Home_Shop_Fragment();
    HomeFragment homeFragment=new HomeFragment();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        Log.d("MainActivity"+MainActivity.this.getTaskId());
        bvHomeNavigation.setOnNavigationItemSelectedListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.DARK_THEME==1){
            bvHomeNavigation.setBackgroundColor(Color.parseColor("#2f3640"));
        }else {
            bvHomeNavigation.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bvHomeNavigation.setItemIconTintList(null);
    }

    @Override
    protected void initData() {
        if (null!=home_shop_fragment){
            home_shop_fragment.setJumpLinstener(new Home_Shop_Fragment.JumpLinstener() {
                @Override
                public void tojump(int index) {
                    if (index==1){
                        bvHomeNavigation.setSelectedItemId(R.id.home_message);
                    }else if (index==2){
                        bvHomeNavigation.setSelectedItemId(R.id.home_found);
                    }
                }
            });
        }
        if (null!=homeFragment){
            homeFragment.setJumpLinstener(new Home_Shop_Fragment.JumpLinstener() {
                @Override
                public void tojump(int index) {
                    if (index==1){
                        bvHomeNavigation.setSelectedItemId(R.id.home_message);
                    }
                }
            });
        }

        mPagerAdapter = new BaseFragmentAdapter<>(this);
        if (Constant.ISSHOPER==1){
            mPagerAdapter.addFragment(home_shop_fragment);
        }else {
            mPagerAdapter.addFragment(homeFragment);
        }

        mPagerAdapter.addFragment(new MFragment());
        mPagerAdapter.addFragment(new MallFragment());
        mPagerAdapter.addFragment(new MyCentreFragment());
        vpHomePager.setAdapter(mPagerAdapter);

        // 限制页面数量
        vpHomePager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                //mViewPager.setCurrentItem(0);
                //mViewPager.setCurrentItem(0, false);
                // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                vpHomePager.setCurrentItem(0, vpHomePager.getCurrentItem() == 1);
                return true;
            case R.id.home_found:
                //mViewPager.setCurrentItem(1);
                //mViewPager.setCurrentItem(1, false);
                vpHomePager.setCurrentItem(1, vpHomePager.getCurrentItem() == 0 || vpHomePager.getCurrentItem() == 2);
                return true;
            case R.id.home_message:
                //mViewPager.setCurrentItem(2);
                //mViewPager.setCurrentItem(2, false);
                vpHomePager.setCurrentItem(2, vpHomePager.getCurrentItem() == 1 || vpHomePager.getCurrentItem() == 3);
                return true;
            case R.id.home_mycenter:
                //mViewPager.setCurrentItem(2);
                //mViewPager.setCurrentItem(2, false);
                vpHomePager.setCurrentItem(3, vpHomePager.getCurrentItem() == 2);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                bvHomeNavigation.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                bvHomeNavigation.setSelectedItemId(R.id.home_found);
                break;
            case 2:
                bvHomeNavigation.setSelectedItemId(R.id.home_message);
                break;
            case 3:
                bvHomeNavigation.setSelectedItemId(R.id.home_mycenter);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 回调当前 Fragment 的 onKeyDown 方法
        if (mPagerAdapter.getCurrentFragment().onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                    // 销毁进程
                    System.exit(0);
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return false;
    }

}
