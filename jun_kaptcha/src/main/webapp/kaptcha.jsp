<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <table>  
            <tr>  
                <td><img src="/jun_biz_erp2/Kaptcha.jpg"></td>  
                <td valign="top">  
              
                    <form method="POST">  
                        <br>sec code:<input type="text" name="kaptchafield"><br />  
                        <input type="submit" name="submit">  
                    </form>  
                </td>  
            </tr>  
        </table>    
      
        <br /><br /><br /><br />  
          
        <%
            String c = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
            String parm = (String) request.getParameter("kaptchafield");  
              
            out.println("Parameter: " + parm + " ? Session Key: " + c + " : ");  
              
            if (c != null && parm != null) {  
                if (c.equals(parm)) {  
                    out.println("<b>true</b>");  
                } else {  
                    out.println("<b>false</b>");  
                }
            } 
        %>  
</body>
</html>