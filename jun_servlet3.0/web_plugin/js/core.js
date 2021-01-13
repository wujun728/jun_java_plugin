//取出串中的前后空格
String.prototype.Trim = function() {
	return this.replace(/^\s+/,'').replace(/\s+$/,'');
};

//组件类型
var eType = {
//eType new
Input : -1,ValidateBox : 0,Combo : 1,ComboBox : 2,ComboTree : 3,ComboGrid : 4,NumberBox : 5,DateBox : 6,DateTimeBox : 7,Calendar : 8,Spinner : 9,NumberSpinner : 10,TimeSpinner : 11,Slider : 12,HtmlEdit : 13,DataGrid : 14,TreeGrid : 15,TextArea :16,SelUsersInput:17,Checkbox:18,FlexPaper:19,Chart:20,MultipleDateBox:21
}

function getEtypeClass(type){
	switch (type) {
		case eType.ValidateBox: return 'easyui-validatebox';
		case eType.TextArea: return 'easyui-validatebox';
		case eType.ComboBox : return 'easyui-combobox';
		case eType.ComboTree : return 'easyui-combotree';
		case eType.ComboGrid : return 'easyui-combogrid';
		case eType.NumberBox : return 'easyui-numberbox';
		case eType.DateBox : return 'easyui-datebox';
		case eType.DateTimeBox : return 'easyui-datetimebox';
		case eType.Calendar : return 'easyui-calendar';
		case eType.NumberSpinner : return 'easyui-numberspinner';
		case eType.TimeSpinner : return 'easyui-timespinner';
		case eType.Slider : return 'easyui-slider';
//css new
		default: return undefined;
	}
}

function setComboTreeDefaultV(obj,v){
	var vs=v.split('/');
	var t=obj.combotree('tree');
	t.tree({'onLoadSuccess':
		function(node,data){
	    if(data){
	        $(data).each(function(index,d){
	        	 if( (vs[vs.length-1]==this.id) || (vs[vs.length-1]==this.text)){
	    	    	 return  obj.combotree('setValue', this.id);
	    	       }
	       if(this.state == 'closed'){
	           t.tree('expandAll');
	       }
        });
        }
	  }});
}

function eSet(k,v){
	if(v==undefined) v='';
	var obj = $(k);
	if(obj.length!=1) return;
	var t=parseInt($(k).attr('reftype'));
	switch(t){
	case eType.HtmlEdit:
		var h=top.formfieldmap.get(k.substring(1));
		h.ready(function(){h.setContent(ttoh(v));})
		break;
	case eType.ComboTree:
		if(v.indexOf('/')!=-1) {
			setComboTreeDefaultV(obj,v);
		}else{
			obj.combotree('setValue', v);
		}
		break;
	case eType.ComboBox:
		obj.combobox('setValue', v);
		break;
	case eType.DateBox:
		obj.datebox('setValue', v);
		break;
	case eType.Slider:
		obj.slider('setValue', v);
		break;
	case eType.NumberBox:
		obj.numberbox('setValue', v);
		break;
	case eType.DateTimeBox:
		return obj.datetimebox('setValue', v);
	case eType.SelUsersInput:
		return obj.html(v);
	case eType.MultipleDateBox:
		return obj.html(v);
	case eType.FlexPaper:
		return obj.html(v);
	case eType.Checkbox:
		return (v=='是'||v=='true')?obj.attr('checked','true'):obj.removeAttr('checked');
//eSet new
	default:
		obj.val(v);
	}
}
//获取值方法，参数k为jquery选择器
function eGet(k){
	var obj = $(k);
	if(obj.length!=1) {return log("ID重复错误!ID为：<"+k+">");}
	var t=parseInt($(k).attr('reftype'));
	switch(t){
	case eType.ComboTree:
		if(obj.combotree('options').valueField=='text'){
			return obj.combotree('getText');
		}else{
			return obj.combotree('getValue');
		}
	case eType.HtmlEdit:
		return top.formfieldmap.get(k.substring(1)).getContent();
	case eType.ComboBox:
		return obj.combobox('getValue');
	case eType.Slider:
		return obj.slider('getValue');
	case eType.DateBox:
		return obj.datebox('getValue');
	case eType.NumberBox:
		return obj.numberbox('getValue');
	case eType.DateTimeBox:
		return obj.datetimebox('getValue');
	case eType.SelUsersInput:
		return obj.html();
	case eType.MultipleDateBox:
		return obj.html();
	case eType.FlexPaper:
		return obj.html();
	case eType.Checkbox:return obj.prop('checked')?'是':'否';
//eGet new
	default:
		return obj.val();
	}
}
//控制控件状态,k为jquery选择器,v为true/false
function eEnable(k,v){
	var obj = $(k);
	if(obj.length!=1) {return log("ID重复错误!ID为：<"+k+">");}
	var t=parseInt($(k).attr('reftype'));
	switch(t){
	case eType.ComboTree:
		if(v){obj.combotree('enable')}else{obj.combotree('disable')};return;
	case eType.HtmlEdit:
		return top.formfieldmap.get(k.substring(1)).setOpt({'readonly':v});
	case eType.ComboBox:
		if(v){obj.combobox('enable')}else{obj.combobox('disable')};return;
	case eType.Slider:
		if(v){obj.slider('enable')}else{obj.slider('disable')};return;
	case eType.DateBox:
		if(v){obj.datebox('enable')}else{obj.datebox('disable')};return;
	case eType.NumberBox:
		if(v){obj.numberbox('enable')}else{obj.numberbox('disable')};return;
	case eType.DateTimeBox:
		if(v){obj.datetimebox('enable')}else{obj.datetimebox('disable')};return;
	case eType.SelUsersInput:
		 if(v){$(k+'_selusers').linkbutton('enable')}else{$(k+'_selusers').linkbutton('disable')};return;
	case eType.FlexPaper:
		if(v){$(k+'_pdfv').linkbutton('enable')}else{$(k+'_pdfv').linkbutton('disable')};return;
	default:
		 if(v){obj.removeAttr('disabled');}else{obj.attr('disabled', 'disabled');}return;
	}
}

