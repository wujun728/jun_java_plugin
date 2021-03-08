package com.andaily.springoauth.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handle commons oauth actions
 *
 * @author Shengzhao Li
 */

@Controller
public class OauthController {

    private static final Logger LOG = LoggerFactory.getLogger(OauthController.class);


    /*
   * Common handle oauth error ,
   * show the error message.
   * */
    @RequestMapping("oauth_error")
    public String oauthError(String error, String message, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("message", message);

        LOG.debug("Go to oauth_error, error={},message={}", error, message);
        return "oauth_error";
    }

}