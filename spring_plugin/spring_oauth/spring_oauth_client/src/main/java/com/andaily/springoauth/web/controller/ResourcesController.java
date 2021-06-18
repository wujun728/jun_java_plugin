package com.andaily.springoauth.web.controller;

import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handle visit Oauth resources, must be have 'access_token'
 *
 * @author Shengzhao Li
 */
@Controller
public class ResourcesController {


    @Autowired
    private OauthService oauthService;


    /*
   * Visit unity role  for get user information from oauth server
   * */
    @RequestMapping("unity_user_info")
    public String unityUserInfo(String access_token, Model model) {
        UserDto userDto = oauthService.loadUnityUserDto(access_token);

        if (userDto.error()) {
            //error
            model.addAttribute("message", userDto.getErrorDescription());
            model.addAttribute("error", userDto.getError());
            return "redirect:oauth_error";
        } else {
            model.addAttribute("userDto", userDto);
            return "resources/unity_user_info";
        }

    }


}