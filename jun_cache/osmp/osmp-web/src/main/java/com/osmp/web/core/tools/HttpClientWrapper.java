/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.osmp.web.core.SystemFrameWork;

public class HttpClientWrapper implements InitializingBean {
    private HttpClient client;

    private int connectionTimeout = 10000;
    private int timeout = 10000;

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(client);
        client.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());
        // milliseconds
        client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        // milliseconds
        client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
    }

    public String post(String url, Map<String, String> params) {
        String result = "";
        client.getParams().setAuthenticationPreemptive(true);
        String user = SystemFrameWork.proMap.get("webuser");
        String password = SystemFrameWork.proMap.get("webpassword");
        client.getState().setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(null == user ? "smx" : user, null == password ? "smx" : password));
        final PostMethod method = new PostMethod(url);

        // method.addParameter("action", command);
        if (params != null && !params.isEmpty()) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            Iterator<Entry<String, String>> iterator = entrySet.iterator();
            Entry<String, String> next;
            String key;
            String value;
            while (iterator.hasNext()) {
                next = iterator.next();
                key = next.getKey();
                value = next.getValue();
                if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                    method.addParameter(key, value);
                }
            }
        }
        try {
            client.executeMethod(method);
            result = method.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return result;
    }

    /**
     * 根据Web Console RESTful API接口获取JSON格式的数据
     *
     * @param url
     * @return
     */
    public String get(String url) {
        String result = "";
        client.getParams().setAuthenticationPreemptive(true);
        String user = SystemFrameWork.proMap.get("webuser");
        String password = SystemFrameWork.proMap.get("webpassword");
        client.getState().setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(null == user ? "smx" : user, null == password ? "smx" : password));
        final GetMethod method = new GetMethod(url);
        try {
            client.executeMethod(method);
            result = method.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return result;
    }

    /**
     * 根据JSON数据解析BUNDLE列表
     *
     * @return
     */
    public <T> List<T> parse(String json, Class<T> t, String jsonNode) {
        List<T> list = new ArrayList<T>();
        try {
            JSONObject jsonObj = JSONObject.parseObject(json);
            if (!StringUtils.isBlank(jsonNode)) {
                JSONArray ja = jsonObj.getJSONArray(jsonNode);
                list = (List<T>) JSONArray.parseArray(ja.toJSONString(), t);
            } else {
                list = (List<T>) JSONArray.parseArray(jsonObj.toString(), t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
