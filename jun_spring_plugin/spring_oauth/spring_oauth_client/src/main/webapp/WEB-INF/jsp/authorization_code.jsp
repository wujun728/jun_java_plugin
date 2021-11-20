<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>authorization_code</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>authorization_code
    <small>从 spring-oauth-server获取 'code'</small>
</h2>
<br/>

<div>
    <p>
        grant_type = 'authorization_code' 模式是Oauth中最常用的, 一般是通过浏览器来完成.
        整个流程分3步完成,依次为:
    </p>
    <ol>
        <li>
            <p>
                <code>从 spring-oauth-server获取 'code'</code>
                <br/>
                -- 该步骤将根据从 spring-oauth-server 中获取的client信息(如client_id,client_secret)将用户引导到server的登录页面.
                <br/>
                <small class="text-muted">
                    <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, 表现为在登录页面中展示的 '通过第三方登录' 或 '通过其他账号登录'
                </small>
            </p>
        </li>
        <li>
            <p>
                <code>用 'code' 换取 'access_token'</code>
                <br/>
                -- 在server端当用户登录成功并授权后将返回到spring-oauth-client的回调地址(即redirect_uri),
                <br/>
                当检查通过后(指检查返回是否有code与state值), 将根据server端获取access_token的URL去换取access_token值.
                <br/>
                <small class="text-muted">
                    <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, 该步骤一般由client后端代码完成,前端不需要表现.
                </small>
            </p>
        </li>
        <li>
            <p>
                <code>用 'access_token' 去访问 spring-oauth-server 的API</code>
                <br/>
                -- 在成功获取access_token后,将根据 spring-oauth-server 中提供的API去获取 资源服务器 中的数据.
                <br/>
                在 spring-oauth-server 中当前只有一个API可供调用(即<code>${unityUserInfoUri}</code>), 用于获取当前登录用户的信息.
                <br/>
                <small class="text-muted">
                    <em class="glyphicon glyphicon-info-sign"></em> 在实际应用中, 资源服务器都会提供许多API供调用
                </small>
            </p>
        </li>
    </ol>
</div>
<hr/>

<div class="panel panel-default">
    <div class="panel-heading">步骤1: <code>从 spring-oauth-server获取 'code'</code></div>
    <div class="panel-body">
        <div ng-controller="AuthorizationCodeCtrl" class="col-md-10">

            <form action="authorization_code" method="post" class="form-horizontal">
                <input type="hidden" name="userAuthorizationUri" value="${userAuthorizationUri}"/>

                <div class="form-group">
                    <label class="col-sm-2 control-label">authorizationUri</label>

                    <div class="col-sm-10">
                        <p class="form-control-static"><code>${userAuthorizationUri}</code>
                            &nbsp;<a href="${userAuthorizationUri}" target="_blank">测试连接</a>
                        </p>

                        <p class="help-block">
                            authorizationUri value from 'spring-oauth-client.properties'
                        </p>
                    </div>
                </div>

                <a href="javascript:void(0);" ng-click="showParams()">显示请求参数</a>

                <div ng-show="visible">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">response_type</label>

                        <div class="col-sm-10">
                            <input type="text" name="responseType" readonly="readonly"
                                   class="form-control" ng-model="responseType"/>

                            <p class="help-block">固定值 'code'</p>
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
                        <label class="col-sm-2 control-label">client_id</label>

                        <div class="col-sm-10">
                            <input type="text" name="clientId" required="required"
                                   class="form-control" ng-model="clientId"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">redirect_uri</label>

                        <div class="col-sm-10">
                            <input type="text" name="redirectUri" class="form-control"
                                   required="required" size="50" ng-model="redirectUri"/>

                            <p class="help-block">
                                redirect_uri 是在 'AuthorizationCodeController.java' 中定义的一个 URI, 用于检查server端返回的
                                'code'与'state',并发起对 access_token 的调用</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">state</label>

                        <div class="col-sm-10">
                            <input type="text" name="state" size="50" class="form-control" required="required"
                                   ng-model="state"/>

                            <p class="help-block">一个随机值, spring-oauth-server 将原样返回,用于检测是否为跨站请求(CSRF)等</p>
                        </div>
                    </div>

                    <div class="well well-sm">
                        <span class="text-muted">最终发给 spring-oauth-server的 URL:</span>
                        <br/>

                        <div class="text-primary">
                            {{userAuthorizationUri}}?response_type={{responseType}}&scope={{scope}}&client_id={{clientId}}&redirect_uri={{redirectUri}}&state={{state}}
                        </div>
                    </div>
                </div>
                <br/>
                <br/>
                <button type="submit" class="btn btn-primary">去登录</button>
                <span class="text-muted">将重定向到 'spring-oauth-server' 的登录页面</span> <span
                    class="label label-info">GET</span>
            </form>

        </div>
    </div>
</div>

<script>
    var AuthorizationCodeCtrl = ['$scope', function ($scope) {
        $scope.userAuthorizationUri = '${userAuthorizationUri}';
        $scope.responseType = 'code';
        $scope.scope = 'read';

        $scope.clientId = 'unity-client';
        $scope.redirectUri = '${host}authorization_code_callback';
        $scope.state = '${state}';

        $scope.visible = false;

        $scope.showParams = function () {
            $scope.visible = !$scope.visible;
        };
    }];
</script>

</body>
</html>