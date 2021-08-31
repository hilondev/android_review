package cn.hilondev.drawing.doodle.core;

import android.graphics.Bitmap;
import java.io.File;
import java.io.InputStream;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 15:15
 *     @version : 1.0
 * </pre>
 */
public interface IDoodle {

    /**
     * 设置底图
     *
     * @param bitmap
     */
    void setBaseMap(Bitmap bitmap);

    /**
     * 设置底图
     *
     * @param file
     */
    void setBaseMap(File file);

    /**
     * 设置底图
     *
     * @param is
     */
    void setBaseMap(InputStream is);

    /**
     * 获取底图bitmap对象
     *
     * @return
     */
    Bitmap getBaseMap();

    /**
     * 获取涂鸦带底图的bitmap对象
     *
     * @return
     */
    Bitmap getDoodleMap();


}
