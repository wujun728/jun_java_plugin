<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>Add User</h2>

<form:form commandName="formDto" cssClass="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label">Username<em class="text-danger">*</em></label>

        <div class="col-sm-10">
            <form:input path="username" cssClass="form-control" placeholder="Type username"
                        required="required"/>

            <p class="help-block">Username, unique.</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Password<em class="text-danger">*</em></label>

        <div class="col-sm-10">
            <form:password path="password" cssClass="form-control" placeholder="Type password"
                           required="required"/>

            <p class="help-block">Password, required.</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Privileges<em class="text-danger">*</em></label>

        <div class="col-sm-10">
            <label class="checkbox-inline">
                <form:checkbox path="privileges" value="MOBILE"/> MOBILE
            </label>
            <label class="checkbox-inline">
                <form:checkbox path="privileges" value="UNITY"/> UNITY
            </label>

            <p class="help-block">Select Privilege(s).</p>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">Phone</label>

        <div class="col-sm-10">
            <form:input path="phone" cssClass="form-control" placeholder="Type phone"/>

            <p class="help-block">User phone, optional.</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Email</label>

        <div class="col-sm-10">
            <form:input path="email" cssClass="form-control" placeholder="Type email"/>

            <p class="help-block">User email, optional.</p>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-2"></div>
        <div class="col-sm-10">
            <form:errors path="*" cssClass="label label-warning"/>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-2"></div>
        <div class="col-sm-10">
            <button type="submit" class="btn btn-success">Save</button>
            <a href="../overview">Cancel</a>
        </div>
    </div>
</form:form>

</body>
</html>