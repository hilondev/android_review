package cn.hilondev.drawing.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hilondev.drawing.doodle.core.IDoodle;
import cn.hilondev.drawing.doodle.core.IDoodleItem;
import cn.hilondev.drawing.doodle.core.IPen;
import cn.hilondev.drawing.doodle.core.IShape;
import cn.hilondev.drawing.doodle.pen.Brush;
import cn.hilondev.drawing.doodle.shape.CurvedLine;
import cn.hilondev.drawing.doodle.shape.ShapeFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 14:25
 *     @version : 1.0
 * </pre>
 */
public class DoodleView extends View implements IDoodle {

    private static final String TAG = "DoodleView";

    /**
     * 涂鸦视图默认宽度（单位：DP）
     */
    private static final int DEFAULT_WIDTH_IN_DP = 100;
    /**
     * 涂鸦视图默认高度（单位：DP）
     */
    private static final int DEFAULT_HEIGHT_IN_DP = 100;
    /**
     * 涂鸦画笔默认笔画宽度（单位：DP）
     */
    private static final int DEFAULT_PEN_SIZE_IN_DP = 2;

    /**
     * 涂鸦画笔默认笔画颜色
     */
    private static final int DEFAULT_PEN_COLOR = Color.BLACK;

    /**
     * 视图默认宽高
     */
    private int mDefaultWidth, mDefaultHeight;

    /**
     * 画笔笔画宽度
     */
    private float mPenSize;

    /**
     * 画笔笔画颜色
     */
    private int mPenColor;

    /**
     * 当前绘制形状类型
     */
    private Class<? extends IShape> mCurrentShapeType;

    /**
     * 当前画笔对象
     */
    private IPen mCurrentPen;

    /**
     * 底图 bitmap
     */
    private Bitmap mBaseMap;

    /**
     * 涂鸦 bitmap
     */
    private Bitmap mDoodleMap;

    /**
     * 涂鸦画布
     */
    private Canvas mDoodleCanvas;

    /**
     * 真实画笔
     */
    private Paint mPaint;

    /**
     * 是否橡皮擦模式
     */
    private boolean mEraserMode;

    /**
     * 旋转角度
     */
    private int mRotateDegree = 0;

    /**
     * 缩放系数
     */
    private float mScale = 1f;

    /**
     * x方向偏移量
     */
    private float mTransX = 0f;

    /**
     * y方向偏移量
     */
    private float mTransY = 0f;

    /**
     * 底图的显示大小
     */
    private int mBaseMapDisplayWidth, mBaseMapDisplayHeight;

    /**
     * 底图的原始大小
     */
    private int mBaseMapWidth, mBaseMapHeight;

    /**
     * 底图绘制矩形区域
     */
    private RectF mBaseMapDrawRect;

    /**
     * 视图中心点（视图坐标系）
     */
    private PointF mCenterPoint;

    /**
     * 所有的涂鸦笔画列表
     */
    private final List<IDoodleItem> mDoodleItems = new ArrayList<>();

    /**
     * 所有的待恢复的笔画列表
     */
    private final List<IDoodleItem> mRedoDoodleItems = new ArrayList<>();

    /**
     * 当前笔画对象
     */
    private IDoodleItem mCurrentDoodleItem;

    /**
     * 判断是否已经测量过
     */
    private boolean mHasMeasured;

    /**
     * 用来解析底图的任务对象
     */
    private BitmapDecoder mBitmapDecodeTask;

    public DoodleView(Context context) {
        this(context, null);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        initViewSize();
        initPenAndShape();
    }

    private void initViewSize() {
        mDefaultWidth = dp2px(DEFAULT_WIDTH_IN_DP);
        mDefaultHeight = dp2px(DEFAULT_HEIGHT_IN_DP);
    }

