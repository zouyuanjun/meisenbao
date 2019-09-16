package com.jxtk.mspay.ui.activity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.common.MyApplication;
import com.jxtk.mspay.utils.BGLinearlayout;
import com.jxtk.mspay.utils.TagAliasOperatorHelper;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

import static com.jxtk.mspay.utils.TagAliasOperatorHelper.ACTION_SET;
import static com.jxtk.mspay.utils.TagAliasOperatorHelper.sequence;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/16 0016
 * description:
 */public class SettingActivity extends MyActivity {
    @BindView(R.id.sb_pay)
    SwitchButton sbPay;
    @BindView(R.id.sb_theme)
    SwitchButton sbTheme;
    @BindView(R.id.ll_fignerpay)
    BGLinearlayout llFignerpay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        if (Constant.DARK_THEME == 1) {
            sbTheme.setChecked(true);
        } else {
            sbTheme.setChecked(false);
        }
        if (Constant.FINGER_PAY == 1) {
            sbPay.setChecked(true);
        } else {
            sbPay.setChecked(false);
        }
        if (!supportFingerprint()) {
            llFignerpay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        sbPay.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton button, boolean isChecked) {


                if (isChecked) {
                    Constant.FINGER_PAY = 1;
                    DataKeeper.saveInt(getBaseContext(), Constant.KEY_FINGER_PAY, 1);
                } else {
                    Constant.FINGER_PAY = 0;
                    DataKeeper.saveInt(getBaseContext(), Constant.KEY_FINGER_PAY, 0);
                }

            }
        });
        sbTheme.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton button, boolean isChecked) {
                if (isChecked) {

                    Constant.DARK_THEME = 1;
                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    DataKeeper.saveInt(getBaseContext(), Constant.KEY_DARK_THEME, 1);
                    statusBarConfig().statusBarColor("#2f3640").init();
                } else {
                    Constant.DARK_THEME = 0;
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    DataKeeper.saveInt(getBaseContext(), Constant.KEY_DARK_THEME, 0);
                    statusBarConfig().statusBarColor("#ffffff").init();
                }
                refeshtitle();

                SettingActivity.this.getContentView().invalidate();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bg_update_pay_password, R.id.sb_pay, R.id.bg_serverrule, R.id.bg_about, R.id.bt_loginout, R.id.bg_update_login_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bg_update_pay_password:
                Intent intent1 = new Intent(SettingActivity.this, VerifyPhoneActivity.class);
                intent1.putExtra(Constant.Intent_TAG, 1);
                startActivity(intent1);
                break;
            case R.id.bg_serverrule:
                Intent intent = new Intent(SettingActivity.this, WebActivity.class);
                intent.putExtra(Constant.Intent_TAG, "11");
                intent.putExtra(Constant.Intent_TAG2, "服务条款");
                startActivity(intent);
                break;
            case R.id.bg_update_login_password:
                Intent intent2 = new Intent(SettingActivity.this, VerifyPhoneActivity.class);
                intent2.putExtra(Constant.Intent_TAG, 2);
                startActivity(intent2);
                break;
            case R.id.bt_loginout:
                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                tagAliasBean.action = ACTION_SET;
                tagAliasBean.alias = "";
                ;
                tagAliasBean.isAliasAction = true;
                TagAliasOperatorHelper.getInstance().handleAction(MyApplication.getContext(), sequence, tagAliasBean);
                DataKeeper.save(getBaseContext(), Constant.TOKEN_KEY, Constant.defValue);
                finish();
                startActivity(LoginActivity.class);
                break;
            case R.id.bg_about:
                startActivity(AboutActivity.class);
                break;
        }

    }

    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
              //  Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
            //    Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
            //    Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


}
