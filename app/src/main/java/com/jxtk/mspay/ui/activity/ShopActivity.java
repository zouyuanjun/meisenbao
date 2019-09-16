package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.FiltrateAdapter;
import com.jxtk.mspay.adapter.ShopAdapter;
import com.jxtk.mspay.common.MyListView;
import com.jxtk.mspay.entity.ShopBean;
import com.jxtk.mspay.entity.ShopTypeBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.LocationUtils;
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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.jxtk.mspay.common.MyApplication.getContext;

public class ShopActivity extends MyListView {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.recyclerview2)
    RecyclerView recyclerview2;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    List<ShopBean> list = new ArrayList<>();
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout smartrefresh;
    Location location;
    String type = new String("");
    String province = new String("");
    ;
    String city = new String("");
    ;
    String region = new String("");
    ;
    String lat = new String("");
    ;
    String lng = new String("");
    ;
    List<ShopTypeBean> shopTypeBeans = new ArrayList<>();
    FiltrateAdapter filtrateAdapter = new FiltrateAdapter(shopTypeBeans);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shoper;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        adapter = new ShopAdapter(list);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.bt_pay) {
                    Intent intent = new Intent(ShopActivity.this, PayActivity.class);
                    intent.putExtra(Constant.Intent_TAG, list.get(position).getUser_id() + "");
                    intent.putExtra(Constant.Intent_TAG2, list.get(position).getCard_head() + "");
                    intent.putExtra("name", list.get(position).getName() + "");
                    startActivity(intent);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ShopActivity.this, ShopDetailActivity.class);
                intent.putExtra(Constant.Intent_TAG, list.get(position));
                startActivity(intent);
            }
        });
        if (XXPermissions.isHasPermission(getContext(), Permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)) {
            location = LocationUtils.getLocation(getContext());

        } else {
            XXPermissions.with(getActivity())
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(Permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            location = LocationUtils.getLocation(getContext());
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {

                        }
                    });
        }
        super.initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview2.setLayoutManager(new GridLayoutManager(getContext(), 4));

        recyclerview2.setAdapter(filtrateAdapter);
        filtrateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean select = shopTypeBeans.get(position).isSelect();
                if (select) {
                    shopTypeBeans.get(position).setSelect(false);
                    type = "";
                    showfilt = false;
                } else {
                    type = shopTypeBeans.get(position).getType();

                    showfilt = false;
                    for (ShopTypeBean shopTypeBean : shopTypeBeans) {
                        shopTypeBean.setSelect(false);
                    }
                    shopTypeBeans.get(position).setSelect(true);

                }

                filtrateAdapter.notifyDataSetChanged();
                llItem.setVisibility(View.GONE);
                isrefresh = true;
                pageindex = 1;
                loadData();
            }
        });
    }

    @Override
    protected void initData() {
        try {
            if (null != location) {
                lat = location.getLatitude() + "";
                lng = location.getLongitude() + "";
                Address address = LocationUtils.getAddress(location.getLatitude(), location.getLongitude());
                province = address.getAdminArea();
                city = address.getLocality();
                region = address.getSubLocality();

                Log.d("地址" + address.getSubLocality() + address.toString());
            } else {
                toast("获取位置失败");
            }

        } catch (Exception e) {
            toast("获取位置失败");
        }

        loadData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    protected void loadData() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("pageSize", "10");
        map.put("page", pageindex + "");
        map.put("type", type);
        map.put("lat", lat);
        map.put("region", region);
        map.put("lng", lng);
        map.put("city", city);
        map.put("province", province);
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().shop_list(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<ShopBean> shopBeans = JSON.parseArray(JsonUtils.getStringValue(result, "data"), ShopBean.class);

                if (null == shopBeans) {
                    if (list.size() == 0 || isrefresh) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                        showEmpty();
                        return;
                    } else {
                        smartrefresh.finishLoadMoreWithNoMoreData();
                        smartrefresh.finishRefresh();
                    }
                } else {
                    if (isrefresh) {
                        //刷新
                        list.clear();
                        list.addAll(shopBeans);
                        adapter.notifyDataSetChanged();
                        smartrefresh.finishRefresh();
                    } else {
                        //加载更多
                        list.addAll(shopBeans);
                        adapter.notifyDataSetChanged();
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
