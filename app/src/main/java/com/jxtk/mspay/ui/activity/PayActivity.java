package com.jxtk.mspay.ui.activity;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jay.easykeyboard.SystemKeyboard;
import com.jay.easykeyboard.action.KeyBoardActionListence;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.zou.fastlibrary.base.BaseDialog;
import com.zou.fastlibrary.base.BaseDialogFragment;
import com.zou.fastlibrary.dialog.PayPasswordDialog;
import com.zou.fastlibrary.helper.EditTextInputHelper;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.common.MyApplication.getContext;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/8 0008
 * description:
 */public class PayActivity extends MyActivity {
    @BindView(R.id.ed_paynum)
    EditText edPaynum;
    @BindView(R.id.Keyboard)
    SystemKeyboard Keyboard;
    @BindView(R.id.tv_payname)
    TextView tvPayname;
    @BindView(R.id.tv_addremark)
    TextView tvAddremark;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.im_icon)
    ImageView imIcon;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.tv_updataremark)
    TextView tvUpdataremark;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    private EditTextInputHelper mEditTextInputHelper;
    CancellationSignal cancellationSignal = new CancellationSignal();//取消的对象
    BaseDialog dialog;

    String codeurl;  //扫码内容
    String remark = ""; //备注
    String user_id;
    boolean isscan = true;//是买单还是扫码
    String nickname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        mEditTextInputHelper = new EditTextInputHelper(btConfirm);
        mEditTextInputHelper.addViews(edPaynum);

        user_id = getIntent().getStringExtra(Constant.Intent_TAG);
        //如果没有商铺id就扫码
        if (StringUtil.isEmpty(user_id)) {
            if (XXPermissions.isHasPermission(getContext(), Permission.CAMERA)) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, Constant.ScanCode);
            } else {
                XXPermissions.with(getActivity())
                        .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                        //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                        .permission(Permission.CAMERA) //不指定权限则自动获取清单中的危险权限
                        .request(new OnPermission() {
                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                Intent intent = new Intent(getContext(), CaptureActivity.class);
                                startActivityForResult(intent, Constant.ScanCode);
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {

                            }
                        });
            }
        } else {
            isscan = false;
            title.setTitle("买单");
            String imurl = getIntent().getStringExtra(Constant.Intent_TAG2);
            nickname = getIntent().getStringExtra("name");
            tvPayname.setText(nickname);
            ImageLoader.loadImage(getContext(), imIcon, Constant.BASE_URL + imurl);

        }

        Keyboard.setOnKeyboardActionListener(new KeyBoardActionListence() {
            @Override
            public void onComplete() {
                moneynum = edPaynum.getText().toString();
                if (StringUtil.isEmpty(moneynum)) {
                    toast("请输入付款金额");
                    return;
                }
                beginpay();
            }

            @Override
            public void onTextChange(Editable editable) {
            }

            @Override
            public void onClear() {
            }

            @Override
            public void onClearAll() {
            }
        });
        Keyboard.setEditText(edPaynum);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.tv_addremark, R.id.bt_confirm,R.id.tv_updataremark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addremark:
                Intent intent = new Intent(getBaseContext(), RemarkActivity.class);
                startActivityForResult(intent, Constant.Remark_Requestcode);
                break;
            case R.id.bt_confirm:
                beginpay();
                break;
            case R.id.tv_updataremark:
                Intent intent2 = new Intent(getBaseContext(), RemarkActivity.class);
                intent2.putExtra(Constant.Intent_TAG,remark);
                startActivityForResult(intent2, Constant.Remark_Requestcode);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ScanCode && resultCode == RESULT_OK && null != data) {
            codeurl = data.getStringExtra("result_string");
            if (null != codeurl) {
                loadinfo(codeurl);
            } else {
                toast("扫码失败");
                finish();
            }

            Log.d(codeurl);
        } else if (requestCode == Constant.Remark_Requestcode && resultCode == Constant.Remark_Backcode) {
            if (null != data) {
                remark = data.getStringExtra(Constant.Intent_TAG);
                if (!StringUtil.isEmpty(remark)){
                    tvRemark.setText(remark);
                    tvRemark.setVisibility(View.VISIBLE);
                    llRemark.setVisibility(View.VISIBLE);
                    tvAddremark.setVisibility(View.GONE);
                }else {
                    tvRemark.setVisibility(View.GONE);
                    llRemark.setVisibility(View.GONE);
                    tvAddremark.setVisibility(View.VISIBLE);
                }
            }

        }else {
            finish();
        }

    }

    private void loadinfo(String url) {
        int index = url.indexOf("receive_pay");
        if (index <= 0) {
            toast("该会员没有收款权限");
            finish();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("url", url);
        map.put("type", "2");

        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getCodeInfo(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                String imurl = JsonUtils.getStringValue(result, "avatar");
                nickname = JsonUtils.getStringValue(result, "nickname");
                tvPayname.setText(nickname);
                ImageLoader.loadImage(getContext(), imIcon, Constant.BASE_URL + imurl);
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

    String moneynum;

    private void pay() {
        if (StringUtil.isEmpty(codeurl) && StringUtil.isEmpty(user_id)) {
            toast("请重新扫码");
            finish();
            return;
        }
        moneynum = edPaynum.getText().toString();
        if (StringUtil.isEmpty(moneynum)) {
            toast("请输入付款金额");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);

        map.put("money", moneynum);
        map.put("memo", remark);
        Observable<ResponseBody> observable;
        if (isscan) {
            map.put("qr_code", codeurl);
            observable = HttpManage.getInstance().getHttpApi().codepay(map);
        } else {
            map.put("user_id", user_id);
            observable = HttpManage.getInstance().getHttpApi().store(map);
        }

        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                showComplete();

                Intent intent = new Intent(PayActivity.this, PayFinishActivity.class);
                intent.putExtra(Constant.Intent_TAG, moneynum);
                intent.putExtra(Constant.Intent_TAG2, nickname);
                startActivity(intent);
                finish();
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
        new PayPasswordDialog.Builder(PayActivity.this)
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

    @Override
    protected void onStop() {
        super.onStop();
        showComplete();
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }
}
