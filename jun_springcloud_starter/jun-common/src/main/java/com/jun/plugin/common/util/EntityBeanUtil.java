package com.jun.plugin.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

//import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * 实体对象操作工具
 *
 * @date 2018/10/15
 */
public class EntityBeanUtil {

    /** 复制实体对象保留的默认字段 */
    private static String[] defaultFields = new String[]{
            "createDate",
            "updateDate",
            "createBy",
            "updateBy",
            "status"
    };

    /**
     * 获取实体对象ID字段值
     *
     * @param entity 实体对象
     * @return [0]为ID字段名，[1]为ID字段值
     */
//    public static Object[] getId(Object entity) {
//        Field[] fields = entity.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            Id id = field.getAnnotation(Id.class);
//            if (id != null) {
//                try {
//                    field.setAccessible(true);
//                    return new Object[]{field.getName(), field.get(entity)};
//                } catch (IllegalAccessException e) {
//                    throw new FatalBeanException(
//                            "获取" + entity.getClass().getName() + "实体对象主键出错！", e);
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 根据字段名获取实体对象值
     *
     * @param entity    实体对象
     * @param fieldName 字段名
     * @return Object对象
     */
    public static Object getField(Object entity, String fieldName) throws InvocationTargetException, IllegalAccessException {
        PropertyDescriptor beanObjectPd = BeanUtils.getPropertyDescriptor(entity.getClass(), fieldName);
        if (beanObjectPd != null) {
            Method readMethod = beanObjectPd.getReadMethod();
            if (readMethod != null) {
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                return readMethod.invoke(entity);
            }
        }
        return null;
    }

    /**
     * 根据字段名获取实体对象值
     *
     * @param entity    实体对象
     * @param fieldName 字段名
     * @param value     字段值
     */
    public static void setField(Object entity, String fieldName, Object value) throws InvocationTargetException, IllegalAccessException {
        PropertyDescriptor beanObjectPd = BeanUtils.getPropertyDescriptor(entity.getClass(), fieldName);
        if (beanObjectPd != null) {
            Method writeMethod = beanObjectPd.getWriteMethod();
            if (writeMethod != null) {
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                writeMethod.invoke(entity, value);
            }
        }
    }

    /**
     * 复制实体对象指定字段的数据，复制默认字段数据
     * {用于保留部分原始数据}
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        EntityBeanUtil.copyProperties(source, target, null, defaultFields);
    }

    /**
     * 复制实体对象指定字段的数据，复制自定义的字段数据
     * {用于保留部分原始数据}
     *
     * @param source 源对象
     * @param target 目标对象
     * @param fields 需要保留的自定义字段
     */
    public static void copyProperties(Object source, Object target, String... fields) throws BeansException {
        // 合并两个数组
        String[] jointRetainProperties = new String[defaultFields.length + fields.length];
        System.arraycopy(defaultFields, 0, jointRetainProperties, 0, defaultFields.length);
        System.arraycopy(fields, 0, jointRetainProperties, defaultFields.length, fields.length);
        EntityBeanUtil.copyProperties(source, target, null, jointRetainProperties);
    }

    /**
     * 复制实体对象指定字段的数据
     * {用于保留部分原始数据}
     * {代码基于BeanUtils.copyProperties(...)}
     *
     * @param source   源对象
     * @param target   目标对象
     * @param editable 将字段设置限制为的类(或接口)
     * @param fields   需要保留的自定义字段
     */
    private static void copyProperties(Object source, Object target, @Nullable Class<?> editable,
                                       @Nullable String... fields) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (fields != null ? Arrays.asList(fields) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 复制实体对象数据，忽略默认字段数据
     * {用于忽略部分原始数据，功能与copyProperties相反}
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnores(Object source, Object target) {
        BeanUtils.copyProperties(source, target, defaultFields);
    }

    /**
     * 复制实体对象数据，忽略默认字段数据
     * {用于忽略部分原始数据，功能与copyProperties相反}
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 需要忽略的自定义字段
     */
    public static void copyPropertiesIgnores(Object source, Object target, String... ignoreProperties) {
        // 合并两个数组
        String[] jointIgnoreProperties = new String[defaultFields.length + ignoreProperties.length];
        System.arraycopy(defaultFields, 0, jointIgnoreProperties, 0, defaultFields.length);
        System.arraycopy(ignoreProperties, 0, jointIgnoreProperties, defaultFields.length, ignoreProperties.length);
        BeanUtils.copyProperties(source, target, jointIgnoreProperties);
    }

    /**
     * 克隆一个新的bean对象
     *
     * @param object 源对象
     */
    public static Object cloneBean(Object object) {
        return cloneBean(object, (String[]) null);
    }

    /**
     * 克隆一个新的bean对象，忽略部分字段
     *
     * @param object           源对象
     * @param ignoreProperties 需要忽略的字段
     */
    public static Object cloneBean(Object object, String... ignoreProperties) {
        Object cloneObject = null;
        if (object != null) {
            try {
                cloneObject = object.getClass().newInstance();
                BeanUtils.copyProperties(object, cloneObject, ignoreProperties);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return cloneObject;
    }
}
