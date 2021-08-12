package cn.hilondev.drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import cn.hilondev.drawing.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/9 12:59
 *     @version : 1.0
 * </pre>
 */
public class DrawBitmapView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;

    public DrawBitmapView(Context context) {
        super(context);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            mBitmap = getResizedBitmap(getMeasuredWidth(), getMeasuredHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap decodeResourceBitmap(int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.android, options);

        Log.d("Henry", "压缩前：options.outWidth=" + options.outWidth);
        Log.d("Henry", "压缩前：options.outHeight=" + options.outHeight);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 并不是向下取2的幂
        options.inSampleSize = 5;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // 从resource里加载，不进行缩放
        options.inScaled = false;

        //options.inDensity = 0;
        //options.inTargetDensity = 0;

        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android, options);

        Log.d("Henry", "压缩后：options.outWidth=" + options.outWidth);
        Log.d("Henry", "压缩后：options.outHeight=" + options.outHeight);
        Log.d("Henry", "压缩后：Bitmap占用内存=" + bitmap.getByteCount());

        return bitmap;
    }

    private Bitmap decodeFileBitmap(int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File imageFile = new File(
                getContext().getApplicationContext().getExternalFilesDir(null) + "/android.png");
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        // 压缩前：options.outWidth=4000
        // 压缩前：options.outHeight=2670
        Log.d("Henry", "压缩前：options.outWidth=" + options.outWidth);
        Log.d("Henry", "压缩前：options.outHeight=" + options.outHeight);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 并不是向下取2的幂
        options.inSampleSize = 5;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // 从resource里加载，不进行缩放
        //options.inScaled = false;

        //options.inDensity = 0;
        //options.inTargetDensity = 0;

        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        // 压缩后：options.outWidth=800
        // 压缩后：options.outHeight=534
        // 压缩后：Bitmap占用内存=1708800

        Log.d("Henry", "压缩后：options.outWidth=" + options.outWidth);
        Log.d("Henry", "压缩后：options.outHeight=" + options.outHeight);
        Log.d("Henry", "压缩后：Bitmap占用内存=" + bitmap.getByteCount());

        return bitmap;
    }

    private Bitmap decodeFileStreamBitmap(int reqWidth, int reqHeight) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File imageFile = new File(
                getContext().getApplicationContext().getExternalFilesDir(null) + "/android.png");
        Log.d("Henry", "imageFile.length(): " + imageFile.length());

        // 将图片文件读入内存，解析成字节数组
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] byteArray = readStream(fis);
        Log.d("Henry", "byteArray.length: " + byteArray.length);

        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

        Log.d("Henry", "压缩前：options.outWidth=" + options.outWidth);
        Log.d("Henry", "压缩前：options.outHeight=" + options.outHeight);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 并不是向下取2的幂
        options.inSampleSize = 5;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // 从resource里加载，不进行缩放
        //options.inScaled = false;

        //options.inDensity = 0;
        //options.inTargetDensity = 0;

        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

        Log.d("Henry", "压缩后：options.outWidth=" + options.outWidth);
        Log.d("Henry", "压缩后：options.outHeight=" + options.outHeight);
        Log.d("Henry", "压缩后：Bitmap占用内存=" + bitmap.getByteCount());

        return bitmap;
    }

    private byte[] readStream(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] data = baos.toByteArray();
        is.close();
        baos.close();
        return data;
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap getResizedBitmap(int reqWidth, int reqHeight) throws IOException {
        //Bitmap sourceBitmap = decodeResourceBitmap(reqWidth, reqHeight);
        //Bitmap sourceBitmap = decodeFileBitmap(reqWidth, reqHeight);
        Bitmap sourceBitmap = decodeFileStreamBitmap(reqWidth, reqHeight);
        Log.d("Henry", "reqWidth=" + reqWidth);
        Log.d("Henry", "reqHeight=" + reqHeight);

        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        // 此处非常关键，如果不将源bitmap的mDensity设置为0，会导致生成的新的bitmap再次被自动缩放。
        sourceBitmap.setDensity(0);
        Log.d("Henry", "width=" + width);
        Log.d("Henry", "height=" + height);

        float scaleWidth = reqWidth / (float) width;
        float scaleHeight = reqHeight / (float) height;

        Log.d("Henry", "scaleWidth=" + scaleWidth);
        Log.d("Henry", "scaleHeight=" + scaleHeight);

        float scale = Math.min(scaleWidth, scaleHeight);

        Log.d("Henry", "scale=" + scale);

        if (scaleHeight > scale) {
            reqHeight = (int) (height * scale);
            Log.d("Henry", "scaleHeight > scale");
        } else if (scaleWidth > scale) {
            reqWidth = (int) (width * scale);
            Log.d("Henry", "scaleWidth > scale");
        }
        Log.d("Henry", "1reqWidth=" + reqWidth);
        Log.d("Henry", "1reqHeight=" + reqHeight);

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);

        Bitmap resizedBitmap =
                Bitmap.createBitmap(sourceBitmap, 0, 0, width, height, matrix, false);
        sourceBitmap.recycle();
        return resizedBitmap;
        //return sourceBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }
}
