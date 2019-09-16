package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.entity.BankBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.bankBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class MyBankActivity extends MyActivity {


    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_cardnumber)
    TextView tvCardnumber;
    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.linearLayout)
    ConstraintLayout linearLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myabank;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getbank(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    result=JsonUtils.getStringValue(result,"account");
                    bankBean = JsonUtils.stringToObject(result, BankBean.class);
                    linearLayout.setVisibility(View.VISIBLE);
                    initData();
                } else {
                    title.setRightTitle("添加");
                }

            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }
        }));

    }

    @Override
    protected void initData() {
        if (null != bankBean) {
            linearLayout.setVisibility(View.VISIBLE);
            tvBankname.setText(bankBean.getBank());
            tvUsername.setText(bankBean.getRealname());
            tvCardnumber.setText(bankBean.getAccount().substring(bankBean.getAccount().length()-4,bankBean.getAccount().length()));
            title.setRightTitle("修改");
        } else {
            title.setRightTitle("添加");
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(AddBankActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public boolean statusBarDarkFont() {
        return true;
    }

}
