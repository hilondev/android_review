package cn.hilondev.drawing.paint;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import cn.hilondev.drawing.R;
import com.google.android.material.tabs.TabLayout;

/**
 * <pre>
 *     MainActivity
 *     @author  : renhailong
 *     @since   : 2021/8/10 11:21
 *     @version : 1.0
 * </pre>
 */
public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        PagerAdapter pagerAdapter =
                new PagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}