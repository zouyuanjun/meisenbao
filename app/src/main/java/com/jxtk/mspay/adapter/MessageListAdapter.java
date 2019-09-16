package com.jxtk.mspay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxtk.mspay.R;
import com.jxtk.mspay.entity.MessageListBean;

import java.util.List;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/22 0022
 * description:
 */public class MessageListAdapter  extends BaseQuickAdapter<MessageListBean, BaseViewHolder> {

    public MessageListAdapter( @Nullable List<MessageListBean> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean item) {
        helper.setText(R.id.tv_meaage_time,item.getCreate_time())
                .setText(R.id.tv_meaage_title,item.getTitle())
                .setText(R.id.tv_message_content,item.getContent());

    }
}
