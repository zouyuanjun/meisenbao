package com.jxtk.mspay.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.ShopBean;
import com.zou.fastlibrary.dialog.MessageDialog;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.CommonUtil;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jxtk.mspay.common.MyApplication.getContext;

public class ShopDetailActivity extends MyActivity {
    ShopBean shopBean;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.im_shop_card)
    ImageView imShopCard;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_type)
    TextView tvShopType;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_phonenumber)
    TextView tvPhonenumber;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopdetail;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        shopBean = (ShopBean) getIntent().getSerializableExtra(Constant.Intent_TAG);
        if (null != shopBean) {
            tvIntro.setText(shopBean.getIntroduce());
            tvPhonenumber.setText(shopBean.getTel());
            tvShopName.setText(shopBean.getName());
            tvShopType.setText(shopBean.getType());
            tvShopAddress.setText(shopBean.getShop_address());
            ImageLoader.loadImage(imShopCard, Constant.BASE_URL + shopBean.getCard_head());
        }
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

    @OnClick({R.id.ll_address, R.id.ll_phone, R.id.ll_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
               if (AppUtils.isAppInstalled("com.autonavi.minimap")){
                   Intent intent = new Intent();
                   intent.setAction(Intent.ACTION_VIEW);
                   intent.addCategory(Intent.CATEGORY_DEFAULT);
                   Uri uri = Uri.parse("amapuri://route/plan/?did=&dlat="+shopBean.getLat()+"&dlon="+shopBean.getLng()+"&dname="+shopBean.getAddress()+"&dev=0&t=0");
                   intent.setData(uri);
                   startActivity(intent);
               }else {
                   toast("地图服务不可用，请安装高德地图");
               }

                break;
            case R.id.ll_phone:
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
                break;
            case R.id.ll_pay:
                Intent intent2 = new Intent(ShopDetailActivity.this, PayActivity.class);
                intent2.putExtra(Constant.Intent_TAG, shopBean.getUser_id() + "");
                intent2.putExtra(Constant.Intent_TAG2, shopBean.getCard_head() + "");
                intent2.putExtra("name", shopBean.getName() + "");
                startActivity(intent2);
                break;
        }
    }

    private void call() {
        new MessageDialog.Builder(getActivity())
                .setTitle("拨打电话？") // 标题可以不用填写
                .setMessage(shopBean.getTel())
                .setConfirm("确定")
                .setCancel("取消") // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        Log.d("打电话");
                        CommonUtil.call(getActivity(), shopBean.getTel());
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                    }

                })
                .show();
    }
}
