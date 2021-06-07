# ShiroFilterChainDefinitionSpringBoot中使用Shiro和JWT做认证和鉴权

[![img](https://upload.jianshu.io/users/upload_avatars/13282795/75bd5d79-2994-494a-a2eb-ee3717b77e7b.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96/format/webp)](https://www.jianshu.com/u/d00590abcb80)

[空挡](https://www.jianshu.com/u/d00590abcb80)关注


最近新做的项目中使用了shiro和jwt来做简单的权限验证，在和springboot集成的过程中碰到了不少坑。做完之后对shiro的体系架构了解的也差不多了，现在把中间需要注意的点放出来，给大家做个参考。
相对于spring security来说，shiro出来较早，框架也相对简单。后面会另起一篇文章对这两个框架做一个简单的对比。

### Shiro的关注点

首先看一下shiro中需要关注的几个概念。

- SecurityManager，可以理解成控制中心，所有请求最终基本上都通过它来代理转发，一般我们程序中不需要直接跟他打交道。
- Subject ，请求主体。比如登录用户，比如一个被授权的app。在程序中任何地方都可以通过`SecurityUtils.getSubject()`获取到当前的subject。subject中可以获取到Principal，这个是subject的标识，比如登陆用户的用户名或者id等，shiro不对值做限制。但是在登录和授权过程中，程序需要通过principal来识别唯一的用户。
- Realm，这个实在不知道怎么翻译合适。通俗一点理解就是realm可以访问安全相关数据，提供统一的数据封装来给上层做数据校验。shiro的建议是每种数据源定义一个realm，比如用户数据存在数据库可以使用JdbcRealm；存在属性配置文件可以使用PropertiesRealm。一般我们使用shiro都使用自定义的realm。
  当有多个realm存在的时候，shiro在做用户校验的时候会按照定义的策略来决定认证是否通过，shiro提供的可选策略有一个成功或者所有都成功等。
  一个realm对应了一个CredentialsMatcher，用来做用户提交认证信息和realm获取得用户信息做比对，shiro已经提供了常用的比如用户密码和存储的Hash后的密码的对比。

### JWT的应用场景

关于JWT是什么，请参考[JWT官网](https://jwt.io/introduction/)。这里就不多解释了，可理解为使用带签名的token来做用户和权限验证，现在流行的公共开放接口用的OAuth 2.0协议基本也是类似的套路。这里只是说下选择使用jwt不用session的原因。
首先，是要支持多端，一个api要支持H5, PC和APP三个前端，如果使用session的话对app不是很友好，而且session有跨域攻击的问题。
其次，后端的服务是无状态的，所以要支持分布式的权限校验。当然这个不是主要原因了，因为session持久化在spring里面也就是加一行注解就解决的问题。不过，spring通过代理httpsession来做，总归觉得有点复杂。

### 项目搭建

#### 需求

需求相对简单，1）支持用户首次通过用户名和密码登录；2）登录后通过http header返回token；3）每次请求，客户端需通过header将token带回，用于权限校验；4）服务端负责token的定期刷新，刷新后新的token仍然放到header中返给客户端

#### pom.xml

这里使用了shiro的web starter。jwt是用的auth0的工具包，其实自己实现也比较简单，我们这里就不自己重新造轮子了。



```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.github.springboot</groupId>
    <artifactId>shiro-jwt-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>Spring Boot with Shiro and JWT Demo</name>
    <description>Demo project for Spring Boot with Shiro and JWT</description>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
    </parent>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <shiro.spring.version>1.4.0</shiro.spring.version>
        <jwt.auth0.version>3.2.0</jwt.auth0.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- 使用redis做数据缓存，如果不需要可不依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring-boot-web-starter</artifactId>
            <version>${shiro.spring.version}</version>
        </dependency>
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>${jwt.auth0.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.5</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.7</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

#### shiro 配置

**ShiroConfiguration**
首先是初始化shiro的bean，主要是初始化Realm，注册Filter，定义filterChain。这些配置的用处后面会逐渐讲到。



```java
@Configuration
public class ShiroConfig {
    /**
     * 注册shiro的Filter，拦截请求
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager,UserService userService) throws Exception{
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter)shiroFilter(securityManager, userService).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);

        return filterRegistration;
    }

    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator(UserService userService) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
    * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
    * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
    */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }
    /**
    * 用于用户名密码登录时认证的realm
    */
    @Bean("dbRealm")
    public Realm dbShiroRealm(UserService userService) {
        DbShiroRealm myShiroRealm = new DbShiroRealm(userService);
        return myShiroRealm;
    }
    /**
    * 用于JWT token认证的realm
    */
    @Bean("jwtRealm")
    public Realm jwtShiroRealm(UserService userService) {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm(userService);
        return myShiroRealm;
    }

    /**
     * 设置过滤器，将自定义的Filter加入
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserService userService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter(userService));
        filterMap.put("anyRole", createRolesFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        return factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");  //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        chainDefinition.addPathDefinition("/image/**", "anon");
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]"); //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken"); // 默认进行用户鉴权
        return chainDefinition;
    }
    //注意不要加@Bean注解，不然spring会自动注册成filter
    protected JwtAuthFilter createAuthFilter(UserService userService){
        return new JwtAuthFilter(userService);
    }
    //注意不要加@Bean注解，不然spring会自动注册成filter
    protected AnyRolesAuthorizationFilter createRolesFilter(){
        return new AnyRolesAuthorizationFilter();
    }

}
```

### 校验流程

我们使用Shiro主要做3件事情，1）用户登录时做用户名密码校验；2）用户登录后收到请求时做JWT Token的校验；3）用户权限的校验

#### 登录认证流程

**登录controller**
从前面的`ShiroFilterChainDefinition`配置可以看出，对于登录请求，Filter直接放过，进到controller里面。Controller会调用shiro做用户名和密码的校验，成功后返回token。



```java
@PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody UserDto loginInfo, HttpServletRequest request, HttpServletResponse response){      
        Subject subject = SecurityUtils.getSubject();
        try {
            //将用户请求参数封装后，直接提交给Shiro处理
            UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword());
            subject.login(token);
            //Shiro认证通过后会将user信息放到subject内，生成token并返回
            UserDto user = (UserDto) subject.getPrincipal();
            String newToken = userService.generateJwtToken(user.getUsername());
            response.setHeader("x-auth-token", newToken);
            
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) { 
           // 如果校验失败，shiro会抛出异常，返回客户端失败
            logger.error("User {} login fail, Reason:{}", loginInfo.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
```

**登录的Realm**
从上面的controller实现我们看到，controller只负责封装下参数，然后扔给Shiro了，这时候Shiro收到后，会到所有的realm中找能处理`UsernamePasswordToken`的Realm（我们这里是DbShiroRealm），然后交给Realm处理。Realm的实现一般直接继承AuthorizingRealm即可，只需要实现两个方法，doGetAuthenticationInfo()会在用户验证时被调用，我们看下实现。



```java
public class DbShiroRealm extends AuthorizingRealm {
    //数据库存储的用户密码的加密salt，正式环境不能放在源代码里
    private static final String encryptSalt = "F12839WhsnnEV$#23b";
    private UserService userService;
    
    public DbShiroRealm(UserService userService) {
        this.userService = userService;
        //因为数据库中的密码做了散列，所以使用shiro的散列Matcher
        this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
    }
    /**
     *  找它的原因是这个方法返回true
     */ 
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
   /**
    *  这一步我们根据token给的用户名，去数据库查出加密过用户密码，然后把加密后的密码和盐值一起发给shiro，让它做比对
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken)token;
        String username = userpasswordToken.getUsername();
        UserDto user = userService.getUserInfo(username);
        if(user == null)
            throw new AuthenticationException("用户名或者密码错误");
        
        return new SimpleAuthenticationInfo(user, user.getEncryptPwd(), ByteSource.Util.bytes(encryptSalt), "dbRealm");
    }

}
```

我们可以看到`doGetAuthenticationInfo`里面只判断了用户存不存在，其实也没做密码比对，只是把数据库的数据封装一下就返回了。真正的比对逻辑在Matcher里实现的，这个shiro已经替我们实现了。如果matcher返回false，shiro会抛出异常，这样controller那边就会知道验证失败了。
**登出**
登出操作就比较简单了，我们只需要把用户登录后保存的salt值清除，然后调用shiro的logout就可以了，shiro会将剩下的事情做完。



```java
    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
            UserDto user = (UserDto)subject.getPrincipals().getPrimaryPrincipal();
            userService.deleteLoginInfo(user.getUsername());
        }
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok().build();
    }
```

这样整个登录/登出就结束了，我们可以看到shiro对整个逻辑的拆解还是比较清楚的，各个模块各司其职。

### 请求认证流程

请求认证的流程其实和登录认证流程是比较相似的，因为我们的服务是无状态的，所以每次请求带来token，我们就是做了一次登录操作。
**JwtAuthFilter**
首先我们先从入口的Filter开始。从`AuthenticatingFilter`继承，重写isAccessAllow方法，方法中调用父类executeLogin()。父类的这个方法首先会createToken()，然后调用shiro的`Subject.login()`方法。是不是跟`LoginController`中的逻辑很像。



```java
public class JwtAuthFilter extends AuthenticatingFilter {
    /**
     * 父类会在请求进入拦截器后调用该方法，返回true则继续，返回false则会调用onAccessDenied()。这里在不通过时，还调用了isPermissive()方法，我们后面解释。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(this.isLoginRequest(request, response))
            return true;
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch(IllegalStateException e){ //not found any token
            log.error("Not found any token");
        }catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }
    /**
     * 这里重写了父类的方法，使用我们自己定义的Token类，提交给shiro。这个方法返回null的话会直接抛出异常，进入isAccessAllowed（）的异常处理逻辑。
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwtToken = getAuthzHeader(servletRequest);
        if(StringUtils.isNotBlank(jwtToken)&&!JwtUtils.isTokenExpired(jwtToken))
            return new JWTToken(jwtToken);

        return null;
    }
    /**
      * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
      */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        return false;
    }
    /**
     *  如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if(token instanceof JWTToken){
            JWTToken jwtToken = (JWTToken)token;
            UserDto user = (UserDto) subject.getPrincipal();
            boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
            if(shouldRefresh) {
                newToken = userService.generateJwtToken(user.getUsername());
            }
        }
        if(StringUtils.isNotBlank(newToken))
            httpResponse.setHeader("x-auth-token", newToken);

        return true;
    }
    /**
      * 如果调用shiro的login认证失败，会回调这个方法，这里我们什么都不做，因为逻辑放到了onAccessDenied（）中。
      */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }
}
```

**JWT token封装**
在上面的Filter中我们创建了一个Token提交给了shiro，我们看下这个Token，其实很简单，就是把jwt的token放在里面。



```java
public class JWTToken implements HostAuthenticationToken {
    private String token;
    private String host;
    public JWTToken(String token) {
        this(token, null);
    }
    public JWTToken(String token, String host) {
        this.token = token;
        this.host = host;
    }
    public String getToken(){
        return this.token;
    }
    public String getHost() {
        return host;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
    @Override
    public String toString(){
        return token + ':' + host;
    }
}
```

**JWT Realm**
Token有了，filter中也调用了shiro的login()方法了，下一步自然是Shiro把token提交到Realm中，获取存储的认证信息来做比对。



```java
public class JWTShiroRealm extends AuthorizingRealm {
    protected UserService userService;

    public JWTShiroRealm(UserService userService){
        this.userService = userService;
        //这里使用我们自定义的Matcher
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }
    /**
     * 限定这个Realm只支持我们自定义的JWT Token
    */ 
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 更controller登录一样，也是获取用户的salt值，给到shiro，由shiro来调用matcher来做认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();
        
        UserDto user = userService.getJwtTokenInfo(JwtUtils.getUsername(token));
        if(user == null)
            throw new AuthenticationException("token过期，请重新登录");

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getSalt(), "jwtRealm");

        return authenticationInfo;
    }
}
```

**JWT Matcher**
跟controller登录不一样，shiro并没有实现JWT的Matcher，需要我们自己来实现。代码如下：



```java
public class JWTCredentialsMatcher implements CredentialsMatcher {
    /**
     * Matcher中直接调用工具包中的verify方法即可
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String) authenticationToken.getCredentials();
        Object stored = authenticationInfo.getCredentials();
        String salt = stored.toString();

        UserDto user = (UserDto)authenticationInfo.getPrincipals().getPrimaryPrincipal();
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", user.getUsername())
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            log.error("Token Error:{}", e.getMessage());
        }
        return false;
    }
}
```

这样非登录请求的认证处理逻辑也结束了，看起来是不是跟登录逻辑差不多。其实对于无状态服务来说，每次请求都相当于做了一次登录操作，我们用session的时候之所以不需要做，是因为容器代替我们把这件事干掉了。
**关于permissive**
前面Filter里面的isAccessAllow方法，除了使用jwt token做了shiro的登录认证之外，如果返回false还会额外调用isPermissive()方法。这里面干了什么呢？我们看下父类的方法：



```java
    /**
     * Returns <code>true</code> if the mappedValue contains the {@link #PERMISSIVE} qualifier.
     *
     * @return <code>true</code> if this filter should be permissive
     */
    protected boolean isPermissive(Object mappedValue) {
        if(mappedValue != null) {
            String[] values = (String[]) mappedValue;
            return Arrays.binarySearch(values, PERMISSIVE) >= 0;
        }
        return false;
    }
```

逻辑很简单，如果filter的拦截配置那里配置了permissive参数，即使登录认证没通过，因为isPermissive返回true，还是会让请求继续下去的。细心的同学或许已经发现我们之前shiroConfig里面的配置了，截取过来看一下：



```java
chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
```

就是这么简单直接，字符串匹配。当然这里也可以重写这个方法插入更复杂的逻辑。
这么做的目的是什么呢？因为有时候我们对待请求，并不都是非黑即白，比如登出操作，如果用户带的token是正确的，我们会将保存的用户信息清除；如果带的token是错的，也没关系，大不了不干啥，没必要返回错误给用户。还有一个典型的案例，比如我们阅读博客，匿名用户也是可以看的。只是如果是登录用户，我们会显示额外的东西，比如是不是点过赞等。所以认证这里的逻辑就是token是对的，我会给把人认出来；是错的，我也直接放过，留给controller来决定怎么区别对待。
**JWT Token刷新**
前面的Filter里面还有一个逻辑（是不是太多了😓），就是如果用户这次的token校验通过后，我们还会顺便看看token要不要刷新，如果需要刷新则将新的token放到header里面。
这样做的目的是防止token丢了之后，别人可以拿着一直用。我们这里是固定时间刷新。安全性要求更高的系统可能每次请求都要求刷新，或者是每次POST，PUT等修改数据的请求后必须刷新。判断逻辑如下：



```java
protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }
```

以上就是jwt token校验的所有逻辑了，是不是有点绕，画一个流程图出来，对比着看应该更清楚一点。



![img](https://upload-images.jianshu.io/upload_images/13282795-0e7d8ee5d4af35e3.png?imageMogr2/auto-orient/strip|imageView2/2/w/1051/format/webp)

jwt filter逻辑

#### 角色配置

认证讲完了，下面看下访问控制。对于角色检查的拦截，是通过继承一个`AuthorizationFilter`的Filter来实现的。Shiro提供了一个默认的实现`RolesAuthorizationFilter`，比如可以这么配置：



```java
chainDefinition.addPathDefinition("/article/edit", "authc,role[admin]");
```

表示要做文章的edit操作，需要满足两个条件，首先authc表示要通过用户认证，这个我们上面已经讲过了；其次要具备admin的角色。shiro是怎么做的呢？就是在请求进入这个filter后，shiro会调用所有配置的Realm获取用户的角色信息，然后和Filter中配置的角色做对比，对上了就可以通过了。
所以我们所有的Realm还要另外一个方法`doGetAuthorizationInfo`，不得不吐槽一下，realm里面要实现的这两个方法的名字实在太像了。
在JWT Realm里面，因为没有存储角色信息，所以直接返回空就可以了：



```java
@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }
```

在DbRealm里面，实现如下：



```java
@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserDto user = (UserDto) principals.getPrimaryPrincipal();
        List<String> roles = user.getRoles();
        if(roles == null) {
            roles = userService.getUserRoles(user.getUserId());
            user.setRoles(roles);
        }
        if (roles != null)
            simpleAuthorizationInfo.addRoles(roles);

        return simpleAuthorizationInfo;
    }
