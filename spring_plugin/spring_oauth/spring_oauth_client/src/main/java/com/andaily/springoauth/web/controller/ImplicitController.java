package com.andaily.springoauth.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handle 'implicit'  type actions
 *
 * @author Shengzhao Li
 */
@Controller
public class ImplicitController {


    private static final Logger LOG = LoggerFactory.getLogger(ImplicitController.class);


    @Value("#{properties['user-authorization-uri']}")
    private String userAuthorizationUri;


    @Value("#{properties['unityUserInfoUri']}")
    private String unityUserInfoUri;

    @Value("#{properties['application-host']}")
    private String host;


    /*
   * Entrance:   step-1
   * */
    @RequestMapping(value = "implicit")
    public String password(Model model) {
        LOG.debug("Go to 'implicit' page, userAuthorizationUri = {}", userAuthorizationUri);
        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("host", host);
        return "implicit";
    }


}