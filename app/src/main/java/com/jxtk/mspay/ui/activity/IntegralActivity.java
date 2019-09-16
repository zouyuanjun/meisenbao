package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.FiltrateAdapter;
import com.jxtk.mspay.adapter.IntegralAdapter;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.IntegralBean;
import com.jxtk.mspay.entity.MonthPointBean;
import com.jxtk.mspay.entity.ShopTypeBean;
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
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/19 0019
 * description:积分明细页面
 */
public class IntegralActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    IntegralAdapter integralAdapter;
    List<MonthPointBean.DataBean> integralBeans = new ArrayList<>();
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout smartrefresh;
    @BindView(R.id.recyclerview2)
    RecyclerView recyclerview2;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    boolean isrefresh = false;  //是否是刷新
    boolean isshow=false; //是否显示选择窗口
    String type = new String("");
    List<ShopTypeBean> shopTypeBeans = new ArrayList<>();
    FiltrateAdapter filtrateAdapter = new FiltrateAdapter(shopTypeBeans);
    //查询参数

    String year = "";
    String month = "";
    String day = "";
    int pageindex = 1;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_list;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    boolean showfilt = false;

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        showfilt = !showfilt;
        if (showfilt) {
            llItem.setVisibility(View.VISIBLE);
        } else {
            llItem.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        integralAdapter = new IntegralAdapter(integralBeans);
        recyclerview.setAdapter(integralAdapter);
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

        recyclerview2.setLayoutManager(new GridLayoutManager(getBaseContext(),4));

        recyclerview2.setAdapter(filtrateAdapter);
        filtrateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean select = shopTypeBeans.get(position).isSelect();
                if (select) {
                    shopTypeBeans.get(position).setSelect(false);
                    type = "";
                } else {
                    type = shopTypeBeans.get(position).getType();

                    showfilt = false;
                    for (ShopTypeBean shopTypeBean : shopTypeBeans) {
                        shopTypeBean.setSelect(false);
                    }
                    shopTypeBeans.get(position).setSelect(true);

                }
                pageindex=1;
                filtrateAdapter.notifyDataSetChanged();
                llItem.setVisibility(View.GONE);
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
        map.put("type", type + "");
        map.put("year", year);
        map.put("month", month);
        map.put("day", day);
        map.put("pageindex", pageindex + "");
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getintegral(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<MonthPointBean> monthPointBeans = JSON.parseArray(JsonUtils.getStringValue(result, "list"), MonthPointBean.class);
                List<MonthPointBean.DataBean> list=new ArrayList<>();


                if (null == monthPointBeans) {
                    if (integralBeans.size() == 0||isrefresh) {
                        showEmpty();
                        integralBeans.clear();
                        integralAdapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                        smartrefresh.finishLoadMore();
                    } else {
                        smartrefresh.finishLoadMoreWithNoMoreData();
                        smartrefresh.finishRefresh();
                    }
                } else {

                    for (MonthPointBean monthPointBean:monthPointBeans){
                        list.addAll(monthPointBean.getData());
                    }

                    if (isrefresh) {  //刷新
                        integralBeans.clear();
                        integralBeans.addAll(list);
                        integralAdapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                    } else { //加载更多
                        integralBeans.addAll(list);
                        integralAdapter.notifyDataSetChanged();
                        smartrefresh.finishLoadMore();
                    }
                }

                if (shopTypeBeans.size() == 0) {
                    String typeString = JsonUtils.getStringValue(result, "type");
                    Map<String, String> map1 = GsonUtils.fromJson(typeString, Map.class);

                    if (null != map1) {
                        for (String key : map1.keySet()) {
                            shopTypeBeans.add(new ShopTypeBean(key, map1.get(key)));
                        }
                        filtrateAdapter.notifyDataSetChanged();

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
    @OnClick(R.id.ll_item)
    public void onViewClicked() {
        llItem.setVisibility(View.GONE);
        showfilt = !showfilt;
    }
}
