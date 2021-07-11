<%@ tag description="Display an image thumbnail" %>

<%@ attribute name="src" required="true" type="com.github.jmitchener.model.Image" %>

<div class="image-thumbnail">
    <a href="${pageContext.request.contextPath}/images/${src.id}">
        <img src="${pageContext.request.contextPath}/images/${src.id}/thumbnail" />
    </a>
</div>