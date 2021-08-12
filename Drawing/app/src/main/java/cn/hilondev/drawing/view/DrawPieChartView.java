package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * <pre>
 *     绘制饼图
 *     @author  : renhailong
 *     @since   : 2021/8/10 14:11
 *     @version : 1.0
 * </pre>
 */
public class DrawPieChartView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mOriginX = 400, mOriginY = 400;


    public DrawPieChartView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
