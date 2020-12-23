# 2 对接流程
## 2.1 接入流程
### 2.1.1 后端接入
 用户提交表单会携带验证码相关参数，产品应用在相关接口处将该参数传给 集成jar包相关接口做二次校验，以确保该次验证是正确有效的。
### 2.1.2 前端接入
 引入相关组件，调用初始化函数，通过配置的一些参数信息。将行为验证码渲染出来。
## 2.2 后端接入
### 2.2.1 引入maven依赖
目前已上传maven仓库，源码已分享
```java
<dependency>
   <groupId>com.github.anji-plus</groupId>
   <artifactId>captcha</artifactId>
   <version>1.1.8</version>
</dependency>
```
### 2.2.2 启动类上添加相应注解
```java
@ComponentScan(basePackages = {
      "com.anji.captcha",
      "产品自身对应的包路径…"
})
```

### 2.2.3 二次校验接口
登录为例，用户在提交表单到产品应用后台，会携带一个验证码相关的参数。产品应用会在登录接口login中将该参数传给集成jar包中相关接口做二次校验。
接口地址：https://****/captcha/verify
### 2.2.4 请求方式
HTTP POST, 接口仅支持POST请求, 且仅接受 application/json 编码的参数
### 2.2.5 请求参数
| 参数  |  类型 | 必填  |  备注 |
| ------------ | ------------ | ------------ | ------------ |
| captchaVerification  | String  |  Y | 验证数据，aes加密，数据在前端success函数回调参数中获取  |


### 2.2.6 响应参数
| 参数  |  类型 | 必填  |  备注 |
| ------------ | ------------ | ------------ | ------------ |
| repCode  | String  | Y  | 异常代号  |
| success  | Boolean  |  Y | 成功或者失败  |
| error  | Boolean  | Y  | 接口报错  |
| repMsg  | String  | Y  | 错误信息  |


### 2.2.7 异常代号

| error  |  说明 |
| ------------ | ------------ |
|  0000 |  无异常，代表成功 |
| 9999  | 服务器内部异常  |
|  0011 | 参数不能为空  |
| 6110  | 验证码已失效，请重新获取  |
| 6111  | 验证失败  |
| 6112  | 获取验证码失败,请联系管理员  |

## 2.3 前端接入
### 2.3.1 兼容性
IE8+、Chrome、Firefox.(其他未测试)
### 2.3.2 初始化组件
引入前端vue组件, npm install axios    crypto-js   -S
// 基础用例

```javascript
<template>
	<Verify
		@success="'success'" //验证成功的回调函数
		:mode="'fixed'"     //调用的模式
		:captchaType="'blockPuzzle'"    //调用的类型 点选或者滑动   
		:imgSize="{ width: '330px', height: '155px' }"//图片的大小对象
	></Verify
</template>

<script>
//引入组件
import Verify from "./../../components/verifition/Verify";
export default {
	name: 'app',
	components: {
		Verify
	}
	methods:{
		success(params){
		// params 返回的二次验证参数
		}
	}
}
</script>
```

### 2.3.3 事件

|  参数 | 说明  |
| ------------ | ------------ |
| success  | 验证码匹配成功后的回调函数  |
| error  | 验证码匹配失败后的回调函数  |
| ready  |  验证码初始化成功的回调函数 |

### 2.3.4 验证码参数

| 参数  | 说明  |
| ------------ | ------------ |
| captchaType  | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord  |
| mode  | 验证码的显示方式，弹出式pop，固定fixed，默认是：mode : ‘pop’  |
| vSpace  | 验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，设置vSpace:5  |
| explain  |  滑动条内的提示，不设置默认是：'向右滑动完成验证' |
|  imgSize |  其中包含了width、height两个参数，分别代表图片的宽度和高度，支持百分比方式设置 如:{width:'100%',height:'200px'} |
| blockSize  | 其中包含了width、height两个参数，分别代表拼图块的宽度和高度，如:{width:'40px',height:'40px'}  |
| barSize  | 其中包含了width、height两个参数，分别代表滑动条的宽度和高度，支持百分比方式设置，如:{width:'100%',height:'40px'}  |

### 2.3.5 获取验证码接口详情
#### 接口地址：http://*:*/captcha/get
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
### 2.3.6 核对验证码接口详情
#### 请求接口：http://*:*/captcha/check
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
## 2.4 IOS接入
待接入
## 2.5 Android
待接入

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













