package org.ws.httphelper.model.config;

import java.util.Map;

/**
 * Created by Administrator on 15-12-30.
 */
public class RequestXmlPath extends WSHttpHelperConfigData {
    public RequestXmlPath(Map<String, Object> data) {
        super(data);
    }

    public String getPath() {
        return getValue("value");
    }
}
