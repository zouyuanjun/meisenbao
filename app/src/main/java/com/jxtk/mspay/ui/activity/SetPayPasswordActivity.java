package com.jxtk.mspay.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxtk.mspay.MainActivity;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.jxtk.mspay.ui.fragment.SetPasswordFragment;
import com.jxtk.mspay.ui.fragment.SetPayPasswordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/20 0020
 * description:
 */public class SetPayPasswordActivity extends MyActivity {

    SetPayPasswordFragment setPayPasswordFragment;
    SetPasswordFragment setPasswordFragment;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_igonr)
    TextView tvIgonr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setpassword;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.7f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.7f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(linearLayout, "scaleY", 1f, 0.9f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(linearLayout, "scaleX", 1f, 0.9f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationY", -340);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageView, "translationY", -310);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(tvIgonr, "translationY", -340);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animator2).with(objectAnimator).with(objectAnimator2).with(animator3).with(animator4).with(objectAnimator3);
        animSet.setDuration(20);
        animSet.start();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (null == setPayPasswordFragment) {
            setPayPasswordFragment = new SetPayPasswordFragment();
            setPayPasswordFragment.setFinish(new SetPayPasswordFragment.FinishListener() {
                @Override
                public void finishself() {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.hide(setPayPasswordFragment);
                    fragmentTransaction.show(setPasswordFragment);
                    fragmentTransaction.commit();
                    tvIgonr.setVisibility(View.VISIBLE);
                }
            });
        }
        if (null == setPasswordFragment) {
            setPasswordFragment = new SetPasswordFragment();
            setPasswordFragment.setFinishListener(new SetPasswordFragment.FinishListener() {
                @Override
                public void finishself() {
                    startActivity(MainActivity.class);
                    finish();
                }
            });
        }
        fragmentTransaction.add(R.id.framelayout, setPayPasswordFragment);
        fragmentTransaction.add(R.id.framelayout, setPasswordFragment);
        fragmentTransaction.hide(setPasswordFragment);
        fragmentTransaction.show(setPayPasswordFragment);
        fragmentTransaction.commit();
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

    @OnClick(R.id.tv_igonr)
    public void onViewClicked() {
        startActivity(MainActivity.class);
        finish();
    }
}
