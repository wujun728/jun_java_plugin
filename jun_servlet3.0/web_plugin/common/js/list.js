$(function() {
	if(flag=="1"){
		setComboboxValue('deptCode',loginDeptCode)
	}
	var urlStr = basePath + '/projectActive.do?ExeMethod=queryActiveProject';
	$('#infoList').treegrid( {
		url : urlStr,
		queryParams : getQueryParams(1, 25),
		width : document.body.clientWidth,
		height : document.body.clientHeight - 27,
		pagination : true,
		autoRowHeight : false,
		pageNumber : 1,
		pageSize : 25,
		pageList : [ 25, 50, 100 ],
		idField : 'ID',
		treeField : 'PJNAME',
		frozenColumns : [ [ {
			field : 'PJNAME',
			title : '项目名称',
			width : 250,
			align : 'left',sortable:'true',
				formatter:function(val ,row,index){
					if(row.ACTSTATE=="<span style='width:100%;text-align:center;'>---</span>"){
						return val;
					}else{
						return '<a href="####" onclick="goto2('+row.ID+');event.returnValue=false;" style="text-decoration: none;">'+'<span style="color:blue">'+val+'</span>'+'</a>';
					}
            	}
		} ] ],
		columns : [ [ {
			field : 'PJCODE',
			title : '项目编码',
			width : 200,
			align : 'left',sortable:'true'
		}, {
			field : 'DEPTNAME',
			title : '承做部门',
			width : 250,
			align : 'left',sortable:'true'
		}, {
			field : 'ACTSTATE',
			title : '本环节进度',
			width : 150,
			align : 'left',sortable:'true'
		}, {
			field : 'PJSTATE',
			title : '项目状态',
			width : 100,
			align : 'center',sortable:'true'
		}, {
			field : 'WHOLESTATE',
			title : '业务活动整体完成标记',
			width : 150,
			hidden: true
		} , {
			field : 'CLSJ',
			title : '创建时间',
			width : 150,
			align : 'center',sortable:'true'
		} ] ],
		onDblClickRow : function(rowData) {
			gotoPage(rowData);
		},
		onLoadSuccess : function() {
			delete $(this).treegrid('options').queryParams['id'];
		}
	});
//分页
	$('#infoList').treegrid('getPager').pagination( {
		onSelectPage : function(pageNumber, pageSize) {
			$("#infoList").treegrid("options").queryParams = getQueryParams(pageNumber, pageSize);
			$('#infoList').treegrid('reload');
		}
	});
});

function reSizeWin() {
	$('#infoList').treegrid('resize', {
		width : document.body.clientWidth,
		height : document.body.clientHeight - 27
	});
}
window.onresize = reSizeWin;


function goto2(row){
	 var record = $('#infoList').treegrid('find',row);
	 if(record){
		 gotoPage(record);
	 }
	 return false;
}


function getQueryParams(pageNumber, pageSize) {
	var pjName = $.trim($("#pjName").val());
	var pjCode = $.trim($("#pjCode").val());
	var deptCode = getComboboxValue("deptCode");

	var params = {
		page : pageNumber,
		rows : pageSize,
		pjName : pjName,
		pjCode : pjCode,
		posiName : encodeURIComponent(posiName),
		userCode : userCode,
		loginDeptCode : loginDeptCode,
		deptCode : deptCode
	};
	return params;
}

function shInfoList(page) {
	var event = jQuery.Event("keydown");//模拟一个键盘事件
	event.keyCode = 13;//keyCode=13是回车
	var pager = $("#infoList").treegrid('getPager');
	pager.find('input.pagination-num').val(page); //设置跳转页为当前指定页面
	pager.find('input.pagination-num').trigger(event);
}

function gotoPage(rowData) {
	if (!rowData._parentId) {
		return;
	}
	//	//添加一个tab
	//	parent.addTab('menu_cbywhd',
	//				'初步业务活动',
	//				'/busmgr/projectActive.do?ExeMethod=loadIndexPage&selBillId=' 
	//				+ rowData.BILLID);
	var wfInstId = "";
	var actInstId = "";
	var biCode = rowData.BICODE;
	var billId = rowData.BILLID;
	var actCode = rowData.ACTCODE;
	var url = basePath + "/projectActive.do?ExeMethod=loadEditPage"
			+ "&biCode=" + biCode + "&billId=" + billId + "&wfInstId="
			+ wfInstId + "&actInstId=" + actInstId + "&actCode=" + actCode
			+ "&exeFlag=0";
	SystemModalDialog(url, 1024, 768);
}