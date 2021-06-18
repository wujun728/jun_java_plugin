<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
  <%
  Object obj = application.getAttribute("count");
     if(obj==null){
   Integer count = 1;
   application.setAttribute("count",count);
   
   }else{
   Integer count = (Integer)obj;
   count++;
   application.setAttribute("count",count);
   }
   %>
</head>
<link href="css/slide.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript">
	$(function(){
			$("#focus").hover(function(){$("#focus-prev,#focus-next").show();},function(){$("#focus-prev,#focus-next").hide();});
			$("#focus").slide({ 
				mainCell:"#focus-bar-box ul",
				targetCell:"#focus-title a",
				titCell:"#focus-num a",
				prevCell:"#focus-prev",
				nextCell:"#focus-next",
				effect:"left",
				easing:"easeInOutCirc",
				autoPlay:true,
				delayTime:200
			})
		})
</script>
<body>
<div style="text-align: center;">
	<div style="padding: 10px;"><input type="button" style="border: 1px solid red;" value="获取姓名" onclick="javascript:window.location='getName'"/>&nbsp;&nbsp;<input type="text" name="name" value="${name }" style="border-bottom: 1px solid red"/>&nbsp;&nbsp;页面刷新次数：<font color="red">${count }</font></div>
</div>
<div id="focus">
<div class="hd">
<div class="focus-title" id="focus-title">
    <a href="http://www.java1234.com"><span class="title">《王的女人》</span>于正版霸王别姬 陈乔恩变腹黑大战情敌袁姗姗</a>
    <a href="http://www.java1234.com"><span class="title">《盲探》</span>刘德华、郑秀文、杜琪峰香港电影铁三角十年归来</a>
    <a href="http://www.java1234.com"><span class="title">《爸爸去哪儿》</span>众星爸厨艺比拼犯难（2013-10-18期）</a>
    <a href="http://www.java1234.com"><span class="title">《到爱的距离》</span>李晨张馨予谱写爱之终曲</a>
    <a href="http://www.java1234.com"><span class="title">《天台》</span>周杰伦自导自演  传递超屌正能量</a>
</div>
    <a class="hdicon" href="http://www.java1234.com"></a>
    </div>
    <div class="focus-bar-box" id="focus-bar-box">
      <ul class="focus-bar">
        <li><a href="http://www.java1234.com"><img src="images/01.jpg"></a></li>
        <li><a href="http://www.java1234.com"><img src="images/02.jpg"></a></li>
        <li><a href="http://www.java1234.com"><img src="images/03.jpg"></a></li>
        <li><a href="http://www.java1234.com"><img src="images/04.jpg"></a></li>
        <li><a href="http://www.java1234.com"><img src="images/05.jpg"></a></li>
      </ul>
    </div>
    <a href="http://www.java1234.com" class="btn-prev" onclick="return false;" hidefocus="" id="focus-prev"></a>
    <a href="http://www.java1234.com" class="btn-next" onclick="return false;" hidefocus="" id="focus-next"></a>
  <div class="ft">
    <div class="ftbg"></div>
    <div id="focus-num" class="change">
        <a class=""></a>
        <a class=""></a>
        <a class=""></a>
        <a class=""></a>
        <a class=""></a>
    </div>
  </div>
</div>
</body>
</html>