package com.andaily.springoauth.infrastructure.httpclient;

/**
 * @author Shengzhao Li
 */

public interface HttpResponseHandler {


    public void handleResponse(MkkHttpResponse response);

}