//值变化时
function eValueChange(k,callback){
	var obj = $(k);
	if(obj.length!=1) {return log("ID重复错误!ID为：<"+k+">");}
	var t=parseInt($(k).attr('reftype'));
	switch(t){
	case eType.ComboTree:
		return obj.ComboTree({onChange:function(newValue, oldValue){callback(newValue, oldValue);}});
	case eType.ComboBox:
		return obj.combobox({onChange:function(newValue, oldValue){callback(newValue, oldValue);}});
	case eType.Slider:
		return obj.slider({onChange:function(newValue, oldValue){callback(newValue, oldValue);}});
	case eType.DateBox:
		return obj.datebox({onChange:function(newValue, oldValue){callback(newValue, oldValue);}});
	case eType.NumberBox:
		return obj.numberbox({onChange:function(newValue, oldValue){callback(newValue, oldValue);}});
	case eType.DateTimeBox:
		return obj.datetimebox({onChange:function(newValue, oldValue){callback(newValue, oldValue);}});
	default:
		 return log('['+k+']暂不支持，类型编号为'+t+'！');
	}
}
//更新方法,参数k为jquery选择器
function eUpdate(k){
	var obj = $(k);
	if(obj.length!=1) return log("ID重复错误!ID为：<"+k+">");
	var t=parseInt($(k).attr('reftype'));
	switch(t){
	case eType.DataGrid:
		obj.datagrid('load').datagrid('uncheckAll').datagrid('unselectAll');
		break;
	case eType.TreeGrid:
		if(obj.treegrid('options').durl){
			obj.treegrid('options').url=obj.treegrid('options').durl;
		}
		obj.datagrid('unselectAll').datagrid('clearSelections').datagrid('clearChecked').treegrid('reload');
		break;
	}
}

function error(err){
	$.messager.progress('close');
	普通窗体('是','平台错误',err);
}
//数据处理方法
function data(url,dataobj,datatype,callback){
	$.ajax({
		url : url,
		data : dataobj,
		dataType : datatype,
		type: "post", 
		async:true,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		beforeSend: function(){
			$.messager.progress('close');
			$.messager.progress({title:'请等待', msg:'平台正在努力工作...'});
		},
		complete: function(){
			$.messager.progress('bar').progressbar({value:100,onChange: function(value){
					$.messager.progress('close');}});   	
		},
		success : function(r) {
			if(callback!=null && callback!='' &&callback!=undefined)callback(r);
			if(r){
				if(r.info){log(r.info);}
			};
		}
	});
};
//设置值
function set(k,v){
	if(k.indexOf('c_')==0) return $('.'+k).html(v);
	if(k=='i_u_userrole') return $('#'+k).html(roleHtml(v));
	if(k.indexOf('i_')==0) return $('#'+k).html(v);
	if(k=='leftMenu')  {top.g_menuData=v;}
	//if(k=='leftMenu')  {if(!top.b_isleft) {topmenu(null);topmenu(v);}else{top.g_menuData=v;} return leftMenu(v);}
	if(k=='themename') { return changeTheme(v);};
	if(k=='title'){return document.title=v;};
	$(k).html(v);
}

