package cn.hilondev.drawing.paint.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import cn.hilondev.drawing.R;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/13 10:57
 *     @version : 1.0
 * </pre>
 */
public class CanvasActionView extends View {

    private static final String TAG = "Henry";

    private Bitmap mBitmap;
    private Bitmap mDrawBitmap;
    private Canvas mDrawCanvas;

    private int mBitmapDisplayWidth, mBitmapDisplayHeight;
    private int mBitmapWidth, mBitmapHeight;

    private int mRotateDegree = 0;
    private float mScale = 1f;
    private RectF mBitmapDrawRect;
    private PointF mCenterPoint;

    private final List<ShapeItem> mShapeItemList = new ArrayList<>();
    private ShapeItem mCurrentShapeItem;

    public CanvasActionView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hencoder);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        mBitmapDrawRect = new RectF();
        mDrawBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mDrawCanvas = new Canvas(mDrawBitmap);
        mCenterPoint = new PointF(getWidth() / 2f, getHeight() / 2f);
        calculateScaleAndSize();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    /**
     * 增加旋转角度，以90度为单位
     */
    public void addRotate() {
        mRotateDegree = (mRotateDegree + 90) % 360;
        calculateScaleAndSize();
        onRotateDegreeAndScaleChanged();
        invalidate();
    }

    public int getRotateDegree() {
        return mRotateDegree;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "event=" + event.toString());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                handleDown(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                handleMove(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                handleUp(event.getX(), event.getY());
                break;

            default:
                break;
        }
        invalidate();
        return true;
    }

    private void handleDown(float x, float y) {
        mCurrentShapeItem = addNewShapeItem(x, y);
    }

    private void handleMove(float x, float y) {
        mCurrentShapeItem.move(x, y);
    }

    private void handleUp(float x, float y) {
        mCurrentShapeItem.up(x, y);
        mCurrentShapeItem.addToList(mShapeItemList);
        mCurrentShapeItem = null;
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    /**
     * 旋转角度和缩放比率改变时调用，用来更新涂鸦对象的相关属性
     */
    private void onRotateDegreeAndScaleChanged() {
        for (ShapeItem shapeItem : mShapeItemList) {
            shapeItem.addRotateDegree();
            shapeItem.setClipRect(mBitmapDrawRect);
            shapeItem.setScale(mScale);
        }
    }

    private ShapeItem addNewShapeItem(float x, float y) {
        ShapeItem shapeItem =
                new ShapeItem(mScale, 0, 0, mBitmapDrawRect, mCenterPoint);
        shapeItem.down(x, y);
        return shapeItem;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 背景灰色
        canvas.drawColor(Color.GRAY);

        // ----------绘制背景底图----------

        // 填充透明像素
        mDrawBitmap.eraseColor(Color.TRANSPARENT);

        // 保存图层
        mDrawCanvas.save();
        // 裁剪画布
        mDrawCanvas.clipRect(mBitmapDrawRect);
        // 偏移画布到视图中心点
        mDrawCanvas.translate(getWidth() / 2f, getHeight() / 2f);
        // 旋转画布
        mDrawCanvas.rotate(mRotateDegree, 0, 0);
        // 缩放画布，以让图片适应视图的尺寸，并完全展示
        mDrawCanvas.scale(mScale, mScale);
        // 绘制图片
        mDrawCanvas.drawBitmap(mBitmap, -mBitmapWidth / 2f, -mBitmapHeight / 2f, null);
        // 恢复图层
        mDrawCanvas.restore();

        // ----------涂鸦区域绘制----------

        for (ShapeItem shapeItem : mShapeItemList) {
            shapeItem.draw(mDrawCanvas);
        }

        if (mCurrentShapeItem != null) {
            mCurrentShapeItem.draw(mDrawCanvas);
        }

        // 将整体涂鸦bitmap绘制到view画布上
        canvas.drawBitmap(mDrawBitmap, 0, 0, null);
    }

    /**
     * 生成涂鸦有效区域的bitmap
     *
     * @return
     */
    public Bitmap getDrawingBitmap() {
        Bitmap realDrawingBitmap = Bitmap.createBitmap(mDrawBitmap, (int) mBitmapDrawRect.left,
                (int) mBitmapDrawRect.top, mBitmapDisplayWidth, mBitmapDisplayHeight);
        return realDrawingBitmap;
    }

    /**
     * 计算底图图片的缩放比率，以及底图展示尺寸和区域
     */
    private void calculateScaleAndSize() {
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "width=" + width);
        Log.d(TAG, "height=" + height);

        int bitmapWidth = mBitmapWidth;
        int bitmapHeight = mBitmapHeight;

        if (mRotateDegree % 180 != 0) {
            // 底图方向为竖直时，交换宽高大小
            bitmapWidth = mBitmapHeight;
            bitmapHeight = mBitmapWidth;
        }

        Log.d(TAG, "bitmapWidth=" + bitmapWidth);
        Log.d(TAG, "bitmapHeight=" + bitmapHeight);

        float xRatio = width * 1f / bitmapWidth;
        float yRatio = height * 1f / bitmapHeight;

        float ratio = Math.min(xRatio, yRatio);

        mBitmapDisplayWidth = (int) (bitmapWidth * ratio);
        mBitmapDisplayHeight = (int) (bitmapHeight * ratio);

        Log.d(TAG, "mBitmapDisplayWidth=" + mBitmapDisplayWidth);
        Log.d(TAG, "mBitmapDisplayHeight=" + mBitmapDisplayHeight);

        Log.d(TAG, "ratio=" + ratio);
        mScale = ratio;

        mBitmapDrawRect.left = getWidth() / 2f - mBitmapDisplayWidth / 2f;
        mBitmapDrawRect.top =
                getHeight() / 2f - mBitmapDisplayHeight / 2f;
        mBitmapDrawRect.right =
                getWidth() / 2f + mBitmapDisplayWidth / 2f;
        mBitmapDrawRect.bottom =
                getHeight() / 2f + mBitmapDisplayHeight / 2f;

        Log.d(TAG, "mBitmapDrawRect=" + mBitmapDrawRect.toString());
    }
}
