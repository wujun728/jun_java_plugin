<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%
    final String callback = request.getParameter("callback");

    int pageSize = Integer.parseInt(request.getParameter("pageSize"));
    int curPage = Integer.parseInt(request.getParameter("curPage"));

    final String sortName = request.getParameter("sortName");
    final String sortOrder = request.getParameter("sortOrder");

    // only for load-time-test.html
    final String load_time_test = request.getParameter("load_time_test");

    // only for search.html
    String xh = request.getParameter("xh");
    if ("".equals(xh)) {
        xh = null;
    }

    // data list
    List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    int totalRows = 26;
    if ("true".equals(load_time_test)) {
        totalRows = 2600;
    }
    for (int i = 0; i < totalRows; i++) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("XH", i + 1);
        map.put("ID", 100 - i);
        map.put("CHAR", "char_" + i);
        map.put("TEXT", "TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_TEXT_" + i);
        map.put("DATE", "2012-12-12 15:01:01");
        map.put("TIME", "15:01:01");
        map.put("NUM", i * 10);
        if (xh != null) {
            if (Integer.toString(i + 1).equals(xh)) {
                data.add(map);
            }
        } else {
            data.add(map);
        }
    }

    if (xh != null) {
        totalRows = data.size();
    }

    // sort
    if ("XH".equals(sortName) || "ID".equals(sortName)) {
        if ("asc".equals(sortOrder)) {
            Collections.sort(data, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return Integer.parseInt(map1.get(sortName).toString()) - Integer.parseInt(map2.get(sortName).toString());
                }
            });
        } else if ("desc".equals(sortOrder)) {
            Collections.sort(data, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return Integer.parseInt(map2.get(sortName).toString()) - Integer.parseInt(map1.get(sortName).toString());
                }
            });
        }
    } else if ("XH,ID".equals(sortName)) {
        Collections.sort(data, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                int xhCp = Integer.parseInt(map1.get("XH").toString()) - Integer.parseInt(map2.get("XH").toString());
                int idCp = Integer.parseInt(map1.get("ID").toString()) - Integer.parseInt(map2.get("ID").toString());
                if (sortOrder.equals("asc,asc")) {
                    return xhCp == 0 ? idCp : xhCp;
                } else if (sortOrder.equals("asc,desc")) {
                    return xhCp == 0 ? -idCp : xhCp;
                } else if (sortOrder.equals("desc,asc")) {
                    return xhCp == 0 ? idCp : -xhCp;
                } else if (sortOrder.equals("desc,desc")) {
                    return xhCp == 0 ? -idCp : -xhCp;
                }
                return 0;
            }
        });
    }

    StringBuilder jsonSb = new StringBuilder();
    jsonSb.append("{");
    jsonSb.append("\"success\": true");
    jsonSb.append(", \"totalRows\": ").append(totalRows);
    jsonSb.append(", \"curPage\": ").append(curPage);
    jsonSb.append(", \"data\": ");
    jsonSb.append("[");

    int startRow = pageSize * (curPage - 1) + 1;
    int endRow = startRow + pageSize - 1;
    // if pageSize == 0, then return all
    if (endRow > totalRows || pageSize == 0) {
        endRow = totalRows;
    }

    for (int i = startRow - 1; i < endRow; i++) {
        if (i != startRow - 1) {
            jsonSb.append(",");
        }
        Map<String, Object> map = data.get(i);
        jsonSb.append("{");
        jsonSb.append("\"XH\": ").append(map.get("XH")).append(",");
        jsonSb.append("\"ID\": ").append(map.get("ID")).append(",");
        jsonSb.append("\"CHAR\": \"").append(map.get("CHAR")).append("\",");
        jsonSb.append("\"TEXT\": \"").append(map.get("TEXT")).append("\",");
        jsonSb.append("\"DATE\": \"").append(map.get("DATE")).append("\",");
        jsonSb.append("\"TIME\": \"").append(map.get("TIME")).append("\",");
        jsonSb.append("\"NUM\": ").append(map.get("NUM"));
        jsonSb.append("}");
    }

    jsonSb.append("]");
    jsonSb.append("}");

    if (callback != null) {
        jsonSb.insert(0, callback + "(");
        jsonSb.append(");");
    }
    out.print(jsonSb.toString());
%>