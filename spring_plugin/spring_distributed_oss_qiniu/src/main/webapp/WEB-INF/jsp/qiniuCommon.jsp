<%--
  Created by IntelliJ IDEA.
  User: kzyuan
  Date: 2017/6/20
  Time: 下午8:51
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <title>七牛云服务示例</title>
</head>
<body>

<input type="file" id="upload" name="imagefile" />
<input type="button" value="上传" onclick="return ajaxFileUploadImage();"/><br>
<div id="imgurl">
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/global.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script type="text/javascript" language="JavaScript" >
    //图片上传
    function ajaxFileUploadImage() {

        $.ajaxFileUpload({
            url: "<%=request.getContextPath()%>/qiniuUpload",//用于文件上传的服务器端请求地址
            secureuri: false,//一般设置为false
            fileElementId: 'upload',//文件上传控件的id属性  <input type="file" id="upload" name="upload" />
            dataType: 'text',//返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
//                console.log(data);
//                $("#imageUrl").html(data);
                var img = "&lt;img src="+data+"&gt;"
                var html = "<br><span>"+img+"</span>";
                $("#imgurl").append(html);
            }
        });
        return false;
    }
</script>

</body>
</html>
