package cn.hilondev.drawing.basic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/9 12:59
 *     @version : 1.0
 * </pre>
 */
public class DrawPathView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();

    public DrawPathView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    {
        mPaint.setStyle(Paint.Style.FILL);

        // 画心形
        mPath.addArc(200, 200, 400, 400, -225, 225);
        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
        mPath.lineTo(400, 542);
        mPath.close();

        // 画相交圆形
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPath.addCircle(300, 800, 200, Path.Direction.CW);
        mPath.addCircle(400, 800, 200, Path.Direction.CW);


        // 画三角形
        mPath.moveTo(200, 1200);
        mPath.lineTo(600, 1200);
        mPath.lineTo(400, 1400);
        mPath.close();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
