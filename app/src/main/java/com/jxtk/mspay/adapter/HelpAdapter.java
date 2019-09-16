package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.HelpBean;

import java.util.List;

public class HelpAdapter  extends BaseQuickAdapter<HelpBean, BaseViewHolder> {
    public HelpAdapter(@Nullable List<HelpBean> data) {
        super(R.layout.item_help_1,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpBean item) {
        helper.setText(R.id.tv_help_title,item.getTitle());
    }
}