    private void initPenAndShape() {
        initPen();
        initShape();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mDefaultHeight);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHasMeasured = true;
        initDoodleMapAndCanvas();
        runBitmapDecodeTask();
        if (mBaseMap != null) {
            calculateScaleAndSize();
        }
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

    @Override
    protected void onDraw(Canvas canvas) {
        // 背景灰色
        canvas.drawColor(Color.GRAY);
        // 绘制背景底图
        if (mBaseMap != null) {
            drawBaseMap(canvas);
        }
        // 绘制涂鸦区域
        drawDoodleItems(canvas);
    }

    private void drawBaseMap(Canvas canvas) {
        // 保存图层
        canvas.save();
        // 裁剪画布
        canvas.clipRect(mBaseMapDrawRect);
        // 偏移画布到视图中心点
        canvas.translate(getWidth() / 2f, getHeight() / 2f);
        // 旋转画布
        canvas.rotate(mRotateDegree, 0, 0);
        // 缩放画布，以让图片适应视图的尺寸，并完全展示
        canvas.scale(mScale, mScale);
        // 设置偏移量
        canvas.translate(mTransX * mScale, mTransY * mScale);
        // 绘制底图
        canvas.drawBitmap(mBaseMap, -mBaseMapWidth / 2f, -mBaseMapHeight / 2f, null);
        // 恢复图层
        canvas.restore();
    }

    private void drawDoodleItems(Canvas canvas) {
        // 填充透明像素
        mDoodleMap.eraseColor(Color.TRANSPARENT);
        // 绘制已完成的涂鸦笔画
        for (IDoodleItem doodleItem : mDoodleItems) {
            doodleItem.draw(mDoodleCanvas, mPaint);
        }
        // 绘制当前正在涂鸦的笔画
        if (mCurrentDoodleItem != null) {
            mCurrentDoodleItem.draw(mDoodleCanvas, mPaint);
        }
        // 将涂鸦bitmap绘制到view画布上
        canvas.drawBitmap(mDoodleMap, 0, 0, null);
    }

    @Override
    public void setBaseMap(@NonNull Bitmap bitmap) {
        mBitmapDecodeTask = new DecodeBitmapTask(bitmap);
        if (mHasMeasured) {
            onBaseMapSetup();
        }
    }

    @Override
    public void setBaseMap(@NonNull File file) {
        mBitmapDecodeTask = new DecodeFileTask(file);
        if (mHasMeasured) {
            onBaseMapSetup();
        }
    }

    @Override
    public void setBaseMap(@NonNull InputStream is) {
        mBitmapDecodeTask = new DecodeInputStreamTask(is);
        if (mHasMeasured) {
            onBaseMapSetup();
        }
    }

    @Override
    public Bitmap getBaseMap() {
        return mBaseMap;
    }

    @Override
    public Bitmap getResultDoodleMap() {
        Bitmap resultBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas resultCanvas = new Canvas(resultBitmap);
        drawBaseMap(resultCanvas);
        drawDoodleItems(resultCanvas);
        return Bitmap.createBitmap(resultBitmap, (int) mBaseMapDrawRect.left,
                (int) mBaseMapDrawRect.top, mBaseMapDisplayWidth, mBaseMapDisplayHeight);
    }

    @Override
    public void setPenSize(float penSize) {
        if (!String.valueOf(mPenSize).equals(String.valueOf(penSize))) {
            mPenSize = penSize;
            onPenSettingsChange();
        }
    }

    @Override
    public void setPenColor(int color) {
        if (mPenColor != color) {
            mPenColor = color;
            onPenSettingsChange();
        }
    }

    @Override
    public void setShape(Class<? extends IShape> shapeType) {
        mCurrentShapeType = shapeType;
    }

    @Override
    public List<IDoodleItem> getDoodleItems() {
        return mDoodleItems;
    }

    @Override
    public List<IDoodleItem> getRedoDoodleItems() {
        return mRedoDoodleItems;
    }

    @Override
    public int getDoodleItemCount() {
        return mDoodleItems.size();
    }

