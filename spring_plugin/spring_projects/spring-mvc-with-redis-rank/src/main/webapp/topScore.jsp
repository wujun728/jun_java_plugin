<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>排行榜</h2>

<table style="width: 500px;">
    <thead>
    <tr>
        <th>排名</th>
        <th>id</th>
        <th>得分</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${topScores}" var="topScore" varStatus="status">
        <tr style="text-align: center">
            <td>${status.index+1}</td>
            <td>${topScore.value}</td>
            <td>${topScore.score}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
