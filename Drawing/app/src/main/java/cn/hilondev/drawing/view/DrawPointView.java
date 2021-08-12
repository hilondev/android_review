package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
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
public class DrawPointView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DrawPointView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(100,100, mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(50);
        canvas.drawPoint(200,100, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(300,100, mPaint);
    }
}
