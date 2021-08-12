package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/9 12:59
 *     @version : 1.0
 * </pre>
 */
public class DrawArcView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRectF = new RectF();

    public DrawArcView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 设置椭圆的矩形边界
        mRectF.left = 200;
        mRectF.top = 100;
        mRectF.right = 800;
        mRectF.bottom = 500;
        // 颜色
        mPaint.setColor(Color.BLACK);
        // 画线模式
        mPaint.setStyle(Paint.Style.STROKE);
        // 画矩形
        canvas.drawRect(mRectF, mPaint);
        // 填充模式
        mPaint.setStyle(Paint.Style.FILL);
        // 绘制扇形
        canvas.drawArc(mRectF, -110, 100, true, mPaint);
        // 绘制弧形
        canvas.drawArc(mRectF, 20, 140, false, mPaint);
        // 画线模式
        mPaint.setStyle(Paint.Style.STROKE);
        // 绘制不封口的弧形
        canvas.drawArc(mRectF, 180, 60, false, mPaint);
    }
}
