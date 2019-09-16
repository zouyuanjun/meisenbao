package com.jxtk.mspay.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.adapter.ChargeAdapter;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.demo.util.OrderInfoUtil2_0;
import com.jxtk.mspay.entity.ChargeMEntity;
import com.jxtk.mspay.entity.PayResult;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/7/31 0031
 * description:
 */public class RechargeActivity extends MyActivity {
    @BindView(R.id.title)
    TitleBar tiele;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    ChargeAdapter chargeAdapter;

    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


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
                        toast("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        toast("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        showLoading();
        tvRule.setText(Html.fromHtml("通过微信支付充值，充值金额实时自动到账，<font size=\"3\" color=\"#06a8fb\">充值金额不允许提现</font>，仅允许在美森宝进行消费使用"));
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getchargelist(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                showComplete();
                List<ChargeMEntity> chargeMEntities = JSON.parseArray(JsonUtils.getStringValue(result, "list"), ChargeMEntity.class);
                if (null != chargeMEntities && chargeMEntities.size() > 0) {
                    recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    chargeAdapter = new ChargeAdapter(chargeMEntities);
                    recyclerview.setAdapter(chargeAdapter);
                    chargeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getpaystring(Constant.TOKEN,chargeMEntities.get(position).getPay_money(),"android","1");
                            HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                                @Override
                                public void onSuccess(String result) {
                                    Log.d(result);

                                    pay(JsonUtils.getStringValue(result,"pay_string"));
                                }

                                @Override
                                public void onFault(String errorMsg) {
                                    toast(errorMsg);
                                }

                            }));


                        }
                    });
                }
            }

            @Override
            public void onFault(String errorMsg) {
                showComplete();
                toast(errorMsg);
            }
        }));

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

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent=new Intent(RechargeActivity.this,WebActivity.class);
        intent.putExtra(Constant.Intent_TAG,"14");
        intent.putExtra(Constant.Intent_TAG2,"充值规则说明");
        startActivity(intent);
    }

    @Override
    public boolean statusBarDarkFont() {
        return true;
    }
 //String getS4="method=alipay.trade.app.pay&app_id=2019082266406350&charset=utf-8&sign_type=RSA2&version=1.0&timestamp=2019-08-29+17%3A19%3A38&notify_url=https%3A%2F%2Fwww.alipay.com&biz_content=%7B%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.08%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22body%22%3A%22%22%2C%22out_trade_no%22%3A%221567070378%22%7D&sign=SQBc2vG8n8MNOaelJDsdtDUiV%2BxKR2n5bcgJLMWOCDQkfVBpF%2FHpQN6v0LIL7Yk2Ghd60UEr8CrCNL9AYtrtXktsj8865x5ElkXwlgyvvwvnBoiTE1kpOTaKuaVsLQMZH4qhiDsf1F2yZFSOF3IuhcrQAGvn1N2hT4XJLc%2BAT8ZTZZqykmL%2FNLZJYN0Abqpu%2F93FvgCugZ5PMQyFSBxWHdjNRg7MuIEq6Es25yhjaG%2FVgOIoKqKkaX8TQaWUqaMsBf5UyXXf4Uta8a%2F94TFfo%2FWKl2udPP3ry6K78aQDp2ecVtXKyN5S5EFW%2FWbnC38hBihDuInGVxlcFycsWJu1jA%3D%3D";
 // String getS4="method=alipay.trade.app.pay&app_id=2019082266406350&charset=utf-8&sign_type=RSA2&version=1.0&timestamp=2019-08-29+17%3A24%3A23&notify_url=https%3A%2F%2Fwww.alipay.com&biz_content=%7B%22passback_params%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A1%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22body%22%3A%22%22%2C%22out_trade_no%22%3A%221567070663%22%7D&sign=H14pxzVuplOxocqpDdR%2BmcXo9HWFopKEoJduTzd88fXmFSSN2sxqTMSiLOwZi75sOmo0OB9wC8NBiw1tr0ofqbLHBReuRWfMz18%2BTyXOMW27s4%2FF4lBE3iO8sekXZuQtnpjjRJQB5EdXfj0wwu%2FzZv8gK8MO5XEfCKOqRRtsrhDBkvQtESeu7P7ukHB%2FBaxZNwAXhIilCP6Q9ySE3tj00l5Mw71zrrflHPbUBgN5htkT0oGQ4REeellWIUhsgnFy1NRd59iTXZW6LpLvWT9u5Yx3WVGkXPBK37FXysS4xLkQfVQCi%2BQcG3nRX4uxtUaZlqeL%2FZ3TTbPhXWnQj9TUjw%3D%3D";
 //  String getS4="method=alipay.trade.app.pay&app_id=2019082266406350&charset=utf-8&sign_type=RSA2&version=1.0&timestamp=2019-08-29+17%3A29%3A29&return_url=&notify_url=https%3A%2F%2Fwww.alipay.com&biz_content=%7B%22passback_params%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A1%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22body%22%3A%22%22%2C%22out_trade_no%22%3A%221567070969%22%7D&sign=UK58IXaBVQo4nE1E%2BcA6jC7nREX5jx3gLN%2B7SSLe%2FJG8zSp8KBcFtIzQvRMkgduy8N5x3icDtBUibYKWt%2FBX1P7L0Z7oKUDpxn96V0cZeeLW2cmALUqjTNfs7co7mqog30IsldSJ4pYdDDUGPKQx2rc8GupawNrNVcoNhLRPye2mJQrbU58FbgPegQqa81%2BxalHfxDCExmdxZoZvesKGDGTZN%2FJQ3mQ79crSr0jMA8AR7yGExN43UgnT8NXlwflgFtogWpSo8kWl2LxAPcTLqkxJdOMs9eeIiZGa4xHsJ286LY7UI7xk3STjEKsU%2FUJQi251WmSSKF1zu0z1OuMk0w%3D%3D";
   String getS4="method=alipay.trade.app.pay&app_id=2019082266406350&format=JSON&charset=UTF-8&sign_type=RSA2&version=1.0&timestamp=2019-08-29+17%3A33%3A51&notify_url=https%3A%2F%2Fwww.alipay.com&biz_content=%7B%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22body%22%3A%22%22%2C%22out_trade_no%22%3A%221567071231%22%2C%22total_amount%22%3A9%2C%22passback_params%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&sign=blZap%2FICgZMHhatYSl1bR476Ibm0zlfsJxjeJK91uDP4gQd1vemc2ibLDddp7FeWOxwAkS%2F0Y3Wk37rHes34UiTNsH53RLPzHug5o%2FgIx6mTeogtl2HiaJIYUBpYWEih2Rw9x9ZtmkRQzEdn%2BWO7bSxhShaIDIx3yiLQ%2FS2%2BjyG2mwMJ6H%2FJEdt%2BnL4rX4zHmadL8z5iIxTR1lOsLEJqcM%2Fj4yaA787Fq%2B0wkuOSd%2BJEuayzxRdLTBFcQoYEDptWBcCeAAssL9QgIbrbPNtK5yrk5YV95bpvKaXct3GyL9F9i8SNMdg5UcJMRVosWECQpoye9oJhDiGV4qC6rSPS0g%3D%3D";
    private void pay(String string) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
           //     Log.d(sg.length()+">>>>>>>"+D.length());
              //  Log.d(string);
              //  Log.d(getAppid);
                Map<String, String> result = alipay.payV2(string, true);
                android.util.Log.i("msp", result.toString());

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
