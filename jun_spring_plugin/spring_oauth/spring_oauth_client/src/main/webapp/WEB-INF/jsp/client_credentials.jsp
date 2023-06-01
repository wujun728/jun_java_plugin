<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>client_credentials</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>client_credentials</h2>

<p>
    grant_type = 'client_credentials' 模式不需要用户去资源服务器登录并授权, 因为客户端(client)已经有了访问资源服务器的凭证(credentials).
    <br/>
    所以当用户访问时,由client直接向资源服务器获取access_token并访问资源即可.
    <br/>
</p>

<p class="text-muted">
    若客户端需要登录(或注册), 则用户仅需在客户端登录(或注册)即可,与资源服务器没有关系
</p>

<p>
    <small class="text-muted">
        <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, client_credentials一般都是由后台来完成的,前台没有任何表现,
        常用于子应用中去访问主应用的资源或API.
    </small>
</p>

<div>
    <p>在本操作中, 首先需要向 spring-oauth-server 数据库中添加<code>client_credentials</code>的client信息(oauth_client_details表),SQL如下:</p>
    <pre>
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
web_server_redirect_uri,authorities, access_token_validity,
refresh_token_validity, additional_information, create_time, archived, trusted)
values
('credentials-client','sos-resource', '$2a$10$Dl2VwWVv/3h5KzK02gysheH7sy28weESL84DiO/CvUiGKcoXGTVlO', 'read,write','client_credentials',
null,'ROLE_UNITY,ROLE_USER',null,
null,null, now(), 0, 0);
    </pre>
    <span class="text-muted">(若已添加则忽略; 在实际应用中, 添加的数据是需要向服务端申请注册的)</span>
</div>

<br/>

<div ng-controller="ClientCredentialsCtrl">
    <div class="panel panel-default">
        <div class="panel-heading">第一步 <code>获取access_token</code></div>
        <div class="panel-body">
            <div class="col-md-10">
                <p class="text-muted">
                    点击 '获取access_token' 按钮, 将向spring-auth-server请求获取access_token.
                    <br/>
                    若是开发者关心请求的参数,可点击'显示请求参数' 展示请求的参数细节.
                </p>

                <form class="form-horizontal" action="#" onsubmit="return false;">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">accessTokenUri</label>

                        <div class="col-sm-10">
                            <p class="form-control-static"><code>${accessTokenUri}</code>
                                &nbsp;<a href="${accessTokenUri}" target="_blank">测试连接</a></p>
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
                            <label class="col-sm-2 control-label">grant_type</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="grant_type" readonly="readonly"
                                       ng-model="grantType"/>

                                <p class="help-block">固定值 'client_credentials'</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">scope</label>

                            <div class="col-sm-10">
                                <select name="scope" ng-model="scope" class="form-control">
                                    <option value="read">read</option>
                                    <option value="write">write</option>
                                    <option value="read write">read write</option>
                                </select>
                            </div>
                        </div>

                    </div>
                    <br/>
                    <br/>
                    <button class="btn btn-primary" ng-click="getAccessToken()">获取access_token</button>
                    <span class="label label-warning">POST</span>
                </form>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">第二步 <code>访问资源服务器的API</code></div>
        <div class="panel-body">
            <div ng-hide="tokenVisible">请先获取access_token</div>
            <div ng-show="tokenVisible" class="col-md-11">
                <div class="well well-sm">
                    <dl class="dl-horizontal">
                        <dt>access_token</dt>
                        <dd><code>{{accessToken}}</code></dd>
                        <dt>token_type</dt>
                        <dd><code>{{tokenType}}</code></dd>
                        <dt>scope</dt>
                        <dd><code>{{tokenScope}}</code></dd>
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
    var ClientCredentialsCtrl = ['$scope', '$http', function ($scope, $http) {
        $scope.clientId = "credentials-client";
        $scope.clientSecret = "credentials-secret";
        $scope.grantType = "client_credentials";

        $scope.scope = "read write";
        $scope.visible = false;
        $scope.tokenVisible = false;

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };

        $scope.getAccessToken = function () {
            var uri = "credentials_access_token?clientId=" + $scope.clientId + "&clientSecret=" + $scope.clientSecret + "&grantType=" + $scope.grantType + "&scope=" + $scope.scope
                    + "&accessTokenUri=" + encodeURIComponent("${accessTokenUri}");

            $http.get(uri).success(function (data) {
                $scope.accessToken = data.accessToken;
                $scope.tokenType = data.tokenType;

                $scope.tokenScope = data.scope;
                $scope.expiresIn = data.expiresIn;
                //if have error
                $scope.tokenError = data.error + " " + data.errorDescription;

                $scope.tokenVisible = true;
            });
        };
    }];
</script>
</body>
</html>