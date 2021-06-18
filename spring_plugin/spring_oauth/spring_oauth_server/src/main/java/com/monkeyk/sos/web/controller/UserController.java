package com.monkeyk.sos.web.controller;

import com.monkeyk.sos.domain.dto.UserFormDto;
import com.monkeyk.sos.domain.dto.UserOverviewDto;
import com.monkeyk.sos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserFormDtoValidator validator;

    /**
     * @return View page
     */
    @RequestMapping("overview")
    public String overview(UserOverviewDto overviewDto, Model model) {
        overviewDto = userService.loadUserOverviewDto(overviewDto);
        model.addAttribute("overviewDto", overviewDto);
        return "user_overview";
    }


    @RequestMapping(value = "form/plus", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("formDto", new UserFormDto());
        return "user_form";
    }


    @RequestMapping(value = "form/plus", method = RequestMethod.POST)
    public String submitRegisterClient(@ModelAttribute("formDto") UserFormDto formDto, BindingResult result) {
        validator.validate(formDto, result);
        if (result.hasErrors()) {
            return "user_form";
        }
        userService.saveUser(formDto);
        return "redirect:../overview";
    }


}