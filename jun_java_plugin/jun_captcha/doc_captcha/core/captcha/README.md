# 2 对接流程
## 2.1 接入流程
### 2.1.1 后端接入
 用户提交表单会携带验证码相关参数，后台需要调用captchaService.verification做二次校验，以确保验证数据的正确有效。
### 2.1.2 前端接入
 引入相关组件，调用初始化函数，通过配置的一些参数信息。将行为验证码渲染出来。
## 2.2 后端接入
### 2.2.1 SpringBoot项目，参考示例：service\springboot。
a.引入jar，已上传至maven中央仓库。
```java
<dependency>
   <groupId>com.github.anji-plus</groupId>
   <artifactId>captcha</artifactId>
   <version>1.1.8</version>
</dependency>
```
b.修改application.properties，自定义底图和水印，启动后前端就可以请求接口了。[社区底图库](https://gitee.com/anji-plus/AJ-Captcha-Images)
```properties
....
#滑动验证，底图路径，不配置将使用默认图片
#captcha.captchaOriginalPath.jigsaw=/app/product/dist/captchabg
#滑动验证，底图路径，不配置将使用默认图片
#captcha.captchaOriginalPath.pic-click=/app/product/dist/captchabg

#汉字统一使用Unicode,保证程序通过@value读取到是中文，可通过这个在线转换 https://tool.chinaz.com/tools/unicode.aspx 中文转Unicode
#右下角水印文字(我的水印)
captcha.water.mark=\u6211\u7684\u6c34\u5370
#右下角水印字体(宋体)
captcha.water.font=\u5b8b\u4f53
#点选文字验证码的文字字体(宋体)
captcha.font.type=\u5b8b\u4f53
#校验滑动拼图允许误差偏移量(默认5像素)
captcha.slip.offset=5
#aes.key(16位，和前端加密保持一致)
#captcha.aes.key=XwKsGlMcdPMEhR1B
```
c.`非常重要`。对于分布式多实例部署的应用，应用必须自己实现CaptchaCacheService，比如用Redis或者memcache，参考service/springboot/src/.../CaptchaCacheServiceRedisImpl.java<br>

### 2.2.2 后端二次校验接口
以登录为例，用户在提交表单到后台，会携带一个验证码相关的参数。后端登录接口login，首先调用CaptchaService.verification做二次校验。
```java
@Autowired
@Lazy
private CaptchaService captchaService;

@PostMapping("/login")
public ResponseModel get(@RequestBody CaptchaVO captchaVO) {
    ResponseModel response = captchaService.verification(captchaVO);
    if(response.isSuccess() == false){
        //验证码校验失败，返回信息告诉前端
        //repCode  0000  无异常，代表成功
        //repCode  9999  服务器内部异常
        //repCode  0011  参数不能为空
        //repCode  6110  验证码已失效，请重新获取
        //repCode  6111  验证失败
        //repCode  6112  获取验证码失败,请联系管理员
    }
    return response;
}
```
### 2.2.3 后端接口
#### 获取验证码接口：http://*:*/captcha/get
##### 请求参数：
```json
{
	"captchaType": "blockPuzzle"  //验证码类型 clickWord
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "originalImageBase64": "底图base64",
        "point": {    //默认不返回的，校验的就是该坐标信息，允许误差范围
            "x": 205,
            "y": 5
        },
        "jigsawImageBase64": "滑块图base64",
        "token": "71dd26999e314f9abb0c635336976635", //一次校验唯一标识
        "result": false,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```
#### 核对验证码接口接口：http://*:*/captcha/check
##### 请求参数：
```json
{
	 "captchaType": "blockPuzzle",
	 "pointJson": "QxIVdlJoWUi04iM+65hTow==",  //aes加密坐标信息
	 "token": "71dd26999e314f9abb0c635336976635"  //get请求返回的token
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "captchaType": "blockPuzzle",
        "token": "71dd26999e314f9abb0c635336976635",
        "result": true,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```

### 2.2.4 防刷功能
    a.同一用户，登录错误3次才要求验证码，考虑是登录模块的功能。
    b.同一用户，限制请求验证码，5分钟不能超过100次等。
    以上功能，我们会在service/springboot示例代码中提供额外的参考代码，不集成在jar中。
### 2.2.5 SpringMVC项目
```
示例：仓库service\springmvc。考虑部分老项目，还是非springboot的，我们提供spring mvc的项目示例代码。
     主要是配置redisTemplate和包扫描。整理中...
```

# 3  Q & A
## 3.1 linux部署注意事项点选文字
### 3.1.1 字体乱码问题
点选文字中所用字体默认为宋体，linux不支持该字体，所以可能会出现以下图中情况，如图3-1所示。

![字体错误](https://mirror.anji-plus.com/captcha-web/static/font-error.png "字体错误")
 
图3-1  点选文字字体乱码
### 3.1.2 乱码解决方案
宋体黑体为例
#### 1、安装字体库
在CentOS 4.x开始用fontconfig来安装字体库，所以输入以下命令即可：
```shell
sudo yum -y install fontconfig
```
这时在/usr/shared目录就可以看到fonts和fontconfig目录了（之前是没有的）：
接下来就可以给我们的字体库中添加中文字体了。
#### 2、首先在/usr/shared/fonts目录下新建一个目录chinese：
CentOS中，字体库的存放位置正是上图中看到的fonts目录，所以我们首先要做的就是找到中文字体文件放到该目录下，而中文字体文件在我们的windows系统中就可以找到，打开c盘下的Windows/Fonts目录：
#### 3、紧接着需要修改chinese目录的权限：
```shell
sudo chmod -R 755 /usr/share/fonts/chinese
```
接下来需要安装ttmkfdir来搜索目录中所有的字体信息，并汇总生成fonts.scale文件，输入命令：
```shell
sudo yum -y install ttmkfdir
```
然后执行ttmkfdir命令即可：
```shell
ttmkfdir -e /usr/share/X11/fonts/encodings/encodings.dir
```
#### 4、最后一步就是修改字体配置文件了，首先通过编辑器打开配置文件 ：
```shell
vim /etc/fonts/fonts.conf
```
可以看到一个Font list，即字体列表，在这里需要把我们添加的中文字体位置加进去：
```shell
/usr/share/fonts/chinese
```
#### 5、然后输入:wq保存退出，最后别忘了刷新内存中的字体缓存，这样就不用reboot重启了：
```shell
fc-cache
```
#### 6、这样所有的步骤就算完成了，最后再次通过fc-list看一下字体列表：
```shell
fc-list
```