package cn.hilondev.drawing.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/18 16:02
 *     @version : 1.0
 * </pre>
 */
public class TouchView extends View implements View.OnTouchListener {

    private static final String TAG = "TouchView";
    public TouchView(Context context) {
        this(context,null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent:开始执行" + MotionEvent.actionToString(event.getAction()));
        boolean handle = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent:结束执行" + MotionEvent.actionToString(event.getAction()) + ", 返回值=" + handle);
        return handle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent:开始执行" + MotionEvent.actionToString(event.getAction()));
        boolean handle = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent:结束执行" + MotionEvent.actionToString(event.getAction()) + ", 返回值=" + handle);
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
