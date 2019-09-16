package com.zou.fastlibrary.widget;

/*
 * @author 棉发糖
 * @emil zouyuanjun@qq.com
 * create  2019/8/7 0007
 * description:
 */import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zou.fastlibrary.R;
import com.zou.fastlibrary.utils.Log;

/**
 * @author 高延荣
 * @date 2019/1/22 14:54
 * 描述:
 */
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private Context context;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * 半径
     */
    private float radius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    /**
     * 描边
     */
    private float borderWidth;
    private float borderSpace;
    private int borderColor;
    /**
     * 是否有弧度，即是否需要绘制圆弧
     */
    private boolean circle;

    float width, height;

    public CircleImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        //  半径
        radius = ta.getDimension(R.styleable.CircleImageView_radius, 12);
        topLeftRadius = ta.getDimension(R.styleable.CircleImageView_topLeftRadius, 0);
        topRightRadius = ta.getDimension(R.styleable.CircleImageView_topRightRadius, 0);
        bottomLeftRadius = ta.getDimension(R.styleable.CircleImageView_bottomLeftRadius, 0);
        bottomRightRadius = ta.getDimension(R.styleable.CircleImageView_bottomRightRadius, 0);

        //  描边
        borderWidth = ta.getDimension(R.styleable.CircleImageView_borderWidth, 0);
        borderSpace = ta.getDimension(R.styleable.CircleImageView_borderSpace, 0);
        borderColor = ta.getColor(R.styleable.CircleImageView_borderColor, Color.WHITE);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= radius && height > radius) {
            Path path = new Path();
            //四个圆角
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.quadTo(width, 0, width, radius);
            path.lineTo(width, height - radius);
            path.quadTo(width, height, width - radius, height);
            path.lineTo(radius, height);
            path.quadTo(0, height, 0, height - radius);
            path.lineTo(0, radius);
            path.quadTo(0, 0, radius, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}

