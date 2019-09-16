package com.jxtk.mspay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.MainActivity;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.zou.fastlibrary.utils.DataKeeper;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/20 0020
 * description:
 */
public class WelcomeActivity extends MyActivity {
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @BindView(R.id.btn_guide_enter)
    TextView btnGuideEnter;
    @BindView(R.id.im_welcome)
    ImageView imWelcome;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {

       String s= DataKeeper.getStringKey(getBaseContext(),Constant.FIRST_OPEN);
       Constant.TOKEN=DataKeeper.getStringKey(getBaseContext(),Constant.TOKEN_KEY);
        if (s.equals(Constant.defValue)){
            //第一次打开
            imWelcome.setVisibility(View.GONE);
            btnGuideEnter.setVisibility(View.VISIBLE);
        }else if (Constant.TOKEN.equals(Constant.defValue)){
            //登陆页面
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        }else {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }


// Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间'
        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
// 设置数据源
        bannerGuideContent.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.lunch1,
                R.drawable.lunch2,
                R.drawable.lunch3);

        bannerGuideContent.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, 0, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                DataKeeper.save(getBaseContext(), Constant.FIRST_OPEN,"1");
                finish();



            }
        });
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
}
