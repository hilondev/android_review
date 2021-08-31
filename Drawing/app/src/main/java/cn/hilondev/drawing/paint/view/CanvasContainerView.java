package cn.hilondev.drawing.paint.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/17 19:33
 *     @version : 1.0
 * </pre>
 */
public class CanvasContainerView extends LinearLayout {
    public CanvasContainerView(Context context) {
        this(context, null);
    }

    public CanvasContainerView(Context context,
                               @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        addViews();
    }

    private void addViews() {
        Button btnRotate = new Button(getContext());
        btnRotate.setText("旋转");
        addView(btnRotate, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Button btnSave = new Button(getContext());
        btnSave.setText("保存");
        addView(btnSave, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        CanvasActionView canvasActionView = new CanvasActionView(getContext());
        addView(canvasActionView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        btnRotate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasActionView.addRotate();
            }
        });

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = canvasActionView.getDrawingBitmap();
                saveBitmapToFile(bitmap);
            }
        });
    }


    private void saveBitmapToFile(Bitmap bitmap){
        if(bitmap == null){
            return;
        }
        File file = new File(getContext().getExternalFilesDir(null),"doodle.jpg");
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
