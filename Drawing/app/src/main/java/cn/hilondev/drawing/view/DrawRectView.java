package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/9 12:59
 *     @version : 1.0
 * </pre>
 */
public class DrawRectView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mRect = new Rect();

    public DrawRectView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 300, 300, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mRect.left = 400;
        mRect.top = 100;
        mRect.right = 900;
        mRect.bottom = 400;
        canvas.drawRect(mRect, mPaint);
    }
}
