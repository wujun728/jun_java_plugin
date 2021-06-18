package com.ibeetl.admin.core.util;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述: 获取注解的值
 *
 * @author Wujun
 */
public class AnnotationUtil {

    private AnnotationUtil() {
    }

    public static AnnotationUtil getInstance() {
        return AnnotationUtilHolder.instance;
    }

    /**
     * 获取一个类注解的名称和值
     *
     * @param annotationClasss   注解定义类
     * @param useAnnotationClass 使用注解的类
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    public List<Map<String, Object>> getAnnotations(Class annotationClasss, Class useAnnotationClass) {
        List<Map<String, Object>> annotationMapList = new ArrayList<>();

        Field[] fields = useAnnotationClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClasss)) {
                Annotation p = field.getAnnotation(annotationClasss);
                Map map = AnnotationUtils.getAnnotationAttributes(p);
                map.put("fieldName", field.getName());
                annotationMapList.add(map);
            }
        }

        return annotationMapList;
    }

    private static class AnnotationUtilHolder {
        private static AnnotationUtil instance = new AnnotationUtil();

        private AnnotationUtilHolder() {
        }
    }
}
