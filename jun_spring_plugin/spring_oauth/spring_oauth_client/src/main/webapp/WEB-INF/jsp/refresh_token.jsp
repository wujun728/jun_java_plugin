<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>refresh_token</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>refresh_token</h2>

<div>
    grant_type = 'refresh_token' 模式用在当获取的access_token未过期之前向服务端换取新的access_token.
    <br/>
    在获取access_token时返回的数据如下
    <pre>
{"access_token":"3420d0e0-ed77-45e1-8370-2b55af0a62e8","token_type":"bearer","refresh_token":"b36f4978-a172-4aa8-af89-60f58abe3ba1","expires_in":43199,"scope":"read write"}
    </pre>
    <p>
        数据中的<code>expires_in</code>即access_token的有效时间(单位:秒), 默认的有效时间为43199(约12小时), 在服务端可配置默认的有效时间.
    </p>

    <p>
        若在一个时间段内(即在43199秒之内)多次去获取access_token, 将返回相同的access_token值, 但<code>expires_in</code>的值将会减少.
        <br/>
        <span class="text-muted">(比如: 相隔10秒去获取access_token, 第一次返回的expires_in=43199,第二次返回的expires_in=43189)</span>
    </p>

    <p>
        数据中<code>refresh_token</code>的值将用于换取新的access_token.
        <br/>
        在此将首先通过 grant_type='password' 去获取access_token, 然后调用 grant_type='refresh_token' 去换取新的access_token.
        <br/>
        换取成功后将得到新的access_token,并会看到<code>expires_in</code>值又还原成43199.
        <br/>
        <strong>注意</strong> refresh_token 成功后旧的access_token将不能再使用.
    </p>
    <small class="text-muted">
        <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, refresh_token一般都是由后台来完成的,前台没有任何表现.
    </small>
</div>