function so(obj){
	var info="";
	if(obj){
		for (var i in obj){
			info += "属性:"+i+" 值:"+obj[i]+"</br>";
			if($.type(obj[i])=='object'){
				info+='<span style="color:green">'+so(obj[i])+'</span>';
			}
		}
	}else{
		info="对象为空";
	}
	return info;
} 

function gridRowEdit(obj,type,index,id){
	switch(type){
	case eType.DataGrid:
		obj.datagrid('uncheckAll').datagrid('checkRow',index);break;
	case eType.TreeGrid:
		obj.treegrid('uncheckAll').treegrid('checkRow',index);break;
	}
	$('#'+id+'_toolbtnicon-edit').click();
}

//生成表格，dobj：表格对象
function gGrid2(dobj){
	var columns=inintDtColunms(dobj.columns);
	var id=dobj.id;
	var url=dobj.url;
	var dId = dobj.dId;
	var etype=dobj.etype;
	var pagination=dobj.pagination;
if(etype==undefined) etype=eType.DataGrid;
var obj = $('#'+id);
obj.attr('reftype',etype);
switch(etype){
case eType.DataGrid:
	dt=obj.datagrid({
		url : url,
		fit : true,
		fitColumns : true,
		width: $(this).width() * 0.067,
		border : false,
		nowrap:false,
		pagination : pagination==undefined?true:false,
		idField : dId,
		pageSize : 20,
		pageList : [ 20, 30, 40, 50,100,1000],
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect : true,
		columns : columns,
		toolbar: '#'+id+'_toolbar',
		dmodel:'',
		hearders:'',
		fields:'',
		striped:true,
		onLoadSuccess:function(data){
			if(data){
			if(dobj.sucCallFun)dobj.sucCallFun(data);
				if(data.modelName=='')return;
				obj.datagrid('options').dmodel=data.modelName;
			}
		},
		onDblClickRow:function(rowIndex, rowData){
			gridRowEdit(obj,eType.DataGrid,rowIndex,id);
		}
	});
	  
	if(top.moduleSearch.get('searchName')!=''){
		dt.datagrid('load', {
			searchName :top.moduleSearch.get('searchName'),
			searchKey : top.moduleSearch.get('searchKey')
		});
		top.moduleSearch.put('searchName','');
	}
	break;
case eType.TreeGrid:
	var dTreeId= dobj.dTreeId;
	var dExpandId=dobj.dExpandId;
	var dExpandUrl=dobj.dExpandUrl;
	dt=obj.treegrid({
		url : url,
		durl:url,
		fit : true,
		fitColumns : true,
	    singleSelect:false,  
        animate: true,
        nowrap:false,
        striped:true,
		idField : dId,
		treeField:dTreeId,
		columns : columns,
		toolbar: '#'+id+'_toolbar',
		dmodel:'',
		hearders:'',
		fields:'',
		lines:true,
		onLoadSuccess:function(row, data){
				if(data){if(data.modelName){obj.treegrid('options').dmodel=data.modelName;}}
		},
		onClickRow:function(row){
			//级联选择
			$(this).treegrid('cascadeCheck',{
				id:row.id, //节点ID
				deepCascade:true //深度级联
			});
		}
		,
		onDblClickRow:function(row){
			gridRowEdit(obj,eType.TreeGrid,row.id,id);
		},
		onBeforeExpand: function(row) {
             if (row) {
                 if (row[dExpandId] == undefined) return;
                 var ids = row[dExpandId].split('!');
                 if (ids.length > 1) {
                     var searchName = $('#' + id + '_searchbox').searchbox('getName');
                     var searchKey = $('#' + id + '_searchbox').searchbox('getValue');
                     $(this).treegrid('options').url = dExpandUrl + ids[1] + '&searchName=' + searchName + '&searchKey=' + searchKey;
                 }
             }
         }
	});
	break;
}
	initDSearchComb(id,columns);
	gHeardersAndFields(id,columns);
	return dt;
}


