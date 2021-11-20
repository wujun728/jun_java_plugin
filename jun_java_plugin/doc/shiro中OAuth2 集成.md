# [shiro中OAuth2 集成](https://www.cnblogs.com/yuluoxingkong/p/8268094.html)

 

**OAuth** **角色**


**资源拥有者（resource owner）：**

　　能授权访问受保护资源的一个实体，可以是一个人，那我们称之为最终用户；如新浪微博用户 zhangsan；

**资源服务器（resource server）：**

　　存储受保护资源，客户端通过 access token 请求资源，资源服务器响应受保护资源给客户端；存储着用户 zhangsan 的微博等信息。

**授权服务器（authorization server）：**

　　成功验证资源拥有者并获取授权之后，授权服务器颁发授权令牌（Access Token）给客户端。

**客户端（client）：**

　　如新浪微博客户端 weico、微格等第三方应用，也可以是它自己的官方应用；其本身不存储资源，而是资源拥有者授权通过后，使用它的授权（授权令牌）访问受保护资源，然后客户端把相应的数据展示出来/提交到服务器。“客户端”术语不代表任何特定实现（如应用运行在一台服务器、桌面、手机或其他设备）。 

![img](https://images2017.cnblogs.com/blog/1249325/201801/1249325-20180111140023801-1216349331.png)

 

1、客户端从资源拥有者那请求授权。授权请求可以直接发给资源拥有者，或间接的通过授权服务器这种中介，后者更可取。

2、客户端收到一个授权许可，代表资源服务器提供的授权。

3、客户端使用它自己的私有证书及授权许可到授权服务器验证。

4、如果验证成功，则下发一个访问令牌。

5、客户端使用访问令牌向资源服务器请求受保护资源。

6、资源服务器会验证访问令牌的有效性，如果成功则下发受保护资源。******
******

 

要实现OAuth服务端，就得先理解客户端的调用流程，服务提供商实现可能也有些区别，实现OAuth服务端的方式很多，具体可能看http://oauth.net/code/

> 各语言的实现有(我使用了Apache Oltu):
>
> - Java
>   - [Apache Oltu](http://oltu.apache.org/)
>   - [Spring Security for OAuth](http://static.springsource.org/spring-security/oauth/)
>   - [Apis Authorization Server (v2-31)](https://github.com/OpenConextApps/apis)
>   - [Restlet Framework (draft 30)](http://www.restlet.org/)
>   - [Apache CXF](http://cxf.apache.org/)
> - NodeJS
>   - [NodeJS OAuth 2.0 Provider](https://github.com/t1msh/node-oauth20-provider)
>   - [Mozilla Firefox Accounts](https://github.com/mozilla/?query=fxa). A full stack Identy Provider system developed to support Firefox market place and other services
> - Ruby
>   - [Ruby OAuth2 Server (draft 18)](https://github.com/nov/rack-oauth2)
> - .NET
>   - [.NET DotNetOpenAuth](http://www.dotnetopenauth.net/)
>   - [Thinktecture IdentityServer](https://github.com/thinktecture/Thinktecture.IdentityServer.v3)

实现主要涉及参数配置如下: 
授权码设置(code) 
第三方通过code进行获取 access_token的时候需要用到，code的超时时间为10分钟，一个code只能成功换取一次access_token即失效。 
授权作用域(scope) 
作用域代表用户授权给第三方的接口权限，第三方应用需要向服务端申请使用相应scope的权限后，经过用户授权，获取到相应access_token后方可对接口进行调用。 
令牌有效期(access_token) 
access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，当access_token超时后，可以使用refresh_token进行刷新，access_token刷新结果有两种： 
  \1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间； 
  \2. 若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。 
refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。

# 项目介绍

项目结构如下： 
AuthzController:获取授权码 
TokenController:获得令牌 
ResourceController:资源服务 
ClientController:客户端

基础代码放到github:https://github.com/zhouyongtao/homeinns-web

[![image](https://images0.cnblogs.com/blog/365537/201412/011257283586994.png)](https://images0.cnblogs.com/blog/365537/201412/011257281081538.png)

确保项目8080端口运行，可以手动调试

> 获得授权码 
> http://localhost:8080/oauth2/authorize?client_id=fbed1d1b4b1449daa4bc49397cbe2350&response_type=code&redirect_uri=http://localhost:8080/oauth_callback 
> 获得令牌(POST) 
> http://localhost:8080/oauth2/access_token?client_id=fbed1d1b4b1449daa4bc49397cbe2350&client_secret=fbed1d1b4b1449daa4bc49397cbe2350&grant_type=authorization_code&redirect_uri=http://localhost:8080/oauth_callback&code={code}

# 客户端

也可以使用如下客户端测试代码，访问 http://localhost:8080/client 测试

``"Consolas"``>``/**`` ``* Created by Irving on 2014/11/24.`` ``* OAuth2 客户端实现`` ``*/``@Controller``@RequestMapping(``"/client"``)``public` `class` `ClientController {` `  ``private` `static` `Logger logger = LoggerFactory.getLogger(ClientController.``class``);``  ``/*``    ``response_type：表示授权类型，必选项，此处的值固定为"code"``    ``client_id：表示客户端的ID，必选项``    ``redirect_uri：表示重定向URI，可选项``    ``scope：表示申请的权限范围，可选项``    ``state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值``  ``*/``  ``/**``   ``* 获得授权码``   ``* @return``   ``*/``  ``@RequestMapping(method = RequestMethod.GET)``  ``public` `String client() {``    ``try` `{``      ``OAuthClientRequest oauthResponse = OAuthClientRequest``                        ``.authorizationLocation(ConstantKey.OAUTH_CLIENT_AUTHORIZE)``                        ``.setResponseType(OAuth.OAUTH_CODE)``                        ``.setClientId(ConstantKey.OAUTH_CLIENT_ID)``                        ``.setRedirectURI(ConstantKey.OAUTH_CLIENT_CALLBACK)``                        ``.setScope(ConstantKey.OAUTH_CLIENT_SCOPE)``                        ``.buildQueryMessage();``      ``return` `"redirect:"``+oauthResponse.getLocationUri();``    ``} ``catch` `(OAuthSystemException e) {``      ``e.printStackTrace();``    ``}``    ``return` `"redirect:/home"``;``  ``}` `  ``/*``    ``grant_type：表示使用的授权模式，必选项，此处的值固定为"authorization_code"``    ``code：表示上一步获得的授权码，必选项。``    ``redirect_uri：表示重定向URI，必选项，且必须与A步骤中的该参数值保持一致``    ``client_id：表示客户端ID，必选项``  ``*/``  ``/**``   ``* 获得令牌``   ``* @return oauth_callback?code=1234``   ``*/``  ``@RequestMapping(value = ``"/oauth_callback"` `,method = RequestMethod.GET)``  ``public` `String getToken(HttpServletRequest request,Model model) throws OAuthProblemException {``    ``OAuthAuthzResponse oauthAuthzResponse = ``null``;``    ``try` `{``      ``oauthAuthzResponse = OAuthAuthzResponse.oauthCodeAuthzResponse(request);``      ``String code = oauthAuthzResponse.getCode();``      ``OAuthClientRequest oauthClientRequest = OAuthClientRequest``                          ``.tokenLocation(ConstantKey.OAUTH_CLIENT_ACCESS_TOKEN)``                          ``.setGrantType(GrantType.AUTHORIZATION_CODE)``                          ``.setClientId(ConstantKey.OAUTH_CLIENT_ID)``                          ``.setClientSecret(ConstantKey.OAUTH_CLIENT_SECRET)``                          ``.setRedirectURI(ConstantKey.OAUTH_CLIENT_CALLBACK)``                          ``.setCode(code)``                          ``.buildQueryMessage();``      ``OAuthClient oAuthClient = ``new` `OAuthClient(``new` `URLConnectionClient());` `      ``//Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is``      ``//application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that``      ``//Custom response classes are an easy way to deal with oauth providers that introduce modifications to``      ``//OAuth 2.0 specification` `      ``//获取access token``      ``OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oauthClientRequest, OAuth.HttpMethod.POST);``      ``String accessToken = oAuthResponse.getAccessToken();``      ``String refreshToken= oAuthResponse.getRefreshToken();``      ``Long expiresIn = oAuthResponse.getExpiresIn();``      ``//获得资源服务``      ``OAuthClientRequest bearerClientRequest = ``new` `OAuthBearerClientRequest(ConstantKey.OAUTH_CLIENT_GET_RESOURCE)``                           ``.setAccessToken(accessToken).buildQueryMessage();``      ``OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.``class``);``      ``String resBody = resourceResponse.getBody();``      ``logger.info(``"accessToken: "``+accessToken +``" refreshToken: "``+refreshToken +``" expiresIn: "``+expiresIn +``" resBody: "``+resBody);``      ``model.addAttribute(``"accessToken"``, ``"accessToken: "``+accessToken + ``" resBody: "``+resBody);``      ``return` `"oauth2/token"``;``    ``} ``catch` `(OAuthSystemException ex) {``      ``logger.error(``"getToken OAuthSystemException : "` `+ ex.getMessage());``      ``model.addAttribute(``"errorMsg"``, ex.getMessage());``      ``return` `"/oauth2/error"``;``    ``}``  ``}``}```

> Refer: 
> https://cwiki.apache.org/confluence/display/OLTU/Index 
> https://open.weixin.qq.com/cgi-bin/readtemplate?t=resource/app_wx_login_tmpl&lang=zh_CN#faq
>
>  
>
> http://jinnianshilongnian.iteye.com/blog/2038646
>
> http://blog.csdn.net/u014386474/article/details/51602264
>
> [http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html](http://www.cnblogs.com/nidakun/p/3195023.html)
>
> http://www.cnblogs.com/nidakun/p/3195023.html