package com.jxtk.mspay.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.dialog.MessageDialog;
import com.zou.fastlibrary.helper.EditTextInputHelper;
import com.zou.fastlibrary.utils.CommonUtil;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.common.MyApplication.getContext;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/19 0019
 * description:
 * 申请退款
 */
public class RefundActivity extends MyActivity {

    String orderid;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_refundnum)
    TextView tvRefundnum;
    @BindView(R.id.ed_refund)
    EditText edRefund;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.bt_server)
    Button btServer;
    private EditTextInputHelper editTextInputHelper;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        editTextInputHelper = new EditTextInputHelper(btConfirm);
        editTextInputHelper.addViews(edRefund);
        orderid = getIntent().getStringExtra(Constant.Intent_TAG);
        tvRefundnum.setText(getIntent().getStringExtra(Constant.Intent_TAG2).substring(1));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                showLoading();
                String memo = edRefund.getText().toString();
                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().refund(Constant.TOKEN, orderid, memo);
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showComplete();
                        toast("申请成功");
                        finish();
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        showComplete();
                        toast(errorMsg);
                    }
                }));
                break;
        }
    }
    @OnClick(R.id.bt_server)
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
