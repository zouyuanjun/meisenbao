package com.jxtk.mspay.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.SignInAdapter;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.SignInBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class SignInActivity extends MyActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_signday)
    TextView tvSignday;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.bt_signin)
    Button btSignin;
    PopupWindow popupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signin;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getsignin(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                List<SignInBean> signInBeanList = JSON.parseArray(JsonUtils.getStringValue(result, "sigin"), SignInBean.class);
                signInBeanList.get(signInBeanList.size() - 1).setIslast(true);
                recyclerview.setAdapter(new SignInAdapter(signInBeanList));
                tvSignday.setText("本前周期已连续签到" + JsonUtils.getStringValue(result, "day") + "天");
                tvRule.setText(JsonUtils.getStringValue(result, "rule"));
                int taday_is_sign_in = JsonUtils.getIntValue(result, "today");
                if (taday_is_sign_in == 1) {
                    btSignin.setText("已签到");
                    btSignin.setEnabled(false);
                    btSignin.setBackgroundResource(R.drawable.shape_corners_5);
//                    if (Constant.DARK_THEME == 1) {
//
//                        btSignin.setBackgroundColor(Color.parseColor("#252b33"));
//                    } else {
//                        btSignin.setBackgroundColor(Color.parseColor("#f5f6fa"));
//                    }

                } else {
                    btSignin.setEnabled(true);
                }
                showComplete();
            }

            @Override
            public void onFault(String errorMsg) {
                if (errorMsg.equals("请重新登陆")) {
                    startActivity(LoginActivity.class);
                }
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

    @OnClick(R.id.bt_signin)
    public void onViewClicked() {
        showLoading();
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow = CreatPopwindows.creatWWpopwindows(SignInActivity.this, R.layout.pop_signin_successful);
        popupWindow.showAtLocation(btSignin, Gravity.CENTER, 0, 0);
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().signin(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);

                initData();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
