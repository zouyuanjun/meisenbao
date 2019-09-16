package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.zou.fastlibrary.image.ImageLoader;
import com.jxtk.mspay.utils.BGLinearlayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jxtk.mspay.Constant.userInfoBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class MyAccoutActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tv_nicknema)
    TextView tvNicknema;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_phonenumber)
    TextView tvPhonenumber;
    @BindView(R.id.tv_updateinfo)
    TextView tvUpdateinfo;
    @BindView(R.id.tv_invoice)
    BGLinearlayout tvInvoice;
    @BindView(R.id.tv_myaddress)
    BGLinearlayout tvMyaddress;
    @BindView(R.id.bg_mybank)
    BGLinearlayout bgMybank;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myaccount;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != userInfoBean) {
            ImageLoader.loadImage(circleImageView, Constant.BASE_URL + userInfoBean.getAvatar());
            tvNicknema.setText(userInfoBean.getNickname());
            tvSex.setText(userInfoBean.getSex());
            tvPhonenumber.setText(userInfoBean.getMobile());
        }
        if (Constant.ISSHOPER == 1) {
            tvMyaddress.setVisibility(View.GONE);
            tvInvoice.setVisibility(View.GONE);
        } else {
            bgMybank.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_invoice, R.id.tv_myaddress, R.id.tv_updateinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_invoice:
                startActivity(MyInvoiceActivity.class);
                break;
            case R.id.tv_myaddress:
                startActivity(MyAddressActivity.class);
                break;
            case R.id.tv_updateinfo:
                startActivity(UpadateInfoActivity.class);
                break;
        }
    }


    @OnClick(R.id.bg_mybank)
    public void onViewClicked() {
        startActivity(MyBankActivity.class);
    }
}
