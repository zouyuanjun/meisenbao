package com.jxtk.mspay.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class InviteActivity extends MyActivity {
    @BindView(R.id.rl_share_to_wechat)
    RelativeLayout rlShareToWechat;
    @BindView(R.id.rl_share_to_friend)
    RelativeLayout rlShareToFriend;
    @BindView(R.id.tv_getmoney)
    TextView tvGetmoney;
    @BindView(R.id.tv_peoplenum)
    TextView tvPeoplenum;
    @BindView(R.id.im_test)
    ImageView imTest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intive;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent = new Intent(getBaseContext(), InviteRewardsActivity.class);
        intent.putExtra(Constant.Intent_TAG, data);
        startActivity(intent);
    }

    String data;

    @Override
    protected void initData() {
        Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().invitation_view(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                data = result;
                String sum = JsonUtils.getStringValue(result, "sum");
                String sum_people = JsonUtils.getStringValue(result, "sum_people");
                String rule = JsonUtils.getStringValue(result, "rule");
                tvGetmoney.setText(sum);
                tvPeoplenum.setText(sum_people);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_share_to_wechat, R.id.rl_share_to_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_share_to_wechat:
                sharepic(2);
                break;
            case R.id.rl_share_to_friend:
                sharepic(1);
                break;
        }
    }

    private IWXAPI api;

    //判断是否安装微信客户端
    private static boolean uninstallSoftware(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void sharepic(int tag){
        Observable<ResponseBody> observable2 = HttpManage.getInstance().getHttpApi().appcode(Constant.TOKEN);
        HttpManage.getInstance().toSubscribe(observable2, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Log.d(result);
                String filename = JsonUtils.getStringValue(result, "filename");
                Glide.with(getBaseContext())
                        .asBitmap()
                        .load(Constant.BASE_URL + filename)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap photo, @Nullable Transition<? super Bitmap> transition) {


                                Bitmap qrcode = ImageUtils.resizeImage(photo, (float)150.00 / photo.getWidth());
                                int with = qrcode.getWidth();
                                int higt = qrcode.getHeight();

                                Bitmap bg = ((BitmapDrawable) getResources().getDrawable(R.drawable.share_bg)).getBitmap();
                                int bgwith = bg.getWidth();
                                int bghigt = bg.getHeight();

                                Bitmap newbitmap = Bitmap.createBitmap(bgwith, bghigt, bg.getConfig());
                                Canvas canvas = new Canvas(newbitmap);
                                canvas.drawARGB(255,0,0,0);
                                Rect src=new Rect(0,0,750,750);
                                Rect drc=new Rect(0,0,750,750);
                                canvas.drawBitmap(bg, src, drc, null);
                                int i=(bgwith -with) / 2;
                                int h=(int) (bghigt * 0.64);
                                canvas.drawBitmap(qrcode, i, h, null);
                                Log.d(bgwith + "高度" + bghigt + ">>>" + newbitmap.getWidth() + ">>>>" + newbitmap.getHeight());
                                //     imTest.setBackground(com.blankj.utilcode.util.ImageUtils.bitmap2Drawable(newbitmap));
                                sharePicByFile(getActivity(), newbitmap, tag);
                            }
                        });
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


    public void sharePicByFile(Context context, Bitmap pic2, int tag) {
        Bitmap pic = pic2;
        if (null == pic) {
            return;
        }
        if (!uninstallSoftware(context, "com.tencent.mm")) {
            Toast.makeText(context, "微信没有安装！", Toast.LENGTH_SHORT).show();
            return;
        }
        api = WXAPIFactory.createWXAPI(context, Constant.WECHAT_ID, true);
        api.registerApp(Constant.WECHAT_ID);

        WXImageObject imageObject = new WXImageObject(ImageUtils.resizeImage(pic, (float) 0.5));
        //这个构造方法中自动把传入的bitmap转化为2进制数据,或者你直接传入byte[]也行
        //注意传入的数据不能大于10M,开发文档上写的

        WXMediaMessage msg = new WXMediaMessage();  //这个对象是用来包裹发送信息的对象
        msg.mediaObject = imageObject;
        //msg.mediaObject实际上是个IMediaObject对象,
        //它有很多实现类,每一种实现类对应一种发送的信息,
        //比如WXTextObject对应发送的信息是文字,想要发送文字直接传入WXTextObject对象就行
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(pic, 150, 150, true);
        pic.recycle();
        msg.thumbData = bitmap2byteArray(thumbBitmap);
        //在这设置缩略图
        //官方文档介绍这个bitmap不能超过32kb
        //如果一个像素是8bit的话换算成正方形的bitmap则边长不超过181像素,边长设置成150是比较保险的
        //或者使用msg.setThumbImage(thumbBitmap);省去自己转换二进制数据的过程
        //如果超过32kb则抛异常
        SendMessageToWX.Req req = new SendMessageToWX.Req();    //创建一个请求对象
        req.message = msg;  //把msg放入请求对象中
        if (tag == 1) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;    //设置发送到朋友圈
        } else {
            req.scene = SendMessageToWX.Req.WXSceneSession;   //设置发送给朋友
        }
        req.transaction = "qiandao";  //这个tag要唯一,用于在回调中分辨是哪个分享请求
        boolean b = api.sendReq(req);   //如果调用成功微信,会返回true
    }

    /**
     * bitmap 转  byte[]数组
     */
    public static byte[] bitmap2byteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
