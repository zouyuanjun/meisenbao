package com.jxtk.mspay.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.UserInfoBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.dialog.PayPasswordDialog;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.userInfoBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/7 0007
 * description:
 */public class VipCenterActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_nowviplv)
    TextView tvNowviplv;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.tv_pointlist)
    TextView tvPointlist;
    @BindView(R.id.tv_validity)
    TextView tvValidity;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.tv_nextlv)
    TextView tvNextlv;
    @BindView(R.id.tv_pointbuy)
    TextView tvPointbuy;
    @BindView(R.id.ll_moneybuy)
    LinearLayout llMoneybuy;
    @BindView(R.id.linearLayout12)
    LinearLayout linearLayout12;
    @BindView(R.id.tv_moneybuy)
    TextView tvMoneybuy;
    @BindView(R.id.constraintLayout_bar)
    ConstraintLayout constraintLayoutBar;
    @BindView(R.id.constraintlayout)
    ConstraintLayout constraintlayout;
    @BindView(R.id.tv_mypoint)
    TextView tvMypoint;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vipcenter;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        tvNowviplv.setText("您现在是" + userInfoBean.getLevel());
        tvValidity.setText("为您的" + userInfoBean.getLevel() + "身份保级至" + userInfoBean.getLevel_endtime() + "。");
        tvMypoint.setText(userInfoBean.getScore()+"");
        if (!StringUtil.isEmpty(userInfoBean.getNext_level())) {
            linearLayout12.setVisibility(View.VISIBLE);
            tvNextlv.setText("升级为" + userInfoBean.getNext_level());
            tvPointbuy.setText(userInfoBean.getNext_integral() + "积分");
            tvMoneybuy.setText(userInfoBean.getNext_money());
        }
        changgebg(userInfoBean.getLevel());

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

    private void changgebg(String level) {
        switch (level) {
            case "普通会员":
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_normal);
                constraintlayout.setBackgroundResource(R.drawable.card_normal);
                break;
            case "黄金会员":
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_gold);
                constraintlayout.setBackgroundResource(R.drawable.card_gold);
                break;
            case "铂金会员":
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_platinum);
                constraintlayout.setBackgroundResource(R.drawable.card_platinum);
                break;
            case "钻石会员":
                constraintLayoutBar.setBackgroundResource(R.drawable.card_bar_diamond);
                constraintlayout.setBackgroundResource(R.drawable.card_diamond);
                break;
        }
    }

    @OnClick({R.id.tv_pointlist, R.id.textView19, R.id.tv_pointbuy, R.id.ll_moneybuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pointlist:
                startActivity(IntegralActivity.class);
                break;
            case R.id.textView19: //等级权益说明
                Intent intent = new Intent(VipCenterActivity.this, WebActivity.class);
                intent.putExtra(Constant.Intent_TAG, "15");
                intent.putExtra(Constant.Intent_TAG2, "等级规则");
                startActivity(intent);
                break;
            case R.id.tv_pointbuy:
                passwordpay(1);
                break;
            case R.id.ll_moneybuy:
                passwordpay(2);
                break;
        }
    }

    private void refresh() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getUserInfo(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                userInfoBean = JsonUtils.stringToObject(result, UserInfoBean.class);
                showComplete();
                initView();
            }
            @Override
            public void onFault(String errorMsg) {
                showComplete();
                toast(errorMsg);
            }
        }));
    }

    private void passwordpay(int pay) {
        new PayPasswordDialog.Builder(getActivity())
                .setTitle("请验证支付密码")
                .setAutoDismiss(true) // 设置点击按钮后不关闭对话框
                .setListener(new PayPasswordDialog.OnListener() {
                    @Override
                    public void onCompleted(Dialog dialog, String password) {
                        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().verification_password(Constant.TOKEN, password);
                        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                            @Override
                            public void onSuccess(String result) {
                              if (pay==1){
                                  pointpay();
                              }else {
                                  mpay();
                              }
                            }

                            @Override
                            public void onFault(String errorMsg) {
                                showComplete();
                                toast(errorMsg);
                            }
                        }));

                    }

                    @Override
                    public void onCancel(Dialog dialog) {

                    }
                })
                .show();
    }

    private void pointpay(){
        showLoading();
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().upgrade(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                toast("兑换成功");
                refresh();
            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
                showComplete();
            }
        }));
    }
    private void mpay(){
        showLoading();
        Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().money_upgrade(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                refresh();
                toast("兑换成功");
            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
                showComplete();
            }
        }));
    }
}
