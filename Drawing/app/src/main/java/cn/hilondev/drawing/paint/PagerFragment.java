package cn.hilondev.drawing.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.hilondev.drawing.R;
import cn.hilondev.drawing.paint.view.CanvasActionView;
import cn.hilondev.drawing.paint.view.CanvasContainerView;
import cn.hilondev.drawing.paint.view.PaintBitmapShaderView;
import cn.hilondev.drawing.paint.view.PaintColorView;

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
        int title = getArguments().getInt(ARG_TITLE, R.string.paint_color);
        if (title == R.string.paint_color) {
            container.addView(new PaintColorView(getContext()));
        } else if (title == R.string.paint_bitmap_shader) {
            container.addView(new PaintBitmapShaderView(getContext()));
        }else if (title == R.string.canvas_action) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(new CanvasContainerView(getContext()), layoutParams);
        }
    }
}