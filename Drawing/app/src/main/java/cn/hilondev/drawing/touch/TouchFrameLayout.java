package cn.hilondev.drawing.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/18 15:59
 *     @version : 1.0
 * </pre>
 */
public class TouchFrameLayout extends FrameLayout implements View.OnTouchListener {

    private static final String TAG = "TouchFrameLayout";

    public TouchFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public TouchFrameLayout(@NonNull Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent:开始执行" + MotionEvent.actionToString(ev.getAction()));
        boolean intercept = true;
        // DOWN 事件不能拦截，否则子view收不到事件
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            intercept = true;
        }
        Log.d(TAG, "onInterceptTouchEvent:结束执行"
                + MotionEvent.actionToString(ev.getAction())
                + ", 拦截="
                + intercept);
        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent:开始执行" + MotionEvent.actionToString(ev.getAction()));
        boolean handle = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent:结束执行"
                + MotionEvent.actionToString(ev.getAction())
                + ", 返回值="
                + handle);
        return handle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent:开始执行" + MotionEvent.actionToString(event.getAction()));
        boolean handle = super.onTouchEvent(event);
        handle = true;
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
