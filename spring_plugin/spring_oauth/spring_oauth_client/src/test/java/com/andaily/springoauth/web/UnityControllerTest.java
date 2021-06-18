package com.andaily.springoauth.web;

import org.testng.annotations.Test;

import java.net.URLEncoder;

/**
 * @author Shengzhao Li
 */
public class UnityControllerTest {


    @Test
    public void redirectUri() throws Exception {

        String url = "http://localhost:7777/authorization_code_callback";

        System.out.println(URLEncoder.encode(url, "UTF-8"));

    }

}