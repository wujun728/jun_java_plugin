package com.designpatterns.prototype;

import java.text.MessageFormat;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class ShapeUtil {
    public static void discribe(Shape shape){
        String content = "这个是一个{0}，它的颜色是{1},他真可爱";
        System.out.println(MessageFormat.format(content,shape.type,shape.color));
    }

    public static void saveOriginShape(Shape shape){
        System.out.println("我存进去了！");
    }
}
