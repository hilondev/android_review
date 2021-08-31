package cn.hilondev.drawing.doodle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 14:25
 *     @version : 1.0
 * </pre>
 */
public class DoodleView extends View {

    private static final int DEFAULT_WIDTH_IN_DP = 100;
    private static final int DEFAULT_HEIGHT_IN_DP = 100;

    /**
     * 视图默认宽高
     */
    private int mDefaultWidth, mDefaultHeight;

    public DoodleView(Context context) {
        this(context, null);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mDefaultWidth = dp2px(DEFAULT_WIDTH_IN_DP);
        mDefaultHeight = dp2px(DEFAULT_HEIGHT_IN_DP);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mDefaultHeight);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    /**
     * dp转px
     *
     * @param dpValue dp值.
     * @return dpValue 对应的 px
     */
    public int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
