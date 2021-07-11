package io.github.isliqian;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;



/**
 * Created by LiQian_Nice on 2018/3/13
 */
public class NiceEmail {

    private static Session session;
    private static String  user;
    private static Timer timer;
    private MimeMessage msg;
    private String  text;
    private String html;
    private static String type;
    private static String username;
    private static String password;
    private static String code;

    private List<MimeBodyPart> attachments = new ArrayList<MimeBodyPart>();



    private NiceEmail(){

    }

    public static Properties defaultConfig() {
        //1.创建连接对象，连接到邮箱服务器
        Properties props=new Properties();
        props.setProperty("mail.smtp.auth", "true");// 打开认证
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.port", "465");
        return props;
    }

    /**
     * smtp entnterprise qq
     *
     * @return
     */
    public static Properties SMTP_ENT_QQ() {
        Properties props = defaultConfig();
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        return props;
    }

    /**
     * smtp qq
     *
     * @return
     */
    public static Properties SMTP_QQ()  {
        Properties props = defaultConfig();
        props.put("mail.smtp.host", "smtp.qq.com");
        return props;
    }

    /**
     * smtp 163
     *
     * @return
     */
    public static Properties SMTP_163() {
        Properties props = defaultConfig();
        props.put("mail.smtp.host", "smtp.163.com");
        return props;
    }
    public static NiceEmail inUse(Class c) throws InvocationTargetException, IllegalAccessException {
        NiceEmail niceEmail=new NiceEmail();
        AnnNiceConfig config= (AnnNiceConfig) c.getAnnotation(AnnNiceConfig.class);
        System.out.println(config);
        for (Method method : config.annotationType().getDeclaredMethods()) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            Object invoke = method.invoke(config);
            if ("type".equals(method.getName())) {
                type = (String) invoke;
            }else if ("username".equals(method.getName())){
                username=(String)invoke;
            }else if ("password".equals(method.getName())){
                password=(String)invoke;
            }
            System.out.println("invoke methd " + method.getName() + " result:" + invoke);
            /**
             * 判断多个Class使用自定义注解
             */
            if (invoke.getClass().isArray()) {
                Object[] temp = (Object[]) invoke;
                for (Object o : temp) {
                    System.out.println(o);
                }
            }
        }
        if (type.equals("SMTP_QQ")){
            config(NiceEmail.SMTP_QQ(),username,password);
        }
        return niceEmail;

    }

    /**
     * 解析自定义注解方法体
     * @param c1
     */
    public static void send( Class<?> c1) throws InvocationTargetException, IllegalAccessException, MessagingException {
        inUse(c1);
        for(Method m:c1.getDeclaredMethods()){
            //  getDeclaredMethods    including public, protected, default (package) access, and private methods, but excluding inherited methods.
            AnnNiceEmail uc=m.getAnnotation(AnnNiceEmail.class);
            if(uc !=null){
                System.out.println("Found Use Case:from= "+uc.from()+"subject="+uc.subject()+"to="+uc.to()+"html="+uc.html()+"text="+uc.text());
                if (uc.html()==null||uc.html().equals("")){
                    NiceEmail.subject(uc.subject())
                            .from(uc.from())
                            .to(uc.to())
                            .text(uc.text())
                            .send();
                }else {
                    NiceEmail.subject(uc.subject())
                            .from(uc.from())
                            .to(uc.to())
                            .html(uc.html())
                            .send();
                }


            }
        }
    }
    /**
     * config username and password
     *
     * @param props    email property config
     * @param username email auth username
     * @param password email auth password
     */
    public static void config(Properties props, final String username, final String password) {
        user=username;
        session = Session.getDefaultInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); // 发件人邮箱账号、授权码
            }
        });
    }

    /**
     * set email subject
     *
     * @param subject subject title
     * @return
     * @throws MessagingException
     */
    public static NiceEmail subject(String subject) throws MessagingException {
        NiceEmail niceEmail = new NiceEmail();
        niceEmail.msg = new MimeMessage(session);
        niceEmail.msg.setSubject(subject);
        return niceEmail;
    }

    /**
     * set email from
     *
     * @param nickName from nickname
     * @return
     * @throws MessagingException
     */
    public  NiceEmail from(String nickName) throws MessagingException {
        return from(nickName, user);
    }

    /**
     * set email nickname and from user
     *
     * @param nickName
     * @param from
     * @return
     * @throws MessagingException
     */
    public NiceEmail from(String nickName, String from) throws MessagingException {
        try {
            nickName = MimeUtility.encodeText(nickName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.setFrom(new InternetAddress(nickName + " <" + from + ">"));
        return this;
    }

    public NiceEmail to(String to) throws MessagingException {
        return addRecipient(to, Message.RecipientType.TO);
    }

    private NiceEmail addRecipient(String recipient, Message.RecipientType type) throws MessagingException {
        msg.setRecipients(type, InternetAddress.parse(recipient.replace(";", ",")));
        return this;
    }

    public NiceEmail text(String text) {
        this.text = text;
        return this;
    }

    public NiceEmail html(String html) {
        this.html = html;
        return this;
    }



    public NiceEmail verificationCode(int size,String[] type)  {
        /*this.code=null;*/
        this.code = VerificationCode.code(size,type);
        this.text="您的验证码为:"+ code;
        return this;

    }

    public NiceEmail attach(File file) throws MessagingException {
        attachments.add(createAttachment(file, null));
        return this;
    }

    public NiceEmail attach(File file, String fileName) throws MessagingException {
        attachments.add(createAttachment(file, fileName));
        return this;
    }

    private MimeBodyPart createAttachment(File file, String fileName) throws MessagingException {
        MimeBodyPart   attachmentPart = new MimeBodyPart();
        FileDataSource fds            = new FileDataSource(file);
        attachmentPart.setDataHandler(new DataHandler(fds));
        try {
            attachmentPart.setFileName(null == fileName ? MimeUtility.encodeText(fds.getName()) : MimeUtility.encodeText(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attachmentPart;
    }

    public void send() throws MessagingException {
        if (text == null&& html == null)
            throw new NullPointerException("At least one context has to be provided: Text");
        MimeMultipart cover;
        boolean       usingAlternative = false;
        boolean       hasAttachments   = attachments.size() > 0;
        if (text != null && html == null) {
            // TEXT ONLY
            cover = new MimeMultipart("mixed");
            cover.addBodyPart(textPart());
        }else if (text == null && html != null) {
            // HTML ONLY
            cover = new MimeMultipart("mixed");
            cover.addBodyPart(htmlPart());
        }else {
            // HTML + TEXT
            cover = new MimeMultipart("alternative");
            cover.addBodyPart(textPart());
            cover.addBodyPart(htmlPart());
            usingAlternative = true;
        }
        MimeMultipart content = cover;
        if (usingAlternative && hasAttachments) {
            content = new MimeMultipart("mixed");
            content.addBodyPart(toBodyPart(cover));
        }

        for (MimeBodyPart attachment : attachments) {
            content.addBodyPart(attachment);
        }
        msg.setSentDate(new Date());
        msg.setContent(cover);
        Transport.send(msg);
    }

    private MimeBodyPart toBodyPart(MimeMultipart cover) throws MessagingException {
        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(cover);
        return wrap;
    }

    private MimeBodyPart textPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(text);
        return bodyPart;
    }

    private MimeBodyPart htmlPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(html, "text/html; charset=utf-8");
        return bodyPart;
    }



}