<hr/>
<div ng-controller="RefreshTokenCtrl">
    <div class="panel panel-default">
        <div class="panel-heading">1. <em>通过 grant_type='password' 去获取access_token</em></div>
        <div class="panel-body">
            <div class="col-md-10">
                <p class="text-muted">
                    点击 '获取access_token' 按钮, 从spring-oauth-server取到access_token数据.
                    <br/>
                    若是开发者关心请求的参数,可点击'显示请求参数' 展示请求的参数细节.
                </p>

                <form class="form-horizontal" action="#" method="post" onsubmit="return false;">
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

                                <p class="help-block">固定值 'password'</p>
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

                        <div class="form-group">
                            <label class="col-sm-2 control-label">username</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="username" required="required"
                                       ng-model="username"/>

                                <p class="help-block">用户在 Oauth Server 中的账号名称</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">password</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="password" required="required"
                                       ng-model="password"/>

                                <p class="help-block">用户在 Oauth Server 中的账号密码</p>
                            </div>
                        </div>

                    </div>
                    <br/>
                    <br/>
                    <button type="button" class="btn btn-primary" ng-click="getAccessToken()">获取access_token</button>
                </form>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">2. <em>调用 grant_type='refresh_token' 去换取新的access_token</em></div>
        <div class="panel-body">
            <div ng-hide="tokenVisible">请先获取access_token</div>
            <div ng-show="tokenVisible" class="col-md-11">
                <div class="well well-sm">
                    <dl class="dl-horizontal">
                        <dt>access_token</dt>
                        <dd><code>{{accessToken}}</code></dd>
                        <dt>token_type</dt>
                        <dd><code>{{tokenType}}</code></dd>
                        <dt>refresh_token</dt>
                        <dd><code>{{refreshToken}}</code></dd>
                        <dt>scope</dt>
                        <dd><code>{{tokenScope}}</code></dd>
                        <dt>expires_in</dt>
                        <dd><code>{{expiresIn}}</code></dd>
                    </dl>
                    <p class="text-danger">{{tokenError}}</p>

                    <p class="help-block">多次点击 '获取access_token' 将会看到<code>expires_in</code>的变化</p>
                </div>

                <form class="form-horizontal" action="#" onsubmit="return false;">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">refreshTokenUri</label>

                        <div class="col-sm-10">
                            <p class="form-control-static"><code>${accessTokenUri}</code>
                                &nbsp;<a href="${accessTokenUri}" target="_blank">测试连接</a></p>
                        </div>
                    </div>
                    <a href="javascript:void(0);" ng-click="showTokenParams()">显示请求参数</a>

                    <div ng-show="tokenParamVisible">

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
                                       ng-model="refreshGrantType"/>

                                <p class="help-block">固定值 'refresh_token'</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">refresh_token</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="refresh_token" readonly="readonly"
                                       ng-model="refreshToken"/>

                                <p class="help-block">从 Oauth Server 返回的 'refresh_token'</p>
                            </div>
                        </div>

                    </div>
                    <br/>
                    <br/>
                    <button class="btn btn-info" ng-click="executeRefreshToken()">刷新access_token</button>
                    <span class="label label-warning">POST</span>
                </form>

                <div ng-show="refreshTokenVisible">
                    <hr/>
                    刷新后的access_token信息
                    <div class="well well-sm">
                        <dl class="dl-horizontal">
                            <dt>access_token</dt>
                            <dd><code>{{newAccessToken}}</code></dd>
                            <dt>token_type</dt>
                            <dd><code>{{newTokenType}}</code></dd>
                            <dt>refresh_token</dt>
                            <dd><code>{{newRefreshToken}}</code></dd>
                            <dt>scope</dt>
                            <dd><code>{{newTokenScope}}</code></dd>
                            <dt>expires_in</dt>
                            <dd><code>{{newExpiresIn}}</code></dd>
                        </dl>
                        <p class="text-danger">{{newTokenError}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var RefreshTokenCtrl = ['$scope', '$http', function ($scope, $http) {
        $scope.accessTokenUri = "${accessTokenUri}";
        $scope.clientId = "mobile-client";
        $scope.clientSecret = "mobile";
        $scope.grantType = "password";

        $scope.username = "mobile";
        $scope.password = "mobile";
        $scope.scope = "read";

        $scope.refreshGrantType = "refresh_token";

        $scope.visible = false;
        $scope.tokenVisible = false;
        $scope.tokenParamVisible = false;

        $scope.refreshTokenVisible = false;

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };

        $scope.showTokenParams = function () {
            $scope.tokenParamVisible = !$scope.tokenParamVisible;
        };

        $scope.getAccessToken = function () {
            var uri = "password_access_token?clientId=" + $scope.clientId + "&clientSecret=" + $scope.clientSecret + "&grantType=" + $scope.grantType
                    + "&scope=" + $scope.scope + "&username=" + $scope.username + "&password=" + $scope.password
                    + "&accessTokenUri=" + encodeURIComponent($scope.accessTokenUri);

            $http.get(uri).success(function (data) {
                $scope.accessToken = data.accessToken;
                $scope.tokenType = data.tokenType;
                $scope.refreshToken = data.refreshToken;

                $scope.tokenScope = data.scope;
                $scope.expiresIn = data.expiresIn;
                //if have error
                $scope.tokenError = data.error + " " + data.errorDescription;

                $scope.tokenVisible = true;
            });
        };


        $scope.executeRefreshToken = function () {
            var uri = "refresh_access_token?clientId=" + $scope.clientId + "&clientSecret=" + $scope.clientSecret + "&grantType=" + $scope.refreshGrantType
                    + "&refreshToken=" + $scope.refreshToken + "&refreshAccessTokenUri=" + encodeURIComponent($scope.accessTokenUri);

            $http.get(uri).success(function (data) {
                $scope.newAccessToken = data.accessToken;
                $scope.newTokenType = data.tokenType;
                $scope.newRefreshToken = data.refreshToken;

                $scope.newTokenScope = data.scope;
                $scope.newExpiresIn = data.expiresIn;
                //if have error
                $scope.newTokenError = data.error + " " + data.errorDescription;

                $scope.refreshTokenVisible = true;
            });
        };
    }];
</script>
</body>
</html>