<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>File upload demo with Spring framework</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/custom.css" />
  </head>

  <body>

    <div class="container">
      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills pull-right">

          </ul>
        </nav>
        <h3 class="text-muted">Java Spring MVC</h3>
      </div>

      <div class="row marketing">
      <h3>File upload form with javax/hibernate validations:</h3>
      <hr />
      <c:if test="${param.success == 1 }">
	<div class="alert alert-success">
				<a href="${pageContext.request.contextPath }#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<span class="glyphicon glyphicon-thumbs-up"></span> Your profile information has been successfully saved
			</div>
	</c:if>
       <form:form
			action="${pageContext.request.contextPath}/index/saveUser"
			 enctype="multipart/form-data" method="post"  commandName="user">
			<div class="form-group">
				<label for="name">Firstname</label> <form:errors path="firstName" cssStyle="color:#ff0000;" />
				<form:input path="firstName" id="name" name="name" class="form-control" />
			</div>
			<div class="form-group">
				<label for="lastName">Lastname</label> <form:errors path="lastName" cssStyle="color:#ff0000;" />
				<form:input path="lastName" id="lastname" name="lastname"
					class="form-control"></form:input>
			</div>
			<div class="form-group">
				<label for="age">Age</label> <form:errors path="age" cssStyle="color:#ff0000;" />
				<form:input path="age" id="age" name="age"
					class="form-control"></form:input>
			</div>
			<div class="form-group">
				<label for="address">Address</label> <form:errors path="address" cssStyle="color:#ff0000;" />
				<form:input path="address" id="address" name="address"
					class="form-control"></form:input>
			</div>
			<div class="form-group">
				<label class="control-label" for="productImage">Upload Image</label>
				<form:input type="file" path="userImage" id="userImage" name="userImage"
					class="form:input-large"></form:input>
			</div>
			<div class="form-group">
				<input type="submit" value="Submit" class="btn btn-success" />
			</div>
		</form:form>
		<a href="${pageContext.request.contextPath}/usersList" class="btn btn-primary pull-right">Users</a>
      </div>
<div class="row marketing">

</div>
      <footer class="footer">
        <p>&copy; 2016 Morebodi Rekz Modise. I have left the record editing to you, but if you have tried and still struggle, then halla at me and will upload it</p>
      </footer>

    </div> <!-- /container -->
  </body>
</html>