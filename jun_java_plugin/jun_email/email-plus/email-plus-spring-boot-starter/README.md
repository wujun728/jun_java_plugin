# email-plus

邮件增强框架

## email-plus-spring-boot-starter

邮件增强框架SpringBoot启动器

```xml
<dependency>
    <groupId>com.kancy</groupId>
    <artifactId>email-plus-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```

### 主要功能

1. 邮件定义配置化（通过emailKey唯一标识一封邮件）
    
    - 邮件定义（收件人，抄送人，主题，内容，附件等）
    - 异步、同步发送
    - 模板化支持
    
2. 内置freemarker邮件模板化
3. 邮件通知定义配置化（通过`@EmailNotice`注解AOP拦截）
   
   - 令牌桶（本地内存，redis）
   - 轮询计数器（本地内存，redis）
   
4. 内置Endpoint配置刷新端点

    - `/actuator/refreshEmailSender`

### 使用方式

#### 配置

```yml

emailplus:
  # 定义一些邮件发送者，注入到邮件池
  sender:
    name01:
      host: smtp.qq.com
      port: 25
      username: aaa@qq.com
      password: ENC(0Xn6xLVRo/JnOaY9e9RwtcFf2mKIw5tmB9W67pVCOR0=)
    name02:
      host: smtp.qq.com
      port: 25
      username: bbb@qq.com
      password: ENC(0Xn6xLVRo/JnOaY9e9RwtcFf2mKIw5tmB9W67pVCOR0=)
    name03:
      host: smtp.qq.com
      port: 25
      username: ccc@qq.com
      password: ENC(0Xn6xLVRo/JnOaY9e9RwtcFf2mKIw5tmB9W67pVCOR0=)
  #定义一些邮件
  email-definitions:
    test-simple-email:
      to: xxx@qq.com
      subject: 测试
      content: "ok!"
      async: true
    test-email-notice:
      subject: 测试邮件异常通知
      to: xxx@qq.com,xxx@gmail.com
      cc: xxx@163.com
      template: error-notice-email-template.ftl
  #定义邮件通知
  email-notices:
    test-email-notice:
      capacity: 3
      refillTokens: 2
      refillDuration: PT5S
      trigger: redisBucketEmailNoticeTrigger
      email-key: test-email-notice
      async: true
```

#### 发送邮件

```java
public class EmailplusServiceTest {
    @Autowired
    private EmailplusService emailplusService;

    /**
     * 发送邮件
     */
    @Test
    public void sendSimpleEmail01() {
        emailplusService.sendSimpleEmail("test-simple-email");
    }
    /**
     * 发送邮件
     */
    @Test
    public void sendSimpleEmail02() {
        emailplusService.sendSimpleEmail("test-simple-email", "custom email contents");
    }

}
```


#### 邮件通知

```java
/**
* `@EmailNotice`监控getUser()方法执行错误信息
*/
@Service
public class UserServiceImpl implements UserService {
    
    /**
    * 当getUser()方法执行错误的次数达到配置的上线，就会进行邮件通知
    */
    @EmailNotice("test-email-notice")
    @Override
    public String getUser() {
        throw new UnsupportedOperationException("测试异常");
    }
}
```