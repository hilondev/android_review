package cn.hilondev.drawing.doodle;

import android.graphics.PointF;
import android.graphics.RectF;
import cn.hilondev.drawing.doodle.core.IPen;
import cn.hilondev.drawing.doodle.core.IShape;
import cn.hilondev.drawing.doodle.pen.Eraser;
import cn.hilondev.drawing.doodle.shape.CurvedLine;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/9/1 8:10
 *     @version : 1.0
 * </pre>
 */
public class DoodleEraserItem extends DoodleItem {

    public DoodleEraserItem(int rotateDegree, float scale, RectF clipRect,
                            PointF centerPoint) {
        super(rotateDegree, scale, clipRect, centerPoint);
        super.setShape(new CurvedLine());
        super.setPen(new Eraser());
    }

    @Override
    public void setShape(IShape shape) {

    }

    @Override
    public void setPen(IPen pen) {

    }
}
