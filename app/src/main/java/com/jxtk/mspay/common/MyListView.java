package com.jxtk.mspay.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.IntegralAdapter;
import com.jxtk.mspay.entity.IntegralBean;
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
import java.lang.reflect.Type;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MyListView extends MyActivity {


    @BindView(R.id.title)
  public   TitleBar title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefresh)
     public SmartRefreshLayout smartrefresh;
    public BaseQuickAdapter adapter;

    public boolean isrefresh = false;  //是否是刷新
    public int pageindex = 1;
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


        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        recyclerview.setAdapter(adapter);
        smartrefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageindex++;
                isrefresh = false;
                loadData();
                smartrefresh.finishLoadMore(3000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageindex = 1;
                isrefresh = true;
                loadData();
                smartrefresh.finishRefresh(3000);
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
    protected void loadData() {
        showLoading();
//        Map<String, String> map = new HashMap<>();
//        map.put("token", Constant.TOKEN);
//        map.put("pagesize", "10");
//        map.put("pageindex", pageindex + "");
//        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getintegral(map);
//        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
//            @Override
//            public void onSuccess(String result) {
//                Log.d(result);
//                showComplete();
//                List<IntegralBean> list = JSON.parseArray(JsonUtils.getStringValue(result, "list"), IntegralBean.class);
//                if (null == list) {
//                    if (integralBeans.size() == 0) {
//                        showEmpty();
//                        return;
//                    } else {
//                        smartrefresh.finishLoadMoreWithNoMoreData();
//                        smartrefresh.finishRefresh();
//                    }
//                } else {
//                    if (isrefresh) {  //刷新
//                        integralBeans.clear();
//                        integralBeans.addAll(list);
//                        integralAdapter.notifyDataSetChanged();
//                        smartrefresh.finishRefresh();
//                    } else { //加载更多
//                        integralBeans.addAll(list);
//                        integralAdapter.notifyDataSetChanged();
//                        smartrefresh.finishLoadMore();
//                    }
//                }
//            }
//
//            @Override
//            public void onFault(String errorMsg) {
//                toast(errorMsg);
//            }
//
//        }));
    }

}
