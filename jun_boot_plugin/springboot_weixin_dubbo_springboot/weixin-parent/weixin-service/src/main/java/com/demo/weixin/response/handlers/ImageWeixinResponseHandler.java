package com.demo.weixin.response.handlers;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class ImageWeixinResponseHandler extends AbstractWeixinResponseHandler<byte[]> {

    @Override
    public byte[] handleResponse(HttpResponse response) throws IOException {
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

        return IOUtils.toByteArray(entity.getContent());
    }

    @Override
    protected byte[] getResponseObject(String responseStr) {
        return null;
    }
}
