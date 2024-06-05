layui.define(['layer','dtree'],function(exports){
  //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
  var layer = layui.layer;
  var dtree = layui.dtree;
  var opts={
		"title":'选择员工',
		// "department_url": "/api/index/get_department_tree",
		// "employee_url": "/api/index/get_employee",
		"department_url": "../../json/get_department_tree.json",
		"employee_url": "../../json/get_employee.json",
		"type":1,
		"ids":[],
		"names":[],
		"dids":[],
		"departments":[],
		"fixedid":0,
        "callback": function(){}
	};
	obj = {
		render:function(){
			var me=this,letterTem='';
			for(var i=0;i<26;i++){
				letterTem+='<span class="layui-letter-span" data-code="'+String.fromCharCode(97+i)+'">'+String.fromCharCode(65+i)+'</span>';
			}
			var tpl='<div style="width:220px; height:388px; border-right:1px solid #eee; overflow-x: hidden; overflow-y: auto; float:left;">\
							<div id="employeetree" style="padding:10px 0;"></div>\
						</div>\
						<div style="width:568px; height:388px; user-select:none; overflow-x: hidden; overflow-y: auto; float:left;">\
							<div style="padding:15px 10px 0;"><div style="color:#999; text-align:center;">⇐ 点击左边部门筛选员工，或者点击下面字母筛选</div><div id="letterBtn" style="color:#999; text-align:center;">'+letterTem+'</div></div>\
							<div id="employee" style="padding:8px 15px"></div>\
							<div style="padding:10px 15px; border-top:1px solid #eee;;"><strong>已选择</strong><span class="layui-tags-all">全选</span></div>\
							<div id="selectTags" style="padding:10px 15px;">'+me.initSelect()+'</div>\
						</div>';
			return tpl;
		},
		initSelect:function(){
			var me=this,select_tags='';
			if(me.settings.type == 0){
				select_tags+='<span style="color:#1E9FFF">'+me.settings.names+'</span>';
			}
			else{
				for(var a=0;a<me.settings.ids.length;a++){
					if(me.settings.fixedid==me.settings.ids[a] && me.settings.fixedid!=0){
						select_tags+='<span class="layui-tags-span">'+me.settings.names[a]+'</span>';
					}
					else{
						select_tags+='<span class="layui-tags-span">'+me.settings.names[a]+'<i data-index="'+a+'" data-id="'+me.settings.ids[a]+'" class="layui-icon layui-tags-close">ဆ</i></span>';
					}
				}
			}
			return select_tags;
		},
		init: function (options) {
			this.settings = $.extend({}, opts, options);
			var me=this;
			layer.open({
				type:1,
				title:me.settings.title,
				area:['800px','500px'],
				content:me.render(),
				success:function(obj,idx){
						var dataList=[];
						$.ajax({
							url:me.settings.department_url,
							type:'get',
							success:function(res){					
								dtree.render({
									elem: "#employeetree",
									data: res.trees,
									type: "all",
									initLevel: "1",
									icon: "2"  //修改二级图标样式  
								});
								dtree.on("node('employeetree')" ,function(obj){
									$('#letterBtn').find('span').removeClass('on');
									$.ajax({
										url:me.settings.employee_url,
										type:'get',
										data:{did:obj.param.nodeId},
										success:function(res){
											dataList=res.data;
											if(dataList.length>2 && me.settings.type == 1){
												$('.layui-tags-all').show();
											}
											else{
												$('.layui-tags-all').hide();
											}
											if(dataList.length>0){
												var tagsItem='';
												for(var i=0; i<dataList.length; i++){
													tagsItem+='<span class="layui-tags-span" data-id="'+dataList[i].id+'" data-did="'+dataList[i].did+'" data-department="'+dataList[i].department+'">'+dataList[i].nickname+'</span>';
												}
												$('#employee').html(tagsItem);
											}else{
												$('#employee').html('<div style="color:#999; text-align:center;">暂无员工</div>');
											}
										}
									})
								});	
								$('#letterBtn').on("click" ,'span',function(){
									var code=$(this).data('code');
									$(this).addClass('on').siblings().removeClass('on');
									$.ajax({
										url:me.settings.employee_url,
										type:'get',
										data:{id:1},
										success:function(res){	
											dataList=res.data;
											var letterData=[];
											if(dataList.length>0){
												var tagsItem='';
												for(var i=0; i<dataList.length; i++){
													if(dataList[i].username.slice(0,1)==code){
														tagsItem+='<span class="layui-tags-span" data-id="'+dataList[i].id+'" data-did="'+dataList[i].did+'" data-department="'+dataList[i].department+'">'+dataList[i].nickname+'</span>';
														letterData.push(dataList[i]);
													}
												}
												dataList=letterData;
												if(dataList.length>2 && me.settings.type == 1){
													$('.layui-tags-all').show();
												}
												else{
													$('.layui-tags-all').hide();
												}
												if(tagsItem==''){
													tagsItem='<div style="color:#999; text-align:center;">暂无员工</div>';
												}
												$('#employee').html(tagsItem);
											}else{
												$('#employee').html('<div style="color:#999; text-align:center;">暂无员工</div>');
											}
										}
									})
								});
							}
						});
						
						if(me.settings.type == 1){
							$('.layui-tags-all').on('click',function(){
								var sIds = me.settings.ids.join(',');
								var sNames = me.settings.names.join(',');
								var sDids = me.settings.dids.join(',');
								var sDepartments = me.settings.departments.join(',');
								for(var a=0; a<dataList.length;a++){
									if(sIds.indexOf(dataList[a].id)<0){
										sIds+=','+dataList[a].id;
										sNames+=','+dataList[a].nickname;
										sDids+=','+dataList[a].did;
										sDepartments+=','+dataList[a].department;
									}
								}
								if(me.settings.ids.length==0){
									sIds=sIds.substr(1);
									sNames=sNames.substr(1);
									sDids=sDids.substr(1);
									sDepartments=sDepartments.substr(1);
								}
								me.settings.ids=sIds.split(',');
								me.settings.names=sNames.split(',');
								me.settings.dids=sDids.split(',');
								me.settings.departments=sDepartments.split(',');
								$('#selectTags').html(me.initSelect());	
							});
						}
						
						
						$('#employee').on('click','.layui-tags-span',function(){
							var item_id=$(this).data('id').toString();
							var item_name=$(this).text();
							var item_did=$(this).data('did').toString();
							var item_department=$(this).data('department').toString();
							if(me.settings.ids.indexOf(item_id)>=0){
								return;
							}
							if(me.settings.type == 0){
								layer.close(idx);
								me.settings.callback(item_id,item_name,item_did,item_department);
							}
							else{
								var sIds = me.settings.ids.join(',')+','+item_id;
								var sNames = me.settings.names.join(',')+','+item_name;
								var sDids = me.settings.dids.join(',')+','+item_did;
								var sDepartments = me.settings.departments.join(',')+','+item_department;
								if(me.settings.ids.length==0){
									sIds=item_id;
									sNames=item_name;
									sDids=item_did;
									sDepartments=item_department;
								}
								me.settings.ids=sIds.split(',');
								me.settings.names=sNames.split(',');
								me.settings.dids=sDids.split(',');
								me.settings.departments=sDepartments.split(',');
								$('#selectTags').html(me.initSelect());	
							}					
						});
						
						$('#selectTags').on('click','.layui-tags-close',function(){
							var index=$(this).data('index');
							var newIds=[],newNames=[],newDids=[],newDepartments=[];
							for(var i=0;i<me.settings.ids.length;i++){
								if(i!=index){
									newIds.push(me.settings.ids[i]);
									newNames.push(me.settings.names[i]);
									newDids.push(me.settings.dids[i]);
									newDepartments.push(me.settings.departments[i]);
								}
							}
							me.settings.ids=newIds;
							me.settings.names=newNames;
							me.settings.dids=newDids;
							me.settings.departments=newDepartments;
							$('#selectTags').html(me.initSelect());
						});
						if(me.settings.type == 0){
							$('#layui-layer'+idx).find('.layui-layer-btn0').hide();
						}						
					},
					btn: ['确定添加', '清空已选'],
					btnAlign:'c',
					btn1: function(idx,obj){
						me.settings.callback(me.settings.ids,me.settings.names,me.settings.dids,me.settings.departments);
						layer.close(idx);
					},
					btn2: function(idx,obj){
						me.settings.callback([],[],[],[]);
						layer.close(idx);
					}
			})	
		}		
	}
	layui.link(rootPath+'module/dtree/dtree.css');
	layui.link(rootPath+'module/dtree/font/dtreefont.css');
	//选择员工单人弹窗	
	$('body').on('click','.picker-one',function () {
		let that = $(this);
		let ids=that.next().val()+'',names = that.val()+'';
		obj.init({
			ids:ids,
			names:names,
			type:0,
			callback:function(ids,names,dids,departments){
				that.val(names);
				that.next().val(ids);
			}
		});
	});
	
	//选择员工多人人弹窗		
	$('body').on('click','.picker-more',function () {
		let that = $(this);
		let ids=that.next().val()+'',names = that.val()+'',ids_array=[],names_array=[];
		if(ids.length>0){
			ids_array=ids.split(',');
			names_array=names.split(',');
		}
		obj.init({
			ids:ids_array,
			names:names_array,
			type:1,
			callback:function(ids,names,dids,departments){
				that.val(names.join(','));
				that.next().val(ids.join(','));
			}
		});
	});
	//输出接口
	exports('employeepicker', obj);
});   