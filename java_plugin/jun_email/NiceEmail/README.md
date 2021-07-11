## NiceEmail - 3.0.0
支持各种功能的邮件发送库
## 特性
- [x] 简洁的邮件发送API
- [x] 支持自定义发件人昵称
- [x] 支持邮箱发送验证码
- [x] 支持发送HTML/附件
- [x] 支持异步发送
- [x] 自定义注解，更加简单方便
## 如何使用

maven坐标

    <dependency>
        <groupId>io.github.isliqian</groupId>
        <artifactId>NiceEmail</artifactId>
        <version>3.0.0</version>
    </dependency>

样例

          
           
               @Before
               public void before(){
                   // 配置，一次即可：邮箱与密码
                   NiceEmail.config(NiceEmail.SMTP_QQ(), "51103942@qq.com", "jtmoybnwknrnbjha");
               }
           
               /**
                * 测试发送文字
                * @throws MessagingException
                */
               @Test
               public void testSendText() throws MessagingException {
                   NiceEmail.subject("这是一封测试TEXT邮件")//主题
                           .from("LqNice")//发件人昵称
                           .to("???@qq.com")//收件人
                           .text("信件内容")//内容
                           .send();//发送
               }
           
               /**
                * 测试发送Html
                * @throws MessagingException
                */
               @Test
               public void testSendHtml() throws MessagingException {
                   NiceEmail.subject("这是一封测试HTML邮件")
                           .from("LqNice")
                           .to("51103942@qq.com")
                           //html内容即可
                           .html("<h1>信件内容</h1>")
                           .send();
               }
           
               /**
                * 测试附件邮件
                * @throws MessagingException
                */
               @Test
               public void testSendAttach() throws MessagingException {
                   NiceEmail.subject("这是一封测试附件邮件")
                           .from("LqNice")
                           .to("51103942@qq.com")
                           .html("<h1 font=red>信件内容</h1>")
                           //附近的路径，以及名称
                           .attach(new File("/Users/DELL/Pictures/Saved Pictures/000028.jpg"), "测试图片.jpeg")
                           //名称可以不设置
                           //.attach(new File("/Users/DELL/Pictures/Saved Pictures/000028.jpg"))
                           .send();
               }
               /**
                * 测试发送验证码
                * @throws MessagingException
                */
               @Test
               public void testSendVerifcationCode() throws MessagingException{
                   NiceEmail.subject("来自远方的验证码")
                             .from("LqNice")
                             .to("51103942@qq.com")
                              //验证码长度为6，如果类型为null,验证码类型为数字英文混合验证码
                             .verificationCode(6,null)
                             //支持纯英文验证码
                             //.verificationCode(4,verificationEnglishArrary)
                             //如果验证码位数不配置，则按照6位验证码发送
                             //.verificationCode(0,verificationNumberArrary)
                             .send();
           
               }
           
           
           
### 新特性 
自定义注解如何使用 **@AnnNiceConfig** + **@AnnNiceEmail** 
    
    
    @AnnNiceConfig(type = "SMTP_QQ",
         username = "51103942@qq.com",
         password = "jtmoybnwknrnbjha")
    public class TestAnnEmail {
 
     @Test
     @AnnNiceEmail(subject = "测试注解发送文本邮件",
             from = "LqNice",
             to="51103942@qq.com",
             text = "text Ann text")
     public void sendTextEmail() throws InvocationTargetException, IllegalAccessException, MessagingException {
         send(TestAnnEmail.class);
     }
 
 
 
     @Test
     @AnnNiceEmail(subject = "测试注解发送Html邮件",
             from = "LqNice",
             to="51103942@qq.com",
             html = "<h1>test Ann Html</h1>")
     public void sendHtmlEmail() throws IllegalAccessException, MessagingException, InvocationTargetException {
        send(TestAnnEmail.class);
 
     }
    }
###注意
在使用验证码的时候，如果第一次输入错误，下次发送会将位数加倍。。。。                 
###[个人博客](www.imqian.top)
作者 51103942@qq.com             
          

    
