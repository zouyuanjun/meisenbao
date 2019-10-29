package com.jxtk.mspay.ui.activity;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.easykeyboard.SystemKeyboard;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.ThreePayDemo;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.base.BaseDialog;
import com.zou.fastlibrary.base.BaseDialogFragment;
import com.zou.fastlibrary.dialog.PayPasswordDialog;
import com.zou.fastlibrary.image.ImageLoader;
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

import static com.jxtk.mspay.common.MyApplication.getContext;

/**
 * PayThreeActivity
 *
 * @blame Android Team
 */
public class PayThreeActivity extends MyActivity {
    @BindView(R.id.im_icon)
    ImageView imIcon;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.sb_paynum)
    SettingBar sbPaynum;
    @BindView(R.id.sb_ordernumber)
    SettingBar sbOrdernumber;
    @BindView(R.id.sb_intro)
    SettingBar sbIntro;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    String url;

    BaseDialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_threepay;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra(Constant.Intent_TAG);
        loadinfo(url);
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

    private void loadinfo(String url) {
        int appid1 = url.indexOf("app_id=");
        int appid2 = url.indexOf("&trade");
        int tro1 = url.indexOf("no=");
        if (appid1 * appid2 * tro1 == 0) {
            toast("扫码错误" + url);
            finish();
        }
        String appid = url.substring(appid1+7, appid2);
        String tro = url.substring(tro1+3, url.length());


        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("trade_no", tro);
        map.put("view", "1");
        map.put("app_id", appid);

        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getThreeCodeInfo(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                ThreePayDemo threePayDemo = JsonUtils.stringToObject(result, ThreePayDemo.class);
                tvShopName.setText(threePayDemo.getShopper_name() + "");
                ImageLoader.loadImage(getContext(), imIcon, Constant.BASE_URL + threePayDemo.getShopper_avatar());
                sbIntro.setRightText(threePayDemo.getBody());
                sbPaynum.setRightText(threePayDemo.getPay_amount() + "");
                sbOrdernumber.setRightText(threePayDemo.getOut_trade_no() + "");
                btConfirm.setEnabled(true);
                Log.d(result);
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

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
       beginpay();
    }
    /**
     * 取消的对象
     **/
    CancellationSignal cancellationSignal = new CancellationSignal();
    private void pay() {
        int appid1 = url.indexOf("app_id=");
        int appid2 = url.indexOf("&trade");
        int tro1 = url.indexOf("no=");
        if (appid1 * appid2 * tro1 == 0) {
            toast("扫码错误" + url);
            finish();
        }
        String appid = url.substring(appid1+7, appid2);
        String tro = url.substring(tro1+3, url.length());


        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("trade_no", tro);
        map.put("view", "0");
        map.put("app_id", appid);

        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getThreeCodeInfo(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                showComplete();
                toast("支付成功");
                finish();
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

    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    private class MyFingerDiscentListener extends FingerprintManagerCompat.AuthenticationCallback {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            if (errMsgId == 5) {//取消识别

            } else if (errMsgId == 7) {
                Toast.makeText(getContext(), "操作过于频繁，请稍后重试", Toast.LENGTH_SHORT).show();
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    cancellationSignal = null;
                }
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            showLoading();
            pay();
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();

            Toast.makeText(getContext(), "指纹识别失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
        }
    }


    private void beginpay() {
        if (Constant.FINGER_PAY == 1) {
            if (supportFingerprint()) {
                if (Build.VERSION.SDK_INT > 23) {
                    dialog = new BaseDialogFragment.Builder(this)
                            .setContentView(R.layout.pop_finger)
                            .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                            .addOnDismissListener(new BaseDialog.OnDismissListener() {
                                @Override
                                public void onDismiss(BaseDialog dialog) {
                                    if (cancellationSignal != null) {
                                        cancellationSignal.cancel();
                                        cancellationSignal = null;
                                    }
                                }
                            })
                            .show();
                    FingerprintManagerCompat mFingerprintManager = FingerprintManagerCompat.from(this);
                    mFingerprintManager.authenticate(null, 0, cancellationSignal, new MyFingerDiscentListener(), null);
                }
            }

        } else {
            passwordpay();
        }
    }


    private void passwordpay() {
        new PayPasswordDialog.Builder(PayThreeActivity.this)
                .setTitle("请输入支付密码")
                .setAutoDismiss(true) // 设置点击按钮后不关闭对话框
                .setListener(new PayPasswordDialog.OnListener() {
                    @Override
                    public void onCompleted(Dialog dialog, String password) {
                        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().verification_password(Constant.TOKEN, password);
                        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                            @Override
                            public void onSuccess(String result) {
                                pay();
                            }

                            @Override
                            public void onFault(String errorMsg) {
                                showComplete();
                                toast(errorMsg);
                            }
                        }));

                    }

                    @Override
                    public void onCancel(Dialog dialog) {

                    }
                })
                .show();
    }
}
