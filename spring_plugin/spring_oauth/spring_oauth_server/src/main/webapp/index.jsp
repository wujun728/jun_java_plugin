<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

</head>
<body>
<h2>Spring Security&OAuth2
    <small class="badge" title="Version">1.0</small>
</h2>

<p>
    <a href="${contextPath}/login.jsp">Login</a>
    &nbsp;|&nbsp;
    <a href="${contextPath}/logout.do">Logout</a>
</p>

<div>
    操作说明:
    <ol>
        <li>
            <p>
                菜单 User 是不需要OAuth 验证即可访问的(即公开的resource); 用于管理用户信息(添加,删除等).
            </p>
        </li>
        <li>
            <p>
                菜单 Unity 与 Mobile 需要OAuth 验证后才能访问(即受保护的resource); <br/>
                Unity 需要 [ROLE_UNITY] 权限(resourceId:
                <mark>unity-resource</mark>
                ), Mobile 需要 [ROLE_MOBILE] 权限(resourceId:
                <mark>mobile-resource</mark>
                ).
            </p>
        </li>
        <li>
            <p>
                在使用之前, 建议先了解OAuth2支持的5类<code>grant_type</code>, 请访问 <a href="https://andaily.com/blog/?p=103"
                                                                       target="_blank">https://andaily.com/blog/?p=103</a>
            </p>
        </li>
        <li>
            <p>
                在项目的 others目录里有 <a
                    href="http://git.oschina.net/shengzhao/spring-oauth-server/blob/master/others/oauth_test.txt"
                    target="_blank">oauth_test.txt</a>文件, 里面有测试的URL地址(包括浏览器与客户端的),<br/>
                若想访问 Unity 与 Mobile, 则先用基于浏览器的测试URL 访问,等验证通过后即可访问(注意不同的账号对应的权限).
            </p>
        </li>
        <li>
            <p>
                若需要自定义<code>client_details</code>数据并进行测试, 可进入<a href="client_details">client_details</a>去手动添加<code>client_details</code>或删除已创建的<code>client_details</code>.
            </p>
        </li>
    </ol>
</div>
<br/>
菜单
<ul>
    <li>
        <p>
            <a href="resources/api/SOS_API-1.0.html" target="_blank">API</a> <span
                class="text-muted">- 查看提供的API文档</span>
        </p>
    </li>
    <li>
        <p>
            <a href="client_details">client_details</a> <span class="text-muted">- 管理ClientDetails</span>
        </p>
    </li>
    <li>
        <p>
            <a href="${contextPath}/user/overview">User</a> <span class="text-muted">- 管理User</span>
        </p>
    </li>
    <li>
        <p>
            <a href="${contextPath}/unity/dashboard">Unity</a> <span class="text-muted">- Unity 资源(resource), 需要具有 [ROLE_UNITY] 权限(resourceId:
                <mark>unity-resource</mark>才能访问</span>
        </p>
    </li>
    <li>
        <p>
            <a href="${contextPath}/m/dashboard">Mobile</a> <span class="text-muted">- Mobile资源(resource), 需要具有 [ROLE_MOBILE] 权限(resourceId:
                <mark>mobile-resource</mark>才能访问</span>
        </p>
    </li>
</ul>
<br/>

<div class="well well-sm">
    <p>
        <strong>说明</strong>: Unity与Mobile菜单需要先获取到<code>access_token</code>后才能正常访问; 可以尝试在URL后面任意添加access_token参数值试试效果,
        <br/>
        如: <a href="${contextPath}/m/dashboard?access_token=i_am_testing_access_token">${contextPath}/m/dashboard?access_token=i_am_testing_access_token</a>
    </p>

    <p>
        请求受保护的资源时传递
        <mark>Access Token</mark>
        有两种方式, 方式一在URL参数中添加<code>access_token</code>, 方式二在请求的Header中添加 <em>Authorization</em>, 其值为 <em>bearer
        your_access_token</em>
    </p>
</div>
</body>
</html>