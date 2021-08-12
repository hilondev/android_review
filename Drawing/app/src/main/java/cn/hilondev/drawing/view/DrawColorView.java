package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/9 12:59
 *     @version : 1.0
 * </pre>
 */
public class DrawColorView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DrawColorView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#88880000"));
    }
}
