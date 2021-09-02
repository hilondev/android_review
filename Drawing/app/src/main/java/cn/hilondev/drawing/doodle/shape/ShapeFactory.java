package cn.hilondev.drawing.doodle.shape;

import cn.hilondev.drawing.doodle.core.IShape;

/**
 * <pre>
 *
 *     @author  : renhailong
 *     @since   : 2021/8/31 16:54
 *     @version : 1.0
 * </pre>
 */
public class ShapeFactory {

    public static IShape create(Class<? extends IShape> shapeClass) {
        try {
            return shapeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(
                    "Unable to instantiate IShape implementation for " + shapeClass, e);
        }
    }
}
