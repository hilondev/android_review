package cn.hilondev.drawing.doodle.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 15:30
 *     @version : 1.0
 * </pre>
 */
public interface IShape extends ITouchEvent {

    /**
     * 绘制
     *
     * @param canvas
     * @param paint
     */
    void draw(@NonNull Canvas canvas, @NonNull Paint paint);
}
