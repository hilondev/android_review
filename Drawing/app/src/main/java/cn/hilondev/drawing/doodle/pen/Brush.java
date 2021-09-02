package cn.hilondev.drawing.doodle.pen;

import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import cn.hilondev.drawing.doodle.core.IPen;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 17:57
 *     @version : 1.0
 * </pre>
 */
public class Brush implements IPen {

    private int color = Color.BLACK;
    private float size = 1f;

    public Brush(int color, float size) {
        this.color = color;
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void config(@NonNull Paint paint) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(size);
        paint.setColor(color);
    }
}
