package com.jxtk.mspay.demo;

import java.util.Map;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.jxtk.mspay.demo.util.OrderInfoUtil2_0;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

/**
 *  重要说明：
 *  
 *  本 Demo 只是为了方便直接向商户展示支付宝的整个支付流程，所以将加签过程直接放在客户端完成
 *  （包括 OrderInfoUtil2_0_HK 和 OrderInfoUtil2_0）。
 *
 *  在真实 App 中，私钥（如 RSA_PRIVATE 等）数据严禁放在客户端，同时加签过程务必要放在服务端完成，
 *  否则可能造成商户私密数据泄露或被盗用，造成不必要的资金损失，面临各种安全风险。
 *
 *  Warning:
 *
 *  For demonstration purpose, the assembling and signing of the request parameters are done on
 *  the client side in this demo application.
 *
 *  However, in practice, both assembling and signing must be carried out on the server side.
 */
public class PayDemoActivity extends AppCompatActivity {
	
	/**
	 * 用于支付宝支付业务的入参 app_id。
	 */
	public static final String APPID = "2019082266406350";


	/**
	 *  pkcs8 格式的商户私钥。
	 *
	 * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
	 * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
	 * 	RSA2_PRIVATE。
	 *
	 * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
	 * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
	 */
	public static final String RSA2_PRIVATE = "MIIEowIBAAKCAQEAp8lvXPdJRvnjNjW+yoHCUHwcgA7F+NXcv9h2hlhF1GY49DXaDoM41x3Yw8s4ZEi7f2rEnbQ24ROvQkCoP804JinCkQGjXKlqXvxnqYcQgdjtBEKrJq+ElaySEAkZT8kKT38EuO3u293woy/fOtHNM7PPznLGjOmgaaxtL6etiHqakLhc/nV8Ekpj19Dzj1kmx2/szDlUD0Bxg+JI+OxpIViIBZYjtrKFUGrfLp2Th6lyXPAP01hTGUjVAturqovkQ0GQ9yeGO10pKfymx991g5aJCs8NnvjlRkmv9LdH+xMCG6lzk6raTOLKns44w+erHeZ+wxwpPiz6kY/jW+OeLQIDAQABAoIBAHFGgCWeWV1OlUPi8wTap6oYsFEQKD2/fIIUjKhUbghgXvEYbjfABZ7Naqt8xnyF0ffKyCTEJwy7cmXjKvOwA8lYBiBJn9WOFt87vmxoYA+ZuSJYNB4w/uzhjda/ndQMoSZcVX7YO5s6chxp1GZsgAH40kHJoRw/Gepd23UB2hk5uq4DhPA0v8oH+igpIyC4R6fFJP9SvSVAs9wJ3s0GbgA0BxFvV67X0HI0o7VxNsxRpiYOTvcfbJ+8ooeTDFcECoNDeC5EHbS9PMeqtnBE06RXLw97W5VQWCCxgv399DsUL295zGRaudZKRUZ90kXr2zmnO7YJxQgxv475ntFSEOECgYEA6lJ0/pnIqEJ8YbkEu8uLXbZ1bR+ZacSvVUYUQ45hRXfmdbDS1dl2J6kRJ9qd8MQE3n0cLjR3239kBG7Uxf3cN0Ji1QWgD/mzW4EFTOE9JIWBV6J29HM6KIRRaJtUsKys+lxmgbCyhn2i3X+fubnI4K2O9uhADURKtcKNWNYTlvkCgYEAt08ykQoWCJqpeAUll4pvA9KPiPJOexdDeT2ggP/MAupbsJ7vxMPDW0OVI3Mw78W3qyofYdu1TLEGS+2RMxcj1mlqsDMSImJNYBd+HgQrfBjTluO6N2Gc4Y/fCspT1GrbeIX3sAYqVZjrO9vMN5uFVCu3vNLoOAnKbpotYDRaSdUCgYEAvM+EhkLUdQBdyUCUl94DhpenNy7nJ1rdDRFS8ryflKJwiRTaTobTtlF9CWgXLBJG+iBFvNV6Xtvfc9L6kC9mawCLMqs9tqG17F2iL7iu0+bfugWnZIIUsuEr1+aR72q0V2htEMOYltZVjbVwtu8E9kXjlQC/2/DTYnJKAvM9BcECgYA1o4YPbN0frWoThaYHXum3O6DmmMigOiFzU7YoqTEgVUV5PDi64fHDPdZhJi7WqothGZVPREQb6d0G0MBhK88/yV7/qiIPT6aGjLlJvTygNn+Lsp1l4TlPw5KnjKbVDGOmM8VTi6pNm86RmHJ8fxSXmadNKIXtB+hlTtYiRY959QKBgCSYSKGwQzo1avf4x4D46Se0ROr7CyndH4V6jb6H/N5dr7Z15828TazB/4FF64prw0EYzcK6avvSh57WVeMKDxu85jxOQBwiGgwJ16fgvuhalGj+6ZuuARuZA7yB/HMwrKNe7h0xMjy6nmjnxqCR4boUzMIxL3N2jgRNLb6NPw3r";
	public static final String RSA_PRIVATE = "";
	
	private static final int SDK_PAY_FLAG = 1;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * 支付宝支付业务示例
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {

			return;
		}
	
		/*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo 的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		
		final Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

}
