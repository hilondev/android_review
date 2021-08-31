package cn.hilondev.drawing.paint.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.view.View;
import cn.hilondev.drawing.R;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/13 10:57
 *     @version : 1.0
 * </pre>
 */
public class PaintBitmapShaderView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PaintBitmapShaderView(Context context) {
        super(context);
    }

    {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(new ComposeShader(shader1, shader2, PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(200, 200, 200, mPaint);
    }
}
