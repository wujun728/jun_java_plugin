### WebMVC

WebMVC模块在YMP框架中是除了JDBC模块以外的另一个非常重要的模块，集成了YMP框架的诸多特性，在功能结构的设计和使用方法上依然保持一贯的简单风格，同时也继承了主流MVC框架的基因，对于了解和熟悉SSH等框架技术的开发人员来说，上手极其容易，毫无学习成本。

其主要功能特性如下：

- 标准MVC实现，结构清晰，完全基于注解方式配置简单；
- 支持约定模式，无需编写控制器代码，直接匹配并执行视图；
- 支持多种视图技术(JSP、Freemarker、Velocity、Text、HTML、JSON、Binary、Forward、Redirect、HttpStatus等)；
- 支持RESTful模式及URL风格；
- 支持请求参数与控制器方法参数的自动绑定；
- 支持参数有效性验证；
- 支持控制器方法的拦截；
- 支持注解配置控制器请求路由映射；
- 支持自动扫描控制器类并注册；
- 支持事件和异常的自定义处理；
- 支持I18N资源国际化；
- 支持控制器方法和视图缓存；
- 支持控制器参数转义；
- 支持插件扩展；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-webmvc</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：在项目的pom.xml中添加上述配置，该模块已经默认引入核心包、验证框架包和缓存包的依赖，无需重复配置。
> 若不想启用缓存服务只需在`ymp-conf.properties`中增加排除caches模块配置，如：`ymp.excluded_modules=cache`

#### 模块初始化

在Web程序中监听器(Listener)是最先被容器初始化的，所以WebMVC模块是由监听器负责对YMP框架进行初始化：

> 监听器(Listener)：net.ymate.platform.webmvc.support.WebAppEventListener

处理浏览器请求并与模块中控制器匹配、路由的过程可分别由过滤器(Filter)和服务端程序(Servlet)完成：

> 过滤器(Filter)：net.ymate.platform.webmvc.support.DispatchFilter

> 服务端程序(Servlet)：net.ymate.platform.webmvc.support.DispatchServlet

