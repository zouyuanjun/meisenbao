package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.widget.SettingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.addressBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class MyAddressActivity extends MyActivity {


    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.sb_name)
    SettingBar sbName;
    @BindView(R.id.sb_phonenum)
    SettingBar sbPhonenum;
    @BindView(R.id.sb_address)
    SettingBar sbAddress;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myaddress;
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
        if (null!=addressBean){
            sbName.setRightText(addressBean.getUser_name());
            sbPhonenum.setRightText(addressBean.getMobile());
            sbAddress.setRightText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
            linearLayout.setVisibility(View.VISIBLE);
            title.setRightTitle("修改");
        }else {
            title.setRightTitle("添加");
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getaddress(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    title.setRightTitle("修改");
                    addressBean = JsonUtils.stringToObject(result, AddressBean.class);
                    sbName.setRightText(addressBean.getUser_name());
                    sbPhonenum.setRightText(addressBean.getMobile());
                    sbAddress.setRightText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
                    linearLayout.setVisibility(View.VISIBLE);
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
    public void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(AddAddressActivity.class);
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
