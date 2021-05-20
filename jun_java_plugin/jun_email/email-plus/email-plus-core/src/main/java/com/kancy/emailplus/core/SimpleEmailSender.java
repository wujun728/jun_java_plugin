package com.kancy.emailplus.core;

import com.kancy.emailplus.core.cryptor.PasswordCryptor;
import com.kancy.emailplus.core.cryptor.SimplePasswordCryptor;
import com.kancy.emailplus.core.exception.EmailAuthenticationException;
import com.kancy.emailplus.core.exception.EmailException;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * SimpleEmailSender
 *
 * @author Wujun
 * @date 2020/2/19 23:11
 */
public class SimpleEmailSender extends AbstractEmailSender implements EmailSender {

    private String protocol = "smtp";

    private String host;

    private Integer port = 25;

    private String username;

    private String password;

    private PasswordCryptor passwordCryptor;

    public SimpleEmailSender() {

    }

    public SimpleEmailSender(PasswordCryptor passwordCryptor) {
        this.passwordCryptor = passwordCryptor;
    }

    @Override
    public void send(Email message) {
        // 正在通过当前传输发送消息
        Transport transport = null;
        try {
            transport = getTransport();
            MimeMessage mimeMessage = createMimeMessage(message);

            String messageId = mimeMessage.getMessageID();
            mimeMessage.saveChanges();
            if (Objects.nonNull(messageId)) {
                mimeMessage.setHeader("Message-ID", messageId);
            }else{
                mimeMessage.setHeader("Message-ID", message.getId());
            }
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } catch (EmailException ex){
            throw ex;
        } catch (Exception ex) {
            throw new EmailException("Email send failed", ex);
        } finally {
            try {
                if (Objects.nonNull(transport)) {
                    transport.close();
                }
            } catch (Exception ex) {
                throw new EmailException("Failed to close server connection after message sending", ex);
            }
        }
    }

    /**
     * 获取EmailSender一个名词标识
     *
     * @return
     */
    @Override
    public String getSenderName() {
        return getUsername();
    }

    /**
     * 发件人
     *
     * @return
     */
    @Override
    public String getFromEmailAddress() {
        return getUsername();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        PasswordCryptor passwordCryptor = getPasswordCryptor();
        if (Objects.nonNull(passwordCryptor)){
            return passwordCryptor.decrypt(password);
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private PasswordCryptor getPasswordCryptor() {
        if (Objects.isNull(passwordCryptor)){
            synchronized (this){
                if (Objects.isNull(passwordCryptor)){
                    passwordCryptor = new SimplePasswordCryptor();
                }
            }
        }
        return passwordCryptor;
    }

    private Transport getTransport() {
        Transport transport;
        try {
            transport = connectTransport();
        } catch (AuthenticationFailedException ex) {
            throw new EmailAuthenticationException("Mail authentication failed", ex);
        } catch (Exception ex) {
            throw new EmailException("Mail server connection failed", ex);
        }
        return transport;
    }

    /**
     * Obtain and connect a Transport from the underlying JavaMail Session,
     * passing in the specified host, port, username, and password.
     * @return the connected Transport object
     * @see #getTransport
     * @see #getHost()
     * @see #getPort()
     * @see #getUsername()
     * @see #getPassword()
     */
    protected Transport connectTransport() throws MessagingException {
        String username = getUsername();
        String password = getPassword();
        // probably from a placeholder
        if ("".equals(username)) {
            username = null;
            // in conjunction with "" username, this means no password to use
            if ("".equals(password)) {
                password = null;
            }
        }

        Transport transport = getTransport(getSession());
        transport.connect(getHost(), getPort(), username, password);
        return transport;
    }

    /**
     * Obtain a Transport object from the given JavaMail Session,
     * using the configured protocol.
     * <p>Can be overridden in subclasses, e.g. to return a mock Transport object.
     * @see Session#getTransport(String)
     * @see #getSession()
     * @see #getProtocol()
     */
    protected Transport getTransport(Session session) throws NoSuchProviderException {
        String protocol	= getProtocol();
        if (protocol == null) {
            protocol = session.getProperty("mail.transport.protocol");
            if (protocol == null) {
                protocol = "smtp";
            }
        }
        return session.getTransport(protocol);
    }
}
