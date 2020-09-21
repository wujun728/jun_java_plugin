### 验证（Validation）

验证模块是服务端参数有效性验证工具，采用注解声明方式配置验证规则，更简单、更直观、更友好，支持方法参数和类成员属性验证，支持验证结果国际化I18N资源绑定，支持自定义验证器，支持多种验证模式；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-validation</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：在项目的pom.xml中添加上述配置，该模块已经默认引入核心包依赖，无需重复配置。


#### 默认验证器及参数说明

##### @VCompare

​比较两个参数值，使用场景如：新密码与重复新密码两参数值是否一致的比较；

   >cond：比较条件，可选EQ和NOT_EQ，默认为EQ；
   >
   >with：与之比较的参数名称；
   >
   >withLabel：与之比较的参数标签名称 (用于在验证消息里显示的名称)，默认为空；
   >
   >msg：自定义验证消息，默认为空；

##### @VDateTime

日期类型参数验证；

   >pattern：日期格式字符串，默认为yyyy-MM-dd HH:mm:ss
   >
   >msg：自定义验证消息，默认为空；

##### @VEmail

邮箱地址格式验证；

   >msg：自定义验证消息，默认为空；

##### @VLength

字符串长度验证；

   >min：设置最小长度，0为不限制；
   >max：设置最大长度，0为不限制；
   >msg：自定义验证消息，默认为空；

##### @VNumeric

数值类型参数验证；

   >min：设置最小值，0为不限制；
   >max：设置最大值，0为不限制；
   >msg：自定义验证消息，默认为空；

##### @VRegex

正则表达式验证；

   >regex：正则表达式；
   >
   >msg：自定义验证消息，默认为空；

##### @VRequried

必填项验证；

   >msg：自定义验证消息，默认为空；

**注**：

- 以上注解中的msg参数即可以是输出的消息内容，也可以是国际化资源文件中的键；
- 验证器是按注解声明的顺序执行的，请一定要注意！！！

#### 默认国际化资源文件内容

验证框架的默认国际化资源文件名称为 **validation.properties**，其内容如下：

    ymp.validation.compare_not_eq={0} can not eq {1}.
    ymp.validation.compare_eq={0} must be eq {1}.

    ymp.validation.datetime={0} not a valid datetime.

    ymp.validation.email={0} not a valid email address.

    ymp.validation.length_between={0} length must be between {1} and {2}.
    ymp.validation.length_min={0} length must be gt {1}.
    ymp.validation.length_max={0} length must be lt {1}.

    ymp.validation.numeric={0} not a valid numeric.
    ymp.validation.numeric_between={0} numeric must be between {1} and {2}.
    ymp.validation.numeric_min={0} numeric must be gt {1}.
    ymp.validation.numeric_max={0} numeric must be lt {1}.

    ymp.validation.regex={0} regex not match.

    ymp.validation.requried={0} must be requried.

#### 验证框架使用示例

- 示例代码：

        @Validation(mode = Validation.MODE.FULL)
        public class UserBase {

            @VRequried(msg = "{0}不能为空")
            @VLength(min = 3, max = 16, msg = "{0}长度必须在3到16之间")
            @VField(label = "用户名称")
            private String username;

            @VRequried
            @VLength(max = 32)
            private String password;

            @VRequried
            @VCompare(cond = VCompare.Cond.EQ, with = "password")
            private String repassword;

            @VModel
            @VField(name = "ext")
            private UserExt userExt;

            //
            // 此处省略了Get/Set方法
            //
        }

        public class UserExt {

            @VLength(max = 10)
            private String sex;

            @VRequried
            @VNumeric(min = 18, max = 30)
            private int age;

            @VRequried
            @VEmail
            private String email;

            //
            // 此处省略了Get/Set方法
            //
        }

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                Map<String, Object> _params = new HashMap<String, Object>();
                _params.put("username", "lz");
                _params.put("password", 1233);
                _params.put("repassword", "12333");
                _params.put("ext.age", "17");
                _params.put("ext.email", "@163.com");

                Map<String, ValidateResult> _results = Validations.get().validate(UserBase.class, _params);
                //
                for (Map.Entry<String, ValidateResult> _entry : _results.entrySet()) {
                    System.out.println(_entry.getValue().getMsg());
                }
            } finally {
                YMP.get().destroy();
            }
        }

- 执行结果：

        username : 用户名称长度必须在3到16之间
        repassword : repassword must be eq password.
        ext.age : ext.age numeric must be between 30 and 18.
        ext.email : ext.email not a valid email address.

> 功能注解说明：

> - `@Validation`：验证模式配置，默认为NORMAL；
> 		+ NORMAL - 短路式验证，即出现验证未通过就返回验证结果；
>		+ FULL   - 对目标对象属性进行全部验证后返回全部验证结果；
>
> - `@VField`：自定义待验证的成员或方法参数名称；
>   	+ name：自定义参数名称，在嵌套验证时上下层参数以'.'分隔；
>   	+ label：自定义参数标签名称，若@VField嵌套使用时功能将不可用；
> 
> - `@VModel`：声明目标对象是否为JavaBean对象，将执行对象嵌套验证；

#### 自定义验证器

写代码前先了解一个新的注解`@Validator`，它的作用是声明一个类为验证器，它的参数需要绑定自定义验证器对应的注解，这个注解的作用与`@VRequried`等注解是一样的，开发人员可以通过该注解配置验证规则；

本例中，我们创建一个简单的自定义验证器，用来验证当前用户输入的邮箱地址是否已存在；

- 创建自定义验证器注解：

        @Target({ElementType.FIELD, ElementType.PARAMETER})
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        public @interface VEmailCanUse {

            /**
             * @return 自定义验证消息
             */
            String msg() default "";
        }

- 实现IValidator接口并声明@Validator注解：

        @Validator(VEmailCanUse.class)
        public class EmailCanUseValidator implements IValidator {

            public ValidateResult validate(ValidateContext context) {
                ValidateResult _result = null;
                if (context.getParamValue() != null) {
                    // 假定邮箱地址已存在
                    VEmailCanUse _anno = (VEmailCanUse) context.getAnnotation();
                    _result = new ValidateResult(context.getParamName(), StringUtils.defaultIfBlank(_anno.msg(), "邮箱地址已存在"));
                }
                return _result;
            }
        }

- 测试代码：

        public class VEmailCanUseBean {

            @VRequried
            @VEmail
            @VEmailCanUse
            private String email;

            //
            // 此处省略了Get/Set方法
            //
        }

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                Map<String, Object> _params = new HashMap<String, Object>();
                _params.put("ext.email", "demo@163.com");

                Map<String, ValidateResult> _results = Validations.get().validate(VEmailCanUseBean.class, _params);
                //
                for (Map.Entry<String, ValidateResult> _entry : _results.entrySet()) {
                    System.out.println(_entry.getKey() + " : " + _entry.getValue().getMsg());
                }
            } finally {
                YMP.get().destroy();
            }
        }

- 执行结果：

        ext.email : 邮箱地址已存在
