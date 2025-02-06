springboot天生支持使用hibernate validation对参数的优雅校验，如果不使用它，只能对参数挨个进行如下方式的手工校验，不仅难看，使用起来还很不方便：

``` java
if(StringUtils.isEmpty(userName)){
	throw new RuntimeException("用户名不能为空");
}
```

下面将介绍hibernate validation的基本使用方法。

## 一、引入依赖

这里在springboot 2.4.1中进行实验，引入以下依赖：

``` xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.1</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.16</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>6.1.6.Final</version>
    </dependency>
</dependencies>
```


##  二、基本请求参数校验

如下的一个spring mvc的请求调用中有一个id参数（Integer类型），如果不允许它为空，该怎么做

1. 在Controller上加上`@Validated`注解

2. 在需要校验的字段前面加上`@NotNull(message = "用户id不能为空")`注解

3. 定义全局异常处理类，定制化返回结果

``` java
   @RestControllerAdvice
   @Slf4j
   public class ValidationAdvice {
       
       @ExceptionHandler(Exception.class)
       @ResponseBody
       public WrapperResult handler(Exception e) {
           //获取异常信息,获取异常堆栈的完整异常信息
           StringWriter sw = new StringWriter();
           PrintWriter pw = new PrintWriter(sw);
           e.printStackTrace(pw);
           //日志输出异常详情
           log.error(sw.toString());
           return WrapperResult.faild("服务异常，请稍后再试");
       }
   
       @ExceptionHandler(ConstraintViolationException.class)
       @ResponseBody
       public WrapperResult handler(ConstraintViolationException e) {
           StringBuffer errorMsg = new StringBuffer();
           Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
           violations.forEach(x -> errorMsg.append(x.getMessage()).append(";"));
           return WrapperResult.faild(errorMsg.toString());
       }
   }
```

Controller层代码如下所示：

``` java
   @RestController
   @Slf4j
   @RequestMapping("/user")
   @Validated
   public class UserController {
   
       /**
        * 根据id查询用户信息
        *
        * @param id
        * @return
        */
       @GetMapping
       public WrapperResult<UserModel> findUser(@NotNull(message = "用户id不能为空")
                                                @RequestParam(value = "id")
                                                String id) {
           return WrapperResult.success(new UserModel());
       }
   }
```

如果发起请求`127.0.0.1:8080/user?id=` 则会返回结果

``` json
   {
       "status": 1,
       "data": "用户id不能为空;",
       "msg": "FAIL",
       "success": false
   }
```

## 三、对象内参数校验

上面是GET请求，下面介绍POST请求，请求对象内的参数校验。

### 1.Controller类上加上@Validated注解

``` java
@RestController
@Slf4j
@RequestMapping("/user")
**@Validated**
public class UserController {
}
```

### 2.在POST请求方法参数前面加上`@Validated `注解

``` java
    @PostMapping("/mobile-regist")
    public WrapperResult<Boolean> mobileRegit(@Validated @RequestBody UserModel userModel) {
        return WrapperResult.success(true);
    }
```

### 3.在上面介绍的`ValidationAdvice`类中加上对象参数校验异常捕获

``` java
//处理校验异常，对于对象类型的数据的校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WrapperResult handler(MethodArgumentNotValidException e) {
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(msg -> sb.append(msg.getDefaultMessage()).append(";"));
        return WrapperResult.faild(sb.toString());
    }
```

UserModel类的定义如下：

``` java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserModel {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "手机号不能为空")
//    @Mobile(message = "手机号格式不正确")
    private String mobile;

    @NotEmpty(message = "电子邮箱不能为空")
	@Email(message = "电子邮箱格式不正确")
    private String email;

    private String password;

    private String address;

    @NotNull(message = "年龄不能为空")
    @Min(value = 12, message = "允许注册年龄最小为12岁")
    @Max(value = 24, message = "允许年龄最大为24岁")
    private Integer age;

    @NotEmpty(message = "联系人不允许为空")
    @Size(min = 1, max = 3, message = "联系人长度只允许1到3之间")
    private List<String> contacts;
}
```

