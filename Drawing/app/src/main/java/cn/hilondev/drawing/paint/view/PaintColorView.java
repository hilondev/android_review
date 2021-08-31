package cn.hilondev.drawing.paint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/13 10:57
 *     @version : 1.0
 * </pre>
 */
public class PaintColorView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Shader mLinearGradient = new LinearGradient(400, 350, 400, 450, Color.GREEN, Color.YELLOW,
            Shader.TileMode.CLAMP);
    private Shader mRadialGradient = new RadialGradient(300, 600, 100, Color.RED, Color.BLUE,
            Shader.TileMode.CLAMP);
    private Shader mSweepGradient = new SweepGradient(500, 600, Color.BLUE, Color.GREEN);

    public PaintColorView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(50);

        mPaint.setColor(Color.parseColor("#009688"));
        canvas.drawRect(30, 30, 200, 180, mPaint);

        mPaint.setColor(Color.parseColor("#FF9800"));
        canvas.drawLine(300, 30, 450, 180, mPaint);

        mPaint.setColor(Color.parseColor("#E91E63"));
        canvas.drawText("Android", 500, 130, mPaint);

        mPaint.setShader(mLinearGradient);

        canvas.drawText("Hello", 30, 400, mPaint);

        canvas.drawCircle(400, 400, 100, mPaint);

        canvas.drawText("World", 630, 400, mPaint);


        mPaint.setShader(mRadialGradient);
        canvas.drawCircle(300, 600, 100, mPaint);

        mPaint.setShader(mSweepGradient);
        canvas.drawCircle(500, 600, 100, mPaint);
    }
}
