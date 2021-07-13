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

    <title>List of saved users</title>

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
     	<a href="${pageContext.request.contextPath }/" class="btn btn-primary">Back</a><br /><br />
      <c:if test="${empty users }">
      <div class="alert alert-success">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong>:(</strong> No users were found
			</div>
      </c:if>
      <c:if test="${param.delete == 1 }">
	<div class="alert alert-danger">
				<a href="${pageContext.request.contextPath}/usersList#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<span class="glyphicon glyphicon-thumbs-up"></span> Your profile information has been successfully deleted
			</div>
	</c:if>
        <table class="table table-striped table-hover">
			<tr>
				<th>Image</th>
				<th>Firstname</th>
				<th>Lastname</th>
				<th>Age</th>
				<th>Address</th>
				<th>Delete</th>
			</tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><img src="<c:url value="/resources/uploaded-images/${user.userId}.png"/>" alt="image" class="image"/></td>
						<td>${user.userId}</td>
						<td>${user.firstName}</td>
						<td>${user.lastName }</td>
						<td>${user.age }</td>
								<td><a href='<c:url value="/index/deleteUser/${user.userId}"/>'><span
								class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
		</table>
      </div>

      <footer class="footer">
        <p>&copy; 2016 Morebodi Rekz Modise</p>
      </footer>

    </div> <!-- /container -->
  </body>
</html>