package cn.hilondev.drawing.basic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.hilondev.drawing.R;
import cn.hilondev.drawing.basic.view.DrawArcView;
import cn.hilondev.drawing.basic.view.DrawBitmapView;
import cn.hilondev.drawing.basic.view.DrawCircleView;
import cn.hilondev.drawing.basic.view.DrawColorView;
import cn.hilondev.drawing.basic.view.DrawHistogramView;
import cn.hilondev.drawing.basic.view.DrawLineView;
import cn.hilondev.drawing.basic.view.DrawOvalView;
import cn.hilondev.drawing.basic.view.DrawPathView;
import cn.hilondev.drawing.basic.view.DrawPieChartView;
import cn.hilondev.drawing.basic.view.DrawPointView;
import cn.hilondev.drawing.basic.view.DrawRectView;
import cn.hilondev.drawing.basic.view.DrawRoundRectView;

/**
 * <pre>
 *     自定义绘制容器fragment
 *     @author  : renhailong
 *     @since   : 2021/8/9 16:22
 *     @version : 1.0
 * </pre>
 */
public class PagerFragment extends Fragment {

    private static final String ARG_TITLE = "section_number";

    public static PagerFragment newInstance(int title) {
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout container = view.findViewById(R.id.container);
        int title = getArguments().getInt(ARG_TITLE, R.string.draw_circle);
        if (title == R.string.draw_color) {
            container.addView(new DrawColorView(getContext()));
        } else if (title == R.string.draw_circle) {
            container.addView(new DrawCircleView(getContext()));
        } else if (title == R.string.draw_rect) {
            container.addView(new DrawRectView(getContext()));
        } else if (title == R.string.draw_point) {
            container.addView(new DrawPointView(getContext()));
        } else if (title == R.string.draw_oval) {
            container.addView(new DrawOvalView(getContext()));
        } else if (title == R.string.draw_line) {
            container.addView(new DrawLineView(getContext()));
        } else if (title == R.string.draw_round_rect) {
            container.addView(new DrawRoundRectView(getContext()));
        } else if (title == R.string.draw_arc) {
            container.addView(new DrawArcView(getContext()));
        } else if (title == R.string.draw_path) {
            container.addView(new DrawPathView(getContext()));
        } else if (title == R.string.draw_bitmap) {
            container.addView(new DrawBitmapView(getContext()));
        } else if (title == R.string.draw_histogram) {
            container.addView(new DrawHistogramView(getContext()));
        } else if (title == R.string.draw_pie_chart) {
            container.addView(new DrawPieChartView(getContext()));
        }
    }
}