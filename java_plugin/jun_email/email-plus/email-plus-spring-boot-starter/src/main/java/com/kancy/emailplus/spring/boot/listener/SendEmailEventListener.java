package com.kancy.emailplus.spring.boot.listener;

import com.kancy.emailplus.core.EmailSender;
import com.kancy.emailplus.core.exception.EmailException;
import com.kancy.emailplus.spring.boot.listener.event.SendEmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;

import java.util.Objects;

/**
 * SendEmailEventListener
 *
 * @author Wujun
 * @date 2020/2/20 22:57
 */
public class SendEmailEventListener implements ApplicationListener<SendEmailEvent> {

    private final Logger log = LoggerFactory.getLogger(SendEmailEventListener.class);

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(SendEmailEvent sendEmailMessageEvent) {
        if (sendEmailMessageEvent.isAsync()){
            asyncSendMailMessage(sendEmailMessageEvent);
        }else{
            syncSendMailMessage(sendEmailMessageEvent);
        }
    }

    /**
     * 异步发送
     * @param message
     */
    private void asyncSendMailMessage(SendEmailEvent message) {
        TaskExecutor taskExecutor = findTaskExecutor();
        if (Objects.nonNull(taskExecutor)){
                taskExecutor.execute(() -> syncSendMailMessage(message));
        }else {
            syncSendMailMessage(message);
        }
    }

    /**
     * 同步发送
     * @param event
     */
    private void syncSendMailMessage(SendEmailEvent event) {

        try {
            emailSender.send(event.getEmailMessage());
            log.info("Send email message [{},{}] completed, result is : {}",
                    event.getEmailMessage().getEmailKey(), event.getEmailMessage().getId() ,true);
        } catch (EmailException e) {
            log.warn("Send email message [{},{}] completed, result is : {} , {}",
                    event.getEmailMessage().getEmailKey(), event.getEmailMessage().getId() , false, e.getMessage());
            throw e;
        }
    }

    private TaskExecutor findTaskExecutor() {
        TaskExecutor taskExecutor = null;
        try {
            taskExecutor = applicationContext.getBean("asyncTaskExecutor", TaskExecutor.class);
        } catch (Exception e) {
            try {
                taskExecutor = applicationContext.getBean("taskExecutor", TaskExecutor.class);
            } catch (BeansException ex) {
                log.warn("Application not found taskExecutor");
            }
        }
        return taskExecutor;
    }
}
