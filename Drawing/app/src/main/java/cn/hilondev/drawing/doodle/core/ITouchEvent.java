package cn.hilondev.drawing.doodle.core;

import android.graphics.PointF;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 15:34
 *     @version : 1.0
 * </pre>
 */
public interface ITouchEvent {

    /**
     * 从某个点按下
     *
     * @param p
     */
    void down(PointF p);

    /**
     * 移动到某个点
     *
     * @param p
     */
    void move(PointF p);

    /**
     * 从某个点抬起
     *
     * @param p
     */
    void up(PointF p);
}