```

这里需要注意一下的就是Shiro默认不会缓存角色信息，所以这里调用service的方法获取角色强烈建议从缓存中获取。
**自己实现RoleFilter**
在实际的项目中，对同一个url多个角色都有访问权限很常见，shiro默认的RoleFilter没有提供支持，比如上面的配置，如果我们配置成下面这样，那用户必须同时具备admin和manager权限才能访问，显然这个是不合理的。



```java
chainDefinition.addPathDefinition("/admin/**", "authc,role[admin,manager]");
```

所以自己实现一个role filter，只要任何一个角色符合条件就通过，只需要重写`AuthorizationFilter`中两个方法就可以了：



```java
public class AnyRolesAuthorizationFilter  extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问
            return true;
        }
        for (String role : rolesArray) {
            if (subject.hasRole(role)) //若当前用户是rolesArray中的任何一个，则有权限访问
                return true;
        }
        return false;
    }
    /**
     * 权限校验失败，错误处理
    */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        return false;
    }

}
```

### 禁用session

因为用了jwt的访问认证，所以要把默认session支持关掉。这里要做两件事情，一个是`ShiroConfig`里面的配置：



```java
   @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }
```

另外一个是在对请求加上`noSessionCreationFilter`,具体原因上面的代码中已经有解释，用法如下：



```java
chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken");
```

#### 跨域支持

对于前后端分离的项目，一般都需要跨域访问，这里需要做两件事，一个是在JwtFilter的postHandle中在头上加上跨域支持的选项（理论上应该重新定义一个Filter的，图省事就让它多干点吧😓）。



```java
@Override
    protected void postHandle(ServletRequest request, ServletResponse response){
        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
    }
