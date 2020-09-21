package cn.jiangzeyin.database.util;

import cn.jiangzeyin.database.base.ReadBase;
import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.config.SystemColumn;
import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.util.Assert;
import cn.jiangzeyin.util.KeyMap;
import cn.jiangzeyin.util.ref.ReflectUtil;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 *
 * @author jiangzeyin
 */
public class Util {


    /**
     * 将list map 转javabean
     *
     * @param reBase base
     * @param list   list
     * @return 结果
     * @throws Exception 异常
     * @author jiangzeyin
     */
    public static <T> List<T> convertList(ReadBase<T> reBase, List<Map<String, Object>> list) throws Exception {
        Assert.notNull(list, "list map");
        Assert.notNull(reBase, "reBase");
        List<T> list_r = new ArrayList<T>();
        for (Map<String, Object> t : list) {
            list_r.add(convertMap(reBase, t, null));
        }
        return list_r;
    }

    /**
     * @param read     对象
     * @param map      map
     * @param refClass 类
     * @param <T>      参数
     * @return 实体
     * @throws Exception 异常
     */
    private static <T> T convertMap(ReadBase<T> read, Map<String, Object> map, Class<?> refClass) throws Exception {
        if (refClass == null)
            refClass = read.getTclass();
        T obj = (T) refClass.newInstance();// 创建 JavaBean 对象
        if (obj == null)
            return null;
        KeyMap<String, Object> keyMap = new KeyMap<>(map);
        HashMap<String, Class<?>> refMap = read.getRefMap();
        HashMap<String, String> refWhere = read.getRefWhere();
        List<String> remove = read.getRemove();
        // 给 JavaBean 对象的属性赋值
        List<Method> methods = ReflectUtil.getAllSetMethods(obj.getClass());// .getDeclaredMethods();
        DataSource dataSource = DatabaseContextHolder.getReadDataSource(read.getTag());
        for (Method method : methods) {
            String name = method.getName();
            if (!name.startsWith("set"))
                continue;
            name = name.substring(3).toLowerCase();
            // 移除字段比较
            if (remove != null && remove.contains(name))
                continue;
            if (SystemColumn.isReadRemove(name))
                continue;
            Object value = keyMap.get(name);
            if (value == null) {
                continue;
            }
            // 判断外键
            if (refMap != null && refMap.containsKey(name)) {
                String where = refWhere == null ? null : refWhere.get(name);
                Class refMapClass = refMap.get(name);
                String sql = SqlUtil.getRefSql(refMapClass, read.getRefKey(), where);
                SystemDbLog.getInstance().info(sql);
                List<Object> parameters = new ArrayList<>();
                parameters.add(value);
                List<Map<String, Object>> refList = JdbcUtils.executeQuery(dataSource, sql, parameters);
                if (refList != null && refList.size() > 0) {
                    Map<String, Object> refMap_data = refList.get(0);
                    Object refValue = convertMap(read, refMap_data, refMapClass);
                    //System.out.println(refValue);
                    try {
                        method.invoke(obj, refValue);
                        //ReflectUtil.setFieldValue(obj, name, refValue);
                    } catch (IllegalArgumentException e) {
                        SystemDbLog.getInstance().error(String.format(obj.getClass() + " map转实体%s字段错误：%s -> %s", name, value.getClass(), value), e);
                    }
                }
                continue;
            }
            // 正常的字段
            try {
                method.invoke(obj, value);
            } catch (IllegalArgumentException ie) {
                // 判断关系没有配置
                Class<?>[] classes = method.getParameterTypes();
                Class pClass = classes[0];
                if (pClass == String.class) {
                    method.invoke(obj, String.valueOf(value.toString()));
                } else if (pClass == Integer.class || pClass == int.class) {
                    method.invoke(obj, (Integer) value);
                } else {
                    SystemDbLog.getInstance().error(String.format(obj.getClass() + " map转实体%s字段类型错误：%s -> %s", name, value.getClass(), value), ie);
                }
            } catch (Exception e) {
                SystemDbLog.getInstance().error(String.format(obj.getClass() + " map转实体%s字段错误：%s -> %s", name, value.getClass(), value), e);
            }
        }
        return obj;
    }
}