首先看一下完整的web.xml配置文件：

	<?xml version="1.0" encoding="UTF-8"?>
	<web-app id="WebApp_ID" version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
	         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
	
	    <listener>
	        <listener-class>net.ymate.platform.webmvc.support.WebAppEventListener</listener-class>
	    </listener>
	
	    <filter>
	        <filter-name>DispatchFilter</filter-name>
	        <filter-class>net.ymate.platform.webmvc.support.DispatchFilter</filter-class>
	    </filter>
	    <filter-mapping>
	        <filter-name>DispatchFilter</filter-name>
	        <url-pattern>/*</url-pattern>
	        <dispatcher>REQUEST</dispatcher>
	        <dispatcher>FORWARD</dispatcher>
	    </filter-mapping>
	    
	    <!--
	    <servlet>
	        <servlet-name>DispatchServlet</servlet-name>
	        <servlet-class>net.ymate.platform.webmvc.support.DispatchServlet</servlet-class>
	    </servlet>
	    <servlet-mapping>
	        <servlet-name>DispatchServlet</servlet-name>
	        <url-pattern>/service/*</url-pattern>
	    </servlet-mapping>
	    -->
	
	    <welcome-file-list>
	        <welcome-file>index.html</welcome-file>
	        <welcome-file>index.jsp</welcome-file>
	    </welcome-file-list>
	</web-app>

#### 模块配置

WebMVC模块的基本初始化参数配置：

	#-------------------------------------
	# 基本初始化参数
	#-------------------------------------
	
	# 控制器请求映射路径分析器，可选值为已知分析器名称或自定义分析器类名称，默认为restful，目前支持已知分析器[default|restful|...]
    ymp.configs.webmvc.request_mapping_parser_class=
	
	# 控制器请求处理器，可选值为已知处理器名称或自定义处理器类名称，自定义类需实现net.ymate.platform.webmvc.IRequestProcessor接口，默认为default，目前支持已知处理器[default|json|xml|...]
	ymp.configs.webmvc.request_processor_class=
	
	# 异常错误处理器，可选参数，此类需实现net.ymate.platform.webmvc.IWebErrorProcessor接口
	ymp.configs.webmvc.error_processor_class=
	
	# 缓存处理器，可选参数，此类需实现net.ymate.platform.webmvc.IWebCacheProcessor接口
	ymp.configs.webmvc.cache_processor_class=
	
	# 默认字符编码集设置，可选参数，默认值为UTF-8
	ymp.configs.webmvc.default_charset_encoding=
	
	# 请求忽略正则表达式，可选参数，默认值为^.+\.(jsp|jspx|png|gif|jpg|jpeg|js|css|swf|ico|htm|html|eot|woff|woff2|ttf|svg)$
	ymp.configs.webmvc.request_ignore_regex=
	
	# 请求方法参数名称，可选参数， 默认值为_method
	ymp.configs.webmvc.request_method_param=
	
	# 请求路径前缀，可选参数，默认值为空
	ymp.configs.webmvc.request_prefix=
	
	# 请求参数转义模式是否开启（开启状态时，控制器方法的所有参数将默认支持转义，可针对具体控制器主法或参数设置忽略转义操作），可选参数，默认值为false
	ymp.configs.webmvc.parameter_escape_mode=

	# 执行请求参数转义顺序，可选参数，取值范围：before(参数验证之前)和after(参数验证之后)，默认值为after
    ymp.configs.webmvc.parameter_escape_order=
	
	# 控制器视图文件基础路径（必须是以 '/' 开始和结尾，默认值为/WEB-INF/templates/）
	ymp.configs.webmvc.base_view_path=

**说明**：在服务端程序Servlet方式的请求处理中，请求忽略正则表达式`request_ignore_regex`参数无效；

#### 模块事件

WebEvent事件枚举对象包括以下事件类型：

|事务类型|说明|
|---|---|
|SERVLET_CONTEXT_INITED|容器初始化事件|
|SERVLET_CONTEXT_DESTROYED|容器销毁事件|
|SERVLET_CONTEXT_ATTR_ADDED||
|SERVLET_CONTEXT_ATTR_REMOVEED||
|SERVLET_CONTEXT_ATTR_REPLACED||
|SESSION_CREATED|会话创建事件|
|SESSION_DESTROYED|会话销毁事件|
|SESSION_ATTR_ADDED||
|SESSION_ATTR_REMOVEED||
|SESSION_ATTR_REPLACED||
|REQUEST_INITED|请求初始化事件|
|REQUEST_DESTROYED|请求销毁事件|
|REQUEST_ATTR_ADDED||
|REQUEST_ATTR_REMOVEED||
|REQUEST_ATTR_REPLACED||
|REQUEST_RECEIVED|接收控制器方法请求事件|
|REQUEST_COMPLETED|完成控制器方法请求事件|

#### 控制器（Controller）

控制器(Controller)是MVC体系中的核心，它负责处理浏览器发起的所有请求和决定响应内容的逻辑处理，控制器就是一个标准的Java类，不需要继承任何基类，通过类中的方法向外部暴露接口，该方法的返回结果将决定向浏览器响应的具体内容；

下面通过示例编写WebMVC模块中的控制器：

	@Controller
	public class DemoController {
		
		@RequestMapping("/sayhi")
		public IView sayHi() {
			return View.textView("Hi, YMPer!");
		}
	}

启动Tomcat服务并访问`http://localhost:8080/sayhi`，得到的输出结果将是：`Hi, YMPer!`

从以上代码中看到有两个注解，分别是：

- @Controller：声明一个类为控制器，框架在启动时将会自动扫描所有声明该注解的类并注册为控制器；

	> name：控制器名称，默认为“”（该参数暂时未被使用）；
	>
	> singleton：指定控制器是否为单例，默认为true；

- @RequestMapping：声明控制器请求路径映射，作用域范围：类或方法；

	> value：控制器请求路径映射，必选参数；
	>
	> method[]：允许的请求方式，默认为GET方式，取值范围：GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE；
	>
	> header[]：请求头中必须存在的头名称；
	>
	> param[]：请求中必须存在的参数名称；

示例一：

创建非单例控制器，其中的控制器方法规则如下：
> 1. 控制器方法仅支持POST和PUT方式访问；
> 2. 请求头参数中必须包含x-requested-with=XMLHttpRequest(即判断是否AJAX请求)；
> 3. 请求参数中必须存在name参数；

	@Controller(singleton = false)
	@RequestMapping("/demo")
	public class DemoController {
		
		@RequestMapping(value = "/sayhi",
            method = {Type.HttpMethod.POST, Type.HttpMethod.PUT},
            header = {"x-requested-with=XMLHttpRequest"},
            param = {"name=*"})
		public IView sayHi() {
			return View.textView("Hi, YMPer!");
		}
	}

示例说明：
> 本例主要展示了如何使用@Controller和@RequestMapping注解来对控制器和控制器方法对进配置；
> 
> 控制器方法必须使用public修饰，否则无效；
> 
> 由于控制器上也声明了@RequestMapping注解，所以控制器方法的请求路径映射将变成：/demo/sayhi；

示例二：

上例中展示了对请求的一些控制，下面展示如何对响应结果进行控制，规则如下：

> 1. 通过注解设置响应头参数：
> 	- from = "china"
> 	- age = 18
> 2. 通过注解设置控制器返回视图及内容："Hi, YMPer!"

	@Controller
	@RequestMapping("/demo")
	public class DemoController {
		
		@RequestMapping("/sayhi")
		@ResponseView(value = "Hi, YMPer!", type = Type.View.TEXT)
	    @ResponseHeader({
	            @Header(name = "from", value = "china"),
	            @Header(name = "age", value = "18", type = Type.HeaderType.INT)})
		public void sayHi() {
		}
	}
 
本例中用到了三个注解：

- @ResponseView：声明控制器方法默认返回视图对象, 仅在方法无返回值或返回值无效时使用

	> name：视图模板文件路径，默认为""；
	> 
	> type：视图文件类型，默认为Type.View.NULL；

- @ResponseHeader：设置控制器方法返回结果时增加响应头参数；

	> value[]：响应头@Header参数集合；

- @Header：声明一个请求响应Header键值对，仅用于参数传递；

	> name：响应头参数名称，必选参数；
	>
	> value：响应头参数值，默认为""；
	>
	> type：响应头参数类型，支持STRING, INI, DATE，默认为Type.HeaderType.STRING；


#### 控制器参数（Parameter）

WebMVC模块不但让编写控制器变得非常简单，处理请求参数也变得更加容易！WebMVC会根据控制器方法参数或类成员的注解配置，自动转换与方法参数或类成员对应的数据类型，参数的绑定涉及以下注解：

##### 基本参数注解

- @RequestParam：绑定请求中的参数；

- @RequestHeader：绑定请求头中的参数变量；

- @CookieVariable：绑定Cookie中的参数变量；

上面三个注解拥有相同的参数：

> value：参数名称，若未指定则默认采用方法参数变量名；
>
> prefix：参数名称前缀，默认为""；
>
> defaultValue：指定参数的默认值，默认为""；

示例代码：
	
		@Controller
		@RequestMapping("/demo")
		public class DemoController {
			
			@RequestMapping("/param")
			public IView testParam(@RequestParam String name,
			                  @RequestParam(defaultValue = "18") Integer age,
			                  @RequestParam(value = "name", prefix = "user") String username,
			                  @RequestHeader(defaultValue = "BASIC") String authType,
			                  @CookieVariable(defaultValue = "false") Boolean isLogin) {

			    System.out.println("AuthType: " + authType);
			    System.out.println("IsLogin: " + isLogin);
				return View.textView("Hi, " + name + ", UserName: " + username + ", Age: " + age);
			}
		}
	
通过浏览器访问URL测试：
	
		http://localhost:8080/demo/param?name=webmvc&user.name=ymper
	
执行结果：

		控制台输出：
		AuthType: BASIC
		IsLogin: false
		
		浏览器输出：
		Hi, webmvc, UserName: ymper, Age: 18

##### 特别的参数注解

- @PathVariable：绑定请求映射中的路径参数变量；
	> value：参数名称，若未指定则默认采用方法参数变量名；

	示例代码：
	
		@Controller
		@RequestMapping("/demo")
		public class DemoController {

			@RequestMapping("/path/{name}/{age}")
			public IView testPath(@PathVariable String name,
			                  @PathVariable(value = "age") Integer age,
			                  @RequestParam(prefix = "user") String sex) {

				return View.textView("Hi, " + name + ", Age: " + age + ", Sex: " + sex);
			}
		}
	
	通过浏览器访问URL测试：
	
		http://localhost:8080/demo/path/webmvc/20?user.sex=F
	
	执行结果：
	
		Hi, webmvc, Age: 20, Sex: F
	
	> **注意**：
	> 
	> + 默认请求解析`request_mapping_parser_class=default`时，基于路径的参数变量必须是连续的：
	>
	>   - 正确：/path/{name}/{age}
	>
	>   - 错误：/path/{name}/age/{sex}
	> 
    > + 当配置请求解析`request_mapping_parser_class=restful`时，则以下均能正确解析：
    >
    >   - 正确：/path/{name}/{age}
    >
    >   - 正确：/path/{name}/age/{sex}
    >
    > + 可以通过实现IRequestMappingParser接口自行定义控制器请求规则；

- @ModelBind：值对象参数绑定注解；
	> prefix：绑定的参数名称前缀，可选参数，默认为""；
	
	示例代码：
	
		public class DemoVO {
			
			@PathVariable
			private String name;
			
			@RequestParam
			private String sex;
			
			@RequestParam(prefix = "ext")
			private Integer age;
			
			// 省略Get和Set方法
		}
		
		@Controller
		@RequestMapping("/demo")
		public class DemoController {

			@RequestMapping("/bind/{demo.name}")
			public IView testBind(@ModelBind(prefix = "demo") DemoVO vo) {
				String _str = "Hi, " + vo.getName() + ", Age: " + vo.getAge() + ", Sex: " + vo.getSex();
				return View.textView(_str);
			}
		}

	通过浏览器访问URL测试：
	
		http://localhost:8080/demo/bind/webmvc?demo.sex=F&demo.ext.age=20
	
	执行结果：
	
		Hi, webmvc, Age: 20, Sex: F

- @ParameterEscape：控制器方法参数转义注解；

    可以通过WebMVC模块配置参数`parameter_escape_order`设定是在控制器方法参数执行验证之前还是之后执行参数转义动作，参数取值范围为`before`或`after`，默认为`after`即参数验证之后进行转义；

	> scope：字符串参数转义范围，默认为Type.EscapeScope.DEFAULT；
	>
	> - 取值范围包括：JAVA, JS, HTML, XML, SQL, CSV, DEFAULT；
	> - 默认值DEFAULT，它完成了对SQL和HTML两项转义；
	>
	> skiped：通知父级注解当前方法或参数的转义操作将被忽略，默认为false；
	>
	> processor：自定义字符串参数转义处理器；
	>
	> - 可以通过IParameterEscapeProcessor接口实现自定义的转义逻辑；
	> - 默认实现为DefaultParameterEscapeProcessor；

	示例代码一：
	
		@Controller
		@RequestMapping("/demo")
		@ParameterEscape
		public class DemoController {

			@RequestMapping("/escape")
		    public IView testEscape(@RequestParam String content,
		                            @ParameterEscape(skiped = true) @RequestParam String desc) {
		
		        System.out.println("Content: " + content);
		        System.out.println("Desc: " + desc);
		        return View.nullView();
		    }
		}
		
		// 或者：(两段代码执行结果相同)
		
		@Controller
		@RequestMapping("/demo")
		public class DemoController {

			@RequestMapping("/escape")
			@ParameterEscape
		    public IView testEscape(@RequestParam String content,
		                            @ParameterEscape(skiped = true) @RequestParam String desc) {
		
		        System.out.println("Content: " + content);
		        System.out.println("Desc: " + desc);
		        return View.nullView();
		    }
		}
	
	通过浏览器访问URL测试：
	
		http://localhost:8080/demo/escape?content=<p>content$<br><script>alert("hello");</script></p>&desc=<script>alert("hello");</script>
	
	执行结果：(控制台输出)
	
		Content: &lt;p&gt;content$&lt;br&gt;&lt;script&gt;alert(&quot;hello&quot;);&lt;/script&gt;&lt;/p&gt;
		Desc: <script>alert("hello");</script>

	> 示例一说明：
	>
	> - 由于控制器类被声明了@ParameterEscape注解，代表整个控制器类中所有的请求参数都需要被转义，因此参数content的内容被成功转义；
	> - 由于参数desc声明的@ParameterEscape注解中skiped值被设置为true，表示跳过上级设置，因此参数内容未被转义；

	示例代码二：
	
		@Controller
		@RequestMapping("/demo")
		@ParameterEscape
		public class DemoController {

		    @RequestMapping("/escape2")
		    @ParameterEscape(skiped = true)
		    public IView testEscape2(@RequestParam String content,
		                            @ParameterEscape @RequestParam String desc) {
		
		        System.out.println("Content: " + content);
		        System.out.println("Desc: " + desc);
		        return View.nullView();
		    }
		}
	
	通过浏览器访问URL测试：
	
		http://localhost:8080/demo/escape2?content=<p>content$<br><script>alert("hello");</script></p>&desc=<script>alert("hello");</script>
	
	执行结果：(控制台输出)
	
		Content: <p>content$<br><script>alert("hello");</script></p>
		Desc: &lt;script&gt;alert(&quot;hello&quot;);&lt;/script&gt;

	> 示例二说明：
	>
	> - 虽然控制器类被声明了@ParameterEscape注解，但控制器方法通过skiped设置跳过转义，这表示被声明的方法参数内容不进行转义操作，因此参数content的内容未被转义；
	> - 由于参数desc声明了@ParameterEscape注解，表示该参数需要转义，因此参数内容被成功转义；
	>
	> **注意**：当控制器类和方法都声明了@ParameterEscape注解时，则类上声明的注解将视为无效；

##### 非单例控制器的特殊用法

单例控制器与非单例控制器的区别：

- 单例控制器类在WebMVC模块初始化时就已经实例化；
- 非单例控制器类则是在每次接收到请求时都将创建实例对象，请求结束后该实例对象被释放；

基于以上描述，非单例控制器是可以通过类成员来接收请求参数，示例代码如下：

	@Controller(singleton = false)
	@RequestMapping("/demo")
	public class DemoController {

		@RequestParam
		private String content;

	    @RequestMapping("/sayHi")
	    public IView sayHi(@RequestParam String name) {
	        return View.textView("Hi, " + name + ", Content: " + content);
	    }
	}

通过浏览器访问URL测试：

	http://localhost:8080/demo/sayHi?name=YMPer&content=Welcome!

此示例代码的执行结果：

	Hi, YMPer, Content: Welcome!

> **注意**：在单例模式下，WebMVC模块将忽略为控制器类成员赋值，同时也建议在单例模式下不要使用成员变量做为参数，在并发多线程环境下会发生意想不到的问题！！

#### 环境上下文对象（WebContext）

为了让开发人员能够随时随地获取和使用Request、Response、Session等Web容器对象，YMP框架在WebMVC模块中提供了一个名叫WebContext的Web环境上下文封装类，简单又实用，先了解一下提供的方法：

直接获取Web容器对象：
> 
> - 获取ServletContext对象：
>
>			WebContext.getServletContext();
>
> - 获取HttpServletRequest对象：
>
>			WebContext.getRequest();
>
> - 获取HttpServletResponse对象：
>
>			WebContext.getResponse();
>
> - 获取PageContext对象：
>
>			WebContext.getPageContext();

获取WebMVC容器对象：

> - 获取IRequestContext对象：
> 
> 			WebContext.getRequestContext();
> 
> 	> WebMVC请求上下文接口，主要用于分析请求路径及存储相关参数；
>
> - 获取WebContext对象实例：
> 
> 			WebContext.getContext();
> 

WebContext将Application、Session、Request等Web容器对象的属性转换成Map映射存储，同时向Map的赋值也将自动同步至对象的Web容器对象中，起初的目的是为了能够方便代码移植并脱离Web环境依赖进行开发测试(功能参考Struts2)：

> - WebContext.getContext().getApplication();
>
> - WebContext.getContext().getSession();
> 
> - WebContext.getContext().getAttribute(Type.Context.REQUEST);
> 
>	> 原本可以通过WebContext.getContext().getRequest方法直接获取的，但由于设计上的失误，方法名已被WebContext.getRequest()占用，若变更方法名受影响的项目太多，所以只好委屈它了:D，后面会介绍更多的辅助方法来操作Request属性，所以可以忽略它的存在！
>
> - WebContext.getContext().getAttributes();
> 
> - WebContext.getContext().getLocale();
> 
> - WebContext.getContext().getOwner();
> 
> - WebContext.getContext().getParameters();
> 

WebContext操作Application的辅助方法：

> - boolean getApplicationAttributeToBoolean(String name);
> 
> - int getApplicationAttributeToInt(String name);
> 
> - long getApplicationAttributeToLong(String name);
> 
> - String getApplicationAttributeToString(String name);
> 
> - \<T> T getApplicationAttributeToObject(String name);
> 
> - WebContext addApplicationAttribute(String name, Object value)
> 

WebContext操作Session的辅助方法：

> - boolean getSessionAttributeToBoolean(String name);
> 
> - int getSessionAttributeToInt(String name);
> 
> - long getSessionAttributeToLong(String name);
> 
> - String getSessionAttributeToString(String name);
> 
> - \<T> T getSessionAttributeToObject(String name);
> 
> - WebContext addSessionAttribute(String name, Object value)
> 

WebContext操作Request的辅助方法：

> - boolean getRequestAttributeToBoolean(String name);
> 
> - int getRequestAttributeToInt(String name);
> 
> - long getRequestAttributeToLong(String name);
> 
> - String getRequestAttributeToString(String name);
> 
> - \<T> T getRequestAttributeToObject(String name);
> 
> - WebContext addRequestAttribute(String name, Object value)
> 

WebContext操作Parameter的辅助方法：

> - boolean getParameterToBoolean(String name);
> 
> - int getParameterToInt(String name)
> 
> - long getParameterToLong(String name);
> 
> - String getParameterToString(String name);
> 

WebContext操作Attribute的辅助方法：

> - \<T> T getAttribute(String name);
>
> - WebContext addAttribute(String name, Object value);
> 


WebContext获取IUploadFileWrapper上传文件包装器：

> - IUploadFileWrapper getUploadFile(String name);
> 
> - IUploadFileWrapper[] getUploadFiles(String name);
>

#### 文件上传（Upload）

WebMVC模块针对文件的上传处理以及对上传的文件操作都非常的简单，通过注解就轻松搞定：

- @FileUpload：声明控制器方法需要处理上传的文件流；

	> 无参数，需要注意的是文件上传处理的表单enctype属性：
	
	>		<form action="/demo/upload" method="POST" enctype="multipart/form-data">
	>		......
	>		</form>

- IUploadFileWrapper：上传文件包装器接口，提供对已上传文件操作的一系列方法；

示例代码：

	@Controller
	@RequestMapping("/demo)
	public class UploadController {
	
		// 处理单文件上传
		@RequestMapping(value = "/upload", method = Type.HttpMethod.POST)
		@FileUpload
		public IView doUpload(@RequestParam
					          IUploadFileWrapper file) throws Exception {
			// 获取文件名称
			file.getName();
			
			// 获取文件大小
			file.getSize();
			
			// 获取完整的文件名及路径
			file.getPath();
			
			// 获取文件Content-Type
			file.getContentType();
			
			// 转移文件
			file.transferTo(new File("/temp", file.getName()));
			
			// 保存文件
			file.writeTo(new File("/temp", file.getName());
			
			// 删除文件
			file.delete();
			
			// 获取文件输入流对象
			file.getInputStream();
			
			// 获取文件输出流对象
			file.getOutputStream();
			
			return View.nullView();
		}

		// 处理多文件上传
		@RequestMapping(value = "/uploads", method = Type.HttpMethod.POST)
		@FileUpload
		public IView doUpload(@RequestParam
					          IUploadFileWrapper[] files) throws Exception {

			// ......

			return View.nullView();
		}
	}

文件上传相关配置参数：

	#-------------------------------------
	# 文件上传配置参数
	#-------------------------------------
	
	# 文件上传临时目录，为空则默认使用：System.getProperty("java.io.tmpdir")
	ymp.configs.webmvc.upload_temp_dir=
	
	# 上传文件大小最大值（字节），默认值：10485760（注：10485760 = 10M）
	ymp.configs.webmvc.upload_file_size_max=

	# 上传文件总量大小最大值（字节）, 默认值：10485760（注：10485760 = 10M）
	ymp.configs.webmvc.upload_total_size_max=

	# 内存缓冲区的大小，默认值： 10240字节（=10K），即如果文件大于10K，将使用临时文件缓存上传文件
	ymp.configs.webmvc.upload_size_threshold=

	# 文件上传状态监听器，可选参数，默认值为空
	ymp.configs.webmvc.upload_file_listener_class=

文件上传状态监听器(upload\_file\_listener\_class)配置：

WebMVC模块的文件上传是基于Apache Commons FileUpload组件实现的，所以通过其自身提供的ProgressListener接口即可实现对文件上传状态的监听；

示例代码：实现上传文件的进度计算；

	public class UploadProgressListener implements ProgressListener {
	
	    public void update(long pBytesRead, long pContentLength, int pItems) {
	        if (pContentLength == 0) {
	            return;
	        }
	        // 计算上传进度百分比
	        double percent = (double) pBytesRead / (double) pContentLength;
	        // 将百分比存储在用户会话中
	        WebContext.getContext().getSession().put("upload_progress", percent);
	    }
	}

> - 将该接口实现类配置到 ymp.configs.webmvc.upload\_file\_listener\_class 参数中；
> 
> - 通过Ajax定时轮循的方式获取会话中的进度值，并展示在页面中；

#### 视图（View）

WebMVC模块支持多种视图技术，包括JSP、Freemarker、Velocity、Text、HTML、JSON、Binary、Forward、Redirect、HttpStatus等，也可以通过IView接口扩展实现自定义视图；

##### 控制器视图的表示方法
> - 通过返回IView接口类型；
> - 通过字符串表达一种视图类型；
> - 无返回值或返回值为空，将使用当前RequestMapping路径对应的JspView视图；

##### 视图文件路径配置

> 控制器视图文件基础路径，必须是以 '/' 开始和结尾，默认值为/WEB-INF/templates/；
> 
>		ymp.configs.webmvc.base_view_path=/WEB-INF/templates/

##### 视图对象操作示例

> 视图文件可以省略扩展名称，通过IView接口可以直接设置请求参数和内容类型；
>
>		// 通过View对象创建视图对象
>		IView _view = View.jspView("/demo/test")
>	            .addAttribute("attr1", "value")
>	            .addAttribute("attr2", 2)
>	            .addHeader("head", "value")
>	            .setContentType(Type.ContentType.HTML.getContentType());
>
>		// 直接创建视图对象
>		_view = new JspView("/demo/test");
>
>		// 下面三种方式的结果是一样的，使用请求路径对应的视图文件返回
>		_view = View.jspView();
>		_view = JspView.bind();
>		_view = new JspView();

##### WebMVC模块提供的视图

JspView：JSP视图；

>		View.jspView("/demo/test.jsp");
>		// = "jsp:/demo/test"

FreemarkerView：Freemarker视图；

> 		View.freemarkerView("/demo/test.ftl");
> 		// = "freemarker:/demo/test"

VelocityView：Velocity视图；

> 		View.velocityView("/demo/test.vm");
> 		// = "velocity:/demo/test"

TextView：文本视图；

> 		View.textView("Hi, YMPer!");
> 		// = "text:Hi, YMPer!"

HtmlView：HTML文件内容视图；

> 		View.htmlView("<p>Hi, YMPer!</p>");
> 		// = "html:<p>Hi, YMPer!</p>"

JsonView：JSON视图；

> 		// 直接传递对象
> 		User _user = new User();
> 		user.setId("...");
> 		...
> 		View.jsonView(_user);
> 
> 		// 传递JSON字符串
> 		View.jsonView("{id:\"...\", ...}");
> 		// = "json:{id:\"...\", ...}"

BinaryView：二进制数据流视图；

> 		// 下载文件，并重新指定文件名称
> 		View.binaryView(new File("/temp/demo.txt"))
> 				.useAttachment("测试文本.txt");
> 		// = "binary:/temp/demo.txt:测试文本.txt"
>
> > 若不指定文件名称，则回应头中将不包含 "attachment;filename=xxx"

ForwardView：请求转发视图；

> 		View.forwardView("/demo/test");
> 		// = "forward:/demo/test"

RedirectView：重定向视图；

> 		View.redirectView("/demo/test");
> 		// = "redirect:/demo/test"

HttpStatusView：HTTP状态视图

> 		View.httpStatusView(404);
> 		// = "http_status:404"
> 
> 		View.httpStatusView(500, "系统忙, 请稍后再试...");
> 		// = "http_status:500:系统忙, 请稍后再试..."

NullView：空视图；

>		View.nullView();

#### 验证（Validation）

WebMVC模块已集成验证模块，控制器方法可以直接使用验证注解完成参数的有效性验证，详细内容请参阅 [验证(Validation)](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-validation/README.md) 模块文档；

> **说明**：
> - 控制器的参数验证规则全部通过验证注解进行配置并按顺序执行，由WebMVC框架自动调用完成验证过程，无需手动干预；
> - 参数验证过程将在控制器配置的拦截器执行完毕后执行，也就是说拦截器中获取的请求参数值并未验证过；

#### 缓存（Cache）

###### 集成缓存模块

WebMVC模块已集成缓存模块，通过@Cacheable注解即可轻松实现控制器方法的缓存，通过配置缓存模块的scope\_processor\_class参数可以支持APPLICATION和SESSION作用域；

	# 设置缓存作用域处理器
	ymp.configs.cache.scope_processor_class=net.ymate.platform.webmvc.support.WebCacheScopeProcessor

示例代码：将方法执行结果以会话(SESSION)级别缓存180秒；

		@Controller
		@RequestMapping("/demo")
		@Cacheable
		public class CacheController {
		
			@RequestMapping("/cache")
			@Cacheable(scope = ICaches.Scope.SESSION, timeout = 180)
			public IView doCacheable(@RequestParam String content) throws Exception {
				// ......
				return View.textView("Content: " + content);
			}
		}

> **注意**：基于@Cacheable的方法缓存只是缓存控制器方法返回的结果对象，并不能缓存IView视图的最终执行结果；

###### 自定义缓存处理器

WebMVC模块提供了缓存处理器IWebCacheProcessor接口，可以让开发者通过此接口对控制器执行结果进行最终处理，该接口作用于被声明@ResponseCache注解的控制器类和方法上；

> **说明**: 框架提供IWebCacheProcessor接口默认实现`net.ymate.platform.webmvc.support.WebCacheProcessor`用以缓存视图执行结果，
> 但需要注意的是当使用它时, 请检查web.xml的过滤器`DispatchFilter`中不要配置`<dispatcher>INCLUDE</dispatcher>`，否则将会产生死循环；

@ResponseCache注解参数说明：

> cacheName：缓存名称, 可选参数, 默认值为default；
>
> key：缓存Key, 可选参数, 若未设置则由IWebCacheProcessor接口实现自动生成；
>
> processorClass：自定义视图缓存处理器, 可选参数，若未提供则采用默认IWebCacheProcessor接口参数配置；
>
> scope：缓存作用域, 可选参数，可选值为APPLICATION、SESSION和DEFAULT，默认为DEFAULT；
>
> timeout：缓存数据超时时间, 可选参数，数值必须大于等于0，为0表示默认缓存300秒；
>
> useGZip：是否使用GZIP压缩, 默认值为true

默认IWebCacheProcessor接口参数配置：

	# 缓存处理器，可选参数
	ymp.configs.webmvc.cache_processor_class=demo.WebCacheProc

> 框架默认提供了该接口的实现类：`net.ymate.platform.webmvc.support.WebCacheProcessor`
>
> - 基于Cache缓存模块使其对@ResponseCache注解中的Scope.DEFAULT作用域支持Last-Modified等浏览器相关配置，并支持GZIP压缩等特性

示例代码：

	package demo;

	import net.ymate.platform.webmvc.*;
	import net.ymate.platform.webmvc.view.IView;

	public class WebCacheProc implements IWebCacheProcessor {
	    public boolean processResponseCache(IWebMvc owner, ResponseCache responseCache, IRequestContext requestContext, IView resultView) throws Exception {
	    	// 这里是对View视图自定义处理逻辑...
	    	// 完整的示例代码请查看net.ymate.platform.webmvc.support.WebCacheProcessor类源码
	        return false;
	    }
	}

	@Controller
	@RequestMapping("/demo")
	public class CacheController {
	
		@RequestMapping("/cache")
		@ResponseCache
		public IView doCacheable(@RequestParam String content) throws Exception {
			// ......
			return View.textView("Content: " + content);
		}
	}

> **说明**：该接口方法返回布尔值，用于通知WebMVC框架是否继续处理控制器视图；

#### 拦截器（Intercept）

WebMVC模块基于YMPv2.0的新特性，原生支持AOP方法拦截，通过以下注解进行配置：

> @Before：用于设置一个类或方法的前置拦截器，声明在类上的前置拦截器将被应用到该类所有方法上；

> @After：用于设置一个类或方的后置拦截器，声明在类上的后置拦截器将被应用到该类所有方法上；

> @Around：用于同时配置一个类或方法的前置和后置拦截器；

> @Clean：用于清理类上全部或指定的拦截器，被清理的拦截器将不会被执行；

> @ContextParam：用于设置上下文参数，主要用于向拦截器传递参数配置；

> @Ignored：声明一个方法将忽略一切拦截器配置；

> **说明**: 声明@Ignored注解的方法、非公有方法和Object类方法及Object类重载方法将不被拦截器处理。

示例代码：

		// 创建自定义拦截器
        public class UserSessionChecker implements IInterceptor {
            public Object intercept(InterceptContext context) throws Exception {
                // 判断当前拦截器执行方向
                if (context.getDirection().equals(Direction.BEFORE)
                		&& WebContext.getRequest().getSession(false) == null) {
                    return View.redirectView("/user/login");
                }
                return null;
            }
        }

		@Controller
		@RequestMapping("/user")
		@Before(UserSessionChecker.class)
		public class Controller {

			@RequestMapping("/center")
			public IView userCenter() throws Exception {
				// ......
				return View.jspView("/user/center");
			}

			@RequestMapping("/login")
			@Clean
			public IView userLogin() throws Exception {
				return View.jspView("/user/login");
			}
		}

#### Cookies操作

WebMVC模块针对Cookies这个小甜点提供了一个名为CookieHelper的小工具类，支持Cookie参数的设置、读取和移除操作，同时支持对编码和加密处理，并允许通过配置参数调整Cookie策略；

##### Cookie配置参数

	#-------------------------------------
	# Cookie配置参数
	#-------------------------------------
	
	# Cookie键前缀，可选参数，默认值为空
	ymp.configs.webmvc.cookie_prefix=
	
	# Cookie作用域，可选参数，默认值为空
	ymp.configs.webmvc.cookie_domain=
	
	# Cookie作用路径，可选参数，默认值为'/'
	ymp.configs.webmvc.cookie_path=
	
	# Cookie密钥，可选参数，默认值为空
	ymp.configs.webmvc.cookie_auth_key=
	
	# Cookie密钥验证是否默认开启, 默认值为false
    ymp.configs.webmvc.default_enabled_cookie_auth=
    
    # Cookie是否默认使用HttpOnly, 默认值为false
    ymp.configs.webmvc.default_use_http_only=

##### 示例代码：演示Cookie操作

	// 创建CookieHelper对象
	CookieHelper _helper = CookieHelper.bind(WebContext.getContext().getOwner());

	// 设置开启采用密钥加密(将默认开启Base64编码)
	_helper.allowUseAuthKey();

	// 设置开启采用Base64编码(默认支持UrlEncode编码)
	_helper.allowUseBase64();

	// 设置开启使用HttpOnly
	_helper.allowUseHttpOnly();

	// 添加或重设Cookie，过期时间基于Session时效
	_helper.setCookie("current_username", "YMPer");

	// 添加或重设Cookie，并指定过期时间
	_helper.setCookie("current_username", "YMPer", 1800);

	// 获取Cookie值
	BlurObject _currUsername = _helper.getCookie("current_username");

	// 获取全部Cookie
	Map<String, BlurObject> _cookies = _helper.getCookies();

	// 移除Cookie
	_helper.removeCookie("current_username");

	// 清理所有的Cookie
	_helper.clearCookies();

#### 国际化（I18N）

基于YMPv2.0框架I18N支持，整合WebMVC模块并提供了默认II18NEventHandler接口实现，配置方法：

	// 指定WebMVC模块的I18N资源管理事件监听处理器
	ymp.i18n_event_handler_class=net.ymate.platform.webmvc.support.I18NWebEventHandler
	
	// 语言设置的参数名称，可选参数，默认为空
	ymp.params._lang=_lang
	
	// 资源文件存放路径，可选参数，默认为${root}/i18n/
	ymp.params.i18n_resources_home=${root}/i18n/

加载当前语言设置的步骤：

>  1. 尝试加载请求作用域中`_lang`参数；
>  2. 尝试加载框架自定义配置`ymp.params._lang`参数；
>  3. 尝试从Cookies里加载`_lang`的参数；
>  4. 使用系统默认语言设置；

#### 约定模式（Convention Mode）

**名词解释**：约定优于配置（Convention Over Configuration），也称作按约定编程，是一种软件设计范式，通过命名规则之类的约束来减少程序中的配置，旨在减少软件开发人员需要做决定的数量，获得简单的好处，而又不失灵活性。

有些时候我们仅仅是为了能够访问一个视图文件而不得不编写一个控制器方法与之对应，当这种重复性的工作很多时，就变成了灾难，因此，在WebMVC模块中，通过开启约定模式即可支持直接访问`base_view_path`路径下的视图文件，无需编写任何代码；

WebMVC模块的约定模式默认为关闭状态，需要通过配置参数开启：

	ymp.configs.webmvc.convention_mode=true

##### 访问权限规则配置

在约定模式模式下，支持设置不同路径的访问权限，规则是：`-`号代表禁止访问，`+`或无符串代表允许访问，多个路径间用`|`分隔；

访问权限示例：禁止访问admin目录和index.jsp文件，目录结构如下：

	WEB-INF\
	|
	|--templates\
	|	|
	|	+--admin\
	|	|
	|	+--users\
	|	|
	|	+--reports\
	|	|
	|	+--index.jsp
	|	|
	|	<...>

示例参数配置：

	ymp.configs.webmvc.convention_view_paths=admin-|index-|users|reports+

##### 拦截器规则配置

由于在约定模式下，访问视图文件无需控制器，所以无法通过控制器方法添加拦截器配置，因此，WebMVC模块针对约定模式单独提供了拦截器规则配置这一扩展功能，主要是通过@InterceptorRule配合IInterceptorRule接口使用；

拦截器规则设置默认为关闭状态，需要通过配置参数开启：

	ymp.configs.webmvc.convention_interceptor_mode=true

拦截规则配置示例：

	@InterceptorRule("/demo")
	@Before(WebUserSessionCheck.class)
	public class InterceptRuleDemo implements IInterceptorRule {

	    @InterceptorRule("/admin/*")
	    @Before(AdminTypeCheckFilter.class)
	    public void adminAll() {
	    }

	    @Clean
	    @InterceptorRule("/admin/login")
	    public void adminLogin() {
	    }

	    @InterceptorRule("/user/*")
	    public void userAll() {
	    }

	    @InterceptorRule("/mobile/person/*")
	    public void mobilePersonAll() {
	    }
	}

> 说明：
> 
> @InterceptorRule：拦截器规则注解；
> 
> - 在实现IInterceptorRule接口的类上声明，表示该类为拦截规则配置；
> - 在类方法上声明，表示针对一个具体的请求路径配置规则，与@RequestMapping的作用相似；
>
> 规则配置中支持的注解：
> 
> - @Before：约定模式下的拦截器仅支持@Before前置拦截；
> - @Clean：清理上层指定的拦截器；
> - @ContextParam：上下文参数；
> - @ResponseCache：声明控制器方法返回视图对象的执行结果将被缓存；

> **注意**：配置规则类的方法可以是任意的，方法本身无任何意义，仅是通过方法使用注解；

##### URL伪静态

WebMVC模块通过约定模式可以将参数融合在URL中，不再通过`?`传递参数，让URL看上去更好看一些；

伪静态模式默认为关闭状态，需要通过配置参数开启：

	ymp.configs.webmvc.convention_urlrewrite_mode=true

> 参数传递规则：
>
> - URL中通过分隔符`_`传递多个请求参数；
> - 通过`UrlParams[index]`方式引用参数值；

伪静态示例：

	URL原始格式：
	http://localhost:8080/user/info/list?type=all&page=2&page_size=15

	URL伪静态格式:
	http://localhost:8080/user/info/list_all_2_15

请求参数的引用：

	// 通过EL表达式获取参数
	${UrlParams[0]}：all
	${UrlParams[1]}：2
	${UrlParams[2]}：15

> **注意**：伪静态参数必须是连续的，UrlParams参数集合存储在Request作用域内；

##### 约定模式完整的配置参数

	#-------------------------------------
	# 约定模式配置参数
	#-------------------------------------
	
	# 是否开启视图自动渲染（约定优于配置，无需编写控制器代码，直接匹配并执行视图）模式，可选参数，默认值为false
	ymp.configs.webmvc.convention_mode=

	# Convention模式开启时视图文件路径(基于base_view_path的相对路径，'-'号代表禁止访问，'+'或无符串代表允许访问)，可选参数，默认值为空(即不限制访问路径)，多个路径间用'|'分隔
	ymp.configs.webmvc.convention_view_paths=

	# Convention模式开启时是否采用URL伪静态(URL中通过分隔符'_'传递多个请求参数，通过UrlParams[index]方式引用参数值)模式，可选参数，默认值为false
	ymp.configs.webmvc.convention_urlrewrite_mode=

	# Convention模式开启时是否采用拦截器规则设置，可选参数，默认值为false
	ymp.configs.webmvc.convention_interceptor_mode=

#### 高级特性

##### 控制器请求处理器

在WebMVC模块中除了支持标准Web请求的处理过程，同时也对基于XML和JSON协议格式的请求提供支持，有两种使用场景：

> 场景一：全局设置，将影响所有的控制器方法；

通过下面的参数配置，默认为default，可选值为[default|json|xml]，也可以是开发者自定义的IRequestProcessor接口实现类名称；

	ymp.configs.webmvc.request_processor_class=default

> 场景二：针对具体的控制器方法进行设置；

	@Controller
	@RequestMapping("/demo")
	public class DemoController {

	    @RequestMapping("/sayHi")
	    @RequestProcessor(JSONRequestProcessor.class)
	    public IView sayHi(@RequestParam String name， @RequestParam String content) {
	        return View.textView("Hi, " + name + ", Content: " + content);
	    }
	    
	    @RequestMapping("/sayHello")
	    @RequestProcessor(XMLRequestProcessor.class)
	    public IView sayHello(@RequestParam String name， @RequestParam String content) {
	        return View.textView("Hi, " + name + ", Content: " + content);
	    }
	}

通过POST方式向`http://localhost:8080/demo/sayHi`发送如下JSON数据：

	{ "name" : "YMPer", "content" : "Welcome!" }

通过POST方式向`http://localhost:8080/demo/sayHello`发送如下XML数据：

	<xml>
		<name>YMPer</name>
		<content><![CDATA[Welcome!]]></content>
	</xml>

> 以上JSON和XML这两种协议格式的控制器方法，同样支持参数的验证等特性；

##### 异常错误处理器

WebMVC模块为开发者提供了一个IWebErrorProcessor接口，允许针对异常、验证结果和约定模式的URL解析逻辑实现自定义扩展；

通过下面的参数配置即可：

	ymp.configs.webmvc.error_processor_class=net.ymate.framework.webmvc.WebErrorProcessor

示例代码：

	public class WebErrorProcessor implements IWebErrorProcessor {
	
		/**
	     * 异常时将执行事件回调
	     *
	     * @param owner 所属YMP框架管理器实例
	     * @param e     异常对象
	     */
	    public void onError(IWebMvc owner, Throwable e) {
	    	// ...你的代码逻辑
	    }

		/**
	     * @param owner   所属YMP框架管理器实例
	     * @param results 验证器执行结果集合
	     * @return 处理结果数据并返回视图对象，若返回null则由框架默认处理
	     */
	    public IView onValidation(IWebMvc owner, Map<String, ValidateResult> results) {
	    	// ...你的代码逻辑
	    	return View.nullView();
	    }

		/**
	     * 自定义处理URL请求过程
	     *
	     * @param owner          所属YMP框架管理器实例
	     * @param requestContext 请求上下文
	     * @return 可用视图对象，若为空则采用系统默认
	     * @throws Exception 可能产生的异常
	     */
	    public IView onConvention(IWebMvc owner, IRequestContext requestContext) throws Exception {
	    	// ...你的代码逻辑
	    	return View.nullView();
	    }
	}