function inintDtColunms(columns){
	for(var i=0; i<columns[0].length; i++){
		if(columns[0][i]){
			if(!('form' in columns[0][i])){
				columns[0][i].form={};
				columns[0][i].form.add=columns[0][i].form.edit=columns[0][i].form.hidden=columns[0][i].form.dataoptions=columns[0][i].form.classname=columns[0][i].form.v=columns[0][i].form.setvalue=undefined;
				columns[0][i].form.type=eType.Input;
			}
			if(!('hidden' in columns[0][i])){
				columns[0][i].hidden=false;
			}
		}
	}
	return columns;
}

function gHeardersAndFields(id,columns){
	var obj = $('#'+id);
	var reftype=parseInt(obj.attr('reftype'))
	for(var i=0; i<columns[0].length; i++){
		var colobj = columns[0][i];
		if(colobj.form.hidden==undefined){
			switch(reftype){
				case eType.TreeGrid:
					obj.treegrid('options').hearders+=colobj.title+',';
					obj.treegrid('options').fields+=colobj.field+',';
					break;
				default:
					obj.datagrid('options').hearders+=colobj.title+',';
					obj.datagrid('options').fields+=colobj.field+',';
			}
		}
	}
}

var formfieldmap = new HashMap();//不允许id一样

//创建datagridtoobar的btn
function gDataGridToolbarBtn(pid,iconCls,callback,text){
	var id=pid+'_toolbtn'+iconCls;
	var html='<a href="javascript:void(0)" id="'+id+'" class="easyui-linkbutton" data-options="iconCls:\''+iconCls+'\',plain:true" onclick="'+callback+';">'+text+'</a>';
	var obj = $('#'+pid+'_toolbtn');
	obj.append(html);
	$.parser.parse(obj);
	return id;
}

function checkFormField(k){
	var obj=$(k);
	switch(parseInt(obj.attr('reftype'))){
		case eType.ValidateBox:
			return obj.validatebox('isValid');
		case eType.ComboTree:
			return obj.combotree('isValid');
		case eType.ComboBox:
			return obj.combobox('isValid');
		case eType.DateBox:
			return obj.datebox('isValid');
		case eType.NumberBox:
			return obj.numberbox('isValid');
	}
	return true;
}

Array.prototype.remove=function(dx){
if(isNaN(dx)||dx>this.length){return false;}
for(var i=0,n=0;i<this.length;i++){
	if(this[i]!=this[dx]){
		this[n++]=this[i];
	}
}
this.length-=1;
}

