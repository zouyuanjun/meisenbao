package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.widget.CountdownView;
import com.zou.fastlibrary.widget.VerificationCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class VerifyPhoneActivity extends MyActivity {
    @BindView(R.id.sengcode)
    CountdownView sengcode;
    @BindView(R.id.verificationcodeview)
    VerificationCodeView verificationcodeview;
    @BindView(R.id.cl_code)
    ConstraintLayout clCode;
int source=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        sengcode.performClick();
        source=getIntent().getIntExtra(Constant.Intent_TAG,0);
        Log.d("VerifyPhoneActivity初始化");
    }

    @Override
    protected void initData() {
        verificationcodeview.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("mobile", Constant.userInfoBean.getMobile());
                map.put("captcha", content);


                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().check_captcha(map);
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(result);
                        showComplete();
                        Intent intent1 = new Intent(VerifyPhoneActivity.this, UpdateActivity.class);
                        intent1.putExtra(Constant.Intent_TAG, source);
                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        showComplete();
                        if (errorMsg.equals("请重新登陆")) {
                            startActivity(LoginActivity.class);
                        }
                        toast(errorMsg);
                    }
                }));
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void sengcode() {

        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().send_message(Constant.TOKEN,Constant.userInfoBean.getMobile());
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);

            }

            @Override
            public void onFault(String errorMsg) {
                showComplete();
                if (errorMsg.equals("请重新登陆")) {
                    startActivity(LoginActivity.class);
                }
                toast(errorMsg);
            }
        }));
    }

    @OnClick(R.id.sengcode)
    public void onViewClicked() {
        Log.d("点击验证码倒计时");
        sengcode();
    }
}
