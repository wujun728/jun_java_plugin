<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<!-- 	 Ç°Ì¨µ÷ÓÃ·½Ê½ -->
    <div class="chknumber">  
           <label>ÑéÖ¤Âë£º          
           <input name="kaptcha" type="text" id="kaptcha" maxlength="4" class="chknumber_input" />               
           </label>  
            <img src="/ClinicCountManager/captcha-image.do" width="55" height="20" id="kaptchaImage"  style="margin-bottom: -3px"/>   
           <script type="text/javascript">      
            $(function(){           
                $('#kaptchaImage').click(function () {//Éú³ÉÑéÖ¤Âë  
                 $(this).hide().attr('src', '/ClinicCountManager/captcha-image.do?' + Math.floor(Math.random()*100) ).fadeIn(); })      
                      });   
            
           </script>   
    </div>  
         
         
         <% //È¡ÑéÖ¤ÂëµÄ·½Ê½
         	String code = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
         %>
</body>
</html>