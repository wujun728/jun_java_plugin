<%@ tag description="Image upload form" %>

<h1>Upload images</h1>

<form method="post" action="${pageContext.request.contextPath}/images/upload" enctype="multipart/form-data">
    File: <input type="file" name="file" /> <br />
    File: <input type="file" name="file" /> <br />
    File: <input type="file" name="file" /> <br />
    
    <input type="submit" />
</form>