    @Override
    public int getRedoDoodleItemCount() {
        return mRedoDoodleItems.size();
    }

    @Override
    public boolean undo() {
        int doodleItemCount = getDoodleItemCount();
        if (doodleItemCount == 0) {
            return false;
        }
        IDoodleItem lastItem = mDoodleItems.get(doodleItemCount - 1);
        lastItem.removeFromList(mDoodleItems);
        lastItem.addToList(mRedoDoodleItems);
        invalidate();
        return true;
    }

    @Override
    public boolean redo() {
        int redoDoodleItemCount = getRedoDoodleItemCount();
        if (redoDoodleItemCount == 0) {
            return false;
        }
        IDoodleItem lastRedoItem = mRedoDoodleItems.get(redoDoodleItemCount - 1);
        lastRedoItem.removeFromList(mRedoDoodleItems);
        lastRedoItem.addToList(mDoodleItems);
        invalidate();
        return true;
    }

    @Override
    public void addRotate() {
        mRotateDegree = (mRotateDegree + 90) % 360;
        calculateScaleAndSize();
        onRotateDegreeAndScaleChanged();
        invalidate();
    }

    @Override
    public int getRotateDegree() {
        return mRotateDegree;
    }

    @Override
    public boolean saveResultDoodleToFile(File file) {
        return BitmapUtil.saveBitmapToFile(getResultDoodleMap(), file);
    }

    /**
     * 设置橡皮擦模式
     *
     * @param enableEraser
     */
    public void setEraserMode(boolean enableEraser) {
        mEraserMode = enableEraser;
    }

    /**
     * dp转px
     *
     * @param dpValue dp值.
     * @return dpValue 对应的 px
     */
    private int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initPen() {
        mPenColor = DEFAULT_PEN_COLOR;
        mPenSize = dp2px(DEFAULT_PEN_SIZE_IN_DP);
        createNewPen();
    }

    private void initShape() {
        mCurrentShapeType = CurvedLine.class;
    }

    private void onPenSettingsChange() {
        createNewPen();
    }

    private void createNewPen() {
        mCurrentPen = new Brush(mPenColor, mPenSize);
    }

    private void onBaseMapSetup() {
        runBitmapDecodeTask();
        if (mBaseMap != null) {
            calculateScaleAndSize();
        }
        invalidate();
    }

