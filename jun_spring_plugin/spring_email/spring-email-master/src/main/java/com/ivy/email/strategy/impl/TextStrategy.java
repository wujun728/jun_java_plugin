package com.ivy.email.strategy.impl;

import com.ivy.email.strategy.MailStrategy;
import com.ivy.email.vo.EmailVO;

/**
 * Created by fangjie04 on 2016/12/3.
 */
//@Component
public class TextStrategy implements MailStrategy {

    @Override
    public String message(EmailVO vo) {
        return vo.getEmailContent();
    }
}
