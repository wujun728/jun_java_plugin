package com.mycompany.myproject.oauth;


import com.mycompany.myproject.oauth.github.GithubOAuthController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("oauth/")
public class ThirdPartLogin {

    @RequestMapping("login")
    public String loginPage(HttpServletRequest request, ModelMap model ){

        String url = String.format(GithubOAuthController.AUTHORIZE_URL, GithubOAuthController.CLIENT_ID
                , GithubOAuthController.CALLBACK_URL, "1");

        model.addAttribute("github", url);
        return "login";
    }
}
