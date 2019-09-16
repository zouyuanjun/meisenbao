package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 */public class BillDetailActivity extends MyActivity {
    MonthBilBean.DataBean dataBean;
    @BindView(R.id.im_icon)
    ImageView imIcon;
    @BindView(R.id.tv_shopname)
    TextView tvShopname;
    @BindView(R.id.tv_paynumber)
    TextView tvPaynumber;
    @BindView(R.id.sb_paysort)
    SettingBar sbPaysort;
    @BindView(R.id.sb_giveintrage)
    SettingBar sbGiveintrage;
    @BindView(R.id.sb_goodsexplain)
    SettingBar sbGoodsexplain;
    @BindView(R.id.sb_creattime)
    SettingBar sbCreattime;
    @BindView(R.id.sb_ordernumber)
    SettingBar sbOrdernumber;
    @BindView(R.id.sb_billsort)
    SettingBar sbBillsort;
    @BindView(R.id.ll_refund)
    LinearLayout llRefund;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.tv_statu)
    TextView tvStatu;





    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill_detail;
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
            sbGoodsexplain.setRightText(dataBean.getMemo());
            sbBillsort.setRightText(dataBean.getGoods_type());
            sbOrdernumber.setRightText(dataBean.getOrder_sn());
            sbGiveintrage.setRightText(dataBean.getIntegral() + "");
            ImageLoader.loadImage(imIcon, Constant.BASE_URL + dataBean.getImg());
            if (dataBean.getIs_apply()==0) {
                llRefund.setVisibility(View.GONE);
            }
            if (dataBean.getStatus() == 1 && dataBean.getIs_handle() == 1) {
                llRefund.setVisibility(View.VISIBLE);
                tvRefund.setText("有退款待处理");
                tvRefund.setTextColor(Color.parseColor("#ff0101"));
                tvStatu.setText("退款待处理");
                tvStatu.setTextColor(Color.parseColor("#ff0101"));
            } else if (dataBean.getStatus()==2){
                tvStatu.setText("已同意退款");
                tvStatu.setTextColor(Color.parseColor("#08be1e"));
            }else if (dataBean.getStatus()==3){
                tvStatu.setText("已拒绝退款");
                tvStatu.setTextColor(Color.parseColor("#ff0101"));
            }else if (dataBean.getIs_handle()==0&&dataBean.getStatus()==1){
                tvStatu.setText("等待商家退款退款");
                tvStatu.setTextColor(Color.parseColor("#ff0101"));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_refund)
    public void onViewClicked() {
        if (dataBean.getStatus() == 1 && dataBean.getIs_handle() == 1) {
            Intent intent = new Intent(getBaseContext(), RefundDealActivity.class);
            intent.putExtra(Constant.Intent_TAG, dataBean);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getBaseContext(), RefundActivity.class);
            intent.putExtra(Constant.Intent_TAG, dataBean.getId() + "");
            intent.putExtra(Constant.Intent_TAG2, dataBean.getGoods_money());
            startActivity(intent);
            finish();
        }

    }
}
