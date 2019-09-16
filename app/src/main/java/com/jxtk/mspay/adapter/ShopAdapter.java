package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.ShopBean;
import com.zou.fastlibrary.image.ImageLoader;

import java.util.List;

public class ShopAdapter extends BaseQuickAdapter<ShopBean, BaseViewHolder> {
    public ShopAdapter(@Nullable List<ShopBean> data) {
        super(R.layout.item_shop,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean item) {
        helper.setText(R.id.tv_shop_name,item.getName())
                .setText(R.id.tv_shop_address,item.getShop_address())
                .setText(R.id.tv_location,item.getDistance())
        .addOnClickListener(R.id.bt_pay);
        ImageView imageView=helper.itemView.findViewById(R.id.cim_shopicon);
        ImageLoader.loadImage(imageView, Constant.BASE_URL+item.getCard_head());

    }
}
