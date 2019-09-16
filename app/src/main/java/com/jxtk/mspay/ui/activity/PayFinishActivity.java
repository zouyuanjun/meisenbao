package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/9 0009
 * description:
 */public class PayFinishActivity extends MyActivity {
    @BindView(R.id.tv_paynumber)
    TextView tvPaynumber;
    @BindView(R.id.tv_payname)
    TextView tvPayname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_paysuccessful;
    }

    @Override
    protected int getTitleId() {
        return R.id.tv_finish;
    }

    @Override
    protected void initView() {
        getStatusBarConfig().statusBarColor("#ffffff").init();
        String money=getIntent().getStringExtra(Constant.Intent_TAG);
        String nickname=getIntent().getStringExtra(Constant.Intent_TAG2);
        tvPayname.setText(nickname);
        tvPaynumber.setText(money);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        finish();
    }


}
