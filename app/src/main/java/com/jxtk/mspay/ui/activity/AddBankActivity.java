package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.helper.InputTextHelper;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.bankBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class AddBankActivity extends MyActivity {


    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_banknumber)
    EditText edBanknumber;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.ed_bankname)
    EditText edBankname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addbank;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        if (null!=bankBean){
            edBankname.setText(bankBean.getBank());
            edBanknumber.setText(bankBean.getAccount());
            edName.setText(bankBean.getRealname());
            title.setTitle("修改银行卡");
        }

        new InputTextHelper.Builder(this)
                .setMain(btConfirm)
                .addView(edName)
                .addView(edBanknumber)
                .addView(edBankname)
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        return true;
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

                String name = edName.getText().toString();
                String banknumber = edBanknumber.getText().toString();
                String bankname = edBankname.getText().toString();
                if (StringUtil.isEmpty(name) || StringUtil.isEmpty(banknumber)|| StringUtil.isEmpty(bankname)) {
                    toast("请填写完整再提交");
                    return;
                }
                showLoading();


                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("realname", name);
                map.put("account", banknumber);
                map.put("bank", bankname);

                Observable<ResponseBody> observable;
                if (null == bankBean) {
                    observable = HttpManage.getInstance().getHttpApi().createbank(map);
                } else {
                    map.put("id", bankBean.getId()+"");
                    observable = HttpManage.getInstance().getHttpApi().updatebank(map);
                }
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(result);
                        showComplete();
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
}
