package cn.hilondev.drawing.paint;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import cn.hilondev.drawing.R;

/**
 * <pre>
 *     PagerAdapter
 *     @author  : renhailong
 *     @since   : 2021/8/9 16:22
 *     @version : 1.0
 * </pre>
 */
public class PagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {
            R.string.paint_color,
            R.string.paint_bitmap_shader,
            R.string.canvas_action,

    };
    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerFragment.newInstance(TAB_TITLES[position]);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}