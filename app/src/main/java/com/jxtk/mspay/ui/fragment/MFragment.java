package com.jxtk.mspay.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.jxtk.mspay.ui.activity.LoginActivity;
import com.jxtk.mspay.ui.activity.RechargeActivity;
import com.jxtk.mspay.ui.activity.ShopActivity;
import com.jxtk.mspay.ui.activity.TixianActivity;
import com.jxtk.mspay.ui.activity.WebActivity;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

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
 */public class MFragment extends MyLazyFragment {
    @BindView(R.id.tv_m_num)
    TextView tvMNum;
    @BindView(R.id.recharge)
    TextView recharge;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.imageView8)
    ImageView imageView8;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    Unbinder unbinder;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.imageView10)
    ImageView imageView10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_m;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        if (Constant.ISSHOPER == 1) {
            recharge.setText("提现");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != Constant.userInfoBean)
            tvMNum.setText(Constant.userInfoBean.getMoney());
        if (Constant.DARK_THEME == 1) {
            title.setTitleColor(Color.parseColor("#ffffff"));
            title.setBackgroundColor(Color.parseColor("#2f3640"));
        }else {
            title.setTitleColor(Color.parseColor("#333333"));
            title.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        initData();
    }
    @Override
    protected void initData() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getM(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                Constant.M_MONEY = JsonUtils.getStringValue(result, "money");
                tvMNum.setText(Constant.M_MONEY);
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

    @OnClick({R.id.recharge, R.id.imageView7, R.id.imageView8, R.id.imageView9})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), WebActivity.class);

        switch (view.getId()) {
            case R.id.recharge:
                if (Constant.ISSHOPER == 1) {
                    startActivity(TixianActivity.class);
                } else {
                    startActivity(RechargeActivity.class);
                }

                break;
            case R.id.imageView7:
                intent.putExtra(Constant.Intent_TAG, "25");
                intent.putExtra(Constant.Intent_TAG2, "充电");
                startActivity(intent);
                break;
            case R.id.imageView8:
                startActivity(ShopActivity.class);
                break;
            case R.id.imageView9:
                intent.putExtra(Constant.Intent_TAG, "26");
                intent.putExtra(Constant.Intent_TAG2, "售货机");
                startActivity(intent);
                break;

        }
    }

}
