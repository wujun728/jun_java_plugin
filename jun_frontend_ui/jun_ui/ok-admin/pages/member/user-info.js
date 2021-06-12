layui.use(['form', 'layer', 'upload', 'laydate', 'okAddlink'], function () {
	var form = layui.form;
	var upload = layui.upload;
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var $form = $('form');
	var okAddlink = layui.okAddlink.init({
		province: 'select[name=province]',
		city: 'select[name=city]',
		area: 'select[name=area]',
	});
	okLoading.close();
	laydate.render({
		elem: '#uDate', //指定元素
		max: "2019-1-1",
		value: '2017-09-10',
	});

	okAddlink.render();//渲染三级联动省市区

});























