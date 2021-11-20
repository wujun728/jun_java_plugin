package com.jun.plugin.oauth.server.infrastructure;


import org.junit.Test;

import com.jun.plugin.oauth.server.infrastructure.PasswordHandler;

import static org.junit.Assert.assertNotNull;

/*
  * @author Wujun
  */
public class PasswordHandlerTest {


    @Test
    public void testMd5() throws Exception {

        final String md5 = PasswordHandler.encode("123456");
        assertNotNull(md5);
        System.out.println(md5);
    }
}