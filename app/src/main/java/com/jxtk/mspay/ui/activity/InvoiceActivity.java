package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.InvoiceAdapter;
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
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class InvoiceActivity extends MyListView {

    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout smartrefresh;
    public List<InvoiceBean> myDataList = new ArrayList();
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.cl_action)
    ConstraintLayout clAction;
    int count = 0;
    float money = 0;
    String selectid="";
    float min_money=0;
    int whether=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    protected void initView() {
        adapter = new InvoiceAdapter(myDataList);
        title.setTitle("发票");
        title.setRightTitle("开票历史");
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InvoiceBean invoiceBean = myDataList.get(position);
                invoiceBean.setIsselect(!invoiceBean.isIsselect());
                adapter.notifyDataSetChanged();

                for (InvoiceBean invoiceBean1 : myDataList) {
                    if (invoiceBean1.isIsselect()) {
                        count++;
                        money = money + Float.parseFloat(invoiceBean1.getAmount());
                        selectid=selectid+invoiceBean1.getId()+",";
                    }
                }
                if (count != 0) {
                    clAction.setVisibility(View.VISIBLE);
                    textView10.setText("开票金额：￥"+money);
                    textView13.setText("你选取了"+count+"条单据开具发票");
                }else {
                    clAction.setVisibility(View.GONE);
                }
            }
        });
        super.initView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(HistoryInvoiceActivity.class);
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
        map.put("type", "1");
        map.put("pageindex", pageindex + "");
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getInvoiceList(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<InvoiceBean> list = JSON.parseArray(JsonUtils.getStringValue(result, "list"), InvoiceBean.class);
                min_money=JsonUtils.getFloatValue(result,"min_money");
                whether=JsonUtils.getIntValue(result,"whether");
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

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (whether!=1){
            toast("开票服务已暂停");
            return;
        }
        if (min_money>money){
            toast("最小开票金额为："+min_money+"元");
            return;
        }
        Intent intent=new Intent(InvoiceActivity.this,CreatInvoiceActivity.class);
        intent.putExtra(Constant.Intent_TAG,count);
        intent.putExtra(Constant.Intent_TAG2,money);
        intent.putExtra("selectid",selectid.substring(0,selectid.length()-1));
        startActivity(intent);
    }
}
