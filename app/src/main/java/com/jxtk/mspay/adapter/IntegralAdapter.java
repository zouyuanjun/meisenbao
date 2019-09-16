package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.IntegralBean;
import com.jxtk.mspay.entity.MonthPointBean;

import java.util.List;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/19 0019
 * description:
 */public class IntegralAdapter extends BaseQuickAdapter<MonthPointBean.DataBean, BaseViewHolder> {
    public IntegralAdapter( @Nullable List<MonthPointBean.DataBean> data) {
        super(R.layout.item_integral, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MonthPointBean.DataBean item) {
        helper.setText(R.id.tv_title,item.getInstructions())
                .setText(R.id.tv_time,item.getCreatetime())
                .setText(R.id.tv_integral_num,item.getIntegral()+"");
    }

}
