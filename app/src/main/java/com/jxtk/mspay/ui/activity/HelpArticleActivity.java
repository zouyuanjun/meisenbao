package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.adapter.HelpAdapter;
import com.jxtk.mspay.common.MyListView;
import com.jxtk.mspay.entity.HelpBean;
import com.jxtk.mspay.entity.IntegralBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class HelpArticleActivity extends MyListView {
    List<HelpBean> helpBeans=new ArrayList<>();
    String category_id;
    @Override
    protected void initView() {
        adapter=new HelpAdapter(helpBeans);
        super.initView();
        category_id=getIntent().getStringExtra(Constant.Intent_TAG);
        title.setTitle(getIntent().getStringExtra(Constant.Intent_TAG2));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getBaseContext(), HelpDetailActivity.class);
                intent.putExtra(Constant.Intent_TAG, helpBeans.get(position).getTitle());
                intent.putExtra(Constant.Intent_TAG2, helpBeans.get(position).getContent());
                startActivity(intent);
            }
        });
        smartrefresh.setEnableLoadMore(false);
    }

    @Override
    protected void loadData() {
        super.loadData();
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().help_article(Constant.TOKEN,category_id);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<HelpBean> list = JSON.parseArray(JsonUtils.getStringValue(result, "list"), HelpBean.class);
                if (null == list) {
                    if (helpBeans.size() == 0) {
                        showEmpty();
                        return;
                    } else {
                        smartrefresh.finishLoadMoreWithNoMoreData();
                        smartrefresh.finishRefresh();
                    }
                } else {
                    if (isrefresh) {  //刷新
                        helpBeans.clear();
                        helpBeans.addAll(list);
                        adapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                    } else { //加载更多
                        helpBeans.addAll(list);
                        adapter.notifyDataSetChanged();
                        smartrefresh.finishLoadMore();
                    }
                }
            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }

        }));
    }
}
