$(function(){
	reSizeWin();
	shControlInfoList();
});

function reSizeWin(){
	resizeToDatagrid('controlMatterList', document.body.clientWidth, document.body.clientHeight);
}
window.onresize = reSizeWin;

function shControlInfoList(){
	var url = basePath + "/projectActive.do?ExeMethod=queryControlList";
	asyncLoadDataGrid("controlMatterList", url);
}

function clickMatter(){
	var record = getSelectedItem("controlMatterList");
	if (record) {
		var biCode = record.BICODE;
		var billId = record.BILLID;
		var wfInstId = record.WFINSTID;
		var actInstId = record.ACTINSTID;
		var actCode = record.ACTCODE;
		var exeMethod = "loadEditPage";
		var url = basePath + "/projectActive.do?ExeMethod=" + exeMethod+"&exeFlag=0"
				+ "&biCode=" + biCode + "&billId=" + billId + "&wfInstId="
				+ wfInstId + "&actInstId=" + actInstId + "&actCode=" + actCode;
		SystemModalDialog(url, 1024, 768);
	} else {
		$.messager.alert("提示", "没有选择的记录!", "info");
	}
}

function showWfPic(){
	var record = getSelectedItem("controlMatterList");
	if (record) {
		var wfInstId = record.WFINSTID;
		var strUrl = "/workflow/flow.do?method=viewDiagram&id=" + wfInstId;
		SystemModalDialogOfScroll(strUrl, 850, 450);
	} else {
		$.messager.alert("提示", "没有选择的记录!", "info");
	}
}