package com.jxtk.mspay.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.MonthBilBean;
import com.jxtk.mspay.ui.activity.BillDetailActivity;
import com.jxtk.mspay.ui.activity.RefundDetailActivity;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/22 0022
 * description:
 */public class MonthBillAdapter extends BaseQuickAdapter<MonthBilBean, BaseViewHolder> {
     public Activity context;

    public MonthBillAdapter( @Nullable List<MonthBilBean> data, Activity context) {
        super(R.layout.item_month_bill, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MonthBilBean item) {
        helper.setText(R.id.tv_time,item.getMonth())
        .setText(R.id.tv_money_in,item.getIncome()+"")
        .setText(R.id.tv_money_out,item.getExpenditure()+"");

        RecyclerView recyclerView=helper.itemView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        BillAdapter billAdapter=new BillAdapter(item.getData());
        recyclerView.setAdapter(billAdapter);
        billAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MonthBilBean.DataBean dataBean=item.getData().get(position);
                Intent intent;
                if (dataBean.getStatus()==2||dataBean.getStatus()==3){
                    intent=new Intent(context, RefundDetailActivity.class);
                }else {
                    intent=new Intent(context, BillDetailActivity.class);
                }
                intent.putExtra(Constant.Intent_TAG,dataBean);
                context.startActivity(intent);
            }
        });


    }
}
