package cn.hilondev.drawing.doodle.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.NonNull;
import cn.hilondev.drawing.doodle.core.IShape;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 16:51
 *     @version : 1.0
 * </pre>
 */
class BaseShape implements IShape {

    /**
     * 内部path对象
     */
    protected Path mPath;

    public BaseShape() {
        mPath = new Path();
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawPath(mPath, paint);
    }

    @Override
    public void down(@NonNull PointF p) {

    }

    @Override
    public void move(@NonNull PointF p) {

    }

    @Override
    public void up(@NonNull PointF p) {

    }
}
