package com.jxtk.mspay.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.jxtk.mspay.utils.WebViewUtil;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.ResponseBody;


public class WebActivity extends MyActivity {
    WebView webView;
    Activity context;
    String url = "";
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.webview);
        context = this;
     webview.setWebViewClient(new WebViewUtil.MyWebViewClient(this, webview));
        webview.loadDataWithBaseURL(null, "正在加载", "text/html", "UTF-8", null);
        url = getIntent().getStringExtra(Constant.Intent_TAG);
        String stitle = getIntent().getStringExtra(Constant.Intent_TAG2);

        title.setTitle(stitle);


    }

    @Override
    protected void initData() {
        if (url.indexOf("ttp")>0){
            webView.loadUrl(url);
        }else {
            Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().service(Constant.TOKEN, url, "0");
            HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                @Override
                public void onSuccess(String result) {

                    result= JsonUtils.getStringValue(result,"service");
                    result=result.replaceAll("src=\\\"","src=\""+Constant.BASE_URL);
                    Log.d(result);
                    webview.loadDataWithBaseURL(null, result, "text/html", "UTF-8", null);
                }

                @Override
                public void onFault(String errorMsg) {
                    toast(errorMsg);
                }

            }));
        }

    }

}
