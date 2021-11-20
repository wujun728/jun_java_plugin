<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Test [${clientDetailsDto.clientId}]</title>

    <script src="${contextPath}/resources/angular.min.js"></script>

</head>
<body>
<div ng-app>
    <a href="../">Home</a>

    <h2>Test [${clientDetailsDto.clientId}]</h2>

    <p>
        针对不同的<code>grant_type</code>提供不同的测试URL,
        完整的Oauth测试请访问<a href="http://git.oschina.net/mkk/spring-oauth-client" target="_blank">spring-oauth-client</a>项目.
    </p>

    <div ng-controller="TestClientCtrl">
        <c:if test="${clientDetailsDto.containsAuthorizationCode}">
            <div class="panel panel-default">
                <div class="panel-heading">Test [authorization_code]</div>
                <div class="panel-body">
                    <p class="text-muted">输入每一步必要的信息后点击其下面的链接地址.</p>
                    <ol>
                        <li>
                            <p>
                                <code>从 spring-oauth-server获取 'code'</code>
                                <br/>
                                redirect_uri: <input type="text" value="" ng-model="redirectUri" size="70"
                                                     required="required"/>
                                <br/>
                                <a href="${contextPath}/oauth/authorize?client_id={{clientId}}&redirect_uri={{redirectUri}}&response_type=code&scope={{scope}}&state=your_state"
                                   target="_blank">
                                    /oauth/authorize?client_id={{clientId}}&redirect_uri={{redirectUri}}&response_type=code&scope={{scope}}&state=your_state</a>
                                <span class="label label-info">GET</span>
                            </p>
                        </li>
                        <li>
                            <code>用 'code' 换取 'access_token'</code>
                            <br/>
                            输入第一步获取的code: <input type="text" name="code" value="" ng-model="code"
                                                 required="required"/>
                            <br/>

                            <form action="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=authorization_code&code={{code}}&redirect_uri={{redirectUri}}"
                                  method="post" target="_blank">
                                    <%--<a href="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=authorization_code&code={{code}}&redirect_uri={{redirectUri}}"--%>
                                    <%--target="_blank">/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=authorization_code&code={{code}}&redirect_uri={{redirectUri}}</a>--%>
                                <button class="btn btn-link" type="submit">
                                    /oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=authorization_code&code={{code}}&redirect_uri={{redirectUri}}
                                </button>
                                <span class="label label-warning">POST</span>
                            </form>
                        </li>
                    </ol>
                </div>
            </div>
        </c:if>

        <c:if test="${clientDetailsDto.containsPassword}">
            <div class="panel panel-default">
                <div class="panel-heading">Test [password]</div>
                <div class="panel-body">
                    <p class="text-muted">输入username, password 后点击链接地址.</p>
                    username: <input type="text" required="required" ng-model="username"/>
                    <br/>
                    password: <input type="text" required="required" ng-model="password"/>

                    <br/>

                    <form action="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=password&scope={{scope}}&username={{username}}&password={{password}}"
                          method="post" target="_blank">
                            <%--<a href="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=password&scope={{scope}}&username={{username}}&password={{password}}"--%>
                            <%--target="_blank">/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=password&scope={{scope}}&username={{username}}&password={{password}}</a>--%>
                        <button class="btn btn-link" type="submit">
                            /oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=password&scope={{scope}}&username={{username}}&password={{password}}
                        </button>
                        <span class="label label-warning">POST</span>
                    </form>
                </div>
            </div>
        </c:if>

        <c:if test="${clientDetailsDto.containsImplicit}">
            <div class="panel panel-default">
                <div class="panel-heading">Test [implicit]</div>
                <div class="panel-body">
                    <p class="text-muted">输入redirect_uri 后点击链接地址. 获取access_token后注意查看redirect_uri的hash部分(#号后边部分)</p>
                    redirect_uri: <input type="text" value="" ng-model="implicitRedirectUri" size="70"
                                         required="required"/>

                    <p>
                        <a href="${contextPath}/oauth/authorize?client_id={{clientId}}&response_type=token&scope={{scope}}&redirect_uri={{implicitRedirectUri}}"
                                >/oauth/authorize?client_id={{clientId}}&response_type=token&scope={{scope}}&redirect_uri={{implicitRedirectUri}}</a>
                        <span class="label label-info">GET</span>
                    </p>
                </div>
            </div>
        </c:if>

        <c:if test="${clientDetailsDto.containsClientCredentials}">
            <div class="panel panel-default">
                <div class="panel-heading">Test [client_credentials]</div>
                <div class="panel-body">
                    <p class="text-muted">点击链接地址即可测试</p>


                    <form action="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=client_credentials&scope={{scope}}"
                          method="post" target="_blank">
                            <%--<a href="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=client_credentials&scope={{scope}}"--%>
                            <%--target="_blank">/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=client_credentials&scope={{scope}}</a>--%>
                        <button class="btn btn-link" type="submit">
                            /oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=client_credentials&scope={{scope}}
                        </button>
                        <span class="label label-warning">POST</span>
                    </form>
                </div>
            </div>
        </c:if>

        <c:if test="${clientDetailsDto.containsRefreshToken}">
            <div class="panel panel-default">
                <div class="panel-heading">Test [refresh_token]</div>
                <div class="panel-body">
                    <p class="text-muted">输入refresh_token 后点击链接地址.</p>
                    refresh_token: <input type="text" ng-model="refreshToken" required="required" size="70"/>

                    <br/>

                    <form action="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=refresh_token&refresh_token={{refreshToken}}"
                          method="post" target="_blank">
                            <%--<a href="${contextPath}/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=refresh_token&refresh_token={{refreshToken}}"--%>
                            <%--target="_blank">/oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=refresh_token&refresh_token={{refreshToken}}</a>--%>
                        <button class="btn btn-link" type="submit">
                            /oauth/token?client_id={{clientId}}&client_secret={{clientSecret}}&grant_type=refresh_token&refresh_token={{refreshToken}}
                        </button>
                        <span class="label label-warning">POST</span>
                    </form>
                </div>
            </div>
        </c:if>

        <div class="panel panel-default">
            <div class="panel-heading">Verify [access_token]</div>
            <div class="panel-body">
                <p class="text-muted">输入access_token 后点击链接地址.</p>
                access_token: <input type="text" ng-model="accessToken" required="required" size="70"
                                     placeholder="access_token"/>

                <br/>

                <form action="${contextPath}/oauth/check_token?token={{accessToken}}&client_id={{clientId}}"
                      method="post" target="_blank">
                    <button class="btn btn-link" type="submit">
                        /oauth/check_token?token={{accessToken}}&client_id={{clientId}}
                    </button>
                    <span class="label label-warning">POST</span>
                </form>
            </div>
        </div>

        <div class="text-center">
            <a href="${contextPath}/client_details" class="btn btn-default">Back</a>
        </div>
    </div>
</div>

<script>
    var TestClientCtrl = ["$scope", function ($scope) {
        $scope.clientId = "${clientDetailsDto.clientId}";
        $scope.clientSecret = "${clientDetailsDto.clientSecret}";
        $scope.scope = "${clientDetailsDto.scopeWithBlank}";

        <c:if test="${empty clientDetailsDto.webServerRedirectUri}" var="eptRedUri">
        $scope.implicitRedirectUri = location.href;
        $scope.redirectUri = "http://localhost:8080/spring-oauth-server/unity/dashboard";
        </c:if>
        <c:if test="${not eptRedUri}">
        $scope.implicitRedirectUri = "${clientDetailsDto.webServerRedirectUri}";
        $scope.redirectUri = "${clientDetailsDto.webServerRedirectUri}";
        </c:if>

        $scope.username = "mobile";
        $scope.password = "mobile";
        //a temp value
        $scope.refreshToken = "1156ebfe-e303-4572-9fb5-4459a5d46610";
        $scope.accessToken = "e2996930-8398-44fd-8de5-7d1b1624ced7";

    }];
</script>
</body>
</html>