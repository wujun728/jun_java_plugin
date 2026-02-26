package io.github.wujun728.rest.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
//import org.springframework.util.StopWatch;

import java.util.*;

public class TreeBuildUtil {

    /**
     * 对象List转为Tree树形结构
     *
     * @param entityList       传进来的泛型List
     * @param rootValue 根节点值,0或者-1或者自定义的值
     * @param idField id,主键名称
     * @param parentIdField  parentId,父级字段名称
     * @return tree list
     */
    public static List<Map<String, Object>> buildTree(List<Map<String, Object>> entityList, String rootValue, String idField, String parentIdField) {
        return listToTree(entityList, rootValue, idField, parentIdField);
    }
    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> entityList, String rootValue, String idField, String parentIdField) {
        return listToTree(entityList, rootValue, idField, parentIdField,"children");
    }
    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> entityList, String rootValue, String idField, String parentIdField, String childrenField) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
        List<Map<String, Object>> treeMap = new ArrayList<>();
        List<Map> listMap = BeanUtil.copyToList(entityList,Map.class);
        Map<String, Map<String, Object>> entityMap = new Hashtable<>();
        listMap.forEach(map -> entityMap.put(map.get(idField).toString(), map));
        listMap.forEach(map -> {
            Object pid = map.get(parentIdField);
            if (pid == null || StrUtil.equals(pid.toString(), rootValue)) {
                treeMap.add(map);
            } else {
                Map<String, Object> parentMap = entityMap.get(pid.toString());
                if (parentMap == null) { //如果parentMap为空，则说明当前map没有父级，当前map就是顶级
                    treeMap.add(map);
                } else {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parentMap.get(childrenField);
                    if (children == null) {  //判断子级集合是否为空，为空则新创建List
                        children = new ArrayList<>();
                        parentMap.put(childrenField, children);
                    }
                    children.add(map);
                }
            }
        });
//        stopWatch.stop();
//        stopWatch.getTotalTimeMillis();
//        StaticLog.info("listToTree 耗时， " + stopWatch.getTotalTimeMillis());
        return treeMap;
    }

}
