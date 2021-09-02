package cn.hilondev.drawing.doodle.core;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.InputStream;
import java.util.List;

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
    void setBaseMap(@NonNull Bitmap bitmap);

    /**
     * 设置底图
     *
     * @param file
     */
    void setBaseMap(@NonNull File file);

    /**
     * 设置底图
     *
     * @param is
     */
    void setBaseMap(@NonNull InputStream is);

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
    Bitmap getResultDoodleMap();

    /**
     * 设置画笔笔画宽度
     *
     * @param penSize
     */
    void setPenSize(float penSize);

    /**
     * 设置画笔笔画颜色
     *
     * @param color
     */
    void setPenColor(int color);

    /**
     * 设置绘制形状
     *
     * @param shapeType
     */
    void setShape(Class<? extends IShape> shapeType);

    /**
     * 获取涂鸦笔画列表
     *
     * @return
     */
    List<IDoodleItem> getDoodleItems();

    /**
     * 获取待恢复的涂鸦笔画列表
     *
     * @return
     */
    List<IDoodleItem> getRedoDoodleItems();

    /**
     * 获取涂鸦笔画数量
     *
     * @return
     */
    int getDoodleItemCount();

    /**
     * 获取待恢复的涂鸦笔画数量
     *
     * @return
     */
    int getRedoDoodleItemCount();

    /**
     * 撤销一笔
     * @return
     */
    boolean undo();

    /**
     * 恢复一笔
     * @return
     */
    boolean redo();

    /**
     * 增加旋转角度（以90度为单位）
     */
    void addRotate();

    /**
     * 获取旋转角度
     *
     * @return
     */
    int getRotateDegree();

    /**
     * 将涂鸦结果保存到文件
     *
     * @param file
     * @return
     */
    boolean saveResultDoodleToFile(File file);
}
