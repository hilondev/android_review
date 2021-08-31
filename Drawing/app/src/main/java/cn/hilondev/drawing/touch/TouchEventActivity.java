package cn.hilondev.drawing.touch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.hilondev.drawing.R;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/18 15:57
 *     @version : 1.0
 * </pre>
 */
public class TouchEventActivity extends AppCompatActivity {
    private static final String TAG = "TouchEventActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "============================");
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
        Log.d(TAG, "onTouchEvent:结束执行"
                + MotionEvent.actionToString(event.getAction())
                + ", 返回值="
                + handle);
        return handle;
    }
}
