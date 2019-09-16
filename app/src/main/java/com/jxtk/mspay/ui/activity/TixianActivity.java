package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.BankBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.bankBean;

public class TixianActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.ll_bank)
    LinearLayout llBank;
    @BindView(R.id.ed_tixianmoney)
    EditText edTixianmoney;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.tv_cardnumber)
    TextView tvCardnumber;
    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    int maxmoney = 0;
    double rate = 0.0;
    @BindView(R.id.tv_cost)
    TextView tvCost;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixian;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        int money = (int) (Double.parseDouble(Constant.userInfoBean.getMoney()));
        maxmoney = (money / 100) * 100;
        tvMax.setText("本次最大可提现金额：￥" + maxmoney);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void initData() {
//        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getbank(Constant.TOKEN);
//        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
//            @Override
//            public void onSuccess(String result) {
//                if (!StringUtil.isEmpty(result)) {
//                    result = JsonUtils.getStringValue(result, "account");
//                    bankBean = JsonUtils.stringToObject(result, BankBean.class);
//                    setdata();
//                    btConfirm.setEnabled(true);
//                } else {
//                    title.setRightTitle("添加");
//                }
//
//            }
//
//            @Override
//            public void onFault(String errorMsg) {
//                toast(errorMsg);
//            }
//        }));
        Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().cashOutView(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    bankBean = JsonUtils.stringToObject(JsonUtils.getStringValue(result, "account"), BankBean.class);
                    rate = com.blankj.utilcode.util.JsonUtils.getDouble(result, "rate");
                    tvCost.setText("提现手续费率:" + rate * (double) 100 + "%");
                    setdata();
                } else {
                    title.setRightTitle("添加");
                }

            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }
        }));

        edTixianmoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String money = editable.toString();
                if (!StringUtil.isEmpty(money)){
                    int m = Integer.parseInt(money);
                    if (m>maxmoney){
                        tvCost.setText("超出最大可提现金额");
                        btConfirm.setEnabled(false);
                    }else if (m%100>0){
                        tvCost.setText("只能提现100的整倍数");
                        btConfirm.setEnabled(false);
                    }else{
                        double s= (double) m*(1-rate);
                        tvCost.setText("实际到账金额：￥"+s);
                        btConfirm.setEnabled(true);
                    }
                }else {
                    tvCost.setText("提现手续费率:" + rate * (double) 100 + "%");
                    btConfirm.setEnabled(false);

                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_bank, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_bank:
                startActivity(AddBankActivity.class);
                break;
            case R.id.bt_confirm:
                String money = edTixianmoney.getText().toString();
                int tixianmoney = 0;
                try {
                    tixianmoney = Integer.parseInt(money);
                } catch (Exception e) {
                    toast("请输入100的整倍数");
                    return;
                }
                if (tixianmoney > maxmoney) {
                    toast("最大金额不能超过:" + maxmoney);
                    return;
                }
                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().cashOutApply(Constant.TOKEN, tixianmoney + "");
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(result);
                        toast("申请提现成功");
                        finish();
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        toast(errorMsg);
                    }
                }));
                break;
        }
    }

    private void setdata() {
        if (null == bankBean) {
            constraintLayout.setVisibility(View.GONE);

        } else {
            llBank.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        }

        tvBankname.setText(bankBean.getBank());
        tvUsername.setText(bankBean.getRealname());
        tvCardnumber.setText(bankBean.getAccount().substring(bankBean.getAccount().length() - 4, bankBean.getAccount().length()));

    }

    @OnClick(R.id.constraintLayout)
    public void onViewClicked() {
        startActivity(AddBankActivity.class);

    }
}
