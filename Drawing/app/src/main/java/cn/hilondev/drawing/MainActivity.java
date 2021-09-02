package cn.hilondev.drawing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import cn.hilondev.drawing.basic.BasicDrawingActivity;
import cn.hilondev.drawing.doodle.DoodleActivity;
import cn.hilondev.drawing.paint.PaintActivity;
import cn.hilondev.drawing.touch.TouchEventActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicDrawingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.paint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaintActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.touch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TouchEventActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.doodle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoodleActivity.class);
                startActivity(intent);
            }
        });
    }
}