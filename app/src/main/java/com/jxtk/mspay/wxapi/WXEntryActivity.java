package com.jxtk.mspay.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jxtk.mspay.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_tranp);
    	api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_ID,true);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("dfgf", "onPayFinish, errCode = " + resp.errStr+resp.errCode);
		if (resp.errCode==0){
			Log.d("weixin", "分享成功");
			//BToast.success(this).text("分享成功").show();
			finish();
		}else {
		//	BToast.error(this).text("分享失败").show();
			Log.d("weixin", "分享失败"+resp.errStr);
			finish();
		}
	}
}