```

在实际使用中发现，对于controller返回@ResponseBody的请求，filter中添加的header信息会丢失。对于这个问题spring已经给出解释，并建议实现ResponseBodyAdvice类，并添加@ControllerAdvice。

> public interface ResponseBodyAdvice<T>
> Allows customizing the response after the execution of an @ResponseBody or a ResponseEntity controller method but >before the body is written with an HttpMessageConverter.
> Implementations may be registered directly with RequestMappingHandlerAdapter and ExceptionHandlerExceptionResolver or more likely annotated with @ControllerAdvice in which case they will be auto-detected by both.

所以如果存在返回@ResponseBody的controller，需要添加一个`ResponseBodyAdvice`实现类



```java
@ControllerAdvice
public class ResponseHeaderAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest serverRequest = (ServletServerHttpRequest)serverHttpRequest;
        ServletServerHttpResponse serverResponse = (ServletServerHttpResponse)serverHttpResponse;
        if(serverRequest == null || serverResponse == null
                || serverRequest.getServletRequest() == null || serverResponse.getServletResponse() == null) {
            return o;
        }

        // 对于未添加跨域消息头的响应进行处理
        HttpServletRequest request = serverRequest.getServletRequest();
        HttpServletResponse response = serverResponse.getServletResponse();
        String originHeader = "Access-Control-Allow-Origin";
        if(!response.containsHeader(originHeader)) {
            String origin = request.getHeader("Origin");
            if(origin == null) {
                String referer = request.getHeader("Referer");
                if(referer != null)
                    origin = referer.substring(0, referer.indexOf("/", 7));
            }
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        String allowHeaders = "Access-Control-Allow-Headers";
        if(!response.containsHeader(allowHeaders))
            response.setHeader(allowHeaders, request.getHeader(allowHeaders));

        String allowMethods = "Access-Control-Allow-Methods";
        if(!response.containsHeader(allowMethods))
            response.setHeader(allowMethods, "GET,POST,OPTIONS,HEAD");
        //这个很关键，要不然ajax调用时浏览器默认不会把这个token的头属性返给JS
        String exposeHeaders = "access-control-expose-headers";
        if(!response.containsHeader(exposeHeaders))
            response.setHeader(exposeHeaders, "x-auth-token");

        return o;
    }
}
```

好了，到这里使用shiro和jwt做用户认证和鉴权的实现就结束了。详细代码地址：[shiro-jwt-demo](https://github.com/chilexun/springboot-demo)