如果POST请求如下所示

``` json
{
    "name":"",
    "mobile":"12666666666",
    "email":"",
    "password":"",
    "address":"",
    "age": null,
    "contacts":[

    ]
}
```

则会返回如下定制化返回结果：

``` json
{
    "status": 1,
    "data": "电子邮箱不能为空;联系人长度只允许1到3之间;年龄不能为空;联系人不允许为空;姓名不能为空;手机号格式不正确;",
    "msg": "FAIL",
    "success": false
}
```

## 四、自定义校验器

像是@NotNull、@Email等注解都是hibernate validation 内置的注解，我们想开发像是@Email注解一样功能的注解，如何做呢，比如@Mobile，它的使用方法将和@Email一模一样。

首先，先定义一个工具类存放`ValidationUtil`两个常量值

``` java
public class ValidationUtil {
    //手机号校验正则
    public static final String MOBILE_REGX = "^[1][3-9][0-9]{9}$";

    public static final String MOBILE_MSG = "手机号格式错误";
}
```

### 1.定义注解`Mobile`

具体代码可以参考@Email的实现，直接将Email名字改成Mobile即可，如下所示：

``` java
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Mobile {

    String message() default ValidationUtil.MOBILE_MSG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default ValidationUtil.MOBILE_REGX;

    Pattern.Flag[] flags() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Mobile[] value();
    }
}
```

### 2.定义`MobileValidator`实现对参数的校验逻辑

``` java
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private String regexp;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        //获取校验的手机号的格式
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true;
        }
        return value.matches(regexp);
    }
}
```

### 3.使用方法和`@Email`一模一样

不赘述

## 五、分组校验

假设一个用户注册的场景，用户注册有三种方式

1. 用户名+图形验证码注册

2. 邮箱+邮箱验证码注册

3. 手机号+短信验证码注册

用户注册的时候除了方式不一样，其他用户信息基本相同，后端开了三个接口对应着着三种注册方式，请求体中我们使用一个Model封装了以上所有信息，包含着用户名、邮箱、手机号等信息，这时候不同的接口被调用，model中需要校验的参数就不一样了：

用户名注册的时候邮箱地址和手机号可以为空，但是用户名不能为空；通过邮箱注册的时候，邮箱地址不能为空，但是用户名和手机号可以为空；......

分组校验专门应对这种情况。

### 1.首先定义三个接口，表示三种组类别

``` java
public interface ValidEmail {
}

public interface ValidMobile {
}

public interface ValidUserName {
}

```

### 2.在UserModel实体类上指名组类别

``` java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserModel {

    @NotEmpty(message = "姓名不能为空", groups = {ValidUserName.class})
    @UserName(groups = {ValidUserName.class})
    private String name;

    @NotEmpty(message = "手机号不能为空", groups = {ValidMobile.class})
    @Mobile(groups = {ValidMobile.class})
    private String mobile;

    @NotEmpty(message = "电子邮箱不能为空", groups = {ValidEmail.class})
    @Email(message = "电子邮箱格式不正确", groups = {ValidEmail.class})
    private String email;

    private String password;

    private String address;

    @NotNull(message = "年龄不能为空")
    @Min(value = 12, message = "允许注册年龄最小为12岁", groups = {ValidEmail.class,ValidMobile.class,ValidUserName.class})
    @Max(value = 24, message = "允许年龄最大为24岁",groups = {ValidEmail.class,ValidMobile.class,ValidUserName.class})
    private Integer age;

    @NotEmpty(message = "联系人不允许为空",groups = {ValidEmail.class,ValidMobile.class,ValidUserName.class})
    @Size(min = 1, max = 3, message = "联系人长度只允许1到3之间",groups = {ValidEmail.class,ValidMobile.class,ValidUserName.class})
    private List<String> contacts;
}
```

### 3.Controller方法上指名验证组别

``` java
    /**
     * 手机号注册
     *
     * @param userModel
     * @return
     */
    @PostMapping("/mobile-regist")
    public WrapperResult<Boolean> mobileRegit(@Validated(ValidMobile.class) @RequestBody UserModel userModel) {
        return WrapperResult.success(true);
    }
```

