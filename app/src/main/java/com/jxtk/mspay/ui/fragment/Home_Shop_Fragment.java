package com.jxtk.mspay.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyApplication;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.entity.ADEntity;
import com.jxtk.mspay.entity.ShopIndexBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.jxtk.mspay.ui.activity.InviteActivity;
import com.jxtk.mspay.ui.activity.LoginActivity;
import com.jxtk.mspay.ui.activity.PayActivity;
import com.jxtk.mspay.ui.activity.PayCodeActivity;
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
 */public class Home_Shop_Fragment extends MyLazyFragment {

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

    @BindView(R.id.cim_ad1)
    CircleImageView cimAd1;
    @BindView(R.id.cim_ad2)
    CircleImageView cimAd2;
    @BindView(R.id.cim_ad3)
    CircleImageView cimAd3;
    @BindView(R.id.cim_ad4)
    CircleImageView cimAd4;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_ordernum)
    TextView tvOrdernum;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_t1)
    TextView tvT1;
    @BindView(R.id.tv_t2)
    TextView tvT2;
    @BindView(R.id.tv_t3)
    TextView tvT3;
    List<ADEntity> adlist = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_shoper;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    int cl_action_height = ScreenUtil.dip2px(MyApplication.getContext(), 120);
    int nestedscrollview_height;

    @Override
    public void onResume() {
        super.onResume();
        loaddata("today");

    }

    @Override
    protected void initView() {

        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(5000);

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

    int i = 1;
    String data = "today";

    @OnClick({R.id.im_scan, R.id.im_paycode, R.id.im_scan2, R.id.tv_qrcode, R.id.cim_ad1, R.id.cim_ad2, R.id.cim_ad3, R.id.cim_ad4, R.id.tv_data})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
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
            case R.id.cim_ad1:
                startActivity(InviteActivity.class);
                break;
            case R.id.cim_ad2:
                jumpLinstener.tojump(1);
                break;
            case R.id.cim_ad3:
                jumpLinstener.tojump(2);
                break;
            case R.id.cim_ad4:
                if (adlist.get(3).getLinkurl().indexOf("ttp") > 0) {
                    intent.putExtra(Constant.Intent_TAG, adlist.get(3).getLinkurl());
                    intent.putExtra(Constant.Intent_TAG2, "美森宝");
                    startActivity(intent);
                }
                break;
            case R.id.tv_data:
                i++;
                if (i == 1) {
                    data = "today";
                    tvT1.setText("本日收入");
                    tvT2.setText("本日交易笔数");
                    tvT3.setText("本日单笔最大");
                } else if (i == 2) {
                    data = "month";
                    tvT1.setText("本月收入");
                    tvT2.setText("本月交易笔数");
                    tvT3.setText("本月单笔最大");
                } else if (i == 3) {
                    data = "year";
                    tvT1.setText("本年收入");
                    tvT2.setText("本年交易笔数");
                    tvT3.setText("本年单笔最大");
                    i = 0;
                }
                loaddata(data);

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

    private void loaddata(String data) {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().shopindex(Constant.TOKEN, data);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                ShopIndexBean shopIndexBean = JsonUtils.stringToObject(JsonUtils.getStringValue(result, "shopper"), ShopIndexBean.class);
                tvIncome.setText(shopIndexBean.getIncome());
                tvOrdernum.setText(shopIndexBean.getSum() + "");
                Constant.QRcode = shopIndexBean.getQr_code(); //获取收款码
                tvMax.setText(shopIndexBean.getMax());

                adlist = JSON.parseArray(JsonUtils.getStringValue(JsonUtils.getStringValue(result, "banner"), "data"), ADEntity.class);
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

    JumpLinstener jumpLinstener;

    public void setJumpLinstener(JumpLinstener jumpLinstener) {
        this.jumpLinstener = jumpLinstener;
    }

    public interface JumpLinstener {
        void tojump(int index);
    }


}
