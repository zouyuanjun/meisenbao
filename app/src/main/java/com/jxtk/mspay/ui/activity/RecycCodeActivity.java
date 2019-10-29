package com.jxtk.mspay.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxtk.mspay.Constant;
import com.jxtk.mspay.R;
import com.jxtk.mspay.common.MyActivity;
import com.zou.fastlibrary.image.ImageLoader;
import com.zou.fastlibrary.utils.DataKeeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 收钱码
*
* */
public class RecycCodeActivity extends MyActivity {
    @BindView(R.id.im_paycode)
    ImageView imPaycode;
    @BindView(R.id.tv_savepic)
    TextView tvSavepic;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.textView17)
    TextView textView17;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyccode;
    }

    @Override
    protected int getTitleId() {
        return R.id.title;
    }

    @Override
    protected void initView() {
        textView17.setText("美森宝：" + Constant.userInfoBean.getNickname());
        ImageLoader.loadImage(imPaycode, Constant.BASE_URL + Constant.QRcode);
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

    @OnClick(R.id.tv_savepic)
    public void onViewClicked() {
         screenShots(getBaseContext(), constraintLayout);
       toast("保存完成，请在相册查看");
    }

    /**
     * 保存一个view的截图
     *
     * @param context
     * @param view
     * @return
     */
    public static boolean screenShots(Context context, View view) {
        boolean flag = false;
        Bitmap bitmap = getCacheBitmap(view);
        if (bitmap != null) {
            final String path = DataKeeper.imagePath + "收款码.png";
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            saveImageToGallery(context, bitmap);
//            flag = ImageUtils.save(bitmap, path, format, true);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                Intent mediaScanIntent = new Intent(
//                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                mediaScanIntent.setData(FileUtils.getUri(context, path));
//                context.sendBroadcast(mediaScanIntent);
//            }

        }
        return flag;
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    public static Bitmap getCacheBitmap(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "meisengbao");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName ="收款码.png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }
}
