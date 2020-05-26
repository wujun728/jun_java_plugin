语法详解
数据类型
和java不同，FreeMarker不需要定义变量的类型，直接赋值即可。

字符串： value = "xxxx" 。如果有特殊字符 string = r"xxxx" 。单引号和双引号是一样的。

数值：value = 1.2。数值可以直接等于，但是不能用科学计数法。

布尔值：true or  false。

List集合：list = [1,2,3] ; list=[1..100] 表示 1 到 100 的集合，反之亦然。

Map集合：map = {"key" : "value" , "key2" : "value2"}，key 必须是字符串哦！

实体类：和EL表达式差不多，直接点出来。

字符串操作
字符串连接：可以直接嵌套${"hello , ${name}"} ； 也可以用加号${"hello , " + name}

字符串截取：string[index]。index 可以是一个值，也可以是形如 0..2 表示下标从0开始，到下标为2结束。一共是三个数。

比较运算符
== （等于），!= （不等于），gt（大于），gte（大于或者等于），lt（小于），lte（小于或者等于）。不建议用 >，<  可能会报错！

一般和 if 配合使用

内建函数
FreeMarker 提供了一些内建函数来转换输出，其结构：变量?内建函数，这样就可以通过内建函数来转换输出变量。

1. html： 对字符串进行HTML编码；
2. cap_first： 使字符串第一个字母大写；
3. lower_case： 将字符串转成小写；
4. upper_case： 将字符串转成大写；
5. size： 获得集合中元素的个数；
6. int： 取得数字的整数部分。

变量空判断
 !  　　指定缺失变量的默认值；一般配置变量输出使用
??   　判断变量是否存在。一般配合if使用 <#if value??></#if>

宏指令
可以理解为java的封装方法，供其他地方使用。宏指令也称为自定义指令，macro指令

语法很简单：<#macro val > 声明macro </#macro>; 使用macro <@val />  

命名空间

可以理解为java的import语句，为避免变量重复。一个重要的规则就是:路径不应该包含大写字母，使用下划线_分隔词语，myName --> my_name

语法很简单：<#import "xxx.ftl" as val> 

 

其他没有说明的语法是因为和java一样，没什么特别之处




FreeMarker Web
这里是和SpringMVC整合的，SpringMVC的配置就不多说了，笔者也写过相关的文章，同时也会提供源码

导入相关的jar pom.xml
 
<!-- freeMarker start -->
    <dependency>
         <groupId>org.freemarker</groupId>
         <artifactId>freemarker</artifactId>
         <version>2.3.20</version>
     </dependency>
     <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-context-support</artifactId>
          <version>4.1.4.RELEASE</version>
      </dependency>
  </dependencies> 
  <!-- freeMarker end --> 
  
springmvc的配置文件：
 
<!-- 整合Freemarker -->
    <!-- 放在InternalResourceViewResolver的前面，优先找freemarker -->  
    <bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">  
        <property name="templateLoaderPath" value="/WEB-INF/views/templates"/>  
    </bean>  
    <bean id="viewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">  
        <property name="prefix" value=""/>  
        <property name="suffix" value=".ftl"/>  
        <property name="contentType" value="text/html; charset=UTF-8"/>
    </bean> 
Controller 层
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloFreeMarkerController {
    
    @RequestMapping("/helloFreeMarker")
    public String helloFreeMarker(Model model) {
        model.addAttribute("name","ITDragon博客");  
        return "helloFreeMarker";
    }

} 
最后是Freemarker文件

复制代码
复制代码
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>FreeMarker Web</title>  
</head>  
<body>  
    <h1>Hello ${name} !</h1>  
</body>  
</html> 