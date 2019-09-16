package com.jxtk.mspay.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.HelpAdapter;
import com.jxtk.mspay.adapter.HelpAdapter2;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.HelpBean;
import com.jxtk.mspay.entity.HelpBean2;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.dialog.MessageDialog;
import com.zou.fastlibrary.utils.CommonUtil;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.common.MyApplication.getContext;

public class HelpActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.recyclerview2)
    RecyclerView recyclerview2;
    @BindView(R.id.ll_server)
    LinearLayout llServer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerview2.setLayoutManager(new GridLayoutManager(getBaseContext(), 4));
    }

    @Override
    protected void initData() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().help(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                List<HelpBean> helpBeans = JSON.parseArray(JsonUtils.getStringValue(result, "common"), HelpBean.class);
                HelpAdapter helpAdapter = new HelpAdapter(helpBeans);
                recyclerview.setAdapter(helpAdapter);
                helpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getBaseContext(), HelpDetailActivity.class);
                        intent.putExtra(Constant.Intent_TAG, helpBeans.get(position).getTitle());
                        intent.putExtra(Constant.Intent_TAG2, helpBeans.get(position).getContent());
                        startActivity(intent);
                    }
                });

                List<HelpBean2> helpBeans2 = JSON.parseArray(JsonUtils.getStringValue(result, "category"), HelpBean2.class);
                HelpAdapter2 helpAdapter2 = new HelpAdapter2(helpBeans2);
                recyclerview2.setAdapter(helpAdapter2);
                helpAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getBaseContext(), HelpArticleActivity.class);
                        intent.putExtra(Constant.Intent_TAG, helpBeans2.get(position).getId()+"");
                        intent.putExtra(Constant.Intent_TAG2, helpBeans2.get(position).getNickname());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFault(String errorMsg) {
                if (errorMsg.equals("请重新登陆")) {
                    startActivity(LoginActivity.class);
                }
                toast(errorMsg);
            }
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_server)
    public void onViewClicked() {
        if (XXPermissions.isHasPermission(getContext(), Permission.CALL_PHONE)) {
            call();

        } else {
            XXPermissions.with(getActivity())
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(Permission.CALL_PHONE) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            call();
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            toast("请在设置-应用权限设置中给予“电话”权限");
                        }
                    });
        }
    }
    private void call() {
        new MessageDialog.Builder(getActivity())
                .setTitle("拨打客服电话？") // 标题可以不用填写
                .setMessage("17688312493")
                .setConfirm("确定")
                .setCancel("取消") // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        Log.d("打电话");
                        CommonUtil.call(getActivity(), "17688312493");
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                    }

                })
                .show();
    }
}
