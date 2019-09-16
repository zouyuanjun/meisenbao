package com.jxtk.mspay.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.ADEntity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.widget.spinner.NiceSpinner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class AddInvoiceActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.ll_bussess)
    LinearLayout llBussess;
    @BindView(R.id.ed_title)
    EditText edTitle;
    @BindView(R.id.ed_number)
    EditText edNumber;
    @BindView(R.id.ed_bankname)
    EditText edBankname;
    @BindView(R.id.ed_banknumber)
    EditText edBanknumber;
    @BindView(R.id.ed_phonenum)
    EditText edPhonenum;
    @BindView(R.id.ed_address)
    EditText edAddress;
    String type = "person";
    @BindView(R.id.ll_expendbussess)
    LinearLayout llExpendbussess;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_bussess)
    TextView tvBussess;
    @BindView(R.id.spinner)
    NiceSpinner spinner;
    List<String> dataset = new LinkedList<>(Arrays.asList("增值税专用"));
    String invoice_type = "增值税专用"; //发票类型

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addinvoice;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {

        spinner.attachDataSource(dataset);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                invoice_type = dataset.get(position);
            }
        });

    }

    @Override
    protected void initData() {
        //获取发票可选类型
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getinvoice_type(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                dataset = JSON.parseArray(JsonUtils.getStringValue(result, "type"), String.class);
                spinner.attachDataSource(dataset);


            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }
        }));

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

    public void changesort(String changetype) {
        if (changetype.equals("company")) {
            llBussess.setBackgroundResource(R.drawable.shape_corners_lan12);
            tvBussess.setTextColor(Color.parseColor("#ffffff"));
            tvPerson.setTextColor(Color.parseColor("#666666"));
            llPerson.setBackgroundResource(R.drawable.shape_corners_12);
            llExpendbussess.setVisibility(View.VISIBLE);
            type = "company";
        } else if (changetype.equals("person")) {
            llBussess.setBackgroundResource(R.drawable.shape_corners_12);
            llPerson.setBackgroundResource(R.drawable.shape_corners_lan12);
            tvBussess.setTextColor(Color.parseColor("#666666"));
            tvPerson.setTextColor(Color.parseColor("#ffffff"));
            llExpendbussess.setVisibility(View.GONE);
            type = "person";
        }
    }

    @OnClick({R.id.ll_person, R.id.ll_bussess, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_person:
                changesort("person");
                break;
            case R.id.ll_bussess:
                changesort("company");
                break;
            case R.id.bt_confirm:
                String corporate_name = edTitle.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("type", type);
                map.put("corporate_name", corporate_name);
                if (type.equals("person")) {


                } else {
                    String duty_paragraph = edNumber.getText().toString();
                    String bank = edBankname.getText().toString();
                    String bank_number = edBanknumber.getText().toString();
                    String tel_phone = edPhonenum.getText().toString();
                    String corporate_address = edAddress.getText().toString();
                    if (StringUtil.isEmpty(duty_paragraph) || StringUtil.isEmpty(bank) || StringUtil.isEmpty(bank_number) || StringUtil.isEmpty(invoice_type)) {
                        toast("请填写完整必填项");
                        return;
                    }
                    map.put("duty_paragraph", duty_paragraph);
                    map.put("bank", bank);
                    map.put("bank_number", bank_number);
                    map.put("tel_phone", tel_phone);
                    map.put("corporate_address", corporate_address);
                    map.put("invoice_type", invoice_type);
                }
                showLoading();
                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().addinvoce(map);
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
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
