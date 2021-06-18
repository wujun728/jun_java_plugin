package com.jun.plugin.json.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
//import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author Wujun
 */
public class JsonUtil {

    public static final String JACKSON_CLASS_TYPE = "jackson";
    public static final String GSON_CLASS_TYPE = "gson";
    public static final String FASTJSON_CLASS_TYPE = "fastjson";
    private static final Logger log = Logger.getLogger("JsonUtil");
    private static final String CLASS_TYPE_OBJECT_MAPPER = "com.fasterxml.jackson.databind.ObjectMapper";
    private static final String CLASS_TYPE_GSON = "com.google.gson.Gson";
    private static final String CLASS_TYPE_FASTJSON = "com.alibaba.fastjson.JSON";
    public static String JSON_CLASS_TYPE = "json.class.type";
    public static Map<String, String> classTypeCache = new ConcurrentHashMap<>();
    private static String classType;
    private static ObjectMapper objectMapper;
    private static Gson gson;

    static {
        try {
            String jsonClassType = null;
            Properties properties = new Properties();
            InputStream in = JsonUtil.class.getClassLoader().getResourceAsStream("application.properties");
            if (in != null) {
                properties.load(in);
                jsonClassType = properties.getProperty(JSON_CLASS_TYPE);
            } else {
                log.warning("未找到application.properties");
                in = JsonUtil.class.getClassLoader().getResourceAsStream("application.yml");
                if (in != null) {
					/*
					 * Yaml yaml = new Yaml(); Map<String, Object> propsMap = yaml.loadAs(in,
					 * LinkedHashMap.class); propsMap = (Map<String, Object>) propsMap.get("json");
					 * if (propsMap != null) { jsonClassType =
					 * String.valueOf(propsMap.get("class-type")); } else {
					 * log.warning("application.yml中未配置json.class-type"); }
					 */
                } else {
                    log.warning("未找到application.yml");
                }
            }

            if (jsonClassType != null && jsonClassType.trim().length() > 0) {
                classType = jsonClassType;
            }

            if (classType != null && classType.length() > 0) {
                if (Class.forName(CLASS_TYPE_OBJECT_MAPPER) != null && classType.equals(JACKSON_CLASS_TYPE)) {
                    log.info("use jackson lib");
                } else if (Class.forName(CLASS_TYPE_GSON) != null && classType.equals(GSON_CLASS_TYPE)) {
                    log.info("use gson lib");
                } else if (Class.forName(CLASS_TYPE_FASTJSON) != null && classType.equals(FASTJSON_CLASS_TYPE)) {
                    log.info("use fastjson lib");
                } else {
                    log.severe("未找到jackson、gson或fastjson的依赖");
                    throw new RuntimeException("未找到jackson、gson或fastjson的依赖");
                }
            } else if (Class.forName(CLASS_TYPE_OBJECT_MAPPER) != null) {
                log.info("use jackson lib");
                classType = "jackson";
            } else if (Class.forName(CLASS_TYPE_GSON) != null) {
                log.info("use gson lib");
                classType = "gson";
            } else if (Class.forName(CLASS_TYPE_FASTJSON) != null) {
                log.info("use fastjson lib");
                classType = "fastjson";
            } else {
                log.severe("未找到jackson、gson或fastjson的依赖");
                throw new RuntimeException("未找到jackson、gson或fastjson的依赖");
            }

            classTypeCache.put(JSON_CLASS_TYPE, jsonClassType);

            // 属性值为NULL的，不序列化
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }

            if (gson == null) {
                gson = new GsonBuilder()
                        .setLenient()
                        // 解决gson序列化时出现整型变为浮点型的问题
                        .registerTypeAdapter(new TypeToken<Map<Object, Object>>() {
                                }.getType(),
                                new JsonDeserializer<Map<Object, Object>>() {
                                    @Override
                                    public Map<Object, Object> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                                        Map<Object, Object> map = new LinkedHashMap<>();
                                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                                        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                        for (Map.Entry<String, JsonElement> entry : entrySet) {
                                            Object obj = entry.getValue();
                                            if (obj instanceof JsonPrimitive) {
                                                map.put(entry.getKey(), ((JsonPrimitive) obj).getAsString());
                                            } else {
                                                map.put(entry.getKey(), obj);
                                            }
                                        }
                                        return map;
                                    }
                                }
                        )
                        .registerTypeAdapter(new TypeToken<List<Object>>() {
                                }.getType(),
                                new JsonDeserializer<List<Object>>() {
                                    @Override
                                    public List<Object> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                                        List<Object> list = new LinkedList<>();
                                        JsonArray jsonArray = jsonElement.getAsJsonArray();
                                        for (int i = 0; i < jsonArray.size(); i++) {
                                            if (jsonArray.get(i).isJsonObject()) {
                                                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                                list.addAll(entrySet);
                                            } else if (jsonArray.get(i).isJsonPrimitive()) {
                                                list.add(jsonArray.get(i));
                                            }
                                        }
                                        return list;
                                    }
                                }
                        )
                        .create();
            }
        } catch (ClassNotFoundException | IOException e) {
            log.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析json字符串到Map
     *
     * @param json 要解析的json字符串
     * @return 返回Map
     */
    public static Map toMap(String json) {
        Map result = null;
        try {
            switch (classType) {
                case JACKSON_CLASS_TYPE:
                    result = objectMapper.readValue(json, LinkedHashMap.class);
                    break;
                case GSON_CLASS_TYPE:
                    TypeToken<Map<Object, Object>> typeToken = new TypeToken<Map<Object, Object>>() {
                    };
                    result = gson.fromJson(json, typeToken.getType());
                    break;
                case FASTJSON_CLASS_TYPE:
                    result = JSON.parseObject(json, LinkedHashMap.class);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 解析json字符串到List
     *
     * @param json 要解析的json字符串
     * @return 返回List
     */
    public static List toList(String json) {
        List result = null;
        try {
            switch (classType) {
                case JACKSON_CLASS_TYPE:
                    result = objectMapper.readValue(json, LinkedList.class);
                    break;
                case GSON_CLASS_TYPE:
                    TypeToken<List<Object>> typeToken = new TypeToken<List<Object>>() {
                    };
                    result = gson.fromJson(json, typeToken.getType());
                    break;
                case FASTJSON_CLASS_TYPE:
                    result = JSON.parseObject(json, LinkedList.class);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 按指定的Type解析json字符串到List
     *
     * @param json 要解析的json字符串
     * @param type {@link Type}
     * @return 返回List
     */
    public static <T> List<T> toList(String json, final Type type) {
        List<T> result = null;
        switch (classType) {
		    case JACKSON_CLASS_TYPE:
		        TypeReference<T> typeReference = new TypeReference<T>() {
		            @Override
		            public Type getType() {
		                return type;
		            }
		        };
//                    result = objectMapper.readValue(json, typeReference);
		        break;
		    case GSON_CLASS_TYPE:
		        result = gson.fromJson(json, type);
		        break;
		    case FASTJSON_CLASS_TYPE:
		        TypeReference<T> typeReference1 = new TypeReference<T>() {
		            @Override
		            public Type getType() {
		                return type;
		            }
		        };
		        result = JSON.parseObject(json, typeReference1.getType());
		        break;
		    default:
		        break;
		}
        return result;
    }

    /**
     * 解析对象为Json字符串
     *
     * @param object 要转换的对象
     * @return 返回对象的json字符串
     */
    public static String toJsonString(Object object) {
        String result = null;
        try {

            switch (classType) {
                case JACKSON_CLASS_TYPE:
                    result = objectMapper.writeValueAsString(object);
                    break;
                case GSON_CLASS_TYPE:
                    result = gson.toJson(object);
                    break;
                case FASTJSON_CLASS_TYPE:
                    result = JSON.toJSONString(object);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 解析对象为Json字符串
     *
     * @param object 要转换的对象
     * @param dateFormatPattern 日期格式，如"yyyy年MM月dd日 HH时mm分ss秒"
     * @return 返回对象的json字符串
     */
    public static String toJsonWithDateFormat(Object object, String dateFormatPattern) {
        String result = null;
        try {

            switch (classType) {
                case JACKSON_CLASS_TYPE:
                    SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
                    result = objectMapper.writer(sdf).writeValueAsString(object);
                    break;
                case GSON_CLASS_TYPE:
                    gson = new GsonBuilder().setDateFormat(dateFormatPattern).create();
                    result = gson.toJson(object);
                    break;
                case FASTJSON_CLASS_TYPE:
                    result = JSON.toJSONStringWithDateFormat(object, dateFormatPattern, SerializerFeature.WriteDateUseDateFormat);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 解析json字符串到指定类型的对象
     *
     * @param json      要解析的json字符串
     * @param valueType 类对象class
     * @param <T>       泛型参数类型
     * @return 返回解析后的对象
     */
    public static <T> T toPojo(String json, Class<T> valueType) {
        T result = null;
        try {

            switch (classType) {
                case JACKSON_CLASS_TYPE:
                    result = objectMapper.readValue(json, valueType);
                    break;
                case GSON_CLASS_TYPE:
                    result = gson.fromJson(json, valueType);
                    break;
                case FASTJSON_CLASS_TYPE:
                    result = JSON.parseObject(json, valueType);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 转换对象到Map
     *
     * @param fromValue 与Map可兼容的对象
     * @return 返回Map对象
     */
    public static Map convertToMap(Object fromValue) {
        Map result = null;
        switch (classType) {
            case JACKSON_CLASS_TYPE:
                result = objectMapper.convertValue(fromValue, LinkedHashMap.class);
                break;
            case GSON_CLASS_TYPE:
                TypeToken<Map<Object, Object>> typeToken = new TypeToken<Map<Object, Object>>() {
                };
                String json = gson.toJson(fromValue);
                result = gson.fromJson(json, typeToken.getType());
                break;
            case FASTJSON_CLASS_TYPE:
                json = JSON.toJSONString(fromValue);
                result = JSON.parseObject(json, LinkedHashMap.class);
                break;
            default:
                break;
        }

        return result;
    }

    /**
     * 从Map转换到对象
     *
     * @param fromMap     Map对象
     * @param toValueType 与Map可兼容的对象类型
     * @param <T>         泛型参数类型
     * @return 返回Map转换得到的对象
     */
    public static <T> T convertFromMap(Map fromMap, Class<T> toValueType) {
        T result = null;
        switch (classType) {
            case JACKSON_CLASS_TYPE:
                result = objectMapper.convertValue(fromMap, toValueType);
                break;
            case GSON_CLASS_TYPE:
                String json = gson.toJson(fromMap);
                result = gson.fromJson(json, toValueType);
                break;
            case FASTJSON_CLASS_TYPE:
                json = JSON.toJSONString(fromMap);
                result = JSON.parseObject(json, toValueType);
                break;
            default:
                break;
        }

        return result;
    }
}
