package cn.hilondev.drawing.doodle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/9/1 12:14
 *     @version : 1.0
 * </pre>
 */
public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    public static boolean saveBitmapToFile(Bitmap bitmap, File file) {
        if (bitmap == null || file == null) {
            return false;
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
        return false;
    }

    public static Bitmap getResizedBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        return getResizedBitmap(bitmap, reqWidth, reqHeight);
    }

    public static Bitmap getResizedBitmapFromResource(Resources resources, int resId, int reqWidth,
                                                      int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId, options);
        return getResizedBitmap(bitmap, reqWidth, reqHeight);
    }

    public static Bitmap getResizedBitmapFromInputStream(InputStream inputStream, int reqWidth,
                                                         int reqHeight) {
        byte[] byteArray;
        try {
            byteArray = readStream(inputStream);
        } catch (IOException e) {
            byteArray = new byte[1024];
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        return getResizedBitmap(bitmap, reqWidth, reqHeight);
    }

    public static Bitmap getResizedBitmap(Bitmap sourceBitmap, int reqWidth, int reqHeight) {
        int sourceBitmapWidth = sourceBitmap.getWidth();
        int sourceBitmapHeight = sourceBitmap.getHeight();
        // 此处非常关键，如果不将源bitmap的mDensity设置为0，会导致生成的新的bitmap再次被自动缩放。
        sourceBitmap.setDensity(0);
        float scaleWidth = reqWidth / (float) sourceBitmapWidth;
        float scaleHeight = reqHeight / (float) sourceBitmapHeight;
        float scale = Math.min(scaleWidth, scaleHeight);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap resizedBitmap =
                Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmapWidth, sourceBitmapHeight,
                        matrix, false);
        sourceBitmap.recycle();
        return resizedBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
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

    private static byte[] readStream(InputStream is) throws IOException {
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
}
