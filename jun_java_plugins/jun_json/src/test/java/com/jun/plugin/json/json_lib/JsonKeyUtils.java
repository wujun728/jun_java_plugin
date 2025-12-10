package com.jun.plugin.json.json_lib;

import cn.hutool.json.JSONObject;

public class JsonKeyUtils {

    /**
     * 重命名 JSON 中指定路径的 Key（支持全路径+自动清理空对象）
     * <p>
     * 路径规则：
     * 1. 默认用 "." 分隔路径层级（如 "user.info.oldAge"）；
     * 2. 若 Key 本身含 "."，请用 "/" 作为路径分隔符（如 "user/info/user.newAge"）；
     * 3. 自动删除操作后产生的空 JSONObject。
     *
     * @param rootObj      根 JSONObject
     * @param oldKeyPath   旧 Key 全路径（示例："user.info.oldAge" 或 "user/info/user.newAge"）
     * @param newKeyPath   新 Key 全路径（示例："newAge2" 或 "user.profile.age"）
     */
    public static void renameJsonKey(JSONObject rootObj, String oldKeyPath, String newKeyPath) {
        // 空值校验
        if (rootObj == null || oldKeyPath == null || newKeyPath == null
                || oldKeyPath.isEmpty() || newKeyPath.isEmpty()) {
            return;
        }

        // 自动识别路径分隔符（优先用 /，无则用 .）
        String pathSeparator = oldKeyPath.contains("/") || newKeyPath.contains("/") ? "/" : ".";

        // 1. 获取旧路径的值 & 删除旧路径 Key（并清理空父级）
        Object oldValue = getValueByFullPath(rootObj, oldKeyPath, pathSeparator);
        if (oldValue == null) {
            return; // 旧路径无值，直接返回
        }
        // 删除旧Key，并递归清理空父级
        removeKeyByFullPathWithClean(rootObj, oldKeyPath, pathSeparator);

        // 2. 将值放入新路径（自动创建不存在的父级 JSONObject）
        setValueByFullPath(rootObj, newKeyPath, oldValue, pathSeparator);
    }

    /**
     * 按全路径获取值（支持自定义分隔符）
     */
    private static Object getValueByFullPath(JSONObject rootObj, String fullPath, String separator) {
        String[] pathSegments = fullPath.split(escapeSeparator(separator));
        JSONObject currentObj = rootObj;

        for (int i = 0; i < pathSegments.length - 1; i++) {
            String segment = pathSegments[i];
            if (!currentObj.containsKey(segment) || !(currentObj.get(segment) instanceof JSONObject)) {
                return null;
            }
            currentObj = currentObj.getJSONObject(segment);
        }

        String finalKey = pathSegments[pathSegments.length - 1];
        return currentObj.get(finalKey);
    }

    /**
     * 按全路径删除 Key + 递归清理空父级 JSONObject
     */
    private static void removeKeyByFullPathWithClean(JSONObject rootObj, String fullPath, String separator) {
        String[] pathSegments = fullPath.split(escapeSeparator(separator));
        // 保存所有层级的 JSONObject 和对应 Key，用于后续清理空对象
        JSONObject[] layerObjs = new JSONObject[pathSegments.length];
        String[] layerKeys = new String[pathSegments.length];

        // 1. 遍历定位路径，记录每一层的 JSONObject 和 Key
        layerObjs[0] = rootObj;
        for (int i = 0; i < pathSegments.length; i++) {
            if (i > 0) {
                layerObjs[i] = layerObjs[i - 1].getJSONObject(layerKeys[i - 1]);
                if (layerObjs[i] == null) {
                    return; // 路径不存在，直接返回
                }
            }
            layerKeys[i] = pathSegments[i];
        }

        // 2. 删除最后一级 Key
        JSONObject targetObj = layerObjs[pathSegments.length - 1];
        String targetKey = layerKeys[pathSegments.length - 1];
        targetObj.remove(targetKey);

        // 3. 递归向上清理空 JSONObject（从倒数第二层开始）
        for (int i = pathSegments.length - 1; i > 0; i--) {
            JSONObject parentObj = layerObjs[i - 1];
            String currentKey = layerKeys[i - 1];
            JSONObject currentObj = layerObjs[i];

            // 若当前 JSONObject 为空，则从父级中删除
            if (currentObj.isEmpty()) {
                parentObj.remove(currentKey);
            } else {
                break; // 非空则停止向上清理
            }
        }
    }

    /**
     * 按全路径设置值（自动创建父级，支持自定义分隔符）
     */
    private static void setValueByFullPath(JSONObject rootObj, String fullPath, Object value, String separator) {
        String[] pathSegments = fullPath.split(escapeSeparator(separator));
        JSONObject currentObj = rootObj;

        for (int i = 0; i < pathSegments.length - 1; i++) {
            String segment = pathSegments[i];
            if (!currentObj.containsKey(segment) || !(currentObj.get(segment) instanceof JSONObject)) {
                currentObj.put(segment, new JSONObject());
            }
            currentObj = currentObj.getJSONObject(segment);
        }

        String finalKey = pathSegments[pathSegments.length - 1];
        currentObj.put(finalKey, value);
    }

    /**
     * 转义分隔符（避免正则特殊字符报错）
     */
    private static String escapeSeparator(String separator) {
        return separator.replace(".", "\\.").replace("/", "\\/");
    }

    // 测试示例
    public static void main(String[] args) {
        // 测试1：普通全路径替换（清理空对象）
        String json1 = "{\"user\":{\"info\":{\"oldAge\":25}}}";
        JSONObject root1 = new JSONObject(json1);
        renameJsonKey(root1, "user.info.oldAge", "user.newAge2");
        System.out.println("普通全路径替换（清理空对象后）：\n" + root1.toStringPretty());

        // 测试2：嵌套多层空对象清理
        String json2 = "{\"a\":{\"b\":{\"c\":{\"d\":\"test\"}}}}";
        JSONObject root2 = new JSONObject(json2);
        renameJsonKey(root2, "a.b.c.d", "x.y.z");
        System.out.println("\n多层空对象清理：\n" + root2.toStringPretty());
    }
}