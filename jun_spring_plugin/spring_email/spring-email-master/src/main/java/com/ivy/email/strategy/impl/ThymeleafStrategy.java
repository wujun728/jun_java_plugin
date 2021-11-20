package com.ivy.email.strategy.impl;

import com.ivy.email.strategy.MailStrategy;
import com.ivy.email.vo.EmailVO;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Created by fangjie04 on 2016/12/3.
 */

public class ThymeleafStrategy implements MailStrategy {

    private SpringTemplateEngine springTemplateEngine;
    private Context context;
    private String templateName;

    public ThymeleafStrategy setSpringTemplateEngine(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
        return this;
    }

    public ThymeleafStrategy(Context context, String templateName) {
        this.context = context;
        this.templateName = templateName;
    }

    public ThymeleafStrategy setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public String message(EmailVO vo) {
        String content = this.springTemplateEngine.process(this.templateName, context);
        return content;
    }
}
