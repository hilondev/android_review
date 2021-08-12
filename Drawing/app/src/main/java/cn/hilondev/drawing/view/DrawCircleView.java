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
public class DrawCircleView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DrawCircleView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(300, 300, 150, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(700, 300, 150, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(300, 700, 150, mPaint);

        mPaint.setStrokeWidth(50);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(700, 700, 150, mPaint);
    }
}
