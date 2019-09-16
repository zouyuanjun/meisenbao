package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.InvoiceBean;
import com.zou.fastlibrary.image.ImageLoader;

import java.util.List;

public class InvoiceAdapter extends BaseQuickAdapter<InvoiceBean, BaseViewHolder> {

    public InvoiceAdapter(@Nullable List<InvoiceBean> data) {
        super(R.layout.item_invoice,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_time,item.getCreatetime())
                .setText(R.id.tv_money_num,item.getAmount());
        ImageView imageView=helper.itemView.findViewById(R.id.im_listicon);
        if (item.isIsselect()){
            imageView.setBackgroundResource(R.drawable.icon_bill_selected_blue);
        }else {
            imageView.setBackgroundResource(R.drawable.icon_bill_unselected_lightgray);
        }
    }
}
