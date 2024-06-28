package io.github.wujun728.sms.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20210111.SmsClient;
// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20210111.models.AddSmsTemplateRequest;
import com.tencentcloudapi.sms.v20210111.models.AddSmsTemplateResponse;
/**
* Tencent Cloud Sms AddSmsTemplate
*
*/
public class AddSmsTemplate
{
  public static void main( String[] args )
  {
      try {
          /* 必要步骤：
           * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
           * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
           * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
           * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
           */
          Credential cred = new Credential("secretId", "secretKey");
           // 实例化一个 http 选项，可选，无特殊需求时可以跳过
          HttpProfile httpProfile = new HttpProfile();
          // 设置代理
          // httpProfile.setProxyHost("真实代理ip");
          // httpProfile.setProxyPort(真实代理端口);
          /* SDK 默认使用 POST 方法
           * 如需使用 GET 方法，可以在此处设置，但 GET 方法无法处理较大的请求 */
          httpProfile.setReqMethod("POST");
          /* SDK 有默认的超时时间，非必要请不要进行调整
           * 如有需要请在代码中查阅以获取最新的默认值 */
          httpProfile.setConnTimeout(60);
          /* SDK 会自动指定域名，通常无需指定域名，但访问金融区的服务时必须手动指定域名
           * 例如 SMS 的上海金融区域名为 sms.ap-shanghai-fsi.tencentcloudapi.com */
          httpProfile.setEndpoint("sms.tencentcloudapi.com");
           /* 非必要步骤:
           * 实例化一个客户端配置对象，可以指定超时时间等配置 */
          ClientProfile clientProfile = new ClientProfile();
          /* SDK 默认使用 TC3-HMAC-SHA256 进行签名
           * 非必要请不要修改该字段 */
          clientProfile.setSignMethod("HmacSHA256");
          clientProfile.setHttpProfile(httpProfile);
          /* 实例化 SMS 的 client 对象
           * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
          SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
          /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
           * 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
           * 属性可能是基本类型，也可能引用了另一个数据结构
           * 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明 */
          AddSmsTemplateRequest req = new AddSmsTemplateRequest();
           /* 填充请求参数，这里 request 对象的成员变量即对应接口的入参
           * 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
           * 基本类型的设置:
           * 帮助链接：
           * 短信控制台：https://console.cloud.tencent.com/smsv2
           * sms helper：https://cloud.tencent.com/document/product/382/3773 */
           /* 模板名称*/
          String templatename = "腾讯云";
          req.setTemplateName(templatename);
           /* 模板内容 */
          String templatecontent = "{1}为您的登录验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。";
          req.setTemplateContent(templatecontent);
           /* 短信类型：0表示普通短信, 1表示营销短信 */
          long smstype = 0;
          req.setSmsType(smstype);
           /* 是否国际/港澳台短信：0：表示国内短信，1：表示国际/港澳台短信。 */
          long international = 0;
          req.setInternational(international);
           /* 模板备注：例如申请原因，使用场景等 */
          String remark = "xxx";
          req.setRemark(remark);
           /* 通过 client 对象调用 AddSmsTemplate 方法发起请求。注意请求方法名与请求对象是对应的
           * 返回的 res 是一个 AddSmsTemplateResponse 类的实例，与请求对象对应 */
          AddSmsTemplateResponse res = client.AddSmsTemplate(req);
           // 输出 JSON 格式的字符串回包
          System.out.println(AddSmsTemplateResponse.toJsonString(res));
           // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
          System.out.println(res.getRequestId());
       } catch (TencentCloudSDKException e) {
          e.printStackTrace();
      }
  }
}
