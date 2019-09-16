package com.jxtk.mspay.utils;

import android.content.Context;
import android.widget.ImageView;

import com.jxtk.mspay.Constant;
import com.youth.banner.loader.ImageLoader;
import com.zou.fastlibrary.widget.CircleImageView;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/9 0009
 * description:
 */public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        com.zou.fastlibrary.image.ImageLoader.loadRoundImage(imageView,(String) Constant.BASE_URL+path,90);

    }

    @Override
    public ImageView createImageView(Context context) {
        CircleImageView circleImageView=new CircleImageView(context);
        circleImageView.setRadius(30);
        return circleImageView;
    }
}
