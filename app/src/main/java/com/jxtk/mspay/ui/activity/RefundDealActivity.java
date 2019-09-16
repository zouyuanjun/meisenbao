package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.MonthBilBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
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

public class RefundDealActivity extends MyActivity {
    MonthBilBean.DataBean dataBean;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.sb_ordernumber)
    SettingBar sbOrdernumber;
    @BindView(R.id.sb_title)
    SettingBar sbTitle;
    @BindView(R.id.sb_creattime)
    SettingBar sbCreattime;
    @BindView(R.id.sv_ordernum)
    SettingBar svOrdernum;
    @BindView(R.id.sb_refund_time)
    SettingBar sbRefundTime;
    @BindView(R.id.sb_phonenum)
    SettingBar sbPhonenum;
    @BindView(R.id.sb_refund_reason)
    SettingBar sbRefundReason;
    @BindView(R.id.rb_agree)
    RadioButton rbAgree;
    @BindView(R.id.rb_refuse)
    RadioButton rbRefuse;
    @BindView(R.id.rg_fund)
    RadioGroup rgFund;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.ed_refund)
    EditText edRefund;
    int is_agree = 1;
    @BindView(R.id.ll_refuse)
    LinearLayout llRefuse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_s;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        dataBean = (MonthBilBean.DataBean) getIntent().getSerializableExtra(Constant.Intent_TAG);
        if (null != dataBean) {
            sbOrdernumber.setRightText(dataBean.getOrder_sn());
            sbTitle.setRightText(dataBean.getName());
            sbCreattime.setRightText(dataBean.getCreatetime());
            svOrdernum.setRightText(dataBean.getGoods_money()+"M币");
            sbRefundTime.setRightText(dataBean.getRefuse_apply_time());
            sbPhonenum.setRightText(dataBean.getMobile());
            sbRefundReason.setRightText(dataBean.getRefund().getMemo());
        }
        rgFund.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == rbAgree.getId()) {
                    is_agree = 1;
                    tvAction.setText("退款金额");
                    edRefund.setHint("请输入退款金额");
                    edRefund.setText("");
                    edRefund.setInputType(EditorInfo.TYPE_CLASS_NUMBER|EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
                } else if (i == rbRefuse.getId()) {
                    is_agree = 0;
                    tvAction.setText("拒绝理由");
                    edRefund.setHint("请输入拒绝理由");
                    edRefund.setText("");
                    edRefund.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

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

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {

        String reson = new String("");
        reson = edRefund.getText().toString();
        if (is_agree == 0) {

            if (StringUtil.isEmpty(reson)) {
                toast("必须输入拒绝理由");
                return;
            }
        } else {
            if (StringUtil.isEmpty(reson)) {
                toast("必须输入退款金额");
                return;
            }
        }
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("is_agree", is_agree + "");
        map.put("id", dataBean.getId()+"");
        map.put("refuse_reason", reson);
        map.put("money", reson);
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().dealRefund(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                toast("处理成功");
                finish();
            }

            @Override
            public void onFault(String errorMsg) {
                showComplete();

                toast(errorMsg);
            }

        }));
    }
}
