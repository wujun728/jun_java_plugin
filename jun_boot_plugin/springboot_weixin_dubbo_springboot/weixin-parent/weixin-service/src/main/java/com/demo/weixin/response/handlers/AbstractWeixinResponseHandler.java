package com.demo.weixin.response.handlers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public abstract class  AbstractWeixinResponseHandler<T> implements ResponseHandler<T>{

    @Override
    public T handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        if (entity == null) {
            return null;
        }
        String responseStr =  EntityUtils.toString(entity, Charset.forName("utf-8"));
        return getResponseObject(responseStr);
    }

    /**
     *
     * 获取返回对象
     * @param responseStr must not be null
     * @return
     */
    protected abstract T getResponseObject(String responseStr);
}
