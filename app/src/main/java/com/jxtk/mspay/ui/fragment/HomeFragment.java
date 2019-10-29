package com.jxtk.mspay.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyApplication;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.entity.ADEntity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.jxtk.mspay.ui.activity.AboutActivity;
import com.jxtk.mspay.ui.activity.InviteActivity;
import com.jxtk.mspay.ui.activity.LoginActivity;
import com.jxtk.mspay.ui.activity.PayActivity;
import com.jxtk.mspay.ui.activity.PayCodeActivity;
import com.jxtk.mspay.ui.activity.RechargeActivity;
import com.jxtk.mspay.ui.activity.ShopActivity;
import com.jxtk.mspay.ui.activity.WebActivity;
import com.jxtk.mspay.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.widget.CircleImageView;
import com.zou.fastlibrary.widget.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/29 0029
 * description:
 */public class HomeFragment extends MyLazyFragment {
    @BindView(R.id.im_scan)
    ImageView imScan;
    @BindView(R.id.im_paycode)
    ImageView imPaycode;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.nestedscrollview)
    MyScrollView nestedscrollview;
    @BindView(R.id.cl_action)
    ConstraintLayout clAction;
    @BindView(R.id.ll_hide)
    LinearLayout llHide;
    List<String> sbannerlist = new ArrayList<>();
    List<ADEntity> adlist = new ArrayList<>();
    @BindView(R.id.cim_ad1)
    CircleImageView cimAd1;
    @BindView(R.id.cim_ad2)
    CircleImageView cimAd2;
    @BindView(R.id.cim_ad3)
    CircleImageView cimAd3;
    @BindView(R.id.cim_ad4)
    CircleImageView cimAd4;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    int cl_action_height = ScreenUtil.dip2px(MyApplication.getContext(), 120);
    int nestedscrollview_height;

    @Override
    protected void initView() {

        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(5000);
//        nestedscrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int []location=new int[2];
//                nestedscrollview.getLocationInWindow(location);
//                cl_action_height=location[1];
//            }
//        });

        nestedscrollview.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY > cl_action_height / 3) {
                    llHide.setVisibility(View.VISIBLE);
                    float alpha = ((float) (3 * scrollY / (float) cl_action_height) - 1);
                    llHide.setAlpha(alpha);
                    clAction.setAlpha(1 - alpha);
                } else {
                    llHide.setVisibility(View.GONE);
                    clAction.setAlpha(1);
                }
                View onlyChild = nestedscrollview.getChildAt(0);
                if (onlyChild.getHeight() <= scrollY + nestedscrollview.getHeight()) {   // 如果满足就是到底部了
                    if (scrollY > ScreenUtil.dip2px(MyApplication.getContext(), 40)) {
                        llHide.setVisibility(View.VISIBLE);
                        llHide.setAlpha(1);
                        clAction.setAlpha(0);
                    }
                }
            }
        });
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getbanner(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                List<ADEntity> bannerlist = JSON.parseArray(JsonUtils.getStringValue(result, "selected"), ADEntity.class);
                adlist = JSON.parseArray(JsonUtils.getStringValue(result, "banner"), ADEntity.class);
                if (null != bannerlist && bannerlist.size() > 0) {
                    for (ADEntity adEntity : bannerlist) {
                        sbannerlist.add(adEntity.getImageurl());
                    }
                    banner.setImages(sbannerlist);
                    banner.start();
                }
                if (null != adlist && adlist.size() > 0) {
                    for (int i = 0; i < adlist.size(); i++) {
                        switch (i) {
                            case 0:
                                ImageLoader.loadImage(cimAd1, Constant.BASE_URL + adlist.get(i).getImageurl());
                                break;
                            case 1:
                                ImageLoader.loadImage(cimAd2, Constant.BASE_URL + adlist.get(i).getImageurl());
                                break;
                            case 2:
                                ImageLoader.loadImage(cimAd3, Constant.BASE_URL + adlist.get(i).getImageurl());
                                break;
                            case 3:
                                ImageLoader.loadImage(cimAd4, Constant.BASE_URL + adlist.get(i).getImageurl());
                                break;
                        }
                    }
                }

            }

            @Override
            public void onFault(String errorMsg) {
                if (errorMsg.equals("请重新登陆")) {
                    startActivity(LoginActivity.class);
                }
                showComplete();
                toast(errorMsg);
            }
        }));
    }

    @Override
    protected void initData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.im_scan, R.id.im_paycode, R.id.im_scan2, R.id.tv_qrcode,R.id.cim_charge, R.id.cim_shoper, R.id.cim_vending_machine, R.id.cim_ad1, R.id.cim_ad2, R.id.cim_ad3, R.id.cim_ad4})
    public void onViewClicked(View view) {
        Intent intent=new Intent(getActivity(), WebActivity.class);
        switch (view.getId()) {


            case R.id.im_scan:
                startActivity(PayActivity.class);
                break;
            case R.id.im_paycode:
                startActivity(PayCodeActivity.class);
                break;
            case R.id.im_scan2:
                startActivity(PayActivity.class);
                break;
            case R.id.tv_qrcode:
                startActivity(PayCodeActivity.class);
                break;
            case R.id.cim_charge:
                intent.putExtra(Constant.Intent_TAG,"25");
                intent.putExtra(Constant.Intent_TAG2,"充电");
                startActivity(intent);
                break;
            case R.id.cim_shoper:
                startActivity(ShopActivity.class);
                break;
            case R.id.cim_vending_machine:
                intent.putExtra(Constant.Intent_TAG,"26");
                intent.putExtra(Constant.Intent_TAG2,"售货机");
                startActivity(intent);
                break;
            case R.id.cim_ad1:
                startActivity(InviteActivity.class);
                break;
            case R.id.cim_ad2:
                jumpLinstener.tojump(1);
                break;
            case R.id.cim_ad3:
                startActivity(RechargeActivity.class);
                break;
            case R.id.cim_ad4:
                if (adlist.get(3).getLinkurl().indexOf("ttp")>0){
                    intent.putExtra(Constant.Intent_TAG,adlist.get(3).getLinkurl());
                    intent.putExtra(Constant.Intent_TAG2,"美森宝");
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            String code = data.getStringExtra("result_string");
            if (null != code) {
                //   startActivity(PayActivity.class);
            }
            Log.d(code);
        }

    }

    @Override
    protected boolean statusBarDarkFont() {
        return false;
    }
Home_Shop_Fragment.JumpLinstener jumpLinstener;

    public void setJumpLinstener(Home_Shop_Fragment.JumpLinstener jumpLinstener) {
        this.jumpLinstener = jumpLinstener;
    }

    public interface JumpLinstener {
        void tojump(int index);
    }


}
