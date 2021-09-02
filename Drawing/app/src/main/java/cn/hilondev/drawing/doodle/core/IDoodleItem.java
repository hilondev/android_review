package cn.hilondev.drawing.doodle.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 16:01
 *     @version : 1.0
 * </pre>
 */
public interface IDoodleItem extends ITouchEvent {

    /**
     * 设置x偏移量
     *
     * @param transX
     */
    void setTransX(float transX);

    /**
     * 设置y偏移量
     *
     * @param transY
     */
    void setTransY(float transY);

    /**
     * 设置缩放系数
     *
     * @param scale 缩放系数
     * @param px 缩放中心点x坐标
     * @param py 缩放中心点y坐标
     */
    void setScale(float scale, float px, float py);

    /**
     * 获取缩放系数
     *
     * @return
     */
    float getScale();

    /**
     * 增加旋转角度，以90度为单位
     */
    void addRotate();

    /**
     * 获取旋转角度
     *
     * @return
     */
    int getRotateDegree();

    /**
     * 恢复到不旋转状态
     */
    void resetRotate();

    /**
     * 将当前item对象添加到列表
     *
     * @param list 列表
     */
    void addToList(@NonNull List<IDoodleItem> list);

    /**
     * 从列表中删除当前item对象
     *
     * @param list 列表
     */
    void removeFromList(@NonNull List<IDoodleItem> list);

    /**
     * 绘制
     *
     * @param canvas
     * @param paint
     */
    void draw(@NonNull Canvas canvas, @NonNull Paint paint);

    /**
     * 设置笔刷对象
     *
     * @param pen
     */
    void setPen(IPen pen);

    /**
     * 获取笔刷对象
     *
     * @return
     */
    IPen getPen();

    /**
     * 设置形状对象
     *
     * @param shape
     */
    void setShape(IShape shape);

    /**
     * 获取形状对象
     *
     * @return
     */
    IShape getShape();

    void setClipRect(RectF clipRect);
}
