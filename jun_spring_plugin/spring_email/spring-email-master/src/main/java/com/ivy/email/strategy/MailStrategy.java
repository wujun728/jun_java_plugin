package com.ivy.email.strategy;

import com.ivy.email.vo.EmailVO;

/**
 * Created by fangjie04 on 2016/12/3.
 */
public interface MailStrategy {

    String message(EmailVO vo);
}
