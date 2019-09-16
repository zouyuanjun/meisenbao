package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.bar.TitleBar;
import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.entity.AddressBean;
import com.jxtk.mspay.entity.UserInfoBean;
import com.jxtk.mspay.netutils.HttpManage;
import com.jxtk.mspay.netutils.OnSuccessAndFaultListener;
import com.jxtk.mspay.netutils.OnSuccessAndFaultSub;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.SelectPhotoUtils;
import com.zou.fastlibrary.utils.StringUtil;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.jxtk.mspay.Constant.addressBean;
import static com.jxtk.mspay.Constant.userInfoBean;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/7 0007
 * description:
 */public class UpadateInfoActivity extends MyActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.title)
    TitleBar title;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tv_formalbum)
    TextView tvFormalbum;
    @BindView(R.id.tv_formcamcra)
    TextView tvFormcamcra;
    @BindView(R.id.ed_nickname)
    EditText edNickname;
    @BindView(R.id.tv_men)
    TextView tvMen;
    @BindView(R.id.ll_men)
    RelativeLayout llMen;
    @BindView(R.id.tv_women)
    TextView tvWomen;
    @BindView(R.id.ll_women)
    RelativeLayout llWomen;
    @BindView(R.id.tv_phonenumber)
    TextView tvPhonenumber;
    String type = "1";

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_updateinfo;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        if (null != userInfoBean) {
            ImageLoader.loadImage(circleImageView, Constant.BASE_URL + userInfoBean.getAvatar());
            edNickname.setText(userInfoBean.getNickname());
            edNickname.setSelection(userInfoBean.getNickname().length());
            tvPhonenumber.setText(userInfoBean.getMobile());
            if (userInfoBean.getSex().equals("女")) {
                changesort("女");
            } else {
                changesort("男");
            }
        }
    }

    @Override
    protected void initData() {

    }

    public void changesort(String changetype) {
        if (changetype.equals("男")) {
            llMen.setBackgroundResource(R.drawable.shape_corners_lan12);
            tvMen.setTextColor(Color.parseColor("#ffffff"));
            tvWomen.setTextColor(Color.parseColor("#666666"));
            llWomen.setBackgroundResource(R.drawable.shape_corners_12);
            type = "1";
        } else if (changetype.equals("女")) {
            llMen.setBackgroundResource(R.drawable.shape_corners_12);
            llWomen.setBackgroundResource(R.drawable.shape_corners_lan12);
            tvMen.setTextColor(Color.parseColor("#666666"));
            tvWomen.setTextColor(Color.parseColor("#ffffff"));
            type = "2";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.tv_formalbum, R.id.tv_formcamcra, R.id.ll_men, R.id.ll_women})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_formalbum:
                SelectPhotoUtils.selectphoto(1, takePhoto);
                break;
            case R.id.tv_formcamcra:
                SelectPhotoUtils.selectphoto(2, takePhoto);
                break;
            case R.id.ll_men:
                changesort("男");
                break;
            case R.id.ll_women:
                changesort("女");
                break;
        }
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        String name = edNickname.getText().toString();
        if (StringUtil.isEmpty(name)) {
            toast("昵称不能为空");
            return;
        }
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("token", Constant.TOKEN);
        map.put("file", image+"");
        map.put("nickname", name);
        map.put("gender", type);
        Observable<ResponseBody> observable   = HttpManage.getInstance().getHttpApi().edit_user(map);

        HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                Observable<ResponseBody> observable = HttpManage.getInstance().getHttpApi().getUserInfo(Constant.TOKEN);
                HttpManage.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(result);
                        userInfoBean = JsonUtils.stringToObject(result, UserInfoBean.class);
                        showComplete();
                        toast("修改成功");
                        initView();
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        showComplete();
                        toast(errorMsg);
                    }
                }));
        }

            @Override
            public void onFault(String errorMsg) {
                showComplete();
                toast(errorMsg);
            }
        }));
    }

    String image;
    @Override
    public void takeSuccess(TResult result) {
        Log.d(result.getImage().getCompressPath() + ">>>>>>" + result.getImage().getOriginalPath());
        File file = new File(result.getImage().getOriginalPath());

         image = "data:image/png;base64,"+ImageUtils.imageToBase64(result.getImage().getOriginalPath());
        Glide.with(getBaseContext()).load(Uri.fromFile(new File(result.getImage().getOriginalPath()))).into(circleImageView);


    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }
}
