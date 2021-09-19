
	$(function() {
		$("#provinceId").combobox({
			width:171,
			url:"areaController.do?method=findProvinces",
			valueField: 'provinceId',
			textField: 'name'
		});
	});
