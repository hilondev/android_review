package cn.hilondev.drawing.doodle;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import cn.hilondev.drawing.doodle.core.IPen;
import cn.hilondev.drawing.doodle.core.IShape;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 15:44
 *     @version : 1.0
 * </pre>
 */
public class DoodleItem {

    /**
     * 内部paint对象
     */
    private Paint mPaint;
    /**
     * 内部path对象
     */
    private Path mPath;
    /**
     * 笔刷
     */
    private IPen mPen;
    /**
     * 形状
     */
    private IShape mShape;

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

    public DoodleItem(float scale, float transX, float transY, RectF clipRect, PointF centerPoint) {
        mScale = scale;
        mTransX = transX;
    }
}
