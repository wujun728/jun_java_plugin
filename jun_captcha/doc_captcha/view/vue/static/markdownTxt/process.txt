# 1 在线体验
#### &emsp; 1.1 电脑在线体验:[https://mirror.anji-plus.com/captcha-web/](https://mirror.anji-plus.com/captcha-web/ "链接")
#### &emsp; 1.2 微信小程序和H5在线体验（基于uni-app实现）
 &emsp;&emsp; 如果图片未能正常展示，可查看码云，和github同步 [码云]( https://gitee.com/anji-plus/captcha "码云")<br>
 &emsp;&emsp; ![微信小程序](https://mirror.anji-plus.com/captcha-web/static/8cm.jpg "微信小程序")&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;![h5](https://images.gitee.com/uploads/images/2020/0429/174246_c33e3fa3_1728982.png "h5.png")<br>
 &emsp;&emsp;&emsp;&emsp;&emsp;微信小程序Demo&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;uni-app H5 demo

# 2 功能概述
#### &emsp; 2.1 组件介绍
 &emsp;&emsp; 行为验证码采用嵌入式集成方式，接入方便，安全，高效。抛弃了传统字符型验证码展示-填写字符-比对答案的流程，采用验证码展示-采集用户行为-分析用户行为流程，用户只需要产生指定的行为轨迹，不需要键盘手动输入，极大优化了传统验证码用户体验不佳的问题；同时，快速、准确的返回人机判定结果。目前对外提供两种类型的验证码，其中包含滑动拼图、文字点选。如图1-1、1-2所示。若希望不影响原UI布局，可采用弹出式交互。<br>
 &emsp;&emsp; ![滑动拼图](https://mirror.anji-plus.com/captcha-web/static/blockPuzzle.png "滑动拼图")&emsp;&emsp;&emsp;&emsp;&emsp;![点选文字](https://mirror.anji-plus.com/captcha-web/static/clickWord.png "点选文字")<br>
 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;  图1-1 滑动拼图(水印自定义)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;图1-2 文字点选(水印自定义)

#### &emsp; 2.2 概念术语描述
| 术语  | 描述  |
| ------------ | ------------ |
| 验证码类型 | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord|
| 验证  |  用户拖动/点击一次验证码拼图即视为一次“验证”，不论拼图/点击是否正确 |
| 二次校验  | 验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。目的是核实验证数据的有效性。  |

# 3 交互流程
①	用户访问应用页面，请求显示行为验证码<br>
②	用户按照提示要求完成验证码拼图/点击<br>
③	用户提交表单，前端将第二步的输出一同提交到后台<br>
④	验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。<br>
⑤	第4步返回校验通过/失败到产品应用后端，再返回到前端。如下图所示。
![时序图](https://mirror.anji-plus.com/captcha-web/static/shixu.png "时序图")

# 4 目录结构
├─core    &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;     java后端jar包<br>
├─images    &emsp;&emsp;&emsp;&emsp;&emsp;    效果图<br>
├─service<br>
├&emsp;├─springboot   &emsp;&emsp;    后端为springboot项目示例<br>
├&emsp;└─springmvc    &emsp;&emsp;    后端为springmvc非springboot项目示例<br>
└─view    &emsp;&emsp;&emsp;&emsp;&emsp;    多语言客户端示例<br>
&emsp;├─android    &emsp;&emsp;    原生android实现示例<br>
&emsp;├─flutter    &emsp;&emsp;    flutter实现示例<br>
&emsp;├─html    &emsp;&emsp;    原生html实现示例<br>
&emsp;├─ios    &emsp;&emsp;    原生ios实现示例<br>
&emsp;├─uni-app    &emsp;&emsp;    uni-app实现示例<br>
&emsp;└─vue    &emsp;&emsp;    vue实现示例<br>
&emsp;

# 5 接入文档
#### &emsp; 5.1 本地启动
&emsp; 第一步，启动后端，service/springboot导入Eclipse或者Intellij，启动StartApplication。<br>
&emsp; 第二步，启动前端，使用visual code打开文件夹view/vue，npm install后npm run dev，浏览器登录<br>
```js
npm install
npm run dev

DONE  Compiled successfully in 29587ms                       12:06:38
I  Your application is running here: http://localhost:8081
``` 
&emsp;详细的前后端接入文档，后端示例代码service目录下，前端示例代码view目录下。
#### &emsp; 5.2 [码云wiki接入文档（国内较快）](https://gitee.com/anji-plus/captcha/wikis/Home "码云")<br>
#### &emsp; 5.3 [github wiki接入文档](https://github.com/anji-plus/captcha/wiki "github")<br>

# 6 近期计划
#### &emsp; 6.2 增加weex示例
#### &emsp; 6.2 增加ReactNative示例

# 6 技术支持微信群
<img src="https://images.gitee.com/uploads/images/2020/0511/091107_b81b631c_1728982.jpeg" width = "200" height = "200" div align=left />

