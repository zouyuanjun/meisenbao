package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.HelpBean2;
import com.zou.fastlibrary.image.ImageLoader;

import java.util.List;

public class HelpAdapter2 extends BaseQuickAdapter<HelpBean2, BaseViewHolder> {
    public HelpAdapter2(@Nullable List<HelpBean2> data) {
        super(R.layout.item_help_2, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpBean2 item) {
        helper.setText(R.id.tv_help_title, item.getNickname());
        ImageView imageView = helper.itemView.findViewById(R.id.im_help);
      //  ImageLoader.loadImage(imageView, Constant.BASE_URL + item.getImage());
        switch (item.getNickname()){
            case "M币":
                imageView.setBackgroundResource(R.drawable.icon_help_money);
                break;
            case "积分":
                imageView.setBackgroundResource(R.drawable.icon_help_point);
                break;
            case "等级":
                imageView.setBackgroundResource(R.drawable.icon_help_level);
                break;
            case "账单":
                imageView.setBackgroundResource(R.drawable.icon_help_bill);
                break;
            case "发票":
                imageView.setBackgroundResource(R.drawable.icon_help_invoice);
                break;
            case "商家":
                imageView.setBackgroundResource(R.drawable.icon_help_merchant);
                break;
        }


    }
}
