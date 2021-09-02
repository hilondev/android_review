package cn.hilondev.drawing.paint.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/20 8:44
 *     @version : 1.0
 * </pre>
 */
public class ShapeItem {

    private Path path;
    private Paint paint;

    private int rotateDegree = 0;
    private float transX = 0f, transY = 0f;
    private float bornScale = 1f;
    private float scale = 1f;
    private RectF clipRect;
    private PointF centerPoint;

    public ShapeItem(float scale, float transX, float transY,
                     RectF clipRect,
                     PointF centerPoint) {
        this.bornScale = scale;
        this.scale = scale / bornScale;
        this.transX = transX;
        this.transY = transY;
        this.clipRect = clipRect;
        this.centerPoint = centerPoint;
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.YELLOW);
        path = new Path();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getRotateDegree() {
        return rotateDegree;
    }

    public void addRotateDegree() {
        this.rotateDegree = (rotateDegree + 90) % 360;
    }

    public float getTransX() {
        return transX;
    }

    public void setTransX(float transX) {
        this.transX = transX;
    }

    public float getTransY() {
        return transY;
    }

    public void setTransY(float transY) {
        this.transY = transY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale / bornScale;
    }

    public RectF getClipRect() {
        return clipRect;
    }

    public void setClipRect(RectF clipRect) {
        this.clipRect = clipRect;
    }

    public void down(float x, float y) {
        PointF eventPoint = new PointF(x, y);
        PointF canvasPoint = transEventPointToCanvasPoint(eventPoint);
        path.moveTo(canvasPoint.x, canvasPoint.y);
    }

    public void move(float x, float y) {
        PointF eventPoint = new PointF(x, y);
        PointF canvasPoint = transEventPointToCanvasPoint(eventPoint);
        path.lineTo(canvasPoint.x, canvasPoint.y);
    }

    public void up(float x, float y) {

    }

    private PointF transEventPointToCanvasPoint(PointF eventPoint) {
        PointF canvasPoint = new PointF();
        canvasPoint.x = eventPoint.x - centerPoint.x;
        canvasPoint.y = eventPoint.y - centerPoint.y;
        canvasPoint.x = canvasPoint.x / scale;
        canvasPoint.y = canvasPoint.y / scale;
        return canvasPoint;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipRect(clipRect);
        canvas.translate(centerPoint.x, centerPoint.y);
        canvas.rotate(rotateDegree, 0, 0);
        canvas.scale(scale, scale);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    public void addToList(@NonNull List<ShapeItem> list) {
        list.add(this);
    }

    public void removeFromList(@NonNull List<ShapeItem> list) {
        list.remove(this);
    }
}
