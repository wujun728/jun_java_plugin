package com.kancy.emailplus.core;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询策略
 * PollingEmailSenderSelector
 *
 * @author kancy
 * @date 2020/2/20 3:11
 */
public class PollingEmailSenderSelector implements EmailSenderSelector {
    private static AtomicInteger atomic = new AtomicInteger(0);
    /**
     * 找到一个可用的EmailSender
     *
     * @param emailSenders
     * @return
     */
    @Override
    public EmailSender findEmailSender(List<EmailSender> emailSenders) {
        return emailSenders.get(atomic.getAndIncrement() % emailSenders.size());
    }
}
