layui.define(['tool'], function (exports) {
	const layer = layui.layer, tool = layui.tool,form=layui.form, table=layui.table, upload = layui.upload;
	// 查找指定的元素在数组中的位置
	Array.prototype.indexOf = function (val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) {
				return i;
			}
		}
		return -1;
	};
	// 通过索引删除数组元素
	Array.prototype.remove = function (val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	
	//是否是对象
	function isObject(val) {
		return typeof val === 'object' && val !== null
	}
	
	//格式化文件大小
	function renderSize(value){
		if(null==value||value==''){
			 return "0 Bytes";
		}
		var unitArr = new Array("Bytes","KB","MB","GB","TB","PB","EB","ZB","YB");
		var index=0;
		var srcsize = parseFloat(value);
		index=Math.floor(Math.log(srcsize)/Math.log(1024));
		var size =srcsize/Math.pow(1024,index);
		size=size.toFixed(2);//保留的小数位数
		return size+unitArr[index];
	} 
	
	const obj = {
		addFile: function (options) {
			let that = this;
			let settings = {
				type:0,
				btn: 'uploadBtn',
				box: 'fileBox',
				url: "/api/index/upload",
				accept: 'file', //普通文件
				exts: 'png|jpg|gif|jpeg|doc|docx|ppt|pptx|xls|xlsx|pdf|zip|rar|7z|txt|wps|avi|wmv|mpg|mov|rm|swf|flv|mp4|dwg|dxf|dwt|xmind', //只允许上传文件格式
				colmd:4,
				isSave:false,
				uidDelete:false,
				ajaxSave:null,
				ajaxDelete:null
			};
			let opts = $.extend({}, settings, options);
			let box = $('#'+opts.box);
			let boxInput = box.find('[data-type="file"]');
			//删除
			box.on('click', '.btn-delete', function () {
				let file_id = $(this).data('id');
				let id = $(this).data('id');
				let uid = $(this).data('uid');
				if (uid != login_admin && opts.uidDelete==true) {
					layer.msg('你不是该文件的上传人，无权限删除');
					return false;
				}
				let idsStr = boxInput.val(),idsArray = [];
				if (typeof idsStr !== 'undefined' && idsStr != '') {
					idsArray = idsStr.split(",");
					idsArray.remove(file_id);
				}
				layer.confirm('确定删除该附件吗？', {
					icon: 3,
					title: '提示'
				}, function(index) {
					if (typeof (opts.ajaxDelete) === "function") {
						//ajax删除
						if(opts.type==1){
							opts.ajaxDelete(file_id);
						}
						else{
							opts.ajaxDelete(idsArray.join(','));
						}						
					}
					else{
						//虚拟删除
						boxInput.val(idsArray.join(','));
						$('#fileItem' + file_id).remove();
					}
					layer.close(index);
				});
			})
			
			//多附件上传
			upload.render({
				elem: '#'+opts.btn,
				url: opts.url,
				accept: opts.accept,
				exts: opts.exts,
				multiple: true,
				before: function(obj){
					layer.msg('上传中...', {icon: 16, time: 0});
				},
				done: function(res){
					if (res.code == 0) {
						//上传成功
						if(opts.type==0){
							let idsStr = boxInput.val(),idsArray = [];
							if (typeof idsStr !== 'undefined' && idsStr != '') {
								idsArray = idsStr.split(",");
							}
							idsArray.push(res.data.id);
							let filesize = renderSize(res.data.filesize);							
							let type_icon = 'icon-sucaiziyuan';
							let view_btn = '<a class="blue" href="'+res.data.filepath+'" download="'+res.data.name+'" target="_blank" title="下载查看"><i class="iconfont icon-tuiguangshezhi"></i></a>';
							if(res.data.fileext == 'pdf'){
								type_icon = 'icon-lunwenguanli';
								view_btn = '<span class="file-view-pdf blue" data-href="'+res.data.filepath+'" title="在线查看"><i class="iconfont icon-yuejuan"></i></span>';
							}
							if(res.data.fileext == 'jpg' || res.data.fileext == 'jpeg' || res.data.fileext == 'png' || res.data.fileext == 'gif'){
								type_icon = 'icon-sucaiguanli';
								view_btn = '<span class="file-view-img blue" data-href="'+res.data.filepath+'" title="在线查看"><i class="iconfont icon-tupianguanli"></i></span>';
							}
							let temp = `<div class="layui-col-md${opts.colmd}" id="fileItem${res.data.id}">
									<div class="file-card">
										<i class="file-icon iconfont ${type_icon}"></i>
										<div class="file-info">
											<div class="file-title">${res.data.name}</div>
											<div class="file-ops">${filesize}，一分钟前</div>
										</div>
										<div class="file-tool">${view_btn}<span class="btn-delete red" data-id="${res.data.id}" title="删除"><i class="iconfont icon-shanchu"></i></span></div>
									</div>
								</div>`;
							boxInput.val(idsArray.join(','));	
							box.append(temp);
							
							if (typeof (opts.ajaxSave) === "function" && opts.isSave ==true) {
								opts.ajaxSave(idsArray.join(','));
							}
							else{
								layer.msg(res.msg);
							}
						}
						if(opts.type==1){
							if (typeof (opts.ajaxSave) === "function" && opts.isSave ==true) {
								opts.ajaxSave(res);
							}
						}
					}else{
						layer.msg(res.msg);
					}
				}
			});
		},
		//选择部门	
		departmentPicker:function(type,callback){				
			let select_type = type==1?'radio':'checkbox',departmentTable;
			layer.open({
				type:1,
				title:'选择部门',
				area:['500px','536px'],
				content:`<div style="width:468px; height:420px; padding:12px;">
						<div id="departmentBox"></div>
					</div>`,
				success:function(){
					departmentTable=table.render({
						elem: '#departmentBox'
						,url: "/api/index/get_department"
						,page: false //开启分页
						,cols: [[
						   {type:select_type,title: '选择'}
						  ,{field:'id', width:80, title: '编号', align:'center'}
						  ,{field:'title',title: '部门名称'}
						]]
					});
				},
				btn: ['确定'],
				btnAlign:'c',
				yes: function(){
					var checkStatus = table.checkStatus(departmentTable.config.id);
					var data = checkStatus.data;
					if(data.length>0){
						callback(data);
						layer.closeAll();
					}else{
						layer.msg('请选择部门');
						return;
					}
				}
			})	
		},
		//选择岗位	
		positionPicker:function(type,callback){
			let select_type = type==1?'radio':'checkbox',positionTable;
			layer.open({
				title:'选择岗位',
				type:1,
				area:['390px','436px'],
				content:'<div style="padding:12px"><div id="positionBox"></div></div>',
				success:function(){
					positionTable=table.render({
						elem: '#positionBox'
						,url: "/api/index/get_position"
						,page: false //开启分页
						,cols: [[
						   {type:select_type,title: '选择'}
						  ,{field:'id', width:80, title: '编号', align:'center'}
						  ,{field:'name',title: '岗位名称'}
						]]
					});			
				},
				btn: ['确定'],
				btnAlign:'c',
				yes: function(){
					var checkStatus = table.checkStatus(positionTable.config.id);
					var data = checkStatus.data;
					if(data.length>0){
						callback(data);
						layer.closeAll();
					}else{
						layer.msg('请选择岗位');
						return;
					}
				}
			})		
		},
		//选择服务类型
		servicePicker:function(type,callback){	
		let select_type = type==1?'radio':'checkbox',departmentTable;		
		var serviceTable;
			layer.open({
				title: '选择服务类型',
				area: ['500px', '536px'],
				type: 1,
				content: '<div class="picker-table"><div id="serviceTable"></div></div>',
				success: function () {
					serviceTable = table.render({
						elem: '#serviceTable'
						, url: '/api/index/get_services'
						,page: false
						, cols: [[
							{ type: select_type, title: '选择' }
							, { field: 'id', width: 100, title: '编号', align: 'center' }
							, { field: 'title', title: '服务名称' }
						]]
					});
				},
				btn: ['确定'],
				btnAlign: 'c',
				yes: function(){
					var checkStatus = table.checkStatus(serviceTable.config.id);
					var data = checkStatus.data;
					if(data.length>0){
						callback(data);
						layer.closeAll();
					}else{
						layer.msg('请选择服务类型');
						return;
					}
				}
			})
		},
		//选择客户
		customerPicker:function(callback){
			var customeTable;
			layer.open({
				title: '选择客户',
				area: ['600px', '580px'],
				type: 1,
				content: '<div class="picker-table">\
					<form class="layui-form pb-2">\
						<div class="layui-input-inline" style="width:382px;">\
						<input type="text" name="keywords" placeholder="客户名称" class="layui-input" autocomplete="off" />\
						</div>\
						<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="search_customer">提交搜索</button>\
						<span class="layui-btn customer-new">新增客户</span>\
				  	</form>\
					<div id="customerTable"></div></div>',
				success: function (layero, idx, that) {
					customeTable = table.render({
						elem: '#customerTable'
						, url: '/contract/api/get_customer'
						, page: true //开启分页
						, limit: 10
						, cols: [[
							{ type: 'radio', title: '选择' }
							, { field: 'id', width: 100, title: '编号', align: 'center' }
							, { field: 'name', title: '客户名称' }
						]]
					});
					//客户搜索提交
					form.on('submit(search_customer)', function (data) {
						customeTable.reload({ where: { keywords: data.field.keywords }, page: { curr: 1 } });
						return false;
					});
					$('.picker-table').on('click','.customer-new',function(){
						layer.close(idx);
						tool.side('/customer/index/add');
					})
				},
				btn: ['确定'],
				btnAlign: 'c',
				yes: function () {
					var checkStatus = table.checkStatus(customeTable.config.id);
					var data = checkStatus.data;
					if (data.length > 0) {
						callback(data[0]);
						layer.closeAll();
					}
					else {
						layer.msg('请先选择客户');
						return false;
					}
				}
			})
		},
		//选择合同
		contractPicker:function(callback){
			var contractTable;
			layer.open({
				title: '选择合同',
				area: ['720px', '580px'],
				type: 1,
				content: '<div class="picker-table">\
					<form class="layui-form pb-2">\
						<div class="layui-input-inline" style="width:600px;">\
						<input type="text" name="keywords" placeholder="合同名称" class="layui-input" autocomplete="off" />\
						</div>\
						<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="search_contract">提交搜索</button>\
					</form>\
					<div id="contractTable"></div></div>',
				success: function () {
					contractTable = table.render({
						elem: '#contractTable'
						, url: '/contract/api/get_contract'
						, page: true //开启分页
						, limit: 10
						, cols: [[
							{ type: 'radio', title: '选择' }
							, { field: 'code', width: 168, title: '合同编号', align: 'center' }
							, { field: 'name', title: '合同名称' }
							, { field: 'customer_name', title: '客户名称',width: 240}
						]]
					});
					//合同搜索提交
					form.on('submit(search_contract)', function (data) {
						contractTable.reload({ where: { keywords: data.field.keywords }, page: { curr: 1 } });
						return false;
					});
				},
				btn: ['确定'],
				btnAlign: 'c',
				yes: function () {
					var checkStatus = table.checkStatus(contractTable.config.id);
					var data = checkStatus.data;
					if (data.length > 0) {
						callback(data[0]);
						layer.closeAll();
					}
					else {
						layer.msg('请先选择合同');
						return false;
					}
				}
			})
		},
		//选择项目
		projectPicker:function(callback){
			var projectTable;
			let projectLayer = layer.open({
				title: '选择项目',
				area: ['600px', '580px'],
				type: 1,
				content: '<div class="picker-table">\
					<form class="layui-form pb-2">\
						<div class="layui-input-inline" style="width:480px;">\
						<input type="text" name="keywords" placeholder="项目名称" class="layui-input" autocomplete="off" />\
						</div>\
						<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="search_project">提交搜索</button>\
					</form>\
					<div id="projectTable"></div></div>',
				success: function () {
					projectTable = table.render({
						elem: '#projectTable'
						, url: '/project/api/get_project'
						, page: true //开启分页
						, limit: 10
						, cols: [[
							{ type: 'radio', title: '选择' }
							, { field: 'id', width: 100, title: '编号', align: 'center' }
							, { field: 'title', title: '项目名称' }
						]]
					});
					//合同搜索提交
					form.on('submit(search_project)', function (data) {
						projectTable.reload({ where: { keywords: data.field.keywords }, page: { curr: 1 } });
						return false;
					});
				},
				btn: ['确定选择','清除数据'],
				btnAlign: 'c',
				btn1: function () {
					var checkStatus = table.checkStatus(projectTable.config.id);
					var data = checkStatus.data;
					if (data.length > 0) {
						callback(data[0]);
						layer.close(projectLayer);
					}
					else {
						layer.msg('请先选择项目');
						return false;
					}
				},
				btn2: function () {
					callback({'id':0,'title':''});
					layer.closeAll();
				}
			})
		},
		//选择任务
		taskPicker:function(callback,where){
			let map = isObject(where)?where:{};
			let taskTable;
			let taskLayer = layer.open({
				title: '选择任务',
				area: ['666px', '580px'],
				type: 1,
				content: '<div class="picker-table">\
					<form class="layui-form pb-2">\
						<div class="layui-input-inline" style="width:480px;">\
						<input type="text" name="keywords" placeholder="任务主题" class="layui-input" autocomplete="off" />\
						</div>\
						<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="task_project">提交搜索</button>\
					</form>\
					<div id="taskTable"></div></div>',
				success: function () {
					taskTable = table.render({
						elem: '#taskTable'
						, url: '/project/api/get_task'
						, page: true //开启分页
						, limit: 10
						, where:map
						, cols: [[
							{ type: 'radio', title: '选择' }
							, { field: 'id', width: 90, title: '编号', align: 'center' }
							, { field: 'title', title: '任务主题' }
							, { field: 'project_name', width: 200, title: '关联项目' }
						]]
					});
					//任务搜索提交
					form.on('submit(search_project)', function (data) {
						let maps = $.extend({}, map, data.field);
						taskTable.reload({ where: maps, page: { curr: 1 } });
						return false;
					});
				},
				btn: ['确定选择','清除数据'],
				btnAlign: 'c',
				btn1: function () {
					var checkStatus = table.checkStatus(taskTable.config.id);
					var data = checkStatus.data;
					if (data.length > 0) {
						callback(data[0]);
						layer.close(taskLayer);
					}
					else {
						layer.msg('请先选择任务');
						return false;
					}
				},
				btn2: function () {
					callback({'id':0,'title':''});
					layer.closeAll();
				}
			})
		}
	};
	
	//选择部门	
	$('body').on('click','.picker-depament',function () {
		let that = $(this);
		let callback = function(data){
			that.val(data[0].title);
			that.next().val(data[0].id);
		}
		obj.departmentPicker(1,callback);
	});
	$('body').on('click','.picker-depaments',function () {
		let that = $(this),ids = [],names=[];
		let callback = function(data){
			for ( var i = 0; i <data.length; i++){
				ids.push(data[i].id);
				names.push(data[i].title);
			}
			that.val(names.join(','));
			that.next().val(ids.join(','));
		}
		obj.departmentPicker(2,callback);
	});
	
	//选择岗位	
	$('body').on('click','.picker-position',function () {
		let that = $(this);
		let callback = function(data){
			that.val(data[0].name);
			that.next().val(data[0].id);
		}
		obj.positionPicker(1,callback);
	});
	$('body').on('click','.picker-positions',function () {
		let that = $(this),ids = [],names=[];
		let callback = function(data){
			for ( var i = 0; i <data.length; i++){
				ids.push(data[i].id);
				names.push(data[i].name);
			}
			that.val(names.join(','));
			that.next().val(ids.join(','));
		}
		obj.positionPicker(2,callback);
	});	
	
	//选择服务
	$('body').on('click','.picker-service',function () {
		let that = $(this);
		let callback = function(data){
			that.val(data[0].title);
			that.next().val(data[0].id);
		}
		obj.servicePicker(1,callback);
	});
	$('body').on('click','.picker-services',function () {
		let that = $(this),ids = [],names=[];
		let callback = function(data){
			for ( var i = 0; i <data.length; i++){
				ids.push(data[i].id);
				names.push(data[i].title);
			}
			that.val(names.join(','));
			that.next().val(ids.join(','));
		}
		obj.servicePicker(2,callback);
	});

	//选择客户	
	$('body').on('click','.picker-customer',function () {
		let that = $(this);
		let callback = function(data){
			that.val(data.name);
			that.next().val(data.id);
		}
		obj.customerPicker(callback);
	});	
	
	//选择合同	
	$('body').on('click','.picker-contract',function () {
		let that = $(this);
		let callback = function(data){
			that.val(data.name);
			that.next().val(data.id);
		}
		obj.contractPicker(callback);
	});	
	
	//选择项目
	$('body').on('click','.picker-project',function () {
		let that = $(this);
		let callback = function(data){
			that.val(data.title);
			that.next().val(data.id);
		}
		obj.projectPicker(callback);
	});
	
	//选择任务
	$('body').on('click','.picker-task',function () {
		let that = $(this);
		let projectid = that.data('projectid'),taskid = that.data('taskid');
		let project_id = projectid?projectid:0;
		let task_id = taskid?taskid:0;
		let callback = function(data){
			that.val(data.title);
			that.next().val(data.id);
		}
		obj.taskPicker(callback,{project_id:project_id,task_id:task_id});
	});	
	
	exports('oaTool', obj);
});  