//对datagrid的行进行操作curds
function dorow(pid,msg,url,callback,model){
	var eobj=$('#'+pid);
	var eReftype=parseInt(eobj.attr('reftype'));
	var rows=[];var rows_=[];var ids = [];//选择的的行
	switch(eReftype){
		case eType.TreeGrid:
			rows_=eobj.treegrid('getSelections');
			break;
		default:
			rows_=eobj.datagrid('getChecked');
			break;
	}
	for ( var i = 0; i < rows_.length; i++) {
		if((rows_[i].id+'').substring(0,1)!='-'){
			ids.push(rows_[i].id);
			rows.push(rows_[i]);
		}
	}
	var callback_=function(d){
		if(callback!='')callback(d);
		eUpdate('#'+pid);
	};
	if(model=='e'){
		var hearders,fields;
		switch(eReftype){
			case eType.TreeGrid:
				hearders =eobj.treegrid('options').hearders;
				fields=eobj.treegrid('options').fields;
				break;
			default:
				hearders =eobj.datagrid('options').hearders;
				fields=eobj.datagrid('options').fields;
		}
		hearders=hearders.split(',');
		fields=fields.split(',');
		var hearders_=[];var fields_=[];
		for(var i=0;i<hearders.length;i++){
			if('undefined'!=hearders[i] && ''!=hearders[i]){
				hearders_.push(hearders[i]);
				fields_.push(fields[i]);
			}
		}
		var sn,sv= $('#'+pid+'_searchbox').searchbox('getValue');
		if(sv=='') sn='';
		else sn=$('#'+pid+'_searchbox').searchbox('getName');
		
		 var isExportAll="false"; 
		  $.messager.confirm('导出选项', '点击<span style="color:red">"确认"</span>:导出所有</br>点击<span style="color:red">"取消"</span>:导出当前页', function(r){
			  if (r){
				  isExportAll="true";
                }
			  data_={
				searchName : sn,
				searchKey : sv,
				page:eobj.datagrid('options').pageNumber,
				rows:eobj.datagrid('options').pageSize,
				hearders:hearders_.join(','),
				fields:fields_.join(','),
				url:url,
				isExportAll:isExportAll
		};
		if(!eobj.datagrid('options').pagination){data_.page=data_.rows=0;}
		var win=window.open(top.sysPath+'/sys/SyExcel.jsp',new Date().getTime());
		d_=data_;
		win.onload=function(){
			$('#excelExportAll',win.document).val(d_.isExportAll);
			$('#searchName',win.document).val(d_.searchName);
			$('#searchKey',win.document).val(d_.searchKey);
			$('#page',win.document).val(d_.page);
			$('#rows',win.document).val(d_.rows);
			$('#hearders',win.document).val(d_.hearders);
			$('#fields',win.document).val(d_.fields);
			$('#excel',win.document).attr('action',d_.url).submit();
			delete d_;
		};
            });
		return;
	}else if(model=='c'){
		$('#'+pid+'_add_btn a').off().click(function(){
			if(typeof(AddBtnClick)!='undefined'){if(!AddBtnClick()) return;}
			var columns,modelname;
			switch(eReftype){
				case eType.TreeGrid:
					columns =eobj.treegrid('options').columns;
					modelname=eobj.treegrid('options').dmodel;
					break;
				default:
					columns =eobj.datagrid('options').columns;
					modelname=eobj.datagrid('options').dmodel;
			}
			var setData={};
			for(var i=0; i<columns[0].length; i++){
				var field=columns[0][i].field;
				var title=columns[0][i].title;
				if(!checkFormField('#'+pid+'_add_form_'+field)){log('['+title+']不能为空，请填写!'); return;}
				setData[modelname+"."+field]=eGet('#'+pid+'_add_form_'+field);
			}
			data(url,setData,'json',callback_);
			$('#'+pid+'_add_dialog').dialog('close');
		});
		var columns=eobj.datagrid('options').columns;
		for(var i=0; i<columns[0].length; i++){
			var field=columns[0][i].field;
			eSet('#'+pid+'_add_form_'+field,'');
		}
		var addDig=$('#'+pid+'_add_dialog');
		addDig.dialog({width:$(self).width(), 
			height:($(self).height()*2)/3,
			top:($(self).height()*1)/3,
			modal:false});
		addDig.dialog('open');
		return addDig;
	} else if (rows.length>0) {
			if( (model=='s') || (model=='d')){
				$.messager.confirm('确认',msg, function(r) {
					if (r) {
						data(url,{ids : ids.join(',')},'json',callback_);
					}
				});
			}else if(model=='u'){
				if(ids.length!=1) return log("请选择一行!");
				var columns=eobj.datagrid('options').columns;
				for(var i=0; i<columns[0].length; i++){
					var field=columns[0][i].field;
					eSet('#'+pid+'_edit_form_'+field,rows[0][field]);
				}
				$('#'+pid+'_edit_dialog').dialog({width:$(self).width(), 
					height:($(self).height()*2)/3,
					top:($(self).height()*1)/3,
					modal:false});
				var editDlg=$('#'+pid+'_edit_dialog');
				$('#'+pid+'_edit_btn a').off().click(function(){
					if(typeof(EditBtnClick)!='undefined'){if(!EditBtnClick()) return;}
					var columns,modelname;
					switch(eReftype){
						case eType.TreeGrid:
							columns =eobj.treegrid('options').columns;
							modelname=eobj.treegrid('options').dmodel;
							break;
						default:
							columns =eobj.datagrid('options').columns;
							modelname=eobj.datagrid('options').dmodel;
					}
					var setData={};
					for(var i=0; i<columns[0].length; i++){
						var field=columns[0][i].field;
						if(!checkFormField('#'+pid+'_edit_form_'+field)) return;
						setData[modelname+"."+field]=eGet('#'+pid+'_edit_form_'+field);
					}
					data(url,setData,'json',callback_);
					editDlg.dialog('close');
				});
				editDlg.dialog({selrows:rows});
				editDlg.dialog('open');
			}
	}else {
		log("请选择要操作的数据!");
	}
	return ids;
}


