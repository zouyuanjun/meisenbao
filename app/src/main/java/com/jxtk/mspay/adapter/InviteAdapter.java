package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.InvitePeopleBean;

import java.util.List;

public class InviteAdapter extends BaseQuickAdapter<InvitePeopleBean, BaseViewHolder> {
    public InviteAdapter(@Nullable List<InvitePeopleBean> data) {
        super(R.layout.item_invitepeople,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitePeopleBean item) {
        helper.setText(R.id.tv_nickname,item.getNickname())
                .setText(R.id.tv_phonenumber,item.getMobile());
    }
}
