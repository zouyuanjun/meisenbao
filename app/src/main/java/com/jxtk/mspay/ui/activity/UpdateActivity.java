package com.jxtk.mspay.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.helper.EditTextInputHelper;
import com.zou.fastlibrary.utils.Log;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class UpdateActivity extends MyActivity {
    @BindView(R.id.ed_newpaypassword)
    EditText edNewpaypassword;
    @BindView(R.id.ed_oldpaypassword)
    EditText edOldpaypassword;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    EditTextInputHelper editTextInputHelper;
    int source = 0;
    @BindView(R.id.title)
    TitleBar title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pay_password;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        editTextInputHelper = new EditTextInputHelper(btConfirm);
        editTextInputHelper.addViews(edNewpaypassword, edOldpaypassword);
        source = getIntent().getIntExtra(Constant.Intent_TAG, 0);
        if (source == 2) {
            title.setTitle("修改登陆密码");
            edNewpaypassword.setHint("请输入8-12位包含数字和英文的登录密码");
            edNewpaypassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12) {
            }});
            edNewpaypassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edNewpaypassword.setTypeface(Typeface.DEFAULT);
         //   edNewpaypassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edOldpaypassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12) {
            }});
            edOldpaypassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edOldpaypassword.setTypeface(Typeface.DEFAULT);
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

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        btConfirm.setEnabled(false);
        String newpassword = edNewpaypassword.getText().toString();
        String oldpassword = edOldpaypassword.getText().toString();
        if (!newpassword.equals(oldpassword)) {
            toast("密码不一致，请重新输入");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        Observable<ResponseBody> observable;
        if (source==2){
            map.put("password", newpassword);
            map.put("password_confirm", oldpassword);
            observable = HttpManage.getInstance().getHttpApi().setpassword(map);
        }else {
            map.put("pay_password", newpassword);
            map.put("re_password", oldpassword);
            observable = HttpManage.getInstance().getHttpApi().setpaypassword(map);
        }


        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                toast("修改成功");
                btConfirm.setEnabled(true);
                finish();
            }

            @Override
            public void onFault(String errorMsg) {
                showComplete();
                if (errorMsg.equals("请重新登陆")) {
                    startActivity(LoginActivity.class);
                }
                btConfirm.setEnabled(true);
                toast(errorMsg);
            }
        }));
    }
}
