package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.HistoryInvoiceAdapter;
import com.jxtk.mspay.common.MyListView;
import com.jxtk.mspay.entity.InvoiceBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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

public class HistoryInvoiceActivity extends MyListView {

    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout smartrefresh;
    public List<InvoiceBean> myDataList = new ArrayList();

    @Override
    protected void initView() {
        adapter = new HistoryInvoiceAdapter(myDataList);
        title.setTitle("开票历史");
        super.initView();
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

    @Override
    protected void loadData() {
        super.loadData();
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("pagesize", "10");
        map.put("type", "2");
        map.put("pageindex", pageindex + "");
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getInvoiceList(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<InvoiceBean> list = JSON.parseArray(JsonUtils.getStringValue(result, "list"), InvoiceBean.class);
                if (null == list) {

                    if (myDataList.size() == 0) {
                        showEmpty();
                        return;
                    } else {
                        smartrefresh.finishLoadMoreWithNoMoreData();
                        smartrefresh.finishRefresh();
                    }
                } else {
                    if (isrefresh) {  //刷新
                        myDataList.clear();
                        myDataList.addAll(list);
                        adapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                    } else { //加载更多
                        myDataList.addAll(list);
                        adapter.notifyDataSetChanged();
                        smartrefresh.finishLoadMore();
                    }
                }
            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
                showEmpty();

            }

        }));
    }
}
