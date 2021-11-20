# spring-email-master
spring sends email by three methods

## 介绍

    此项目基于Java配置的方式搭建，此种配置方式比XML配置方式更加强大，类型安全并且易于重构
    建议在开发中优先使用基于Java去配置，如本实例中的 EmailApplicationContext类

    Spring Email抽象核心接口MailSender，其实现类JavaMailSenderImpl,在其中配置邮件
    服务器host,pssword,协议等 。。。。。

    1.发送简单的消息
        SimpleMailMessage:发送简单的消息
    2.发送丰富的消息(比如带有附件，内连图片)
        MineMessage：发送带附件等消息，通过mailSender.createMimeMassage()创建实例
    3.使用模版(Velocity/ Thymeleaf)
        具体参照本实例中的代码

## 工具以及环境
    1 工具：Intellij Idea
    2 JDK：1.8
    3 Spring 4.3.4.release
    4 maven:3.x
    
## 项目结构

    src
         此项目是基于java的spring配置方式，下面介绍一个EmailApplicationContext这个配置类；
         EmailApplicationContext：实现的功能与<beans></beans>一样，定义方法返回实例，相当于xml中的<bean id="" class=""></bean>；

         config:主要是关于邮件配置，模板的配置，映射与properties文件；
         constant:定义一些常；
         sendertemplate:定义邮件发送模板服务；
         strategy:邮件发送策略
         type:邮件发送的类型，包括：简单的html，velocity模版， thymeleaf模版三种类型；
         vo:邮件信息的vo，包括，发件人，收件人，抄送人，密送人，邮件内容等；

    resource
         properties：资源文件
         velocity:velocity模版文件
         thymeleaf:thymeleaf模版文件
     test
          单元测试

## 邮件发送模板服务
public interface MailSenderTemplate {

    /**
     * 发送邮件
     *
     * @param vo
     */
    void sendMail(EmailVO vo) throws MessagingException;

    /**
     * 设置邮件发送策略
     *
     * @param strategy
     * @return
     */
    public MailSenderTemplateImpl setStrategy(MailStrategy strategy);
}

 邮件发送只定义了两个接口，一个是发送邮件接口，一个是指定邮件发送的策略

 邮件发送使用模版方法，减少大量的重复代码

 public void sendMail(EmailVO vo) throws MessagingException {

         MimeMessage mimeMessage = mailSender.createMimeMessage();

         // 第二个参数表示这个是mulipart类型的
         MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, Charsets.UTF_8.toString());
         boolean isHtmlText = true;

         if (this.strategy instanceof TextStrategy) {
             isHtmlText = false;
         }

         this.emailMessage(helper, vo);

         // true表示发送的是html消息
         helper.setText(this.sendContent(vo), isHtmlText);

         // 显示是内置图片等
         if (vo.getClassPathResource() != null && vo.getClassPathResource().length > 0) {
             for (ClassPathResource resource : vo.getClassPathResource()) {
                 String fileName = resource.getFilename();
                 helper.addInline(fileName.substring(0, fileName.lastIndexOf(".")), resource);
             }
         }

         // 附件
         if (vo.getAttachFile() != null && vo.getAttachFile().length > 0) {
             for (File file : vo.getAttachFile()) {
                 FileSystemResource resource = new FileSystemResource(file);
                 helper.addAttachment(file.getName(), resource);
             }
         }

         mailSender.send(mimeMessage);

     }

     private void emailMessage(MimeMessageHelper helper, EmailVO vo) throws MessagingException {
         if (vo.getCc().length > 0) {
             helper.setCc(vo.getCc());
         }
         if (vo.getBcc().length > 0) {
             helper.setBcc(vo.getBcc());
         }

         helper.setFrom(vo.getSender());
         helper.setTo(vo.getReceivers());
         helper.setSubject(vo.getSubject());
         helper.setSentDate(new Date());
     }

     private String sendContent(EmailVO vo) {
         return this.strategy.message(vo).toString();
     }

 发送策略，有TextStrategy,HtmlStrategy,VelocityStrategy,ThymeleafStrategy,都实现
 了MailStrategy接口，分别是发送简单文本，HTML类型，使用Velocity模版，使用Thymeleaf模板，

 public interface MailStrategy {

     String message(EmailVO vo);
 }

## 使用

 单元测试 MailSenderTempateImplTest

 例如：
    MailSenderTemplate template = context.getBean(MailSenderTemplateImpl.class);
    template.setStrategy(new TextStrategy()).sendMail(vo);