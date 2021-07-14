package com.jun.plugin.tool.json;

import java.util.List;
import java.util.Map;

import com.jun.plugin.tool.json.reader.BasicJsonReader;
import com.jun.plugin.tool.json.writer.BasicJsonwriter;

/**
 * A json serialization and deserialization library.
 *
 * @author xuxueli 2018-11-30
 */
public class BasicJsonTool {

    private static final BasicJsonReader basicJsonReader = new BasicJsonReader();
    private static final BasicJsonwriter basicJsonwriter = new BasicJsonwriter();

    /**
     * object to json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return basicJsonwriter.toJson(object);
    }

    /**
     * json to List<Object>
     *
     * @param json
     * @return
     */
    public static List<Object> parseList(String json) {
        return basicJsonReader.parseList(json);
    }

    /**
     * json to Map<String, Object>
     *
     * @param json
     * @return
     */
    public static Map<String, Object> parseMap(String json) {
        return basicJsonReader.parseMap(json);
    }

}
