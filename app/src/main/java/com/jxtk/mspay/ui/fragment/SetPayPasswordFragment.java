package com.jxtk.mspay.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.widget.VerificationCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/20 0020
 * description:
 */public class SetPayPasswordFragment extends MyLazyFragment {


    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.vcd_paypassword)
    VerificationCodeView vcdPaypassword;
    @BindView(R.id.tv_again)
    TextView tvAgain;
    @BindView(R.id.bt_next)
    Button btNext;
    Unbinder unbinder;
    boolean firstinput = true;
    String paypassword;
    FinishListener finish;

    public void setFinish(FinishListener finish) {
        this.finish = finish;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setpaypassword;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        vcdPaypassword.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {
                if (firstinput) {
                    paypassword = content;
                    tvAgain.setVisibility(View.VISIBLE);
                } else {
                    if (content.equals(paypassword)) {
                        btNext.setEnabled(true);
                        btNext.setVisibility(View.VISIBLE);
                    } else {
                        toast("两次密码输入不一致");
                    }
                }
            }
        });
        vcdPaypassword.setOnCodeChangeListener(new VerificationCodeView.OnCodeChangeListener() {
            @Override
            public void onChange() {
                btNext.setVisibility(View.GONE);
                tvAgain.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_clean, R.id.tv_again, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clean:
                vcdPaypassword.clean();
                tvClean.setVisibility(View.GONE);
                btNext.setVisibility(View.GONE);
                firstinput = true;
                paypassword = "";
                break;
            case R.id.tv_again:
                firstinput = false;
                vcdPaypassword.clean();
                tvAgain.setVisibility(View.GONE);
                btNext.setVisibility(View.VISIBLE);
                tvClean.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_next:
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("pay_password", paypassword);
                map.put("re_password", paypassword);
                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().setpaypassword(map);
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        finish.finishself();
                        showComplete();
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

    public interface FinishListener {
        void finishself();
    }

    ;
}
