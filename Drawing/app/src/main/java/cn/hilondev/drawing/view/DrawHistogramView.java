package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     绘制直方图
 *     @author  : renhailong
 *     @since   : 2021/8/10 14:11
 *     @version : 1.0
 * </pre>
 */
public class DrawHistogramView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Item> mItemList = new ArrayList<>();
    // 所有矩形的path
    private Path mPath = new Path();
    // 原点 x
    private int mOriginX = 100;
    // 原点 y
    private int mOriginY = 600;

    public DrawHistogramView(Context context) {
        super(context);
    }

    /**
     * 直方图每一项的实体
     */
    private static class Item {
        private RectF rectF = new RectF();
        private String text;

        Item(int left, int top, int right, int bottom, String text) {
            rectF.left = left;
            rectF.top = top;
            rectF.right = right;
            rectF.bottom = bottom;
            this.text = text;
        }

        public RectF getRectF() {
            return rectF;
        }

        public float getTextStartX() {
            return rectF.centerX();
        }

        public String getText() {
            return text;
        }
    }

    {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(20);

        mPaintRect.setColor(Color.GREEN);
        mPaintRect.setStyle(Paint.Style.FILL);

        mItemList.add(new Item(150, 580, 250, mOriginY, "S"));
        mItemList.add(new Item(300, 550, 400, mOriginY, "M"));
        mItemList.add(new Item(450, 380, 550, mOriginY, "L"));
        mItemList.add(new Item(600, 180, 700, mOriginY, "XL"));
        mItemList.add(new Item(750, 280, 850, mOriginY, "XXL"));

        for (Item item : mItemList) {
            mPath.addRect(item.getRectF(), Path.Direction.CW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 背景色
        canvas.drawColor(Color.DKGRAY);

        // y轴
        canvas.drawLine(mOriginX, mOriginY, 100, 100, mPaint);
        // x轴
        canvas.drawLine(mOriginX, mOriginY, 1000, 600, mPaint);

        // 绘制矩形
        canvas.drawPath(mPath, mPaintRect);

        // 绘制文本
        for (Item item : mItemList) {
            canvas.drawText(item.getText(), item.getTextStartX(), mOriginY + 50, mPaint);
        }
    }
}
