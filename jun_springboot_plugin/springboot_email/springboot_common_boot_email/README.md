# common-boot-email

#### 项目介绍
SpringBoot发送邮件神器，只需简单配置即可，支持自定义模板。

#### 软件架构
SpringBoot+spring-boot-starter-mail +spring-boot-starter-thymeleaf 


#### 安装教程

1. mvn install 打包到本地仓库，然后引入gav坐标。
2. 后期会制作boot-starter。

#### 使用说明

1. text普通文本发送（MailContentTypeEnum.TEXT）

   ```java
   @Autowired
   private MailBiz mailBiz;
   
   public void sendMail() {
   	mailBiz
           .contentType(MailContentTypeEnum.TEXT)
           .title("测试")
           .to("1104399635@qq.com")
           .content("测试content")
           .send();
   }
   ```

2. html格式发送（MailContentTypeEnum.HTML）

   ```java
   @Autowired
   private MailBiz mailBiz;
   
   public void sendMail() {
   	mailBiz
           .contentType(MailContentTypeEnum.HTML)
           .title("测试")
           .to("1104399635@qq.com")
           .content("<h1>测试content</h1>")
           .send();
   }
   ```

3. 自定义模板发送（MailContentTypeEnum.TEMPLATE）

   ```java
   @Autowired
   private MailBiz mailBiz;
   
   public void sendMail() {
   	Map<String, Object> params = new HashMap<>();
   	params.put("href", "http://www.baidu.com");
   	mailBiz
           .contentType(MailContentTypeEnum.TEMPLATE)
           .title("测试")
           .to("1104399635@qq.com")
           .content("测试content")
           .templateName("regist-template")
           .maps(params)
           .send();
   }
   
   /**
    * @Description: 模板名称枚举
    *
    * @author TongWei.Chen 2018-6-19 10:54:35
    * @Project tucaole
    */
   public enum EmailTemplateEnum {
       /**
        * 注册模板
        */
       REGIST_TEMPLATE("regist-template")
       ;
   
       EmailTemplateEnum(String value) {
           this.value = value;
       }
   
       private String value;
   
       public String value() {
           return value;
       }
   }
   ```

   ```
   我有个xxx-common-email模块，将所有email模板都放到了resources/templates/里。
   ```