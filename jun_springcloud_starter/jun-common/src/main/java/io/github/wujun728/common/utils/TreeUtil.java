package io.github.wujun728.common.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtil {

    static String jsonstr = "  [\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 0,\n" +
            "            \"id\": 1,\n" +
            "            \"sort\": 1,\n" +
            "            \"title\": \"普通公告\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 0,\n" +
            "            \"id\": 2,\n" +
            "            \"sort\": 2,\n" +
            "            \"title\": \"紧急公告\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 0,\n" +
            "            \"id\": 3,\n" +
            "            \"sort\": 3,\n" +
            "            \"title\": \"防疫公告\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 1,\n" +
            "            \"id\": 14,\n" +
            "            \"sort\": 1,\n" +
            "            \"title\": \"节假日公告\"\n" +
            "        }\n" +
            "    ] ";

    public static void main(String[] args) {
        JSONArray jsonArray = JSONUtil.parseArray(jsonstr);
        List<Map> maps = jsonArray.toList(Map.class);
        StaticLog.info(JSONUtil.toJsonPrettyStr(jsonArray));
        TreeUtil.buildTree(maps, "id", "pid", "children");
        StaticLog.info(JSONUtil.toJsonPrettyStr(maps));

    }

    public static <T, K> Map<K, T> toMap(/* */Collection<T> collection,
            /*@ApiParam("Map键值lambda表达式") */Function<T, K> keyMapper) {
        return toMap(collection, keyMapper, item -> item);
    }

    /**
     * @param collection
     * @param keyMapper   E::getName 或 e->e.getName()
     * @param valueMapper E::getName 或 e->e.getName() 或
     *                    e->MapUtils.as("name",e.getName)
     * @return
     */
    //@ApiOperation("将对象集合转换为对象Map")
    public static <T, K, V> Map<K, V> toMap(Collection<T> collection,
                                            Function<T, K> keyMapper,
                                            Function<T, V> valueMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    //@ApiOperation("将集合构建为树集合")
    public static <T, K> void buildTree(Collection<T> collection,
                                        Function<T, K> keyMapper,
                                        Function<T, K> parentMapper,
                                        Function<T, Collection<T>> getChildrenMapper,
                                        BiConsumer<T, Collection<T>> setChildrenMapper) {
        Map<K, T> map = toMap(collection, item -> {
            Collection<T> children = getChildrenMapper.apply(item);
            if (children == null) {
                children = new ArrayList<>();
                setChildrenMapper.accept(item, children);
            }
            return keyMapper.apply(item);
        });
        for (Iterator<T> iterator = collection.iterator(); iterator.hasNext(); ) {
            T t = iterator.next();
            T parentT = map.get(parentMapper.apply(t));
            if (parentT != null) {
                Collection<T> children = getChildrenMapper.apply(parentT);
                children.add(t);
                iterator.remove();
            }
        }
    }

    public static void buildTree(Collection<Map> collection, String key, String parent) {
        String childrenKey = "children";
        buildTree(collection, item -> item.get(key), item -> item.get(parent), item -> getValue(item, childrenKey, new ArrayList<>()), null);
    }

    public static void buildTree(Collection<Map> collection, String key, String parent, String childrenKey) {
        buildTree(collection, item -> item.get(key), item -> item.get(parent), item -> getValue(item, childrenKey, new ArrayList<>()), null);
    }

    public static <T> T getValue(Map map, String key, T defaultValue) {
        T value = (T) map.get(key);
        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
            map.put(key, value);
        }
        return value;
    }

}
