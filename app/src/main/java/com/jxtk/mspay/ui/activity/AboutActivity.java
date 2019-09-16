package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/16 0016
 * description:
 */public class AboutActivity extends MyActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bg_introduce)
    public void onViewClicked() {
        Intent intent=new Intent(AboutActivity.this,WebActivity.class);
        intent.putExtra(Constant.Intent_TAG,"13");
        intent.putExtra(Constant.Intent_TAG2,"美森宝介绍");
        startActivity(intent);
    }
}
