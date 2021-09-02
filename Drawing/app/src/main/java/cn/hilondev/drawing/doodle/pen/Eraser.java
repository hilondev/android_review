package cn.hilondev.drawing.doodle.pen;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import androidx.annotation.NonNull;
import cn.hilondev.drawing.doodle.core.IPen;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 18:06
 *     @version : 1.0
 * </pre>
 */
public class Eraser implements IPen {

    public Eraser() {

    }

    @Override
    public void config(@NonNull Paint paint) {
        paint.reset();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.STROKE);
        // 橡皮擦画笔宽度
        paint.setStrokeWidth(50);
    }
}
