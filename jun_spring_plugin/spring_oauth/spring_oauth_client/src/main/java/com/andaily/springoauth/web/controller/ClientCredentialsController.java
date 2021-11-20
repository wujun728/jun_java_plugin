package com.andaily.springoauth.web.controller;

import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.AccessTokenDto;
import com.andaily.springoauth.service.dto.AuthAccessTokenDto;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

import static com.andaily.springoauth.web.WebUtils.writeJson;

/**
 * Handle 'client_credentials'  type actions
 *
 * @author Shengzhao Li
 */
@Controller
public class ClientCredentialsController {


    private static final Logger LOG = LoggerFactory.getLogger(ClientCredentialsController.class);


    @Value("#{properties['access-token-uri']}")
    private String accessTokenUri;


    @Value("#{properties['unityUserInfoUri']}")
    private String unityUserInfoUri;


    @Autowired
    private OauthService oauthService;

    /*
   * Entrance:   step-1
   * */
    @RequestMapping(value = "client_credentials", method = RequestMethod.GET)
    public String password(Model model) {
        LOG.debug("Go to 'client_credentials' page, accessTokenUri = {}", accessTokenUri);
        model.addAttribute("accessTokenUri", accessTokenUri);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        return "client_credentials";
    }


    /*
   * Ajax call , get access_token
   * */
    @RequestMapping(value = "credentials_access_token")
    public void getAccessToken(AuthAccessTokenDto authAccessTokenDto, HttpServletResponse response) {
        AccessTokenDto accessTokenDto = oauthService.retrieveCredentialsAccessTokenDto(authAccessTokenDto);
        writeJson(response, JSONObject.fromObject(accessTokenDto));
    }

}