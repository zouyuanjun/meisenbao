package com.jxtk.mspay.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.IntegralBean;
import com.jxtk.mspay.entity.MonthBilBean;
import com.zou.fastlibrary.image.ImageLoader;

import java.util.List;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/19 0019
 * description:
 */public class BillAdapter extends BaseQuickAdapter<MonthBilBean.DataBean, BaseViewHolder> {
    public BillAdapter(@Nullable List<MonthBilBean.DataBean> data) {
        super(R.layout.item_bill, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MonthBilBean.DataBean item) {
        helper.setText(R.id.tv_title,item.getName())
                .setText(R.id.tv_time,item.getCreatetime())
                .setText(R.id.tv_money_num,item.getGoods_money());
        ImageView imageView=helper.itemView.findViewById(R.id.im_listicon);

        switch (item.getGoods_type()){
            case "充值":
                imageView.setBackgroundResource(R.drawable.icon_service_top_up);
                break;
            case "充电":
                imageView.setBackgroundResource(R.drawable.icon_service_recharge);
                break;
            case "便利店":
                imageView.setBackgroundResource(R.drawable.icon_service_store);
                break;
            case "售货机":
                imageView.setBackgroundResource(R.drawable.icon_service_vendingmachine);
                break;
            case "收款":
                imageView.setBackgroundResource(R.drawable.icon_service_pay);
                break;
            case "升级会员":
                imageView.setBackgroundResource(R.drawable.icon_service_levelup);
                break;
            case "提现M币":
                imageView.setBackgroundResource(R.drawable.icon_service_withdrawcash);
                break;
        }
        TextView textView=helper.itemView.findViewById(R.id.tv_statu);
        if (item.getIs_handle()==1&&item.getStatus()==1){
            textView.setText("有待处理的退款");
            textView.setTextColor(Color.parseColor("#ff0101"));
        }else if (item.getStatus()==2){
            textView.setText("已同意退款");
            textView.setTextColor(Color.parseColor("#08be1e"));
        }else if (item.getStatus()==3){
            textView.setText("已拒绝退款");
            textView.setTextColor(Color.parseColor("#ff0101"));
        }else if (item.getIs_handle()==0&&item.getStatus()==1){
            textView.setText("等待商家处理退款");
            textView.setTextColor(Color.parseColor("#ff0101"));
        }
    }

}