    private void initDoodleMapAndCanvas() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDoodleMap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mDoodleCanvas = new Canvas(mDoodleMap);
        mCenterPoint = new PointF(getWidth() / 2f, getHeight() / 2f);
        mBaseMapDrawRect = new RectF();
        mBaseMapDrawRect.left = 0;
        mBaseMapDrawRect.top = 0;
        mBaseMapDrawRect.right = getWidth();
        mBaseMapDrawRect.bottom = getHeight();
        mBaseMapWidth = getWidth();
        mBaseMapHeight = getHeight();
        mBaseMapDisplayWidth = getWidth();
        mBaseMapDisplayHeight = getHeight();
    }

    /**
     * 计算底图图片的缩放比率，以及底图展示尺寸和区域
     */
    private void calculateScaleAndSize() {
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "width=" + width);
        Log.d(TAG, "height=" + height);

        int bitmapWidth = mBaseMapWidth;
        int bitmapHeight = mBaseMapHeight;
        if (mRotateDegree % 180 != 0) {
            // 底图方向为竖直时，交换宽高大小
            bitmapWidth = mBaseMapHeight;
            bitmapHeight = mBaseMapWidth;
        }
        Log.d(TAG, "bitmapWidth=" + bitmapWidth);
        Log.d(TAG, "bitmapHeight=" + bitmapHeight);

        float xRatio = width * 1f / bitmapWidth;
        float yRatio = height * 1f / bitmapHeight;
        float ratio = Math.min(xRatio, yRatio);
        Log.d(TAG, "ratio=" + ratio);

        mBaseMapDisplayWidth = (int) (bitmapWidth * ratio);
        mBaseMapDisplayHeight = (int) (bitmapHeight * ratio);
        Log.d(TAG, "mBitmapDisplayWidth=" + mBaseMapDisplayWidth);
        Log.d(TAG, "mBitmapDisplayHeight=" + mBaseMapDisplayHeight);

        mScale = ratio;

        mBaseMapDrawRect.left = getWidth() / 2f - mBaseMapDisplayWidth / 2f;
        mBaseMapDrawRect.top =
                getHeight() / 2f - mBaseMapDisplayHeight / 2f;
        mBaseMapDrawRect.right =
                getWidth() / 2f + mBaseMapDisplayWidth / 2f;
        mBaseMapDrawRect.bottom =
                getHeight() / 2f + mBaseMapDisplayHeight / 2f;
        Log.d(TAG, "mBitmapDrawRect=" + mBaseMapDrawRect.toString());
    }

    /**
     * 旋转角度和缩放比率改变时调用，用来更新涂鸦对象的相关属性
     */
    private void onRotateDegreeAndScaleChanged() {
        for (IDoodleItem doodleItem : mDoodleItems) {
            doodleItem.addRotate();
            doodleItem.setClipRect(mBaseMapDrawRect);
            doodleItem.setScale(mScale, 0, 0);
        }
    }

    private void handleDown(float x, float y) {
        mCurrentDoodleItem = makeNewDoodleItem(new PointF(x, y));
    }

    private void handleMove(float x, float y) {
        mCurrentDoodleItem.move(new PointF(x, y));
    }

    private void handleUp(float x, float y) {
        mCurrentDoodleItem.up(new PointF(x, y));
        mCurrentDoodleItem.addToList(mDoodleItems);
        mCurrentDoodleItem = null;
    }

    private IDoodleItem makeNewDoodleItem(PointF p) {
        IDoodleItem doodleItem;
        if (mEraserMode) {
            doodleItem =
                    new DoodleEraserItem(mRotateDegree, mScale, mBaseMapDrawRect, mCenterPoint);
        } else {
            doodleItem = new DoodleItem(mRotateDegree, mScale, mBaseMapDrawRect, mCenterPoint);
            doodleItem.setPen(mCurrentPen);
            doodleItem.setShape(ShapeFactory.create(mCurrentShapeType));
        }
        doodleItem.down(p);
        return doodleItem;
    }

    private void runBitmapDecodeTask() {
        if (mBitmapDecodeTask != null) {
            mBaseMap = mBitmapDecodeTask.decode(getWidth(), getHeight());
            mBaseMapWidth = mBaseMap.getWidth();
            mBaseMapHeight = mBaseMap.getHeight();
            mBitmapDecodeTask = null;
        }
    }

    private static class DecodeBitmapTask implements BitmapDecoder {

        private Bitmap bitmap;

        DecodeBitmapTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public Bitmap decode(int reqWidth, int reqHeight) {
            return BitmapUtil.getResizedBitmap(bitmap, reqWidth, reqHeight);
        }
    }

    private static class DecodeFileTask implements BitmapDecoder {

        private File file;

        DecodeFileTask(File file) {
            this.file = file;
        }

        @Override
        public Bitmap decode(int reqWidth, int reqHeight) {
            return BitmapUtil.getResizedBitmapFromFile(file, reqWidth, reqHeight);
        }
    }

    private static class DecodeInputStreamTask implements BitmapDecoder {

        private InputStream is;

        DecodeInputStreamTask(InputStream is) {
            this.is = is;
        }

        @Override
        public Bitmap decode(int reqWidth, int reqHeight) {
            return BitmapUtil.getResizedBitmapFromInputStream(is, reqWidth, reqHeight);
        }
    }

    private interface BitmapDecoder {
        Bitmap decode(int reqWidth, int reqHeight);
    }
}
