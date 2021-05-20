package com.andaily.springoauth.service.impl;

import com.andaily.springoauth.infrastructure.json.JsonUtils;
import com.andaily.springoauth.service.dto.AccessTokenDto;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Shengzhao Li
 */
public class OauthServiceImplTest {


    @Test
    public void testParseJSON() {

        String text = "{\"access_token\":\"3420d0e0-ed77-45e1-8370-2b55af0a62e8\",\"token_type\":\"bearer\",\"refresh_token\":\"b36f4978-a172-4aa8-af89-60f58abe3ba1\",\"expires_in\":43199,\"scope\":\"read write\"}";

        final AccessTokenDto accessTokenDto = JsonUtils.textToBean(new AccessTokenDto(), text);
        assertNotNull(accessTokenDto);

        System.out.println(accessTokenDto);

    }

}