这时候进行如下请求：

POST http://127.0.0.1:8080/user/mobile-regist

``` json
{
    "mobile":"12666666666",
    "password":"",
    "address":"",
    "age": null,
    "contacts":[

    ]

}
```

则会返回结果：

``` json
{
    "status": 1,
    "data": "联系人长度只允许1到3之间;手机号格式错误;联系人不允许为空;",
    "msg": "FAIL",
    "success": false
}
```

该请求中并没有传递email和username字段，而且结果中也未校验出这两个字段，符合预期结果。

## 六、手动校验

此处的手动校验并非是使用if/else进行简单的手动校验，而是使用Validation自带的校验工具对使用了@NotNull等注解的实体对象进行属性校验。

首先先获取Valiation对象：

``` java
private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
```

### 1. 全属性校验

``` java
/**
  * 验证某个对象所有字段
  *
  * @param obj
  * @param <T>
  * @return
  */
public static <T> ValidationResult validateEntity(T obj) {
    ValidationResult result = new ValidationResult();
    Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
    if (!CollectionUtils.isEmpty(set)) {
        result.setHasErrors(true);
        Map<String, String> errorMsg = new HashMap<>();
        for (ConstraintViolation<T> cv : set) {
            errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
        }
        result.setErrorMsg(errorMsg);
    }
    return result;
}
```

### 2.某个字段的单独校验

``` java
/**
* 验证某个对象某个字段
*
* @param obj
* @param propertyName
* @param <T>
* @return
*/
public static <T> ValidationResult validateProperty(T obj, String propertyName) {
    ValidationResult result = new ValidationResult();
    Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
    if (!CollectionUtils.isEmpty(set)) {
        result.setHasErrors(true);
        Map<String, String> errorMsg = new HashMap<>();
        for (ConstraintViolation<T> cv : set) {
            errorMsg.put(propertyName, cv.getMessage());
        }
        result.setErrorMsg(errorMsg);
    }
    return result;
}
```

ValidationResult的定义如下：

``` java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ValidationResult {
    private Boolean hasErrors;
    private Map<String, String> errorMsg;
}
```

## 七、文件上传校验

### 1.tomcat容器下文件上传校验

在springboot+tomcat架构下的文件上传校验，假如已经有了如下的配置：

``` yaml
spring:
    servlet:
    multipart:
      max-file-size: 1MB
      max-request-size: 1MB
```

这表示只允许上传小于1MB大小的文件，如果不指定异常处理器，默认会报前端400，在`ValidationAdvice`类中添加如下代码可以自定义返回结果：

``` java
    //文件上传文件大小超出限制
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public WrapperResult<Map<String,Object>> fileSizeException(MaxUploadSizeExceededException exception) {
        log.error("文件太大，上传失败",exception);
        return WrapperResult.faild("只允许上传不大于"+exception.getMaxUploadSize()+"的文件");
    }
```

### 2.其它容器

在Jetty容器中1中的方法可能会失效，未验证；在undertow容器中是一定会失效，已经验证。undertow容器毕竟和spring-boot没有完全打磨好，不建议现阶段使用。

## 八、附录

### 1.所有校验规则注解说明

| 注解                     | 说明                                             |
| ------------------------ | ------------------------------------------------ |
| @Null                    | 被注解的元素必须为空                             |
| @NotNull                 | 被注解的元素必须不为空                           |
| @AssertTrue              | 被注解的元素必须为true                           |
| @AssertFlase             | 被注解的元素必须为false                          |
| @Min(value)              | 被注解的元素必须是数字，且必须大于指定的最小值   |
| @Max(value)              | 被注解的元素必须是数字，且必须小于指定的最大值   |
| @DecimalMin(value)       | 被注解的元素必须是数字，且必须大于指定的最小值   |
| @DecaimalMax(value)      | 被注解的元素必须是数字，且必须小于指定的最大值   |
| @Size(max=,min=)         | 被注解元素的大小必须在指定的范围内               |
| @Digit(integer,fraction) | 被注解元素必须是数字，且其值必须在可接受的范围内 |
| @Past                    | 被注解元素必须是一个过去的日期                   |
| @Futrue                  | 被注解元素必须是一个将来的日期                   |
| @Pattern(regex=,flag=)   | 被注解元素必须符合指定的正则表达式               |
| @NotBlank                | 验证非空，且长度必须大于0                        |
| @Email                   | 被注解的元素必须是电子邮件地址                   |
| @Length(max=,min=)       | 被注解的字符串大小必须在指定的范围内             |
| @NotEmpty                | 被注解的字符串必须非空                           |
| @Range(max=,min=)        | 被注解的元素必须在指定范围内                     |

