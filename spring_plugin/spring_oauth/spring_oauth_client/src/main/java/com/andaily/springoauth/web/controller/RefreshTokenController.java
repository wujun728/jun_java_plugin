package com.andaily.springoauth.web.controller;

import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.AccessTokenDto;
import com.andaily.springoauth.service.dto.AuthAccessTokenDto;
import com.andaily.springoauth.service.dto.RefreshAccessTokenDto;
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
 * Handle 'refresh_token'  type actions
 *
 * @author Shengzhao Li
 */
@Controller
public class RefreshTokenController {


    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenController.class);


    @Autowired
    private OauthService oauthService;


    @Value("#{properties['access-token-uri']}")
    private String accessTokenUri;


    /*
   * Entrance:   step-1
   * */
    @RequestMapping(value = "refresh_token", method = RequestMethod.GET)
    public String password(Model model) {
        LOG.debug("Go to 'refresh_token' page, accessTokenUri = {}", accessTokenUri);
        model.addAttribute("accessTokenUri", accessTokenUri);
        return "refresh_token";
    }

    /*
    * Ajax call , get access_token
    * */
    @RequestMapping(value = "password_access_token")
    public void getAccessToken(AuthAccessTokenDto authAccessTokenDto, HttpServletResponse response) {
        AccessTokenDto accessTokenDto = oauthService.retrievePasswordAccessTokenDto(authAccessTokenDto);
        writeJson(response, JSONObject.fromObject(accessTokenDto));
    }

    /*
    * Ajax call , refresh access_token
    * */
    @RequestMapping(value = "refresh_access_token")
    public void refreshAccessToken(RefreshAccessTokenDto refreshAccessTokenDto, HttpServletResponse response) {
        AccessTokenDto accessTokenDto = oauthService.refreshAccessTokenDto(refreshAccessTokenDto);
        writeJson(response, JSONObject.fromObject(accessTokenDto));
    }


}