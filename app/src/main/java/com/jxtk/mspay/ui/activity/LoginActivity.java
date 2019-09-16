package com.jxtk.mspay.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.MainActivity;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.common.MyApplication;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.jxtk.mspay.utils.TagAliasOperatorHelper;
import com.jxtk.mspay.utils.VerificationCodeView;
import com.zou.fastlibrary.helper.ActivityStackManager;
import com.zou.fastlibrary.helper.DoubleClickHelper;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.widget.CountdownView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.utils.TagAliasOperatorHelper.ACTION_SET;
import static com.jxtk.mspay.utils.TagAliasOperatorHelper.sequence;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/22 0022
 * description:登陆界面
 */public class LoginActivity extends MyActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_text86)
    TextView tvText86;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.constraintlayout)
    ConstraintLayout constraintlayout;
    @BindView(R.id.ly_passworditem)
    LinearLayout lyPassworditem;
    @BindView(R.id.cl_password)
    ConstraintLayout clPassword;
    @BindView(R.id.verificationcodeview)
    VerificationCodeView verificationcodeview;
    @BindView(R.id.cl_code)
    ConstraintLayout clCode;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_action)
    TextView tv_action;
    String phonenum;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.im_passwordshow)
    ImageView imPasswordshow;
    @BindView(R.id.im_next)
    Button imNext;
    @BindView(R.id.tv_codeinfo)
    TextView tvCodeinfo;
    @BindView(R.id.sengcode)
    CountdownView cdSengcode;
    int lunchmode = 0;
    @BindView(R.id.tv_sendcode2)
    TextView tvSendcode2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phonenum = s.toString();
                if (phonenum.length() == 11) {
                    lyPassworditem.setVisibility(View.VISIBLE);
                } else {
                    lyPassworditem.setVisibility(View.GONE);
                    tv_action.setVisibility(View.GONE);
                    clCode.setVisibility(View.GONE);
                    clPassword.setVisibility(View.GONE);
                }
            }
        });
        verificationcodeview.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {
                phonenum = editText.getText().toString();
                login(phonenum, content, 2, "0");
                showLoading();
            }
        });
        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 6) {
                    imNext.setEnabled(true);
                } else {
                    imNext.setEnabled(false);
                }
            }
        });
//        cdSengcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("点击发送验证码倒计时");
//                phonenum = editText.getText().toString();
//                sendCode(phonenum);
//            }
//        });

    }

    @Override
    protected void initData() {
        Log.d("initdata");
    }

    @OnClick(R.id.tv_text)
    public void onViewClicked() {
        tvText.setVisibility(View.GONE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        EditTextUtil.showKeyboard(getBaseContext(), editText, true);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(linearLayout, "scaleY", 1f, 1f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(linearLayout, "scaleX", 1f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationY", -340);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageView, "translationY", -310);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(tv_action, "translationY", -370);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animator2).with(objectAnimator).with(objectAnimator2).with(animator3).with(animator4).with(objectAnimator3);
        animSet.setDuration(200);
        animSet.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    boolean showpassword = true;

    @OnClick({R.id.tv_password, R.id.tv_code, R.id.im_next, R.id.cl_password, R.id.tv_action, R.id.tv_sendcode2, R.id.im_passwordshow,R.id.sengcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_password:
                lyPassworditem.setVisibility(View.GONE);
                clPassword.setVisibility(View.VISIBLE);
                clCode.setVisibility(View.GONE);
                lunchmode = 1;
                tv_action.setText("验证码登陆");
                tv_action.setVisibility(View.VISIBLE);
                edPassword.setFocusable(true);
                edPassword.setFocusableInTouchMode(true);
                edPassword.requestFocus();
                break;
            case R.id.tv_code:
                lyPassworditem.setVisibility(View.GONE);
                clPassword.setVisibility(View.GONE);
                clCode.setVisibility(View.VISIBLE);
                lunchmode = 2;
                tv_action.setText("密码登陆");
                tv_action.setVisibility(View.VISIBLE);
                break;
            case R.id.sengcode:  //发送验证码
                Log.d("点击发送验证码倒计时");
                phonenum = editText.getText().toString();
                sendCode(phonenum);
                break;
            case R.id.im_next:  //密码登陆
                String password = edPassword.getText().toString();
                login(phonenum, "", 1, password);
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
            case R.id.tv_action:
                if (lunchmode == 1) {
                    lyPassworditem.setVisibility(View.GONE);
                    clPassword.setVisibility(View.GONE);
                    clCode.setVisibility(View.VISIBLE);
                    lunchmode = 2;
                    tv_action.setText("密码登陆");
                } else if (lunchmode == 2) {
                    lyPassworditem.setVisibility(View.GONE);
                    clPassword.setVisibility(View.VISIBLE);
                    clCode.setVisibility(View.GONE);
                    lunchmode = 1;
                    tv_action.setText("验证码登陆");
                    edPassword.setFocusable(true);
                    edPassword.setFocusableInTouchMode(true);
                    edPassword.requestFocus();
                }
                break;
            case R.id.tv_sendcode2:    //获取验证码
                tvSendcode2.setVisibility(View.GONE);
                phonenum = editText.getText().toString();
                Log.d("点击获取验证码");
                sendCode(phonenum);
                verificationcodeview.focus();
                cdSengcode.performClick();
                break;
        }
    }

    private void sendCode(String phonenum) {
        Log.d("登陆sendCode");
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phonenum);
        map.put("key", "88131431");
        map.put("send_type", "android");
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().send_message(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                toast("发送成功");
            }

            @Override
            public void onFault(String errorMsg) {
                toast(errorMsg);
            }
        }));
    }

    private void login(String phonenum, String code, int type, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("mobile", phonenum);
        if (type == 1) {
            map.put("password", password);
        } else {
            map.put("captcha", code);
            map.put("source", "android");
        }
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().app_login(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                showComplete();
                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                tagAliasBean.action = ACTION_SET;
                tagAliasBean.alias = JsonUtils.getStringValue(result, "user_id");
                tagAliasBean.isAliasAction = true;
                TagAliasOperatorHelper.getInstance().handleAction(MyApplication.getContext(), sequence, tagAliasBean);
                Constant.TOKEN = JsonUtils.getStringValue(result, "token");
                DataKeeper.save(getBaseContext(), Constant.TOKEN_KEY, Constant.TOKEN);
                //是否设置密码
                int is_set = JsonUtils.getIntValue(result, "is_set");

                if (is_set == 1) {
                    startActivity(MainActivity.class);
                } else {
                    startActivity(SetPayPasswordActivity.class);
                }
                Constant.ISSHOPER = JsonUtils.getIntValue(result, "is_shopper");
                DataKeeper.saveInt(getBaseContext(), Constant.ISSHOPER_KEY, Constant.ISSHOPER);
                finish();
            }

            @Override
            public void onFault(String errorMsg) {
                showComplete();
                toast(errorMsg);
            }
        }));
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                    // 销毁进程
                    System.exit(0);
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

}
