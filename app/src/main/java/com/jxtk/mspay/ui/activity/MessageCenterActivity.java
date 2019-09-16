package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.MainActivity;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.MessageNum;
import com.jxtk.mspay.entity.UserInfoBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.helper.ActivityStackManager;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.widget.ShapeCornerBgView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.messageNum;
import static com.jxtk.mspay.Constant.userInfoBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/7 0007
 * description:
 */public class MessageCenterActivity extends MyActivity {
    @BindView(R.id.scb_severmessage_num)
    ShapeCornerBgView scbSevermessageNum;
    @BindView(R.id.scb_settingmessage_num)
    ShapeCornerBgView scbSettingmessageNum;
    @BindView(R.id.scb_accountmessage_num)
    ShapeCornerBgView scbAccountmessageNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_messagecenter;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
Log.d("MessageCenterActivity启动");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("MessageCenterActivity启动dd");
    }

    @Override
    protected void initData() {
        if (null != messageNum && messageNum.getService() != 0) {
            scbSevermessageNum.setText(messageNum.getService() + "");
            scbSevermessageNum.setVisibility(View.VISIBLE);
        } else {
            scbSevermessageNum.setVisibility(View.GONE);
        }

        if (null != messageNum && messageNum.getSystem() != 0) {
            scbSettingmessageNum.setText(messageNum.getSystem() + "");
            scbSettingmessageNum.setVisibility(View.VISIBLE);
        } else {
            scbSettingmessageNum.setVisibility(View.GONE);
        }
        if (null != messageNum && messageNum.getAccount() != 0) {
            scbAccountmessageNum.setText(messageNum.getAccount() + "");
            scbAccountmessageNum.setVisibility(View.VISIBLE);
        } else {
            scbAccountmessageNum.setVisibility(View.GONE);

        }
        if (null==messageNum){
            Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getUserInfo(Constant.TOKEN);
            HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                @Override
                public void onSuccess(String result) {
                    Log.d(result);
                    userInfoBean = JsonUtils.stringToObject(result, UserInfoBean.class);
                    showComplete();
                }

                @Override
                public void onFault(String errorMsg) {
                    showComplete();
                    toast(errorMsg);
                }
            }));
            Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().getMessage(Constant.TOKEN);
            HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                @Override
                public void onSuccess(String result) {
                    Log.d(result);
                    messageNum = JsonUtils.stringToObject(result, MessageNum.class);
                    initData();
                    showComplete();
                }

                @Override
                public void onFault(String errorMsg) {
                    if (errorMsg.equals("请重新登陆")) {
                        startActivity(LoginActivity.class);
                    }
                    showComplete();
                    toast(errorMsg);
                }
            }));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bg_servermessage, R.id.bg_sysmessage, R.id.bg_accountmessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bg_servermessage:
                startActivity(ServerMessageActivity.class);
                update("1");
                messageNum.setService(0);
                break;
            case R.id.bg_sysmessage:
                startActivity(SysMessageActivity.class);
                update("3");
                messageNum.setSystem(0);
                break;
            case R.id.bg_accountmessage:
                startActivity(AccountMessageActivity.class);
                update("2");
                messageNum.setAccount(0);
                break;
        }
    }

    private void update(String type) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("type", type);
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().setRead(map);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFault(String errorMsg) {

            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MessageCenterActivity停止了");
    }

    @Override
    public void onBackPressed() {
Log.d("MessageCenterActivity的栈"+MessageCenterActivity.this.getTaskId());
        if (ActivityStackManager.getInstance().mActivitySet.keySet().size()==1) {
            startActivity(MainActivity.class);
            Log.d("ActivityStackManager返回了");
        } else {
            super.onBackPressed();
        }
    }
}
