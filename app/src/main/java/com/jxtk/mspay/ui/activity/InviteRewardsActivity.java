package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.InviteAdapter;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.InvitePeopleBean;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.widget.SettingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteRewardsActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.sb_invitenum)
    SettingBar sbInvitenum;
    @BindView(R.id.sb_reward_point)
    SettingBar sbRewardPoint;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_rule)
    TextView tvRule;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_rewards;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        String data = getIntent().getStringExtra(Constant.Intent_TAG);
        String sum = JsonUtils.getStringValue(data, "sum");
        String sum_people = JsonUtils.getStringValue(data, "sum_people");
        String rule = JsonUtils.getStringValue(data, "rule");
        sbInvitenum.setRightText(sum_people);
        sbRewardPoint.setRightText(sum+"积分");
        tvRule.setText(rule);
        List<InvitePeopleBean> list= JSON.parseArray(JsonUtils.getStringValue(data,"people"),InvitePeopleBean.class);
        if (null!=list){
            recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerview.setAdapter(new InviteAdapter(list));
        }else {
            recyclerview.setVisibility(View.GONE);
        }
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

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent=new Intent(getBaseContext(),WebActivity.class);
        intent.putExtra(Constant.Intent_TAG,"12");
        intent.putExtra(Constant.Intent_TAG2,"邀请好友奖励细则");
        startActivity(intent);
    }
}
