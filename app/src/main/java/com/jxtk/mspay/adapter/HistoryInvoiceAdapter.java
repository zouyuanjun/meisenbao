package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.InvoiceBean;

import java.util.List;

public class HistoryInvoiceAdapter extends BaseQuickAdapter<InvoiceBean, BaseViewHolder> {

    public HistoryInvoiceAdapter(@Nullable List<InvoiceBean> data) {
        super(R.layout.item_history_invoice,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_time,item.getCreatetime())
                .setText(R.id.tv_money_num,item.getAmount());
        ImageView imageView=helper.itemView.findViewById(R.id.im_listicon);
        imageView.setVisibility(View.GONE);
    }
}
