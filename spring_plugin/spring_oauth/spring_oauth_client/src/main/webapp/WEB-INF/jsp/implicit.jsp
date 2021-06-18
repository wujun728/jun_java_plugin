<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>implicit</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>implicit</h2>

<p>
    grant_type = 'implicit' 模式通过在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash(不通过client服务器).
    <br/>
    使用该模式时要求浏览器绝对可信，不然可能会将访问信息(client_id,client_secret等)泄露给恶意用户或应用程序. 一般使用在临时访问的场景.
    <br/>
</p>

<div>
    <p>在本操作中, 首先需要向 spring-oauth-server 数据库中添加<code>implicit</code>的client信息(oauth_client_details表),SQL如下:</p>
    <pre>
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
web_server_redirect_uri,authorities, access_token_validity,
refresh_token_validity, additional_information, create_time, archived, trusted)
values
('implicit-client','sos-resource', 'implicit-secret', 'read','implicit',
'http://localhost:7777/spring-oauth-client/implicit','ROLE_UNITY,ROLE_USER',null,
null,null, now(), 0, 0);
    </pre>
    <p>
        注意检查SQL中的<code>web_server_redirect_uri</code>字段的值,必须是正确能访问的.
    </p>
    <span class="text-muted">(若已添加则忽略; 在实际应用中, 添加的数据是需要向服务端申请注册的)</span>
</div>

<br/>

<div ng-controller="ImplicitCtrl">
    <div class="panel panel-default">
        <div class="panel-heading">第一步 <code>去spring-oauth-server登录并授权</code></div>
        <div class="panel-body">
            <div class="col-md-10">
                <p class="text-muted">
                    点击 '登录并授权' 按钮, 将跳转到spring-auth-server的登录页面.
                    <br/>
                    若是开发者关心请求的参数,可点击'显示请求参数' 展示请求的参数细节.
                </p>

                <form class="form-horizontal" action="${userAuthorizationUri}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">userAuthorizationUri</label>

                        <div class="col-sm-10">
                            <p class="form-control-static"><code>${userAuthorizationUri}</code>
                                &nbsp;<a href="${userAuthorizationUri}" target="_blank">测试连接</a></p>
                        </div>
                    </div>
                    <a href="javascript:void(0);" ng-click="showParams()">显示请求参数</a>

                    <div ng-show="visible">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">client_id</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="client_id" required="required"
                                       ng-model="clientId"/>

                                <p class="help-block">客户端从 Oauth Server 申请的client_id, 有的Oauth服务器中又叫 appKey</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">client_secret</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="client_secret" required="required"
                                       ng-model="clientSecret"/>

                                <p class="help-block">客户端从 Oauth Server 申请的client_secret, 有的Oauth服务器中又叫 appSecret</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">response_type</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="response_type" readonly="readonly"
                                       ng-model="responseType"/>

                                <p class="help-block">固定值 'token'</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">scope</label>

                            <div class="col-sm-10">
                                <select name="scope" ng-model="scope" class="form-control">
                                    <option value="read">read</option>
                                </select>

                                <p class="help-block">必须是client_details中支持的scope; 当前的只支持read</p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">redirect_uri</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="redirect_uri"
                                       ng-model="redirectUri"/>

                                <p class="help-block">必须与client_details中的<code>web_server_redirect_uri</code>一致</p>
                            </div>
                        </div>

                    </div>
                    <br/>
                    <br/>
                    <button class="btn btn-primary" type="submit">登录并授权</button> <span class="label label-info">GET</span>
                </form>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">第二步 <code>访问资源服务器的API</code></div>
        <div class="panel-body">
            <div ng-hide="tokenVisible">请先 登录并授权</div>
            <div ng-show="tokenVisible" class="col-md-11">
                <p class="text-info">注意查看地址栏中URL的<strong>hash部分(#号后面)</strong>已经包含了access_token信息,通过JS解析即可获取</p>

                <div class="well well-sm">
                    <dl class="dl-horizontal">
                        <dt>access_token</dt>
                        <dd><code>{{accessToken}}</code></dd>
                        <dt>token_type</dt>
                        <dd><code>{{tokenType}}</code></dd>
                        <dt>expires_in</dt>
                        <dd><code>{{expiresIn}}</code></dd>
                    </dl>
                    <p class="text-danger">{{tokenError}}</p>
                </div>

                <p>
                    获取access_token成功, 访问资源服务器API
                </p>
                <a href="${unityUserInfoUri}?access_token={{accessToken}}" target="_blank">${unityUserInfoUri}?access_token={{accessToken}}</a>

                <p class="help-block">JSON格式的资源服务器数据</p>
            </div>
        </div>
    </div>
</div>

<script>
    var ImplicitCtrl = ['$scope', function ($scope, $http) {
        $scope.clientId = "implicit-client";
        $scope.clientSecret = "implicit-secret";
        $scope.responseType = "token";

        $scope.scope = "read";
        $scope.redirectUri = "${host}implicit";

        $scope.visible = false;
        $scope.tokenVisible = false;

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };

        // hash
        var hash = location.hash.substring(1);
        console.log(hash + ",  " + hash.length);
        if (hash.length > 0) {
            var regex = /([^&=]+)=([^&]*)/g;
            var keyValue;
            //access_token=f46c6a79-872e-43a0-88f8-a340aee5229c&token_type=bearer&expires_in=40513
            while (keyValue = regex.exec(hash)) {
                var key = keyValue[1];
                var value = keyValue[2];

                if ('access_token' == key) {
                    $scope.accessToken = value;
                } else if ('token_type' == key) {
                    $scope.tokenType = value;
                } else if ('expires_in' == key) {
                    $scope.expiresIn = value;
                } else {
                    $scope.tokenError = value;
                }
            }

            $scope.tokenVisible = true;
        }

    }];
</script>
</body>
</html>