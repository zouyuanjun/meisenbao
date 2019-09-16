package com.jxtk.mspay.ui.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyLazyFragment;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;

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
 */public class SetPasswordFragment extends MyLazyFragment {
    FinishListener finishListener;
    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.im_passwordshow)
    ImageView imPasswordshow;
    @BindView(R.id.cl_password)
    ConstraintLayout clPassword;
    @BindView(R.id.tv_again)
    TextView tvAgain;
    @BindView(R.id.bt_next)
    Button btNext;
    Unbinder unbinder;
    String password;
    boolean showpassword = true;
    boolean isfirstinput = true;
    @BindView(R.id.tv_setpassword)
    TextView tvSetpassword;
    @BindView(R.id.ll_input)
    LinearLayout llInput;

    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setpassword;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        edPassword.requestFocus();
        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (isfirstinput) {//第一次输入
                    if (length > 7) {
                        tvAgain.setVisibility(View.VISIBLE);
                    } else {
                        tvAgain.setVisibility(View.GONE);
                    }
                } else {//第二次输入
                    btNext.setVisibility(View.VISIBLE);
                    if (s.toString().equals(password)) {
                        btNext.setEnabled(true);
                    } else {
                        btNext.setEnabled(false);

                    }
                    ;
                }

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

    @OnClick({R.id.tv_clean, R.id.tv_again, R.id.bt_next, R.id.im_passwordshow,R.id.tv_setpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clean:
                password = "";
                edPassword.setText("");
                tvClean.setVisibility(View.GONE);
                btNext.setVisibility(View.GONE);
                btNext.setEnabled(false);
                isfirstinput=true;
                break;
            case R.id.tv_setpassword:
               tvSetpassword.setVisibility(View.GONE);
               llInput.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_again:
                isfirstinput = false;
                password = edPassword.getText().toString();
                edPassword.setText("");
                tvAgain.setVisibility(View.GONE);
                tvClean.setVisibility(View.VISIBLE);
                btNext.setVisibility(View.GONE);
                btNext.setEnabled(false);
                break;
            case R.id.im_passwordshow:
                if (showpassword) {
                    imPasswordshow.setBackgroundResource(R.drawable.icon_eye_close);
                    edPassword.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imPasswordshow.setBackgroundResource(R.drawable.icon_eye_open);
                    edPassword.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                }
                edPassword.setSelection(edPassword.getText().length());
                showpassword = !showpassword;
                break;
            case R.id.bt_next:
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("token", Constant.TOKEN);
                map.put("password", password);
                map.put("password_confirm", password);
                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().setpassword(map);
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        finishListener.finishself();
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
}