### 2.校验规则注解例子

``` java
// 空和非空检查: @Null、@NotNull、@NotBlank、@NotEmpty
@Null(message = "验证是否为 null")
private Integer isNull;

@NotNull(message = "验证是否不为 null, 但无法查检长度为0的空字符串")
private Integer id;

@NotBlank(message = "检查字符串是不是为 null，以及去除空格后长度是否大于0")
private String name;
          
@NotEmpty(message = "检查是否为 NULL 或者是 EMPTY")
private List<String> stringList;
          
// Boolean值检查: @AssertTrue、@AssertFalse
@AssertTrue(message = " 验证 Boolean参数是否为 true")
private Boolean isTrue;
          
@AssertFalse(message = "验证 Boolean 参数是否为 false ")
private Boolean isFalse;
          
// 长度检查: @Size、@Length
@Size(min = 1, max = 2, message = "验证（Array,Collection,Map,String）长度是否在给定范围内")
private List<Integer> integerList;
      
@Length(min = 8, max = 30, message = "验证字符串长度是否在给定范围内")
private String address;
      
// 日期检查: @Future、@FutureOrPresent、@Past、@PastOrPresent
@Future(message = "验证日期是否在当前时间之后")
private Date futureDate;
      
@FutureOrPresent(message = "验证日期是否为当前时间或之后")
private Date futureOrPresentDate;
      
@Past(message = "验证日期是否在当前时间之前")
private Date pastDate;
      
@PastOrPresent(message = "验证日期是否为当前时间或之前")
private Date pastOrPresentDate;
      
// 其它检查: @Email、@CreditCardNumber、@URL、@Pattern、
@ScriptAssert、@UniqueElements
@Email(message = "校验是否为正确的邮箱格式")
private String email;
      
@CreditCardNumber(message = "校验是否为正确的信用卡号")
private String creditCardNumber;
      
@URL(protocol = "http", host = "127.0.0.1", port = 8080, message= "校验是否为正确的URL地址")
private String url;
      
@Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$", message = "正则校验是否为正确的手机号")
private String phone;
         
// 对关联对象元素进行递归校验检查
@Valid
@UniqueElements(message = "校验集合中的元素是否唯一")
private List<CalendarEvent> calendarEvent;

@Data
@ScriptAssert(lang = "javascript", script ="_this.startDate.before(_this.endDate)",message = "通过脚本表达式校验参数")
private class CalendarEvent {
  private Date startDate;
  private Date endDate;
}

// 数值检查: @Min、@Max、@Range、@DecimalMin、@DecimalMax、@Digits
@Min(value = 0, message = "验证数值是否大于等于指定值")
@Max(value = 100, message = "验证数值是否小于等于指定值")
@Range(min = 0, max = 100, message = "验证数值是否在指定值区间范围内")
private Integer score;

@DecimalMin(value = "10.01", inclusive = false, message = "验证数值是否大于等于指定值")
@DecimalMax(value = "199.99", message = "验证数值是否小于等于指定值")
@Digits(integer = 3, fraction = 2, message = "限制整数位最多为3，小数位最多为2")
private BigDecimal money;
```

## 九、源代码地址

https://gitee.com/kdyzm/validation-spring-boot-demo

我的博客地址：https://blog.kdyzm.cn 欢迎留言指教~



