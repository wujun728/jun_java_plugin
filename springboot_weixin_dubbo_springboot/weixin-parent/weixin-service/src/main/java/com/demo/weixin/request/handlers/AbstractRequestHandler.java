package com.demo.weixin.request.handlers;

import com.demo.weixin.exception.WeixinException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @description
 * @date 2017/7/31
 * @since 1.0
 */
public abstract class AbstractRequestHandler {


    public abstract Map<String, Object> getParamMap() throws WeixinException;

    public void addValueFromString(String key, String value, Map<String, Object> paramMap) {
        if (StringUtils.isAnyBlank(key, value) || paramMap == null) {
            return;
        }
        paramMap.put(key, value);
    }

    public void addValueFromList(String key, List value, Map<String, Object> paramMap) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(value) || paramMap == null) {
            return;
        }
        paramMap.put(key, value);
    }

}