function initDSearchComb(pid,columns) {
	var fields = columns;
	var muit = "";
	for ( var i = 1; i < fields[0].length;i++) {
		var field = fields[0][i];
		if(!('hidden' in field)) continue;
		if(field.hidden) continue;
		muit += "<div name='" + field.field + "'>" + field.title + "</div>";
	}
	$('#'+pid+'_dSComb')[0].innerHTML =$('#'+pid+'_dSComb').html() + muit;
	$('#'+pid+'_searchbox').searchbox({
		menu : '#'+pid+'_dSComb'
	});
}

function dSearch(value, name) {
	var eobj=$('#'+$(this).attr('pdt'));
	switch(parseInt(eobj.attr('reftype'))){
	case eType.TreeGrid:
		data(eobj.treegrid('options').durl,{searchName : name,searchKey : value},'json',
				function(d){
			eobj.treegrid('loadData', d);
		});
		break;
	default:
		eobj.datagrid('load', {
			searchName : name,
			searchKey : value
		});
}
}

//更换主题
function changeTheme(themeName) {
	$('#i_m_header').css('background-color',getThemeColor(themeName));
	if(top.themeName!=themeName){
		top.themeName=themeName;
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	}
};

function getThemeColor(themeName){
	switch(themeName){
	case 'default':
		return '#E0ECFF';
	case 'gray':
		return '#f3f3f3';
	case 'cupertino':
		return '#e4f1fb';
	case 'metro-blue':
		return '#daeef5';
	case 'metro-gray':
		return '#c7ccd1';
	case 'metro-green':
		return '#e5f0c9';
	case 'metro-orange':
		return '#f0e3bf';
	case 'metro-red':
		return '#f0e1e3';
	case 'sunny':
		return '#FEE273';
	case 'black':
		return '#000';
	default:
		return '#fff';
	}
}

function changeTheme_(path,themeName) {
	data(path+actionUrl('/sys/','syUser','Update_Theme'),{themename:themeName},'json','');
	changeTheme(themeName);
};

//通用方法
//去掉首尾空格
function trim(str){
	str=isUdef(str);
	return str.replace(/(^\s*)|(\s*$)/g,"");
}

