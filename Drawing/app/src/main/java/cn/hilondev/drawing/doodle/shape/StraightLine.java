package cn.hilondev.drawing.doodle.shape;

import android.graphics.PointF;
import androidx.annotation.NonNull;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 16:53
 *     @version : 1.0
 * </pre>
 */
public class StraightLine extends BaseShape {

    private PointF mDownPoint;

    @Override
    public void down(@NonNull PointF p) {
        super.down(p);
        mPath.moveTo(p.x, p.y);
        mDownPoint = p;
    }

    @Override
    public void move(@NonNull PointF p) {
        super.move(p);
        if (mDownPoint != null) {
            mPath.reset();
            mPath.moveTo(mDownPoint.x, mDownPoint.y);
            mPath.lineTo(p.x, p.y);
        }
    }

    @Override
    public void up(@NonNull PointF p) {
        super.up(p);
        if (mDownPoint != null) {
            mPath.reset();
            mPath.moveTo(mDownPoint.x, mDownPoint.y);
            mPath.lineTo(p.x, p.y);
        }
    }
}
