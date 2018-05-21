package sang.com.minitools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

/**
 * 作者： ${PING} on 2018/5/21.
 * 圆角ImageView
 */

public class CircularImageView extends android.support.v7.widget.AppCompatImageView {

    /**
     * 是否显示边框
     */
    private boolean showBorder = true;

    /**
     * 边框颜色
     */
    private int borderColor;

    private int borderWidth;

    /**
     * 左上圆角半径
     */
    protected int radiusLeftTop;
    /**
     * 右上圆角半径
     */
    protected int radiusRightTop;
    /**
     * 右下圆角半径
     */
    protected int radiusRightBottom;
    /**
     * 左下圆角半径
     */
    protected int radiusLeftBottom;

    protected RectF rectLeftTop, rectRightTop, rectRightBottom, rectLeftBottom;
    protected RectF rectF;


    /**
     * 统一设置圆角半径，单个和统一的都设置，以统一为主
     */
    protected int radios;

    private Path borderPath;

    private Paint mPaint;

    private Xfermode xfermode;
    private WeakReference<Bitmap> mWeakBitmap;

    public CircularImageView(Context context) {
        this(context, null, 0);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
        setRadius(100);
        setBackgroundColor(Color.WHITE);
    }


    private void initView(Context context, AttributeSet attrs) {
        //默认为白色
        borderColor = Color.RED;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(borderColor);
        borderPath = new Path();

        rectLeftTop = new RectF();
        rectRightTop = new RectF();
        rectRightBottom = new RectF();
        rectLeftBottom = new RectF();
        borderWidth = 5;
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    }

    /**
     * 设置圆角大小
     *
     * @param radius
     */
    public void setRadius(int radius) {
        if (getWidth()>0){
            if (radios > Math.min(getWidth(), getHeight()) / 2) {
                radios =  Math.min(getWidth(), getHeight()) / 2;
            }
        }
        setRadius(radius, radius, radius, radius);
    }

    /**
     * 设置圆角大小
     *
     * @param leftTop
     * @param rightTop
     * @param rightBottom
     * @param leftBottom
     */
    public void setRadius(int leftTop, int rightTop, int rightBottom, int leftBottom) {
        radiusLeftTop = leftTop;
        radiusRightTop = rightTop;
        radiusRightBottom = rightBottom;
        radiusLeftBottom = leftBottom;
        postInvalidate();
    }


    /**
     * 设置是否显示边框
     *
     * @param showBorder
     */
    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
        postInvalidate();
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        postInvalidate();
    }

    /**
     * 设置边框颜色
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(rectF, mPaint, Canvas.ALL_SAVE_FLAG);
        setPadding(borderWidth, borderWidth, borderWidth, borderWidth);
        super.onDraw(canvas);
        mPaint.setXfermode(xfermode);
        mPaint.setStrokeWidth(borderWidth);

        mPaint.setStyle(Paint.Style.FILL);
        initRadios(getWidth(), getHeight());
        canvas.drawPath(borderPath, mPaint);
        mPaint.setXfermode(null);

        if (showBorder) {
            mPaint.setColor(borderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(borderPath, mPaint);
        }

        canvas.restore();

    }

    private void initRadios(int w, int h) {
        borderPath.reset();
        JLog.i(radios+">>>>>>>"+Math.min(w, h) / 2);
        int left = (int) Math.ceil(borderWidth);
        int top = (int) Math.ceil(borderWidth);

        int right = (int) Math.ceil(w - borderWidth);
        int bottom = (int) Math.ceil(h - borderWidth);


        //左上
        borderPath.moveTo(left, top + radiusLeftTop);
        rectLeftTop.left = left;
        rectLeftTop.top = top;
        rectLeftTop.right = radiusRightTop * 2 + left;
        rectLeftTop.bottom = top + radiusLeftTop * 2;
        borderPath.arcTo(rectLeftTop, 180, 90);


        //右上
        borderPath.lineTo(right - radiusRightTop, top);
        rectRightTop.left = right - radiusRightTop * 2;
        rectRightTop.top = top;
        rectRightTop.right = right;
        rectRightTop.bottom = top + radiusLeftTop * 2;
        borderPath.arcTo(rectRightTop, 270, 90);
//
//        //右下
        borderPath.lineTo(right, bottom - radiusRightBottom);
        rectRightBottom.left = right - radiusRightTop * 2;
        rectRightTop.top = bottom - radiusRightBottom * 2;
        rectRightTop.right = right;
        rectRightTop.bottom = bottom;
        borderPath.arcTo(rectRightTop, 360, 90);
//
//        //左下
        borderPath.lineTo(left + radiusLeftBottom, bottom);
        rectLeftBottom.left = left;
        rectLeftBottom.top = bottom - radiusLeftBottom * 2;
        rectLeftBottom.right = radiusLeftBottom * 2 + left;
        rectLeftBottom.bottom = bottom;
        borderPath.arcTo(rectLeftBottom, 90, 90);
        borderPath.lineTo(left, top + radiusLeftTop);
        borderPath.close();
    }

}
