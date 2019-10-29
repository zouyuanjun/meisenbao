package com.jxtk.mspay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhh.rxlife2.RxLife;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.google.gson.Gson;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.PayCodeEntity;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.WebSocket;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/15 0015
 * description:
 */public class PayCodeActivity extends MyActivity {
    @BindView(R.id.im_paycode)
    ImageView imPaycode;
    @BindView(R.id.tv_recyccode)
    TextView tvRecyccode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_paycode;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        showLoading();
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
                //    .setClient(yourClient)   //if you want to set your okhttpClient
                .setShowLog(true, "your logTag")
                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
                //     .setSSLSocketFactory(yourSSlSocketFactory, yourX509TrustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);
        if (Constant.ISSHOPER == 1) {
            tvRecyccode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(PayActivity.class);
    }

    @Override
    protected void initData() {
        RxWebSocket.get(Constant.WEBSOCKET)
                //RxLifecycle : https://github.com/dhhAndroid/RxLifecycle
                .compose(RxLife.with(this).bindToLifecycle())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(WebSocket webSocket) {
                        super.onOpen(webSocket);
                        Log.d("链接成功");
                        String message = sendData();
                        if (null != webSocket) {
                            webSocket.send(message);
                        }
                        showComplete();
                    }

                    @Override
                    protected void onMessage(String text) {
                        super.onMessage(text);
                        Log.d("收到消息" + text);
                        int code = JsonUtils.getIntValue(text, "code");
                        if (code == 1001) {
                            ImageLoader.loadImage(imPaycode, Constant.BASE_URL + JsonUtils.getStringValue(text, "data"));
                        } else if (code == 1002) {

                            PayCodeEntity payCodeEntity = JsonUtils.stringToObject(text, PayCodeEntity.class);
                            if (payCodeEntity.getIs_pay() == 1) {
                                finish();
                            }
                        }


                    }

                    @Override
                    protected void onReconnect() {
                        super.onReconnect();
                    }

                    @Override
                    protected void onClose() {
                        super.onClose();
                        Log.d("关闭链接");
                        showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showComplete();
                        Log.d("关闭链接" + e.toString());
                    }
                });

    }


    private String sendData() {
        String jsonHead = "";
        Map<String, Object> mapHead = new HashMap<>();
        mapHead.put("token", Constant.TOKEN);
        mapHead.put("api", "pay");
        jsonHead = buildRequestParams(mapHead);
        Log.e("TAG", "sendData: " + jsonHead);
        return jsonHead;
    }


    public static String buildRequestParams(Object params) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(params);
        return jsonStr;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_recyccode)
    public void onViewClicked() {
        startActivity(RecycCodeActivity.class);
        finish();
    }
}
