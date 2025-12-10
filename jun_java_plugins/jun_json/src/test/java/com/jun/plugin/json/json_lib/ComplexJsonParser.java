package com.jun.plugin.json.json_lib;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexJsonParser {

    // 正则：匹配 // 单行注释（需处理换行）
    private static final Pattern COMMENT_PATTERN = Pattern.compile("//.*?\\n");
    // 正则：匹配数组中 "}{" 之间缺失的逗号（如 }{ → },{）
    private static final Pattern MISSING_COMMA_PATTERN = Pattern.compile("\\}\\s*\\{");
    // 正则：匹配数组中 "key: value" 格式，替换为 {"key": value}（修复 ruleAction）
    private static final Pattern ARRAY_KEY_VALUE_PATTERN = Pattern.compile("\"(\\w+)\":\\s*({[^}]+})");

    /**
     * 解析复杂JSON（含自动修复非法JSON逻辑）
     */
    public static JSONObject parseComplexJson(String jsonStr) {
        JSONObject rootObj = JSONUtil.parseObj(jsonStr);
        // 递归处理嵌套JSON字符串（带修复）
        parseNestedJsonFieldsWithFix(rootObj);
        return rootObj;
    }

    /**
     * 递归解析 + 预处理修复非法JSON字符串
     */
    private static void parseNestedJsonFieldsWithFix(JSONObject jsonObj) {
        if (jsonObj == null || jsonObj.isEmpty()) {
            return;
        }

        for (String key : jsonObj.keySet()) {
            Object value = jsonObj.get(key);
            if (value == null || !(value instanceof String)) {
                // 非字符串：递归处理JSONObject/JSONArray
                if (value instanceof JSONObject) {
                    parseNestedJsonFieldsWithFix((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    parseNestedJsonArrayWithFix((JSONArray) value);
                }
                continue;
            }

            String strValue = ((String) value).trim();
            // 仅处理疑似JSON的字符串（以{/[开头，以}/]结尾）
            if (!((strValue.startsWith("{") && strValue.endsWith("}")) || (strValue.startsWith("[") && strValue.endsWith("]")))) {
                continue;
            }

            // 步骤1：预处理修复非法JSON
            String fixedJson = fixInvalidJson(strValue);

            // 步骤2：尝试解析修复后的JSON
            try {
                Object parsedObj = JSONUtil.parse(fixedJson);
                jsonObj.put(key, parsedObj);
                // 递归处理新解析的对象
                if (parsedObj instanceof JSONObject) {
                    parseNestedJsonFieldsWithFix((JSONObject) parsedObj);
                } else if (parsedObj instanceof JSONArray) {
                    parseNestedJsonArrayWithFix((JSONArray) parsedObj);
                }
            } catch (Exception e) {
                System.out.println("字段[" + key + "]修复后仍解析失败，保留原字符串：" + e.getMessage());
            }
        }
    }

    /**
     * 修复JSONArray中的嵌套字段
     */
    private static void parseNestedJsonArrayWithFix(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            Object element = jsonArray.get(i);
            if (element instanceof JSONObject) {
                parseNestedJsonFieldsWithFix((JSONObject) element);
            } else if (element instanceof JSONArray) {
                parseNestedJsonArrayWithFix((JSONArray) element);
            } else if (element instanceof String) {
                String strElement = ((String) element).trim();
                if ((strElement.startsWith("{") && strElement.endsWith("}")) || (strElement.startsWith("[") && strElement.endsWith("]"))) {
                    String fixed = fixInvalidJson(strElement);
                    try {
                        jsonArray.set(i, JSONUtil.parse(fixed));
                    } catch (Exception e) {
                        // 保留原字符串
                    }
                }
            }
        }
    }

    /**
     * 核心：修复非法JSON字符串的常见问题
     */
    private static String fixInvalidJson(String jsonStr) {
        if (StrUtil.isBlank(jsonStr)) {
            return jsonStr;
        }

        String fixed = jsonStr;
        // 1. 移除 // 单行注释（JSON不支持注释）
        Matcher commentMatcher = COMMENT_PATTERN.matcher(fixed);
        fixed = commentMatcher.replaceAll("\n");

        // 2. 补全数组中缺失的逗号（如 }{ → },{）
        Matcher commaMatcher = MISSING_COMMA_PATTERN.matcher(fixed);
        fixed = commaMatcher.replaceAll("},{");

        // 3. 修复数组中的键值对（如 "addEdit": {...} → {"addEdit": {...}}）
        Matcher keyValueMatcher = ARRAY_KEY_VALUE_PATTERN.matcher(fixed);
        fixed = keyValueMatcher.replaceAll("{\"$1\": $2}");

        // 4. 移除多余的空白字符（可选）
        fixed = fixed.replaceAll("\\r\\n", "").replaceAll("\\t", "");

        return fixed;
    }

    /**
     * 美观打印JSON
     */
    public static void printHierarchicalJson(JSONObject jsonObj) {
        System.out.println("===== 层级展示JSON数据 =====");
        System.out.println(jsonObj.toStringPretty());
    }

    // 测试
    public static void main(String[] args) {
        // 替换为你的原始JSON字符串
        String rawJsonStr = "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"操作成功\",\n" +
                "    \"data\": {\n" +
                "        \"duplicateCondition\": \"{\\r\\n\\t\\t\\\"filterFormula\\\": \\\"1 AND 2\\\",\\r\\n\\t\\t\\\"duplicationConditions\\\": [\\r\\n\\t\\t\\t{\\r\\n\\t\\t\\t\\t\\\"objectCode\\\": \\\"import_export_test_4161\\\",\\r\\n\\t\\t\\t\\t\\\"instanceName\\\": \\\"字段名称1\\\", // 字段名称\\r\\n\\t\\t\\t\\t\\\"instanceCode\\\": \\\"FID_km80kxv5d6\\\",\\r\\n\\t\\t\\t\\t\\\"instanceType\\\": \\\"input\\\", \\r\\n\\t\\t\\t\\t\\\"valueType\\\": \\\"ALL_DATA|NO_NULL_DATA\\\",  \\r\\n\\t\\t\\t\\t\\\"checkType\\\": \\\"EQ|KEYWORD_EQ\\\",  \\r\\n\\t\\t\\t\\t\\\"sort\\\": 1\\r\\n\\t\\t\\t},\\r\\n\\t\\t\\t {\\r\\n\\t\\t\\t\\t\\\"objectCode\\\": \\\"import_export_test_4161\\\",\\r\\n\\t\\t\\t\\t\\\"instanceName\\\": \\\"字段名称2\\\", // 字段名称\\r\\n\\t\\t\\t\\t\\\"instanceCode\\\": \\\"FID_km80kxv5d6\\\",\\r\\n\\t\\t\\t\\t\\\"instanceType\\\": \\\"input\\\", \\r\\n\\t\\t\\t\\t\\\"valueType\\\": \\\"ALL_DATA|NO_NULL_DATA\\\",  \\r\\n\\t\\t\\t\\t\\\"checkType\\\": \\\"EQ|KEYWORD_EQ\\\",  \\r\\n\\t\\t\\t\\t\\\"sort\\\": 1\\r\\n\\t\\t\\t}\\r\\n\\t\\t] \\r\\n\\t}\",\n" +
                "        \"ruleScope\": \"{\\r\\n\\t\\t\\\"filterFormula\\\": \\\"1 AND 2\\\",\\r\\n\\t\\t\\\"duplicationConditions\\\": [\\r\\n\\t\\t\\t{\\r\\n\\t\\t\\t\\t\\\"instanceName\\\": \\\"字段名称1\\\",\\r\\n\\t\\t\\t\\t\\\"valueType\\\": \\\"FIXED_VALUE\\\",\\r\\n\\t\\t\\t\\t\\\"value\\\": \\\"111\\\", \\r\\n\\t\\t\\t\\t\\\"objectCode\\\": \\\"import_export_test_4161\\\",\\r\\n\\t\\t\\t\\t\\\"conditionType\\\": \\\"LIKE\\\",\\r\\n\\t\\t\\t\\t\\\"instanceCode\\\": \\\"FID_kc2zts8ob6\\\",\\r\\n\\t\\t\\t\\t\\\"instanceType\\\": \\\"textarea\\\",\\r\\n\\t\\t\\t\\t\\\"sort\\\": 1\\r\\n\\t\\t\\t}\\r\\n\\t\\t\\t{\\r\\n\\t\\t\\t\\t\\\"instanceName\\\": \\\"字段名称2\\\",\\r\\n\\t\\t\\t\\t\\\"valueType\\\": \\\"FIXED_VALUE\\\",\\r\\n\\t\\t\\t\\t\\\"value\\\": \\\"111\\\", \\r\\n\\t\\t\\t\\t\\\"objectCode\\\": \\\"import_export_test_4161\\\",\\r\\n\\t\\t\\t\\t\\\"conditionType\\\": \\\"LIKE\\\",\\r\\n\\t\\t\\t\\t\\\"instanceCode\\\": \\\"FID_kc2zts8ob6\\\",\\r\\n\\t\\t\\t\\t\\\"instanceType\\\": \\\"textarea\\\",\\r\\n\\t\\t\\t\\t\\\"sort\\\": 2\\r\\n\\t\\t\\t}\\r\\n\\t\\t] \\r\\n\\t}\",\n" +
                "        \"ruleAction\": \"[\\r\\n    \\\"addEdit\\\":  \\r\\n        { \\r\\n            \\\"hitType\\\": \\\"1\\\", \\r\\n\\t\\t\\t\\\"value\\\": \\\"LYT_0001 | 禁止XXX重复\\\"  \\r\\n        },\\r\\n\\t\\\"service\\\":  \\r\\n\\t\\t\\t{ \\r\\n\\t\\t\\t\\t\\\"hitType\\\": \\\"1\\\", \\r\\n\\t\\t\\t}\\r\\n\\t\\\"import\\\":  \\r\\n\\t\\t\\t{ \\r\\n\\t\\t\\t\\t\\\"hitType\\\": \\\"1\\\", \\r\\n\\t\\t\\t\\t\\\"value\\\": \\\"禁止XXX重复\\\"  \\r\\n\\t\\t\\t} \\t\\t\\r\\n]\"\n" +
                "    },\n" +
                "    \"trace-id\": \"metadata1208150203545b9ii\"\n" +
                "}";

        // 解析并打印
        JSONObject fullJsonObj = parseComplexJson(rawJsonStr);
        printHierarchicalJson(fullJsonObj);
    }
}