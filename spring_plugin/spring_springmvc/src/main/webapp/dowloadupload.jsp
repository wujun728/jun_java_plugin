<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="//cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
</head>
<body>
	<form action="/upload" method="post" enctype="multipart/form-data">
		<input type="file" name="file"/>
		<input type="submit" value="上传"/>
	</form>
	<hr/>
	<table>
		<thead>
			<tr>
				<th>
					文件名
				</th>
				<th>
					图片
				</th>
				<th>
					操作
				</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<%-- <img src="/common/img/${filename}" alt="说明"/> --%>
	<script type="text/javascript">
		$.get("/getfiles",function(data){
			var tbody=$("table>tbody");
			$.each(data,function(i,item){
				var tr=$("<tr></tr>");
				$("<td></td>").html(item.filename).appendTo(tr);
				$("<td></td>").html(
					$("<img></img>").attr("src","/common/img/"+item.filename)
					).appendTo(tr);
				$("<td></td>").html(
					$("<a></a>").html("下载").attr("href","/download?filename="+item.filename)
					).appendTo(tr);
				tr.appendTo(tbody);
			})
		},"json")
	</script>
</body>
</html>