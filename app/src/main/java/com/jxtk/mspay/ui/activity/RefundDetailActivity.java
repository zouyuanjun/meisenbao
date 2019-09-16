package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.MonthBilBean;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.widget.SettingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/23 0023
 * description:
 */public class RefundDetailActivity extends MyActivity {
    MonthBilBean.DataBean dataBean;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.im_icon)
    ImageView imIcon;
    @BindView(R.id.tv_shopname)
    TextView tvShopname;
    @BindView(R.id.tv_paynumber)
    TextView tvPaynumber;
    @BindView(R.id.tv_statu)
    TextView tvStatu;
    @BindView(R.id.sb_ordernumber)
    SettingBar sbOrdernumber;
    @BindView(R.id.sb_creattime)
    SettingBar sbCreattime;
    @BindView(R.id.sv_ordernum)
    SettingBar svOrdernum;
    @BindView(R.id.sb_refund_time)
    SettingBar sbRefundTime;
    @BindView(R.id.sb_refund_time2)
    SettingBar sbRefundTime2;
    @BindView(R.id.sb_refund_reason)
    SettingBar sbRefundReason;
    @BindView(R.id.sb_refund_money)
    SettingBar sbRefundMoney;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_detail;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        dataBean = (MonthBilBean.DataBean) getIntent().getSerializableExtra(Constant.Intent_TAG);
    }

    @Override
    protected void initData() {
        if (null != dataBean) {
            tvShopname.setText(dataBean.getName());
            tvPaynumber.setText(dataBean.getGoods_money());
            sbCreattime.setRightText(dataBean.getCreatetime());
            sbOrdernumber.setRightText(dataBean.getOrder_sn());
            svOrdernum.setRightText(dataBean.getGoods_money());
            sbRefundReason.setRightText(dataBean.getRefund().getMemo());
            sbRefundTime.setRightText(dataBean.getRefuse_apply_time());
            sbRefundTime2.setRightText(dataBean.getRefuse_time());
            sbRefundMoney.setRightText(dataBean.getRefuse_money() + "");
            ImageLoader.loadImage(imIcon, Constant.BASE_URL + dataBean.getImg());
            if (dataBean.getStatus()==2){
                tvStatu.setText("已同意用户退款");
            }else {
                tvStatu.setText("已拒绝用户退款");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
