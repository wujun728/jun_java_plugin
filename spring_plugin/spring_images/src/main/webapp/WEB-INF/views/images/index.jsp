<%@ include file="../includes.jsp" %>

<m:layout title="Image Uploader" style="images">
    <div id="image-container">
        <div id="image-uploadForm">
            <i:uploadForm />
        </div>
        <div id="image-recentUploads">
            <h1>Recent images</h1>
            
            <div id="recent-thumbnails">
                <c:forEach var="image" items="${recentImages}">
                    <i:thumbnail src="${image}" />
                </c:forEach>
            </div>
        </div>
    </div>
</m:layout>