package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpDetailActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_help_detail)
    TextView tvHelpDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_detail;

    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        title.setTitle(getIntent().getStringExtra(Constant.Intent_TAG));
        tvHelpDetail.setText(getIntent().getStringExtra(Constant.Intent_TAG2));
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
}
