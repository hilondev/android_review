package cn.hilondev.drawing.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/18 16:02
 *     @version : 1.0
 * </pre>
 */
public class TouchView2 extends View implements View.OnTouchListener {

    private static final String TAG = "TouchView2";

    public TouchView2(Context context) {
        this(context, null);
    }

    public TouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        Log.d(TAG,"构造");
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(VISIBLE);
            }
        },1000);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG,"onFinishInflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"onAttachedToWindow");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG,"onSizeChanged");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow");
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.d(TAG,"onVisibilityChanged");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent:开始执行" + MotionEvent.actionToString(event.getAction()));
       /* if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 不让父控件拦截事件
            getParent().requestDisallowInterceptTouchEvent(true);
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            getParent().requestDisallowInterceptTouchEvent(false);
        }*/
        boolean handle = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent:结束执行"
                + MotionEvent.actionToString(event.getAction())
                + ", 返回值="
                + handle);
        return handle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent:开始执行" + MotionEvent.actionToString(event.getAction()));
        boolean handle = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent:结束执行"
                + MotionEvent.actionToString(event.getAction())
                + ", 返回值="
                + handle);
        return handle;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "OnTouchListener#onTouch:开始执行" + MotionEvent.actionToString(event.getAction()));
        boolean handle = false;
        Log.d(TAG, "OnTouchListener#onTouch:结束执行"
                + MotionEvent.actionToString(event.getAction())
                + ", 返回值="
                + handle);
        return handle;
    }
}
