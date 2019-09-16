package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/9 0009
 * description:
 */public class RemarkActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.ed_edit)
    EditText edEdit;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remark;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        String remark = getIntent().getStringExtra(Constant.Intent_TAG);
        if (!StringUtil.isEmpty(remark)){
            edEdit.setText(remark);
        }

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        String s = "";
        Intent intent = new Intent();
        intent.putExtra(Constant.Intent_TAG, s);
        setResult(Constant.Remark_Backcode, intent);
        finish();
    }

    @Override
    public void onLeftClick(View v) {
        String s = edEdit.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Constant.Intent_TAG, s);
        setResult(Constant.Remark_Backcode, intent);
        finish();
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

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        String s = edEdit.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Constant.Intent_TAG, s);
        setResult(Constant.Remark_Backcode, intent);
        finish();
    }
}
