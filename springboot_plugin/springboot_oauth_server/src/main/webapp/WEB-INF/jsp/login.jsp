<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>OAuth Login</title>
</head>
<body>
<h2 class="page-header">OAuth Login</h2>

<div class="row">
    <div class="col-md-6">

        <div class="panel panel-default">
            <div class="panel-body">

                <form action="${pageContext.request.contextPath}/signin" method="post" class="form-horizontal">
                    <tags:csrf/>
                    <div class="form-group">
                        <label for="username" class="col-sm-3 control-label">Username</label>

                        <div class="col-sm-9">
                            <input type="text" id="username" name="oidc_user" value="" placeholder="Type username"
                                   required="required" class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label">Password</label>

                        <div class="col-sm-9">
                            <input type="password" name="oidcPwd" id="password" value="" placeholder="Type password"
                                   required="required" class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label"></label>

                        <div class="col-sm-9">
                            <input type="submit" value="Login" class="btn btn-primary"/>
                            <%--Login error--%>
                            <c:if test="${param.error eq '2'}"><span
                                    class="label label-danger">Access denied !!!</span></c:if>
                            <c:if test="${param.error eq '1'}"><span
                                    class="label label-danger">Authentication Failure</span></c:if>
                        </div>
                    </div>

                </form>
            </div>
        </div>

    </div>
    <div class="col-md-6">
        <p>你可以使用以下几个初始的账号进行登录:</p>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>Remark</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>admin</td>
                <td>admin</td>
                <td>All privileges, allow visit [Mobile] and [Unity] resources, manage user</td>
            </tr>
            <tr>
                <td>unity</td>
                <td>unity</td>
                <td>Only allow visit [Unity] resource, support grant_type:
                    <em>authorization_code,refresh_token,implicit</em></td>
            </tr>
            <tr>
                <td>mobile</td>
                <td>mobile</td>
                <td>Only allow visit [Mobile] resource, support grant_type: <em>password,refresh_token</em></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>


</body>
</html>