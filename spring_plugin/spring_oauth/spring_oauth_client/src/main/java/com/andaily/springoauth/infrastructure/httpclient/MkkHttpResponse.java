package com.andaily.springoauth.infrastructure.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Ext. CloseableHttpResponse actions.
 * Add more conversation methods
 *
 * @author Wujun
 */
public class MkkHttpResponse implements Serializable {

    protected CloseableHttpResponse httpResponse;


    public MkkHttpResponse(CloseableHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }


    public CloseableHttpResponse httpResponse() {
        return httpResponse;
    }

    /*
   * Convert response as string
   * */
    public String responseAsString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            httpResponse.getEntity().writeTo(baos);
            return new String(baos.toByteArray(), "UTF-8");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /*
   * Check response status is 200 or not
   * */
    public boolean isResponse200() {
        return httpResponse.getStatusLine().getStatusCode() == 200;
    }


}