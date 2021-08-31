package cn.hilondev.drawing.doodle.core;

import java.util.List;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 16:01
 *     @version : 1.0
 * </pre>
 */
public interface IDoodleItem {

    /**
     * 设置缩放系数
     *
     * @param scale 缩放系数
     * @param px 缩放中心点x坐标
     * @param py 缩放中心点y坐标
     */
    void setScale(float scale, float px, float py);

    /**
     * 获取缩放系数
     *
     * @return
     */
    float getScale();

    /**
     * 增加旋转角度，以90度为单位
     */
    void addRotate();

    /**
     * 获取旋转角度
     *
     * @return
     */
    int getRotateDegree();

    /**
     * 恢复到不旋转状态
     */
    void resetRotate();

    /**
     * 将当前item对象添加到列表
     *
     * @param list 列表
     */
    void addToList(List<IDoodleItem> list);

    /**
     * 从列表中删除当前item对象
     *
     * @param list 列表
     */
    void removeFromList(List<IDoodleItem> list);
}
