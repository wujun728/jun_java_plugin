package com.kancy.emailplus.core;

import com.kancy.emailplus.core.cryptor.PasswordCryptor;
import com.kancy.emailplus.core.cryptor.SimplePasswordCryptor;
import com.kancy.emailplus.core.exception.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * PooledEmailSender
 *
 * @author Wujun
 * @date 2020/2/20 2:50
 */
public class PooledEmailSender extends AbstractEmailSender implements EmailSenderRefreshCapable, EmailSender {
    private static final Logger log = LoggerFactory.getLogger(PooledEmailSender.class);

    private static Map<String, Integer> sendFailCountMap = new HashMap<>();
    private static ThreadLocal<EmailSender> emailSenderThreadLocal = new ThreadLocal<>();

    private final EmailSenderSelector emailSenderSelector;
    private final PasswordCryptor passwordCryptor;


    public PooledEmailSender() {
        this(new PollingEmailSenderSelector(), new SimplePasswordCryptor());
    }

    public PooledEmailSender(EmailSenderSelector emailSenderSelector) {
        this(emailSenderSelector, new SimplePasswordCryptor());
    }
    public PooledEmailSender(EmailSenderSelector emailSenderSelector, PasswordCryptor passwordCryptor) {
        this.emailSenderSelector = emailSenderSelector;
        this.passwordCryptor = passwordCryptor;
        init();
    }

    /**
     * EmailSender
     */
    private List<EmailSender> emailSenders = new ArrayList<>();

    /**
     * 刷新
     */
    @Override
    public final void refresh(){
        init();
        log.info("{} refresh finished.", getClass().getName());
    }

    /**
     * 加载邮件属性数据源
     */
    private void init() {
        // Spi机制加载数据来源
        SpiEmailSenderPropertiesDataSource spiEmailPropertiesDataSource = new SpiEmailSenderPropertiesDataSource();
        List<EmailSenderProperties> emailSenderPropertiesList = spiEmailPropertiesDataSource.load();
        // 排序
        Collections.sort(emailSenderPropertiesList);
        // 初始化
        initEmailSender(emailSenderPropertiesList);
    }

    /**
     * 初始化EmailSender
     */
    private void initEmailSender(List<EmailSenderProperties> emailSenderPropertiesList) {
        List<EmailSender> emailSenderList = new ArrayList<>();
        for (EmailSenderProperties properties : emailSenderPropertiesList) {
            if (properties.isEnable()) {
                SimpleEmailSender mailSender = new SimpleEmailSender(this.passwordCryptor);
                mailSender.setEncoding(properties.getEncoding());
                mailSender.setHost(properties.getHost());
                mailSender.setPort(properties.getPort());
                mailSender.setProtocol(properties.getProtocol());
                mailSender.setUsername(properties.getUsername());
                mailSender.setPassword(getRealPassword(properties.getPassword()));
                mailSender.getJavaMailProperties().putAll(properties.getProperties());
                emailSenderList.add(mailSender);
                String mailSenderName = Optional.ofNullable(properties.getName()).orElse(properties.getUsername());
                log.info("EmailSender [{}] init success.", mailSenderName);
            }
        }

        synchronized (this.emailSenders){
            this.emailSenders.clear();
            this.emailSenders.addAll(emailSenderList);
        }

        if (this.emailSenders.isEmpty()){
            log.warn("Not EmailSender available");
        }
    }

    /**
     * 发送邮件
     *
     * @param message
     * @throws EmailException
     */
    @Override
    public void send(Email message) {
        assert Objects.nonNull(message) : "email message is null";
        if (!emailSenders.isEmpty()){
            EmailSender emailSender = getCurrentEmailSender();
            try {
                log.info("Send email message [{}] by emailSender : {}", message.getId(), emailSender.getSenderName());
                emailSender.send(message);
            } catch (EmailException e) {
                String messageId = message.getId();
                Integer retry = sendFailCountMap.getOrDefault(messageId, 0);
                retry++;
                // 重试次数大于等于EmailSender个数时停止重试
                if(retry < emailSenders.size()){
                    log.warn("Sender [{}] send email message [{}] fail : {} , retry is {}", emailSender.getSenderName(), messageId, e.getMessage(), retry);
                    sendFailCountMap.put(messageId, retry);
                    removeCurrentEmailSender();
                    send(message);
                    sendFailCountMap.remove(messageId);
                    return;
                }
                sendFailCountMap.remove(messageId);
                throw e;
            } finally {
                removeCurrentEmailSender();
            }

        }else {
            throw new EmailException(String.format("Send email message [%s] fail : Not EmailSender available", message.getId()));
        }
    }

    /**
     * 发件人邮箱地址
     *
     * @return
     */
    @Override
    protected String getFromEmailAddress() {
        EmailSender currentEmailSender = getCurrentEmailSender();
        if (Objects.nonNull(currentEmailSender) && currentEmailSender instanceof SimpleEmailSender){
            return ((SimpleEmailSender) currentEmailSender).getFromEmailAddress();
        }
       throw new UnsupportedOperationException();
    }

    /**
     * 获取EmailSender一个名词标识
     *
     * @return
     */
    @Override
    public String getSenderName() {
        EmailSender currentEmailSender = getCurrentEmailSender();
        if (Objects.nonNull(currentEmailSender)){
            return currentEmailSender.getSenderName();
        }
        return null;
    }

    public EmailSender getCurrentEmailSender() {
        EmailSender emailSender = emailSenderThreadLocal.get();
        if (Objects.isNull(emailSender)){
            emailSender = emailSenderSelector.findEmailSender(emailSenders);
            setCurrentEmailSender(emailSender);
        }
        return emailSender;
    }

    private void removeCurrentEmailSender() {
        emailSenderThreadLocal.remove();
    }

    private void setCurrentEmailSender(EmailSender emailSender) {
        emailSenderThreadLocal.set(emailSender);
    }

    private String getRealPassword(String password) {
        if (Objects.nonNull(this.passwordCryptor)){
            return this.passwordCryptor.decrypt(password);
        }
        return password;
    }

}
