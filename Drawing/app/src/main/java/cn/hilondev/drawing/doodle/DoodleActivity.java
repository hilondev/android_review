package cn.hilondev.drawing.doodle;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cn.hilondev.drawing.R;
import cn.hilondev.drawing.doodle.shape.CurvedLine;
import cn.hilondev.drawing.doodle.shape.StraightLine;
import java.io.File;

/**
 * <pre>
 *     MainActivity
 *     @author  : renhailong
 *     @since   : 2021/8/10 11:21
 *     @version : 1.0
 * </pre>
 */
public class DoodleActivity extends AppCompatActivity {

    private DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);
        doodleView = findViewById(R.id.doodleView);

        findViewById(R.id.btn_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setPenColor(Color.BLACK);
            }
        });

        findViewById(R.id.btn_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setPenColor(Color.RED);
            }
        });

        findViewById(R.id.btn_straight_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setEraserMode(false);
                doodleView.setShape(StraightLine.class);
            }
        });
        findViewById(R.id.btn_curved_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setEraserMode(false);
                doodleView.setShape(CurvedLine.class);
            }
        });

        findViewById(R.id.btn_thick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setPenSize(10);
            }
        });

        findViewById(R.id.btn_thin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setPenSize(2);
            }
        });
        findViewById(R.id.btn_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.addRotate();
            }
        });

        findViewById(R.id.btn_set_base_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setBaseMap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.hencoder));
            }
        });

        findViewById(R.id.btn_eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setEraserMode(true);
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean saveSuccess = doodleView.saveResultDoodleToFile(
                        new File(getExternalFilesDir(null), "result.jpg"));
                if (saveSuccess) {
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = doodleView.undo();
                if (success) {
                    Toast.makeText(getApplicationContext(), "撤销成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "当前没有可撤销的笔画", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        findViewById(R.id.btn_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = doodleView.redo();
                if (success) {
                    Toast.makeText(getApplicationContext(), "恢复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "当前没有可恢复的笔画", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}