# 三步构建layuiAdmin环境 serving-web-content_layui-admin

#### 项目介绍 注：layuiAdmin 模板只保留修改的几段代码，其余已经删除

使用 Spring Tool Suite (STS) IDE开发工具
https://spring.io/tools3/sts/all

使用Spring MVC提供Web内容
https://spring.io/guides/gs/serving-web-content/

layuiAdmin - 通用后台管理模板系统 iframe版
https://www.layui.com/admin/

构建git 动图下载
[https://gitee.com/lh_yun/spring-Boot_templates_layui-Admin/attach_files](https://gitee.com/lh_yun/spring-Boot_templates_layui-Admin/attach_files)
![输入图片说明](http://img0.ph.126.net/i0Zjhs9xzwVHh-z8j8QgPg==/6599308372332501336.gif "TIM图片20181020183011.jpg")
#### 软件架构

> 使用 Spring Tool Suite (STS) IDE开发工具 
> [https://spring.io/tools3/sts/all](https://spring.io/tools3/sts/all)
> 
> 使用Spring MVC提供Web内容
> [ https://spring.io/guides/gs/serving-web-content/](https://spring.io/guides/gs/serving-web-content/)
> 
> layuiAdmin - 通用后台管理模板系统 iframe版 
> [https://www.layui.com/admin/](https://www.layui.com/admin/)


#### 安装教程和使用说明

### 一、git 导入https://github.com/spring-guides/gs-serving-web-content.git


### 二、复制layuiAdmin iframe版 到springBoot 项目页面目录下面

1.layuiadmin 目录复制到/gs-serving-web-content/src/main/resources/static/目录下

2.views下所有文件和目录复制到/gs-serving-web-content/src/main/resources/templates/目录下

### 三、配置url 路径和修改文件

1./gs-serving-web-content/src/main/resources/templates/index.html 
修改
`<iframe src="home/console.html" frameborder="0" class="layadmin-iframe"></iframe> `
为
`<iframe src="home/console" frameborder="0" class="layadmin-iframe"></iframe> `

2.修改src/main/java/hello/GreetingController.java

```
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

```

为 

```
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("home")
public class HomeController {

    @GetMapping("/console")
    public String console(@RequestParam(name="name", required=false, defaultValue="测试") String name, Model model) {
        model.addAttribute("name", name);
        return "home/console";
    }

}
```