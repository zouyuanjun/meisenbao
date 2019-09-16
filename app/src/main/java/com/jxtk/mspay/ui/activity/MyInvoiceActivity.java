package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.MyInvoiceTitleBean;
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

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class MyInvoiceActivity extends MyActivity {


    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.sb_class)
    SettingBar sbClass;
    @BindView(R.id.sb_title)
    SettingBar sbTitle;
    @BindView(R.id.sb_sort)
    SettingBar sbSort;
    @BindView(R.id.sb_number)
    SettingBar sbNumber;
    @BindView(R.id.ll_invoice_2)
    LinearLayout llInvoice2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myinvoice;
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
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getinvoice(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    title.setRightTitle("修改");
                    llInvoice2.setVisibility(View.VISIBLE);
                    showComplete();
                    MyInvoiceTitleBean myInvoiceTitleBean= JsonUtils.stringToObject(result,MyInvoiceTitleBean.class);
                    if (null!=myInvoiceTitleBean){
                        if (myInvoiceTitleBean.getType().equals("person")){
                            sbClass.setRightText(myInvoiceTitleBean.getTypetext());
                            sbTitle.setRightText(myInvoiceTitleBean.getCorporate_name());
                            sbSort.setVisibility(View.GONE);
                            sbNumber.setVisibility(View.GONE);
                        }else {
                            sbClass.setRightText(myInvoiceTitleBean.getTypetext());
                            sbTitle.setRightText(myInvoiceTitleBean.getCorporate_name());
                            sbSort.setRightText(myInvoiceTitleBean.getInvoice_type()+"");
                            sbNumber.setRightText(myInvoiceTitleBean.getDuty_paragraph()+"");
                        }


                    }

                } else {
                    title.setRightTitle("添加");
                    showLayout(R.drawable.empty_invoice_silver, R.string.no_invoice);
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(AddInvoiceActivity.class);
    }

    @Override
    public boolean statusBarDarkFont() {
        return true;
    }
}
