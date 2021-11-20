<%@ include file="../includes.jsp" %>

<m:layout title="">
    id: ${image.id}<br />
    contentType: ${image.contentType}<br />
    
    <i:img src="${image}" />
</m:layout>