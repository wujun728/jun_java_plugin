var base_url = "http://localhost:7500/zeus";
jQuery.extend( {	
	/**
	*@Author 师晓波
	*2017/9/21
	*将form表单数据转化为json-data
	*@form : form id
	*@jsondata : 需要拓展的json数据
	*@symbol : 多选分隔符
	*@blacklist:不需要的element
	*/
	form2json : function(form, jsondata, symbol, blacklist) {
		var formString = "";
		var dataString = "";
		if (isEmpty(symbol)) {
			symbol = "";
		}
		if (isNotEmpty(form)) {
			var formEle = new Map();
			var elements = jQuery("#" + form).formToArray(true);
			for (var i = 0; i < elements.length; i++) {
				if (elements[i].type == "checkbox" && elements[i].checked != true) {
					continue;
				}
				if (formEle.containsKey(elements[i].name)) {
					formEle.get(elements[i].name).push(elements[i].value);
				} else if (elements[i].name != blacklist) {
					var dataArray = new Array();
					dataArray[0] = elements[i].value;
					formEle.put(elements[i].name, dataArray);
				}
			}
			for (var i = 0; i < formEle.array.length; i++) {
				var ele = formEle.array[i];
				var eleName = ele.key;
				var eleValue = "";
				for (var j = 0; j < ele.value.length; j++) {
					eleValue += symbol + ele.value[j].replaceAll('#','%23').replaceAll('&',"%26") + symbol + ",";
				}
				if (eleValue.length > 0) {
					eleValue = eleValue.substring(0, eleValue.length - 1);
				}
				formString += '"' + eleName + '":"' + eleValue + '",';
			}

			if (formString.length > 0) {
				formString = formString.substring(0, formString.length - 1);
			}
		}

		if (isNotEmpty(jsondata)) {
			dataString = JSON.stringify(jsondata);
			dataString = trim(dataString.substring(1, dataString.length - 1));
		}

		var obj = {};
		if (formString.length > 0 && dataString.length > 0) {
			obj.data = "{" + formString + "," + dataString + "}";
		} else if (formString.length > 0) {
			obj.data = "{" + formString + "}";
		} else {
			obj.data = "{" + dataString + "}";
		}
		
		return obj;
	},


	/**
	*@Author 师晓波
	*2017/9/21
	*将jsondata转化为form表单展示
	*@from form表单id
	*@jsondata  传入json
	*/
	json2form : function(form,data){
		jQuery("#" + form).find("input,select,textarea").each(function() {
			var obj = jQuery(this);
			switch (this.tagName.toLowerCase()) {
			case "input":
				if(!obj.attr("type")) {obj.attr("type","text");}
				if(obj.attr("type")==null) {obj.attr("type","text");};
				switch (obj.attr("type").toLowerCase()) {
				case "radio":
					for (var key in data) {
						if (obj.attr("name") == key) {
							if (obj.val() == data[key]) {
								obj.attr("checked", true);
							} else {
								obj.attr("checked", false);
							}
							break;
						}
					}
					break;
				case "checkbox":
					for (var key in data) {
						if (obj.attr("name") == key) {
							var arr = data[key].split(',');
							for (var i = 0; i < arr.length; i++) {
								if (obj.val() == arr[i]) {
									obj.attr("checked", true);
									break;
								}
								obj.attr("checked", false);
							}
						}
					}
					break;
				case "text":
					
				case "hidden":
					for (var key in data) {
						if (obj.attr("id") == key) {
							if (obj.attr("class") && (obj.attr("class").indexOf('easyui-combobox') > -1) && obj.attr("comboname")) {
			
							} 
							else {
								obj.val(data[key]);
								break;
							}
						}
					}
					break;
				}
			    break;
			case "select":
				for (var key in data) {
					if (obj.attr("id") == key) {
						var arr = data[key].split(',');
						obj.children().each(function() {
							for (var i = 0; i < arr.length; i++) {
								if (jQuery(this).val() == arr[i]) {
									this.selected = true;
									break;
								}
							}
						});
					}
				}
				break;
			case "textarea":
				for (var key in data) {
					if (obj.attr("id") == key) {
						obj.text(data[key]);
						break;
					}
				}
				break;
			}
		});		
	},

	/**
	 * @Author 师晓波
	 * 
	 * AJAX请求
	 * 
	 * @url：表单提交的url；
	 *
	 * @header： header
	 *
	 * @reqdata：表单提交的数据；json格式，第一层为json字符串，例如：{data : '{key1 : value1, key2 : value2, key3 : value3}'}；
	 *            
	 * @sucesscallback：成功的回调函数；
	 *            
	 * @completecallback：完成的回调函数；
	 *            
	 * @dataType：从服务器端返回的数据类型；
	 *            
	 * @timeout：超时时间；毫秒；
	 *            
	 * @async：是否同步；缺省为异步；
	 *            
	 * @errorcallback：失败的回调函数；
	 *            
	 * @requestType：request请求方式；缺省为：POST；
	 */
	tajax : function(url, header,reqdata, requestType, successcallback, completecallback, dataType, timeout, async,errorcallback) {
		jQuery.ajax( {
			headers: header,
			processData: false,
			url : base_url + url,
			data : reqdata,
			dataType : dataType || 'json',
			contentType: 'application/json',
			timeout : timeout,
			async : isEmpty(async) ? true : async,
			type : requestType || 'POST',
			success : successcallback,
			error : errorcallback
					|| function(XMLHttpRequest, textStatus, errorThrown) {
						alert("调用ajax报错");
					},
			complete : completecallback
		});
	},
	/**
	 * @author 师晓波
	 * 异步文件上传
	 * @url    请求后台url
	 * @fileId file控件id
	 *
	 * @reqdata：表单提交的数据；json格式，第一层为json字符串，例如：{data : '{key1 : value1, key2 : value2, key3 : value3}'}；
	 *            
	 * @sucesscallback：成功的回调函数；
	 *            
	 * @completecallback：完成的回调函数；
	 *            
	 * @dataType：从服务器端返回的数据类型；
	 *            
	 * @timeout：超时时间；毫秒；
	 *            
	 * @async：是否同步；缺省为异步；
	 *            
	 * @errorcallback：失败的回调函数；
	 *            
	 * @requestType：request请求方式；缺省为：POST；
	 */
	fileupload : function(url,fileId,reqdata,successcallback, completecallback, dataType, timeout,  async,errorcallback) {
		var formData = new FormData();
	    formData.append('file',$("#" + fileId)[0].files[0]);    //将文件转成二进制形式
		for ( var key in reqdata) {		
			formData.append(key,reqdata[key]);
		}
		jQuery.ajax( {
			processData: false,
			url : base_url + url,
			data : formData,
			dataType : dataType || 'json',
			contentType: false,
			processData: false,
			cache:false,
			timeout : timeout,
			async : false,
			type : 'POST',
			success : successcallback,
			error : errorcallback
					|| function(XMLHttpRequest, textStatus, errorThrown) {
						alert("调用ajax报错");
					},
			complete : completecallback
		});
	},
	/**
	 * @author 师晓波
	*获取header
	*/
	reqHeader : function(){
		var header = {};
		var token = $.cookie("zeus_token");
		if(typeof token != "undefined" && token != null) {
			header["Authorization"] = "Bearer " + token;
		}
		return header;
	},

	/**
	 * @author 师晓波
	*@gridID         grid 容器id
	*@toolbarId      工具类id
	*@url			 请求url
	*@columns        列属性
	*@condition    请求参数
	*@header       请求headers
	*@pageSize     初始每列显示条数
	*@noPage       是否分页
	*/    
	tgrid : function(gridID, toolbarId,url, columns, condition,header ,pageSize, noPage,onClickRow) {
		//传递的参数
		function queryParams(params) {
			var param={};
			param["pageNum"]=(params.offset / params.limit) + 1;
			param["pageSize"]=params.limit;
			param["order"]=params.order;
			param["orderBy"]=params.sort;
			if( typeof(condition) != "undefined"&&condition !=null ){
				for ( var key in condition) {			
					param[key]=condition[key];
				}
			}
			return param;
		}
		$('#' + gridID).bootstrapTable({
            url: base_url + url, // 请求后台的URL（*）
            dataType : "json",
            method: 'post', // 请求方式（*）
            toolbar: '#'+toolbarId, // 工具按钮用哪个容器
            striped: true, // 是否显示行间隔色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: isEmpty(noPage) ? true : noPage, // 是否显示分页（*）
            sortable: false, // 是否启用排序
            sortOrder: "asc", // 排序方式
            queryParamsType : "limit",     // restful
            queryParams: queryParams, // 传递参数（*）
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: pageSize, // 每页的记录行数（*）
            pageList: [10, 25, 50, 100], // 可供选择的每页的行数（*）
            //			height : 456,
            uniqueId: "ID", // 每一行的唯一标识，一般为主键列
            search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,
            showColumns: false, // 是否显示所有的列
            showRefresh: false, // 是否显示刷新按钮
            minimumCountColumns: 2, // 最少允许的列数
            clickToSelect: false, // 设置true 将在点击行时，自动选择rediobox 和 checkbox
            showToggle: false, // 是否显示详细视图和列表视图的切换按钮
            cardView: false, // 是否显示详细视图
            detailView: false, // 是否显示父子表、
            paginationLoop: true,
            maintainSelected: true,
            responseHandler: function(res) { // res 为后台return的值
                return res;
            },
            onCheck: function(row) {
                return false;
            },
            onUncheck: function(row) {
                return false;
            },
            onCheckAll: function(rows) {
                return false;
            },
            onUncheckAll: function(rows) {
                return false;
            },
            onClickRow : onClickRow,
            columns: columns,
			ajaxOptions:{
				headers: header
			}
        });		
	},
	/**
	 * @author 师晓波
	 * 自动生成checkbox
	 * @container 容器id
	 * @data      数据
	 * @cid       checkbox id or name
	 */
	tcheckbox : function(container,data,cid){
		var str = "";
		for (var i = 0; i < data.length; i++){
			str = str + "<label class='checkbox-inline i-checks'> <input type='checkbox' id='"+cid+"' name='"+cid+"' value='" + data[i].zxUuid + "'>" + data[i].zxName + "</label>"
		}
		$('#' + container).empty(); 
		$('#' + container).append(str);

		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			})
		});
	},
	/**
	 * @author 师晓波
	 * 自动生成select
	 * @container 容器id
	 * @data   数据
	 */
	tselect : function(container,data){
		var str = "";
		for (var i = 0; i < data.length; i++){
			str = str + "<option value="+data[i].dcValue+">"+data[i].dcName+"</option>"
		}
		$('#' + container).empty(); 
		$('#' + container).append(str);
	},
	
	tselect_search : function(container,data){
		var str = "<option value=''>全部</option>";
		for (var i = 0; i < data.length; i++){
			str = str + "<option value="+data[i].dcValue+">"+data[i].dcName+"</option>"
		}
		$('#' + container).empty(); 
		$('#' + container).append(str);
	}	


});