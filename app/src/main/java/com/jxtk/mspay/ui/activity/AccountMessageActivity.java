package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.MessageListAdapter;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.MessageListBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/7 0007
 * description:
 */public class AccountMessageActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    MessageListAdapter messageListAdapter;
    List<MessageListBean> listBeans = new ArrayList<>();
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout smartrefresh;
    int pageindex = 1;
    boolean isrefresh = false;  //是否是刷新
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        title.setTitle("账户消息");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(listBeans);
        recyclerview.setAdapter(messageListAdapter);
        smartrefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageindex++;
                isrefresh = false;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageindex = 1;
                isrefresh = true;
                loadData();
            }
        });
    }

    @Override
    protected void initData() {
        loadData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void loadData() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("pagesize", "10");
        map.put("type","2");
        map.put("page", pageindex + "");
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getMessageList(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<MessageListBean> list = JSON.parseArray(JsonUtils.getStringValue(result, "list"), MessageListBean.class);
                if (null == list) {
                    if (listBeans.size() == 0) {
                        showEmpty();
                        return;
                    } else {
                        smartrefresh.finishLoadMoreWithNoMoreData();
                        smartrefresh.finishRefresh();
                    }
                } else {
                    if (isrefresh) {  //刷新
                        listBeans.clear();
                        listBeans.addAll(list);
                        messageListAdapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                    } else { //加载更多
                        listBeans.addAll(list);
                        messageListAdapter.notifyDataSetChanged();
                        smartrefresh.finishLoadMore();
                    }
                }
            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
                showComplete();
            }

        }));
    }
}
