package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.SignInBean;

import java.util.List;

public class SignInAdapter extends BaseQuickAdapter<SignInBean, BaseViewHolder> {
    public SignInAdapter(@Nullable List<SignInBean> data) {
        super(R.layout.item_signin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignInBean item) {
        helper.setText(R.id.tv_point, "+" + item.getIntegral())
                .setText(R.id.tv_time, item.getDate());
        ImageView imageView = helper.itemView.findViewById(R.id.imageView11);
        if (item.getIs_sigin() == 1) {
            imageView.setBackgroundResource(R.drawable.success_sign_in);
            helper.setGone(R.id.tv_point, false);
        }
        if (item.isIslast()) {
            helper.setGone(R.id.linearLayout6, false);
        }
    }
}
