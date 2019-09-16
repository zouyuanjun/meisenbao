package com.jxtk.mspay.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.HelpBean;
import com.jxtk.mspay.entity.ShopTypeBean;

import java.util.List;

public class FiltrateAdapter extends BaseQuickAdapter<ShopTypeBean, BaseViewHolder> {
    public FiltrateAdapter(@Nullable List<ShopTypeBean> data) {
        super(R.layout.item_filtrate,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopTypeBean item) {
        TextView textView=helper.itemView.findViewById(R.id.tv_filt);
        textView.setText(item.getName());
        if (item.isSelect()){
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setBackgroundResource(R.drawable.selector_button_5);
        }else {
            textView.setTextColor(Color.parseColor("#7f8fa6"));
            textView.setBackgroundResource(R.drawable.shape_corners_5_1);
        }

    }
}
