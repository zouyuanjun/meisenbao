package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.entity.MyInvoiceTitleBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.widget.SettingBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.addressBean;

//开票页面
public class CreatInvoiceActivity extends MyActivity {
    @BindView(R.id.tv_invoice_num)
    TextView tvInvoiceNum;
    @BindView(R.id.tv_invoice_money)
    TextView tvInvoiceMoney;
    @BindView(R.id.ll_invoice)
    LinearLayout llInvoice;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_addremark)
    TextView tvAddremark;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    int count = 0;
    float money = 0;
    String selectid = "";
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
    @BindView(R.id.sb_name)
    SettingBar sbName;
    @BindView(R.id.sb_phonenum)
    SettingBar sbPhonenum;
    @BindView(R.id.sb_address)
    SettingBar sbAddress;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    MyInvoiceTitleBean myInvoiceTitleBean;
    @BindView(R.id.tv_updataremark)
    TextView tvUpdataremark;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.tv_remark)
    TextView tvRemark;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_invoice;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        count = getIntent().getIntExtra(Constant.Intent_TAG, 0);
        money = getIntent().getFloatExtra(Constant.Intent_TAG2, 0);
        selectid = getIntent().getStringExtra("selectid");
        tvInvoiceNum.setText("你选取了" + count + "条单据开具发票");
        tvInvoiceMoney.setText("开票金额：￥" + money);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void initData() {
        //获取地址
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getaddress(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    addressBean = JsonUtils.stringToObject(result, AddressBean.class);
                    sbName.setRightText(addressBean.getUser_name());
                    sbPhonenum.setRightText(addressBean.getMobile());
                    sbAddress.setRightText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
                    linearLayout.setVisibility(View.VISIBLE);
                    llAddress.setVisibility(View.GONE);
                } else {

                }
                if (null != addressBean || null != myInvoiceTitleBean) {
                    btConfirm.setEnabled(true);
                }

            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }
        }));
//获取发票
        Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().getinvoice(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    llInvoice2.setVisibility(View.VISIBLE);
                    llInvoice.setVisibility(View.GONE);
                    showComplete();
                    myInvoiceTitleBean = JsonUtils.stringToObject(result, MyInvoiceTitleBean.class);
                    if (null != myInvoiceTitleBean) {
                        if (myInvoiceTitleBean.getType().equals("person")) {
                            sbClass.setRightText(myInvoiceTitleBean.getTypetext());
                            sbTitle.setRightText(myInvoiceTitleBean.getCorporate_name());
                            sbSort.setVisibility(View.GONE);
                            sbNumber.setVisibility(View.GONE);
                        } else {
                            sbClass.setRightText(myInvoiceTitleBean.getTypetext());
                            sbTitle.setRightText(myInvoiceTitleBean.getCorporate_name());
                            sbSort.setRightText(myInvoiceTitleBean.getInvoice_type() + "");
                            sbNumber.setRightText(myInvoiceTitleBean.getDuty_paragraph() + "");
                        }
                    }

                } else {

                }
                if (null != addressBean || null != myInvoiceTitleBean) {
                    btConfirm.setEnabled(true);
                }

            }

            @Override
            public void onFault(String errorMsg) {
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

    @OnClick({R.id.ll_invoice, R.id.ll_address, R.id.tv_addremark, R.id.bt_confirm,R.id.tv_updataremark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_invoice:
                startActivity(AddInvoiceActivity.class);
                break;
            case R.id.ll_address:
                startActivity(AddAddressActivity.class);
                break;
            case R.id.tv_addremark:
                Intent intent = new Intent(getBaseContext(), RemarkActivity.class);
                startActivityForResult(intent, Constant.Remark_Requestcode);
                break;
            case R.id.bt_confirm:
                if (null == addressBean || null == myInvoiceTitleBean) {
                    toast("发票抬头和地址信息不完整");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("duty_paragraph", myInvoiceTitleBean.getDuty_paragraph() + "");
                map.put("bank_number", myInvoiceTitleBean.getBank_number() + "");
                map.put("corporate_name", myInvoiceTitleBean.getCorporate_name() + "");
                map.put("corporate_address", myInvoiceTitleBean.getCorporate_address() + "");
                map.put("invoice_type", myInvoiceTitleBean.getInvoice_type() + "");
                map.put("amount", money + "");
                map.put("address", sbAddress.getRightText().toString());
                map.put("mobile", addressBean.getMobile() + "");
                map.put("user_name", addressBean.getUser_name() + "");
                map.put("type", myInvoiceTitleBean.getType() + "");
                map.put("nature", "纸质");
                map.put("remarks", remark + "");
                map.put("order_id", selectid);
                Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().ask_invoice(map);
                HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        toast("提交申请成功");
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        toast(errorMsg);
                    }
                }));
                break;
            case R.id.tv_updataremark:
                Intent intent2 = new Intent(getBaseContext(), RemarkActivity.class);
                intent2.putExtra(Constant.Intent_TAG,remark);
                startActivityForResult(intent2, Constant.Remark_Requestcode);
                break;
        }
    }

    String remark = ""; //备注

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.Remark_Requestcode && resultCode == Constant.Remark_Backcode && null != data) {
            remark = data.getStringExtra(Constant.Intent_TAG);
            if (!StringUtil.isEmpty(remark)) {
                tvRemark.setText(remark);
                tvRemark.setVisibility(View.VISIBLE);
                llRemark.setVisibility(View.VISIBLE);
                tvAddremark.setVisibility(View.GONE);
            } else {
                tvRemark.setVisibility(View.GONE);
                llRemark.setVisibility(View.GONE);
                tvAddremark.setVisibility(View.VISIBLE);
            }


            Log.d(remark);
        }

    }

}
