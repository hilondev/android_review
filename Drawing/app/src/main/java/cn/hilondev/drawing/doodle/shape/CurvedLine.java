package cn.hilondev.drawing.doodle.shape;

import android.graphics.PointF;
import androidx.annotation.NonNull;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 19:54
 *     @version : 1.0
 * </pre>
 */
public class CurvedLine extends BaseShape {

    private PointF mPrePoint;

    @Override
    public void down(@NonNull PointF p) {
        super.down(p);
        mPath.moveTo(p.x, p.y);
        mPrePoint = p;
    }

    @Override
    public void move(@NonNull PointF p) {
        super.move(p);
        if (mPrePoint != null) {
            float dx = Math.abs(p.x - mPrePoint.x);
            float dy = Math.abs(p.y - mPrePoint.y);
            if (dx >= 5 || dy >= 5) {
                mPath.quadTo(mPrePoint.x, mPrePoint.y, (p.x + mPrePoint.x) / 2,
                        (p.y + mPrePoint.y) / 2);
                mPrePoint = p;
            }
        }
    }
}
