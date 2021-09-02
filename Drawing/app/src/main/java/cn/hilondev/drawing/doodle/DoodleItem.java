package cn.hilondev.drawing.doodle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import cn.hilondev.drawing.doodle.core.IDoodleItem;
import cn.hilondev.drawing.doodle.core.IPen;
import cn.hilondev.drawing.doodle.core.IShape;
import java.util.List;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 15:44
 *     @version : 1.0
 * </pre>
 */
public class DoodleItem implements IDoodleItem {

    /**
     * 笔刷
     */
    private IPen mPen;
    /**
     * 形状
     */
    private IShape mShape;

    /**
     * 创建时继承的旋转角度
     */

    private int mBornRotateDegree = 0;

    /**
     * 旋转角度
     */
    private int mRotateDegree = 0;

    /**
     * 创建时继承的缩放比率
     */
    private float mBornScale = 1.0f;

    /**
     * 缩放比率
     */
    private float mScale = 1.0f;

    /**
     * 缩放中心点x坐标
     */
    private float mScalePivotX = 0f;

    /**
     * 缩放中心点y坐标
     */
    private float mScalePivotY = 0f;

    /**
     * x方向偏移量
     */
    private float mTransX = 0f;

    /**
     * y方向偏移量
     */
    private float mTransY = 0f;

    /**
     * 裁切范围
     */
    private RectF mClipRect;

    /**
     * 中心点坐标
     */
    private PointF mCenterPoint;

    public DoodleItem(int rotateDegree, float scale, RectF clipRect,
                      PointF centerPoint) {
        mBornRotateDegree = rotateDegree;
        mRotateDegree = 0;
        mBornScale = scale;
        mScale = scale / mBornScale;
        mClipRect = clipRect;
        mCenterPoint = centerPoint;
    }

    @Override
    public void setTransX(float transX) {
        mTransX = transX;
    }

    @Override
    public void setTransY(float transY) {
        mTransY = transY;
    }

    @Override
    public void setScale(float scale, float px, float py) {
        mScale = scale / mBornScale;
        //mScalePivotX = px - mCenterPoint.x;
        //mScalePivotY = py - mCenterPoint.y;
    }

    @Override
    public float getScale() {
        return mScale;
    }

    @Override
    public void addRotate() {
        mRotateDegree = (mRotateDegree + 90) % 360;
    }

    @Override
    public int getRotateDegree() {
        return mRotateDegree;
    }

    @Override
    public void resetRotate() {
        // 根据继承的旋转角度，恢复到合适的旋转角度
        mRotateDegree = (mRotateDegree - mBornRotateDegree + 360);
    }

    @Override
    public void addToList(@NonNull List<IDoodleItem> list) {
        if (!list.contains(this)) {
            list.add(this);
        }
    }

    @Override
    public void removeFromList(@NonNull List<IDoodleItem> list) {
        list.remove(this);
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint) {

        // 保存画布状态
        canvas.save();

        // 设置画笔
        if (mPen != null) {
            mPen.config(paint);
        }

        // 画布转换
        canvas.clipRect(mClipRect);
        canvas.translate(mCenterPoint.x, mCenterPoint.y);
        canvas.rotate(mRotateDegree);
        canvas.scale(mScale, mScale, mScalePivotX, mScalePivotY);
        canvas.translate(mTransX * mScale, mTransY * mScale);

        // 绘制形状
        if (mShape != null) {
            mShape.draw(canvas, paint);
        }

        // 恢复画布状态
        canvas.restore();
    }

    @Override
    public void setPen(IPen pen) {
        mPen = pen;
    }

    @Override
    public IPen getPen() {
        return mPen;
    }

    @Override
    public void setShape(IShape shape) {
        mShape = shape;
    }

    @Override
    public IShape getShape() {
        return mShape;
    }

    @Override
    public void setClipRect(RectF clipRect) {
        mClipRect = clipRect;
    }

    @Override
    public void down(@NonNull PointF p) {
        PointF canvasPoint = transEventPointToCanvasPoint(p);
        if (mShape != null) {
            mShape.down(canvasPoint);
        }
    }

    @Override
    public void move(@NonNull PointF p) {
        PointF canvasPoint = transEventPointToCanvasPoint(p);
        if (mShape != null) {
            mShape.move(canvasPoint);
        }
    }

    @Override
    public void up(@NonNull PointF p) {
        PointF canvasPoint = transEventPointToCanvasPoint(p);
        if (mShape != null) {
            mShape.up(canvasPoint);
        }
    }

    private PointF transEventPointToCanvasPoint(PointF eventPoint) {
        PointF canvasPoint = new PointF();
        canvasPoint.x = eventPoint.x - mCenterPoint.x;
        canvasPoint.y = eventPoint.y - mCenterPoint.y;
        canvasPoint.x = canvasPoint.x / mScale;
        canvasPoint.y = canvasPoint.y / mScale;
        return canvasPoint;
    }
}
