layui.use([ 'form', 'laydate' ], function() {
	var laydate = layui.laydate;
	var form = layui.form
	var $ = layui.$;
	$('[date="true"]').each(function(idx, item) {
		laydate.render({
			elem : item,
			type : 'date'
		});
	});
	$('[datetime="true"]').each(function(idx, item) {
		laydate.render({
			elem : item,
			type : 'datetime'
		});
	});
	$('[time="true"]').each(function(idx, item) {
		laydate.render({
			elem : item,
			type : 'time'
		});
	});
	log($('#closeWinBtn'));
	log($('#closeWinBtn').length);
	$('#closeWinBtn').click(function() {
		log('close win btn');
		parent.closeCurrentLayer();
	});
	form.verify({
		  username: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
		      return '用户名不能有特殊字符';
		    }
		    if(/(^\_)|(\__)|(\_+$)/.test(value)){
		      return '用户名首尾不能出现下划线\'_\'';
		    }
		    if(/^\d+\d+\d$/.test(value)){
		      return '用户名不能全为数字';
		    }
		  }
		  //我们既支持上述函数式的方式，也支持下述数组的形式
		  //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		  ,pass: [
		    /^[\S]{6,18}$/
		    ,'密码必须6到18位，且不能出现空格'
		  ] 
		  ,digits: [
			  /^\d+$/
			  ,'必须输入整数'
			  ] 
		});
});
