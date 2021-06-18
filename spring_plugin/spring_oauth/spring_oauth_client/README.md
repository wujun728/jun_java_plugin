#spring-oauth-client

<div>
  spring-oauth-client depend on <a href="http://git.oschina.net/shengzhao/spring-oauth-server">spring-oauth-server</a>,
  it is the oauth2 client demos.
</div>
<div>
    <strong>注意</strong>  从 1.1 版本开始支持 spring-oauth-server  config分支 (旧版本的spring-oauth-server 测试请使用 1.0 分支)
</div>
<hr/>


项目用Maven管理


使用的技术与版本号
<ol>
     <li>JDK (1.7.0_40)</li>
     <li>Spring (4.1.6.RELEASE)</li>
     <li>Spring MVC (4.1.6.RELEASE)</li>
     <li>HttpClient (4.3.5)</li>
     <li>json-lib (2.4)</li>
     <li>Log4j (1.2.14)</li>
</ol>
前端使用的技术与版本号
<ol>
    <li>Angular-JS (1.1.5)</li>
    <li>Bootstrap (3.3.4)</li>
</ol>
<hr/>

<h3>
    Oauth服务端项目请访问 <a href="http://git.oschina.net/shengzhao/spring-oauth-server">spring-oauth-server</a>
</h3>
<h3>
    在线测试地址 <a href="http://andaily.com/spring-oauth-client/">http://andaily.com/spring-oauth-client/</a>
</h3>

<hr/>
<p>
    <strong>如何使用?</strong>
    <br/>
    前提: 在使用之前必须保证 spring-oauth-server 项目已正常运行.
    <ol>
        <li>
            项目是Maven管理的, 需要本地安装maven(开发用的maven版本号为3.1.0)
        </li>
        <li>
            <a href="http://git.oschina.net/mkk/spring-oauth-client/repository/archive?ref=master">下载</a>(或clone)项目到本地
        </li>
        <li>
            修改<code>spring-oauth-client.properties</code>(位于src/main/resources目录)中的配置信息(主要包括与spring-oauth-server的连接地址)
        </li>
        <li>
            将本地项目导入到IDE(如Intellij IDEA)中,配置Tomcat(或类似的servlet运行服务器), 并启动Tomcat(默认端口为8080) ,通过浏览器访问即可.
            <br/>
            注意将项目的 contextPath(根路径) 设置为 'spring-oauth-client'.
            <br/>
            所有的操作说明都在页面上体现.
            <br/>
               另: 也可通过maven package命令将项目编译为war文件(spring-oauth-client.war),
                     将war放在Tomcat中并启动(注意: 这种方式需要将spring-oauth-client.properties加入到classpath中并正确配置)
        </li>
        <li>
            <p>
                若在<strong>Android</strong>中使用, 可查看示例代码 <code>AndroidClientTest.java</code>(位于<em> src/master/src /test /java /com/andaily/springoauth/client/</em>目录).
                里面包括获取 access_token 与 调用API的示例.
            </p>
        </li>
    </ol>
</p>


<hr/>
<div>
    <strong>实现思路</strong>
    <p>
        spring-oauth-client 的实现没有使用开源项目 <a
            href="https://github.com/spring-projects/spring-security-oauth/tree/master/spring-security-oauth2"
            target="_blank">spring-security-oauth2</a> 中提供的代码与配置, 如:<code>&lt;oauth:client
        id="oauth2ClientFilter" /&gt;</code>
    </p>
    <p>
        而是按照Oauth2协议支持的5类grant_type依次去实现.
        <br/>
        <ol>
            <li><code>authorization_code</code> -- 授权码模式(即先登录获取code,再获取token)</li>
            <li><code>password</code> -- 密码模式(将用户名,密码传过去,直接获取token)</li>
            <li><code>client_credentials</code> -- 客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向'服务端'获取资源)</li>
            <li><code>implicit</code> -- 简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)</li>
            <li><code>refresh_token</code> -- 刷新access_token</li>
        </ol>

    </p>
</div>

<p>
    项目的开发管理使用开源项目 <a href="http://git.oschina.net/mkk/andaily-developer">andaily-developer</a>.
</p>
<hr/>

<p>
    <strong>项目日志</strong>
    <ol>
        <li>
            <p>2015-03-17    项目创建</p>
        </li>
        <li>
            <p>2015-06-02    V-0.1版本发布</p>
        </li>
        <li>
            <p>2015-11-16    添加在线测试, 访问地址 http://andaily.com/spring-oauth-client/ </p>
        </li>
        <li>
            <p>2018-04-16    V-1.0发布; 开始V-1.1,增加对OIDC协议支持 </p>
        </li>
    </ol>
</p>

<hr/>
<p>
    <strong>参考资源</strong>
    <br/>
    以下是在开发与学习过程中参考的Oauth资源,总结下来方便学习回顾.
    <ul>
        <li><p>
            <a href="http://www.dannysite.com/blog/176/">OAuth2：Authorization Flows</a>
        </p></li>
        <li><p>
            <a href="http://www.dannysite.com/blog/178/">OAuth2：隐式授权（Implicit Grant）类型的开放授权</a>
        </p></li>
        <li><p>
            <a href="http://www.tuicool.com/articles/QrUVvuf">oauth2.0协议之Implicit grant模式解析</a>
        </p></li>
    </ul>
</p>

<hr/>
<h4>
    与项目相关的技术文章请访问 <a href="http://andaily.com/blog/?cat=19">http://andaily.com/blog/?cat=19</a> (不断更新与Oauth相关的文章)
</h4>
<p>
    <strong>问答与讨论</strong>
    <br/>
    与项目相关的，与Oauth相关的问题与回答，以及各类讨论请访问<br/>
    <a href="http://andaily.com/blog/?dwqa-question_category=oauth">http://andaily.com/blog/?dwqa-question_category=oauth</a>
</p>

<br/>
<p>
 关注更多我的开源项目请访问 <a href="http://andaily.com/my_projects.html">http://andaily.com/my_projects.html</a>
</p>
<p>
 若需更多的技术支持请联系 <a href="mailto:monkeyk@shengzhaoli.com">monkeyk@shengzhaoli.com</a>
</p>

<hr/>
<div>
  Expect your joining...
</div>