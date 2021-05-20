package com.kancy.emailplus.spring.boot.listener.event;

import com.kancy.emailplus.spring.boot.message.EmailMessage;
import org.springframework.context.ApplicationEvent;

/**
 * EmailplusMessage
 *
 * @author Wujun
 * @date 2020/2/20 23:33
 */
public class SendEmailEvent extends ApplicationEvent {

    private EmailMessage emailMessage;

    private boolean async;

    private SendEmailEvent(Object source) {
        super(source);
    }

    public SendEmailEvent(EmailMessage emailMessage) {
        this(emailMessage, emailMessage);
    }

    public SendEmailEvent(Object source, EmailMessage emailMessage) {
        this(source);
        this.emailMessage = emailMessage;
        this.async = emailMessage.isAsync();
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public EmailMessage getEmailMessage() {
        return emailMessage;
    }
}
