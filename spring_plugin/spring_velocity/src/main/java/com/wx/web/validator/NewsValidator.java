package com.wx.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wx.web.command.NewsCommand;

/**
 * @author Wujun
 *
 */
public class NewsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewsCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "news.title.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "news.description.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "news.address.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newsTime", "news.newsTime.empty");
    }

}
