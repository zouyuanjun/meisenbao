package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.ChargeMEntity;

import java.util.List;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/16 0016
 * description:
 */public class ChargeAdapter extends BaseQuickAdapter<ChargeMEntity, BaseViewHolder> {
    public ChargeAdapter(@Nullable List<ChargeMEntity> data) {
        super(R.layout.item_charge_m, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargeMEntity item) {
        helper.setText(R.id.tv_m, item.getMoney())
                .setText(R.id.tv_give, "送 "+item.getGive())
                .setText(R.id.tv_money, "￥"+item.getPay_money());
    }
}
