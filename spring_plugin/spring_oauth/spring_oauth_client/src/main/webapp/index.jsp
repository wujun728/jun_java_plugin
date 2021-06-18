<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h2>Spring Security&Oauth2 Client is work!</h2>


<div>
    操作说明:
    <ol>
        <li>
            <p>
                spring-oauth-client 的实现没有使用开源项目 <a
                    href="https://github.com/spring-projects/spring-security-oauth/tree/master/spring-security-oauth2"
                    target="_blank">spring-security-oauth2</a> 中提供的代码与配置, 如:<code>&lt;oauth:client
                id="oauth2ClientFilter" /&gt;</code>
            </p>
        </li>
        <li>
            <p>
                按照Oauth2支持的grant_type依次去实现. 共5类.
                <br/>
            <ul>
                <li>authorization_code</li>
                <li>password</li>
                <li>client_credentials</li>
                <li>implicit</li>
                <li>refresh_token</li>
            </ul>
        </li>
        <li>
            <p>
                <em>
                    在开始使用之前, 请确保 <a href="http://git.oschina.net/shengzhao/spring-oauth-server" target="_blank">spring-oauth-server</a>
                    项目已正确运行, 且 spring-oauth-client.properties (位于项目的 src/main/resources 目录) 配置正确
                </em>
            </p>
        </li>
    </ol>
</div>
<br/>

<p>
    &Delta; 注意: 项目前端使用了 Angular-JS 来处理动态数据展现.
</p>
<hr/>

<div>
    <strong>菜单</strong>
    <ul>
        <li>
            <p><a href="authorization_code">authorization_code</a><br/>授权码模式(即先登录获取code,再获取token) [最常用]</p>
        </li>
        <li>
            <p><a href="password">password</a> <br/>密码模式(将用户名,密码传过去,直接获取token) [适用于移动设备]</p>
        </li>
        <li>
            <p><a href="client_credentials">client_credentials</a> <br/>客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向'服务端'获取资源)</p>
        </li>
        <li>
            <p><a href="implicit">implicit</a> <br/>简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)</p>
        </li>
        <li>
            <p><a href="refresh_token">refresh_token</a> <br/>刷新access_token</p>
        </li>
    </ul>
    <br/>

    <p class="alert alert-warning">
        <strong>注意</strong>: 在测试时默认填写的数据有可能不正确, 建议先在 <a href="https://andaily.com/spring-oauth-server/" target="_blank">spring-oauth-server</a>
        添加 client_details 后, 使用其client_id, client_secret来进行测试.
    </p>
</div>
</body>
</html>