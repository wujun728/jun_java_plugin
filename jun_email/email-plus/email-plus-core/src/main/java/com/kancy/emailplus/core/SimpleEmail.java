package com.kancy.emailplus.core;


import java.io.Serializable;

/**
 * SimpleEmail
 *
 * @author Wujun
 * @date 2020/2/19 23:09
 */
public class SimpleEmail extends AbstractEmail implements Email, Serializable {

    /**
     * 默认主题：系统通知
     */
    private static final String DEFAULT_SUBJECT = "\u7CFB\u7EDF\u901A\u77E5";

    private String[] to;
    private String[] cc;
    private String subject = DEFAULT_SUBJECT;
    private String content;

    public SimpleEmail() {
        super();
    }

    public SimpleEmail(String to, String content) {
        this(to, DEFAULT_SUBJECT, content, false);
    }

    public SimpleEmail(String to, String subject, String content) {
        this(to, subject, content, false);
    }

    public SimpleEmail(String to, String subject, String content, boolean html) {
        this();
        this.to = new String[]{to};
        this.subject = subject;
        this.content = content;
        setHtml(html);
    }

    /**
     * 收件人
     *
     * @return
     */
    @Override
    public String[] getTo() {
        return to;
    }

    /**
     * 抄送
     *
     * @return
     */
    @Override
    public String[] getCc() {
        return cc;
    }

    /**
     * 主题
     *
     * @return
     */
    @Override
    public String getSubject() {
        return subject;
    }

    /**
     * 邮件内容
     */
    @Override
    public String getContent() {
        return content;
    }


    public void setTo(String ... to) {
        this.to = to;
    }

    public void setCc(String ... cc) {
        this.cc = cc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
