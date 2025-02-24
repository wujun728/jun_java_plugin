package com.mycompany.myproject.oauth.github;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * github 第三方登录官方api文档 https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 */
@Controller
@RequestMapping("oauth/github/")
public class GithubOAuthController {

    private final static Logger logger = LoggerFactory.getLogger(GithubOAuthController.class);


    public static final String AUTHORIZE_URL =
            "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s";
    public static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token?" +
            "client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&state=%s";
    public static final String USER_INFO_URL = "https://api.github.com/user?access_token=%s";
    public static final String CALLBACK_URL = "https://dashena.com/barry/oauth/github/callback";

    public static final String CLIENT_ID = "233e191f1dd7e5e96a75";
    public static final String CLIENT_SECRET = "3e00719c4713d29f8e2e8e5cd4f59e1de3a133b5";

    @RequestMapping(value = "callback", method = RequestMethod.GET)
    @ResponseBody
    public String callback(@RequestParam(value = "code") String code, HttpServletRequest request){
        logger.debug("GithubOAuthController callback : code=" + code );

        String tokenUrl = String.format(ACCESS_TOKEN_URL, CLIENT_ID, CLIENT_SECRET,code
                        , CALLBACK_URL, "1");

        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.getForObject(tokenUrl, String.class);

        //access_token=829bbd509aef6f4b4298806904daafbcc42ebbad&scope=&token_type=bearer
        logger.debug("GithubOAuthController callback : token=" + resp );

        if (resp.contains("access_token")) {
            Map<String, String> map = getParam(resp);
            String access_token = map.get("access_token");

            String userUrl = String.format(USER_INFO_URL, access_token);
            String resp2 = restTemplate.getForObject(userUrl, String.class);

            return resp2;
        }

        return resp;
    }

    @RequestMapping(value = "custom/callback", method = RequestMethod.GET)
    public String callbackCustom(@RequestParam(value = "code") String code, HttpServletRequest request){
        logger.debug("GithubOAuthController callbackCustom : " + code);

        return "succ";
    }

    private Map<String, String> getParam(String string) {
        Map<String, String> map = new HashMap();
        String[] kvArray = string.split("&");
        for (int i = 0; i < kvArray.length; i++) {
            String[] kv = kvArray[i].split("=");
            if(kv.length == 2) {
                map.put(kv[0], kv[1]);
            } else if(kv.length == 1) {
                map.put(kv[0],"");
            }
        }
        return map;
    }
}
