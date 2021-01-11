<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订餐</title>
<link href='/css/style.css' rel='stylesheet' />
<link href='/css/fullcalendar.css' rel='stylesheet' />
<link href='/css/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='/js/jquery.min.js'></script>
<script src='/js/jquery-ui.custom.min.js'></script>
<script src='/js/fullcalendar.min.js'></script>
<script>
$(document).ready(function() {
	
	bindData();

	var datetime = $.fullCalendar.parseDate("${ServerTime}");

	/* initialize the calendar
	-----------------------------------------------------------------*/
	
	$('#calendar').fullCalendar({
		year: datetime.getFullYear(),
		month: datetime.getMonth(),
		date: datetime.getDate(),
		header: {
			left: '',
			center: 'title',
			right: 'prev,next today'
		},
		firstDay:1,
		buttonText: {
			today: '今天',
			month: '月',
			week: '周',
			day: '日'
		},
		monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		dayNames: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
		dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
		timeFormat: {
			agenda: 'h:mmtt{ - h:mmtt}',
			'': 'h(:mm)tt'
		},
		columnFormat: {
			month: 'ddd',
			week: 'MM/dd ddd',
			day: 'ddd'
		},
		titleFormat: {
			month: 'yyyy 年 MMM 月',
			week: "yyyy.MM.dd{ '&#8212;' yyyy.MM.dd}",
			day: 'ddd'
		},
		editable: false,
		droppable: true, 
		drop: function(date, allDay) { 
		
			var originalEventObject = $(this).data('eventObject');
			
			var copiedEventObject = $.extend({}, originalEventObject);
			copiedEventObject.start = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
			copiedEventObject.allDay = allDay;
			copiedEventObject.userId = "<%=session.getAttribute("userId")%>";
			copiedEventObject.state = $("input[name='timeFlag']:checked").val();
			
			$.getJSON("/userMenu/addFoodEvent",copiedEventObject, function(data){
				if(data.isSuccess){
					$('#calendar').fullCalendar('renderEvent', data.event, false);
				}else{
					if(data.msgOption==1){
						if(confirm(data.msg+"["+originalEventObject.title+"]")){
							$.getJSON("/userMenu/updateFoodEvent",copiedEventObject, function(data){
								if(data.isSuccess){
									$('#calendar').fullCalendar('removeEvents', data.event.id);
									$('#calendar').fullCalendar('renderEvent', data.event, false);
								}else{
									alert("替换失败！");
								}
							});
						}
					}else{
						alert(data.msg);
					}
				}
			});
			
		},
		eventSources: [
               {
            	   url: '/userMenu/getDurationEvent',
            	   data: {
            		   userId:"<%=session.getAttribute("userId")%>"
            	   }
               }
			],
		eventMouseover: function( event, jsEvent, view ) {
			if(event.isExpire == 0){
				$(this).children("div").append("<div class='delIcon'><a href='/userMenu/delFootEvent/"+event.id+"'>&times</a></div>");
			}
		},
		eventMouseout: function( event, jsEvent, view ) {
			$(".delIcon").remove();
		},
		loading: function(bool) {
			if (bool) $('#loading').show();
			else $('#loading').hide();
		}

	});
	
	
});

function changeMenu(){
	var url = "/menu/getMenu/"+$("input[name='timeFlag']:checked").val();
	$.getJSON(url,function(data){
		$(".external-event").remove();
		$(data).each(function(index) {
            var val = data[index];
            $("#external-events").append("<div class='external-event' menuId='"+val.id+"' price='"+val.price+"'>"+val.name+"("+val.price+"元)</div>");
        });
		bindData();
	});
}

function bindData(){
	$('#external-events div.external-event').each(function() {
		var eventObject = {
			menuId: $(this).attr("menuId"),
			title: $.trim($(this).text()),
			price: $(this).attr("price")
		};
		
		$(this).data('eventObject', eventObject);
		
		$(this).draggable({
			zIndex: 999,
			revert: true,     
			revertDuration: 0  
		});
		
	});
}

</script>
</head>
<body>
	<div id='wrap'>

		<div id='external-events'>
			<h4><span>菜单</span>
				<input type="radio" name="timeFlag" value="1" checked="checked" onclick="changeMenu()">午餐
				<input type="radio" name="timeFlag" value="2" onclick="changeMenu()">晚餐
			</h4>
			<c:forEach items="${menuList}" var="menu">
				<div class='external-event' menuId="${menu.id}" price="${menu.price }">${menu.name}(${menu.price }元)</div>
			</c:forEach>
		</div>

		<div id='calendar'></div>

		<div style='clear: both'></div>
	</div>
	<div id='loading'>loading...</div>
	<div id='exit'><a href="/exit">退出</a></div>
</body>
</html>