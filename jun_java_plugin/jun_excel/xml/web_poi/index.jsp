<%@ page language="java" pageEncoding="UTF-8"%>  
<%  
    String path = request.getContextPath();  
%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
    <body>  
        <form action="<%=path%>/downloadAction.action" method="post">  
            请选择您要生成报表的格式:  
            <select name="downType">  
                <option>  
                    请选择  
                </option>  
                <option value="PDF">  
                    PDF  
                </option>  
                <option value="CSV">  
                    CSV  
                </option>  
                <option value="Excel">  
                    Excel  
                </option>  
            </select>  
            <input type="submit" value="导出" />  
            <br />  
            <br/>  
            <table >  
            <tfoot align="right">列表内容</tfoot>  
                <thead>  
                    <tr>  
                        <th>  
                            id  
                        </th>  
                        <th>  
                            name  
                        </th>  
                        <th>  
                            age  
                        </th>  
                        <th>  
                            sex  
                        </th>  
                    </tr>  
                </thead>  
                <tbody>  
                    <%  
                        for (int i = 1; i < 6; i++) {  
                    %>  
                    <tr>  
                        <td><%=String.valueOf(i)%></td>  
                        <td><%=String.valueOf((char) (i + 64))%></td>  
                        <td><%=i + 20%></td>  
                        <td>  
                            man  
                        </td>  
                    </tr>  
                    <%  
                        }  
                    %>  
                </tbody>  
            </table>  
        </form>  
    </body>  
</html>  

