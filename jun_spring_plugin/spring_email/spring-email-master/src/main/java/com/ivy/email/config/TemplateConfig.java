package com.ivy.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fangjie04 on 2016/12/1.
 */

@Component
public class TemplateConfig {

    @Value("${velocity.vm.name}")
    private String velocityTemplateName ;

    @Value("${thymeleaf.html.name}")
    private String thymeleafTemplateName;

    public String getVelocityTemplateName() {
        return velocityTemplateName;
    }

    public void setVelocityTemplateName(String velocityTemplateName) {
        this.velocityTemplateName = velocityTemplateName;
    }

    public String getThymeleafTemplateName() {
        return thymeleafTemplateName;
    }

    public void setThymeleafTemplateName(String thymeleafTemplateName) {
        this.thymeleafTemplateName = thymeleafTemplateName;
    }
}
