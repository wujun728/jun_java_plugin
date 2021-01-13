
$(function () {
	//按钮的设定
	$('#submit').button();
	$('#reg').buttonset();//按钮组
	//日历UI
	$('#StuBorndate').datepicker({
		closeText: '关闭',
		prevText: '&#x3C;上月',
		nextText: '下月&#x3E;',
		currentText: '今天',
		monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		changeMonth : true,
		changeYear : true,
		maxDate:0,
		yearRange: '1950:2020',
	});
	$('#TeaBorndate').datepicker({
		closeText: '关闭',
		prevText: '&#x3C;上月',
		nextText: '下月&#x3E;',
		currentText: '今天',
		monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		changeMonth : true,
		changeYear : true,
		maxDate:0,
		yearRange: '1950:2020',
	});
	//邮箱自动补全
	$('#Email').autocomplete({
		autoFocus : true,
		delay : 0,
		source : function (request, response) {
		var hosts = ['qq.com','163.com', '126.com','sina.com','263.com', 'gmail.com', 'hotmail.com'], //起始
		term = request.term, //获取输入值
		ix = term.indexOf('@'), //@
		name = term, //用户名
		host = '', //域名
		result = []; //结果
		//结果第一条是自己输入
		result.push(term);
		if(ix > -1) { //如果有@的时候
			name = term.slice(0, ix); //得到用户名
			host = term.slice(ix + 1); //得到域名
			}
			if(name) {
			//得到找到的域名
			var findedHosts = (host ? $.grep(hosts, function (value, index) {
			return value.indexOf(host) > -1;
			}) : hosts),
			//最终列表的邮箱
			findedResults = $.map(findedHosts, function (value, index) {
			return name + '@' + value;
			});
			//增加一个自我输入
			result = result.concat(findedResults);
			}
			response(result);
		},
	});
	(function ($) {
		$.extend($.validator.messages, {
			required: "必选字段",
			remote: "请修正该字段",
			email: "请输入正确格式的电子邮件",
			url: "请输入合法的网址",
			date: "请输入合法的日期",
			dateISO: "请输入合法的日期 (ISO).",
			number: "请输入合法的数字",
			digits: "只能输入整数",
			creditcard: "请输入合法的信用卡号",
			equalTo: "请再次输入相同的值",
			accept: "请输入拥有合法后缀名的字符串",
			maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
			rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
			range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
			max: $.validator.format("请输入一个最大为 {0} 的值"),
			min: $.validator.format("请输入一个最小为 {0} 的值")
		});
	}(jQuery));
});