function htot(str){
	str=isUdef(str);
	return str.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

function ttoh(str){
	str=isUdef(str);
	return str.replace(/&lt;/g, "<").replace(/&gt;/g, ">");
}

//action
function actionUrl(namespace,tablename,fun){
	return namespace+tablename+'_'+fun;
}

//url
function getUrl(modelname,fun){
	return top.sysPath+actionUrl('/sys/',modelname,fun);
}

function getCurrentUserName(){
	return $('#i_u_username',top.document).text();
}

function getCurrentUserId(){
	return $('#i_u_userid',top.document).text();
}


function getCurrentUserRole(){
	return $('#i_u_userrole',top.document).text();
}

function getYearComboBoxData(){
	var currentYear=2009;
	var yearComboBoxData='[';
	for(var i=0;i<100;i++){
		var c=currentYear+i;
		var yearcbd='{id:'+c+',text:'+c+'},';
		yearComboBoxData+=yearcbd;
	}
	yearComboBoxData=yearComboBoxData.substring(0, yearComboBoxData.length-1);
	return yearComboBoxData+']';
}

//全部替换
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

//文件组件html，id:表格id，modal:'add'/'edit'
function createFileDlg(id,modal){
	var eId=id+'-'+modal+'-';
	var filedlg='<div class="easyui-layout" data-options="fit:true">'+
	'<div data-options="region:\'north\'" style="height:25px;">'+
	'<form id="'+eId+'fileuploadform"  enctype="multipart/form-data" method="post" ><input type="text" name="fileid"  style="display:none;"/>上传文件：<input type="file" name="fileGroup"><span style="color:red">注：上传文件大小不能超过2M</span><br/>'+
	'</div>'+
	'<div data-options="region:\'center\'" style="height:30px;">'+
	'<input type="button" id="'+eId+'submitFileForm" value="上传"/></form>'+
	' </div>'+
	'<div  data-options="region:\'south\',border:false" style="height:20px;">'+
	'<div id="'+eId+'fileContent"></div>'+
	'</div></div>' ;
	return filedlg;
}

//文件组件script，id:表格id，modal:'add'/'edit'
function initFileDlg(id,modal){
	var eId=id+'-'+modal+'-';
	$("#"+eId+"submitFileForm").click(function(){
		$("#"+eId+"fileuploadform").form('submit', {   
		    url:top.sysPath+actionUrl('/sys/','syFile','Upload'),   
		    success:function(d){  
		    	 d=jQuery.parseJSON(d);
		        if(d.info){log(d.info);}
		        setFileData(id,modal,d);
		    }   
		});  

	});
}

//文件组件script
function setFileData(id,modal,d){
	var eId=id+'-'+modal+'-';
	 if(d.syFiles){
     	for(var i=0;i<d.syFiles.length;i++){
     		var file=d.syFiles[i];
     		var html='<a href="'+top.sysPath+'/fileupload/'+file.sysname+'" name="'+file.id+'" target="view_frame" >'+file.filename+'</a>,';
     		$("#"+eId+"fileContent")[0].innerHTML=$("#"+eId+"fileContent").html()+html;
     	}
     }
}

//设置表单值，obj来至ajax
function setForm(obj,pid){
	if(obj){
		for (var i in obj){
			eSet('#'+pid+'_edit_form_'+i,obj[i]);
		}
	}
}

//页面视图控制函数
function pageView(dtid,columns){
	var pView=$.getUrlParam('pview');
	if(pView){
		if($('a',$('#i_flowtoobar')).length>0){
			$.parser.parse($('#i_flowtoobar'));
			workFlowBtnClick($.getUrlParam('processInstanceId'),$.getUrlParam('processDefinitionKey'),$.getUrlParam('taskId'),'#'+dtid+'_'+pView+'_btn');
			$($('#'+dtid+'_'+pView+'_dialog').dialog('options').buttons).hide();
			$('#'+dtid+'_'+pView+'_dialog').dialog('options').buttons='#i_flowtoobar';  
		}
		eval('pageView_'+pView+'()');
	}else{
		pageView_list();
	}
}

//文档在线显示
function pdfViews(title,url){
	var s = top.sysPath+'/fileupload/swf/'+url+'.swf';
	//var swfname = "D:\\smarte开发-备份\\smarte平台\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\smarte\\fileupload\\"+destUrl.split(".")[0]+".swf";
	var pdfObj=$.window({
		title :title,
		url : top.sysPath+'/plug/flexPaper/index.html',
		isIframe : true,
		height : 400,
		width : 800,
		winId : 'pdfview'+new Date().getTime(),
		target : 'self',
		maximizable : false,
		onComplete: function() {
			var obj = pdfObj.find('iframe')[0].contentWindow;
			obj.showPdf(s);
		}
	});
}

//清空
function cleanSpan(id){
	$('#muti').val('');
	$('#'+id).html('');
	$('#'+id).val('');
}

//选人
function selUserDlg(title,id) {
		var selUser=$.window({
			title :title,
			url : top.sysPath+'/component/SyUser_frame.jsp?pview=list&topthemeName='+top.themeName,
			isIframe : true,
			height : 400,
			width : 800,
			winId : 'selUserdig'+new Date().getTime(),
			target : 'self',
			maximizable : false,
			buttons:[{  
                text:'确定',
                 handler : function() {                 	
                 	eSet('#'+id,selUser.find('iframe')[0].contentWindow.getSelUserInfos());	
                 	selUser.window('close');  
                 }  
        }]});
	}

function isUdef(v){
	if(v==undefined){return '';}return v;
}

function gChart(chart) {
	var href=top.sysPath + chart.url;
	$('#'+chart.id).panel({
		title: chart.title,
		content:'<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:95%;"></iframe>',
		left: chart.left||0,
		top: chart.top||0,
		width: chart.width||0,
		height: chart.height||0,
		maximizable: chart.maximizable||false,
		minimizable: chart.minimizable||false
	});
}



function gField(title,ero){
	if(!columns){logt("gField执行错误",1);}
	for(var i=0; i<columns[0].length; i++){
		var obj=columns[0][i];
		if(obj.title==title){
			return gId(obj.field,ero);
		}
	}
	return null;
}

function gFieldClassName(title){
	if(!columns){logt("gField执行错误",1);}
	for(var i=0; i<columns[0].length; i++){
		var obj=columns[0][i];
		if(obj.title==title){
			return obj.field;
		}
	}
	return null;
}

function 获取当前用户名(){
	return getCurrentUserName();
}

function 获取当前用户编号(){
	return getCurrentUserId();
}

function 获取当前用户角色(){
	return getCurrentUserRole();
}
function 禁用新建表单字段(字段名){
	eEnable(gField(字段名,'add'),false);
}

function 禁用新建表单字段们(字段名们){
	for(var i=0;i<arguments.length;i++){
		eEnable(gField(arguments[i],'add'),false);
	}
}

function 启用新建表单字段(字段名){
	eEnable(gField(字段名,'add'),true);
}

function 启用新建表单字段们(字段名们){
	for(var i=0;i<arguments.length;i++){
		eEnable(gField(arguments[i],'add'),true);
	}
}

function 禁用编辑表单字段(字段名){
	eEnable(gField(字段名,'edit'),false);
}

function 禁用编辑表单字段们(字段名们){
	for(var i=0;i<arguments.length;i++){
		eEnable(gField(arguments[i],'edit'),false);
	}
}

function 启用编辑表单字段(字段名){
	eEnable(gField(字段名,'edit'),true);
}

function 启用编辑表单字段们(字段名们){
	for(var i=0;i<arguments.length;i++){
		eEnable(gField(arguments[i],'edit'),true);
	}
}
function 获取新建表单字段值(字段名){
	return eGet(gField(字段名,'add'));
}

function 获取编辑表单字段值(字段名){
	return eGet(gField(字段名,'edit'));
}

function 设置新建表单字段值(字段名,值){
	eSet(gField(字段名,'add'),值);
}

function 设置编辑表单字段值(字段名,值){
	eSet(gField(字段名,'edit'),值);
}

function 新建表单字段联动(字段名,回调函数){
	eValueChange(gField(字段名,'add'),回调函数);
}

function 编辑表单字段联动(字段名,回调函数){
	eValueChange(gField(字段名,'edit'),回调函数);
}

function 字段联动(编号,回调函数){
	eValueChange(编号,回调函数);
}

function  弹窗(信息,时间){
	logt(信息,时间);
}

function 窗体(标题,内容){
	普通窗体('是',标题,内容)
}

function 普通窗体(是否全屏,标题,内容){
	var debugObj;
	var obj={
		title :标题,
		url : top.sysPath+'/component/debug.jsp?topthemeName='+top.themeName,
		isIframe : true,
		height : 400,
		width : 800,
		winId : 'debugdig'+new Date().getTime(),
		target : 'self',
		maximizable : true,
		onComplete: function() {
			var obj = debugObj.find('iframe')[0].contentWindow;
			obj.setDebugInfo(内容);
		}
    };
	if(是否全屏=='否'){
		debugObj=$.window(obj);
	}else{
		debugObj=top.$.window(obj);
	}
}

function 链接窗体(标题,链接){
	var chartObj=$.window({
		title :标题,
		url : top.sysPath+链接,
		isIframe : true,
		height : 400,
		width : 800,
		winId : 'chatdig'+new Date().getTime(),
		target : 'self',
		maximizable : true
    });
}

function 获取链接参数(参数名){
	return $.getUrlParam(参数名);
}

function 打开模块(模块名){
	top.openModule(模块名);
}

function 打开带条件模块(模块名,搜索英文字段名,搜索值){
	top.openModuleBySearch(模块名,搜索英文字段名,搜索值);
}

function 查看对象(对象){
	窗体('平台调试',so(对象));
}

function 字段查询(条件,值){
	return "<查询 条件='"+条件+"'/><查询 值='"+值+"'/>";
}

function 获取数据对象(已知对象,表名,数据回调函数){
	var className=top.doroodo_db[表名];
	if(!className) return log('['+表名+']不存在类!');
	var ndata=null;
	if(已知对象){
		ndata='{';
		for (var i in 已知对象){
			var fieldName=i;
			if(!fieldName) continue;
			var fieldNames=fieldName.split('_');
			fieldName='';
			for(var z=0;z<fieldNames.length;z++){
				fieldName_=fieldNames[z];
				if(z==0){fieldName+=fieldName_.substring(0,1).toLowerCase()+fieldName_.substring(1,fieldName_.length)}
				else{fieldName+=fieldName_.substring(0,1).toLocaleUpperCase()+fieldName_.substring(1,fieldName_.length)}
			}
			if($.type=='number'){ndata+='"'+d.className+'.'+fieldName+'":'+已知对象[i]+',';}else{
				ndata+='"'+className+'.'+fieldName+'":"'+已知对象[i]+'",';
			}
		}
		ndata=ndata.substring(0, ndata.length-1);
		ndata+="}";
	}
	data(getUrl(className,'Get_ByObj'),$.parseJSON(ndata),'json',数据回调函数);
}
