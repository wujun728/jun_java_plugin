<%@ page language="java" pageEncoding="UTF-8" %>
<%
    int curPage = Integer.parseInt(request.getParameter("curPage"));

    StringBuilder jsonSb = new StringBuilder();
    jsonSb.append("{");
    jsonSb.append("\"success\": true");
    jsonSb.append(", \"totalRows\": ").append(5);
    jsonSb.append(", \"curPage\": ").append(curPage);
    jsonSb.append(", \"data\": ");

    if (curPage == 1) {
        jsonSb.append("[");
        jsonSb.append("{\"XH\": 1, \"ID\": 101, \"CHAR\": \"CHAR_1\"}");
        jsonSb.append(",");
        jsonSb.append("{\"XH\": 2, \"ID\": 102, \"CHAR\": \"CHAR_2\"}");
        jsonSb.append("]");
        jsonSb.append(",");
        jsonSb.append("\"userdata\": {\"dynamic_columns\": [\"XH\", \"ID\", \"CHAR\"]}");
    } else if (curPage == 2) {
        jsonSb.append("[");
        jsonSb.append("{\"XH\": 3, \"TEXT\": \"TEXT_3\"}");
        jsonSb.append(",");
        jsonSb.append("{\"XH\": 4, \"TEXT\": \"TEXT_4\"}");
        jsonSb.append("]");
        jsonSb.append(",");
        jsonSb.append("\"userdata\": {\"dynamic_columns\": [\"XH\", \"TEXT\"]}");
    } else if (curPage == 3) {
        jsonSb.append("[");
        jsonSb.append("{\"XH\": 5, \"ID\": 105, \"CHAR\": \"CHAR_5\", \"TEXT\": \"TEXT_5\"}");
        jsonSb.append("]");
        jsonSb.append(",");
        jsonSb.append("\"userdata\": {\"dynamic_columns\": [\"XH\", \"ID\", \"CHAR\", \"TEXT\"]}");
    }

    jsonSb.append("}");
    out.print(jsonSb.toString());
%>