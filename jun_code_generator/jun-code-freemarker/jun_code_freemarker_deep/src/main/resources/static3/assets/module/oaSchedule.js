layui.define(['tool'], function (exports) {
	const layer = layui.layer;
	const form = layui.form;
	const tool = layui.tool;
	const laydate = layui.laydate;
	const obj = {
		loading:false,
		load: function (tid) {
			let callback = function (res) {
				if (res.code == 0 && res.data.length > 0) {
					let itemSchedule = '';
					$.each(res.data, function (index, item) {
						itemSchedule += `
							<div class="pt-2">
								<p class="gray">${item.start_time}~${item.end_time}，${item.labor_time}工时</p>
								<p><span class="blue">${item.name}：</span>${item.title}</p>
							</div>
						`;
					});
					$("#schedule_" + tid).html(itemSchedule);
					layer.closeAll();
				}
			}
			tool.get("/project/api/task_schedule", { tid: tid }, callback);
		},
		//保存
		save: function (postData) {
			let callback = function (e) {
				if (e.code == 0) {
					layer.closeAll();
					layer.msg(e.msg);
					if(layui.scheduleTable){
						layui.scheduleTable.reload();
					}
					else{
						setTimeout(function(){
							location.reload();
						},1000);	
					}					
				} else {
					layer.msg(e.msg);
				}
			}
			tool.post("/oa/schedule/add", postData, callback);
		},
		//查看
		view: function (detail) {
			let project = '';
			if(detail.tid>0){
				project = `<table class="layui-table" style="margin:12px 12px 0;">
								<tr>
								<td class="layui-td-gray">关联项目</td>
								<td>${detail.project}</td>
								</tr>
								<tr>
									<td class="layui-td-gray">关联任务</td>
									<td>${detail.task}</td>
								</tr>
							</table>`;
			}
			let content = `<div style="width:666px">
							<table class="layui-table" style="margin:12px 12px 0;">
								<tr>
									<td class="layui-td-gray">执行员工</td>
									<td>${detail.name}</td>									
									<td class="layui-td-gray">所在部门</td>
									<td>${detail.department}</td>
									<td class="layui-td-gray">工作类别</td>
									<td>${detail.work_cate}</td>
								</tr>
								<tr>
									<td class="layui-td-gray">时间范围</td>
									<td colspan="3">${detail.start_time} 至 ${detail.end_time}，共${detail.labor_time}工时</td>
									<td class="layui-td-gray">工作类型</td>
									<td>${detail.labor_type_string}</td>
								</tr>
								<tr>
									<td class="layui-td-gray">工作内容</td>
									<td colspan="5">${detail.title}</td>
								</tr>
								<tr>
									<td class="layui-td-gray">补充描述</td>
									<td colspan="5">${detail.remark}</td>
								</tr>
							</table>
							${project}
						</div>`;
			layer.open({
				type: 1,
				title: '工作记录详情',
				area: ['692px', '432px'],
				content: content,
				success: function () {

				},
				btn: ['关闭'],
				btnAlign: 'c',
				yes: function (idx) {
					layer.close(idx);
				}
			})
		},
		add: function (tid, schedule) {
			let that = this, detail = {}, title = '添加工作记录';
			if(that.loading == true){
				return false;
			}
			that.loading = true;
			let html_time = '';
			if (schedule['id'] > 0) {
				if(schedule['admin_id'] != login_admin){
					layer.msg('不能编辑他人的工作记录');
					return false;
				}
				title = '编辑工作记录';
				detail['id'] = schedule['id'];
				detail['cid'] = schedule['cid'];
				detail['tid'] = schedule['tid'];
				detail['title'] = schedule['title'];
				detail['labor_type'] = schedule['labor_type'];
				detail['start_time_a'] = schedule['start_time_a'];
				detail['start_time_b'] = schedule['start_time_b'];
				detail['end_time_a'] = schedule['end_time_a'];
				detail['end_time_b'] = schedule['end_time_b'];
				detail['remark'] = schedule['remark'];
				
				html_time = detail.start_time_a+' '+detail.start_time_b+' 至 '+detail.end_time_b+'，共'+schedule.labor_time+'工时';
			} else {
				detail['id'] = schedule['id'];
				detail['tid'] = tid;
				detail['cid'] = 1;
				detail['title'] = '';
				detail['labor_type'] = 0;
				detail['start_time_a'] = schedule['start_time_a']?schedule['start_time_a']:'';
				detail['start_time_b'] = '09:00';
				detail['end_time_a'] = schedule['end_time_a']?schedule['end_time_a']:'';
				detail['end_time_b'] = '09:30';
				detail['remark'] = '';
				
				html_time = `<input id="start_time_a" name="start_time_a" style="width:160px; display:inline-block;" autocomplete="off" class="layui-input" value="${detail.start_time_a}" readonly lay-verify="required" lay-reqText="请选择"><div style="display: inline-block; margin-left:3px; width: 100px;"><select lay-filter="start_time_b" id="start_time_b"></select></div> 至 <input id="end_time_a" name="end_time_a" style="width:160px; display:inline-block;" autocomplete="off" class="layui-input" value="${detail.end_time_a}" readonly lay-verify="required" lay-reqText="请选择"><div style="display: inline-block; margin-left:3px; width: 100px;"><select lay-filter="end_time_b" id="end_time_b"></select></div>`;
			}			
			let callback = function(e){
				that.loading = false;
				if(e.code==0){
					let cate = e.data;	
					let content = `<form class="layui-form" style="width:666px">
							<table class="layui-table" style="margin:12px 12px 0;">
								<tr>
									<td class="layui-td-gray">时间范围 <span style="color: red">*</span></td>
									<td colspan="3">${html_time}</td>
								</tr>
								<tr>
									<td class="layui-td-gray">工作类型<font>*</font></td>
									<td>
										<input type="radio" name="labor_type" lay-filter="labor_type" value="1" title="案头工作"><input type="radio" name="labor_type" value="2" lay-filter="labor_type" title="外勤工作">
									</td>
									<td class="layui-td-gray">工作类别<font>*</font></td>
									<td>
										<select name="cid" lay-filter="work_cate" id="work_cate"></select>
									</td>
								</tr>
								<tr>
									<td class="layui-td-gray">工作内容 <span style="color: red">*</span></td>
									<td colspan="3"><input name="title" class="layui-input" value="${detail.title}" lay-verify="required" lay-reqText="请完成工作内容"></td>
								</tr>
								<tr>
									<td class="layui-td-gray">补充描述</td>
									<td colspan="3">
										<textarea name="remark" form-input="remark" class="layui-textarea" style="min-height:120px;">${detail.remark}</textarea>
										<input name="tid" type="hidden" value="${detail.tid}">
									</td>
								</tr>
							</table>
						</form>`;					
					layer.open({
						type: 1,
						title: title,
						area: ['692px', '432px'],
						content: content,
						success: function () {
							if (detail['id'] == 0) {
								//日期时间范围
								laydate.render({
									elem: '#start_time_a',
									type: 'date',
									min: -7,
									max: 0,
									format: 'yyyy-MM-dd',
									showBottom: false,
									done: function (a, b, c) {
										$('#end_time_a').val(a);
										detail.start_time_a = a;
										detail.end_time_a = a;
									}
								});

								//日期时间范围
								laydate.render({
									elem: '#end_time_a',
									type: 'date',
									min: -7,
									max: 0,
									format: 'yyyy-MM-dd',
									showBottom: false,
									done: function (a, b, c) {
										$('#start_time_a').val(a);
										detail.start_time_a = a;
										detail.end_time_a = a;
									}
								});
								$('#start_time_b,#end_time_b').empty();

								var hourArray = [];
								for (var h = 0; h < 24; h++) {
									var t = h < 10 ? '0' + h : h
									var t_1 = t + ':00', t_2 = t + ':15', t_3 = t + ':30', t_4 = t + ':45';
									hourArray.push(t_1, t_2, t_3, t_4);
								}

								var html_1 = '', html_2 = '', def_h1 = detail['start_time_b'], def_h2 = detail['end_time_b'];
								for (var s = 0; s < hourArray.length; s++) {
									var check_1 = '', check_2 = '';
									if (hourArray[s] == def_h1) {
										check_1 = 'selected';
									}
									if (hourArray[s] == def_h2) {
										check_2 = 'selected';
									}
									html_1 += '<option value="' + hourArray[s] + '" ' + check_1 + '>' + hourArray[s] + '</option>';
									html_2 += '<option value="' + hourArray[s] + '" ' + check_2 + '>' + hourArray[s] + '</option>';
								}

								$('#start_time_b').append(html_1);
								$('#end_time_b').append(html_2);
							}
							$("input[name=labor_type][value="+detail.labor_type+"]").prop("checked","true");	
							
							var html_c='';
							for (var c = 0; c < cate.length; c++) {
								var check_c = '';
								if (cate[c].id == detail['cid']) {
									check_c = 'selected';
								}
								html_c += '<option value="' + cate[c].id + '" ' + check_c + '>' + cate[c].title + '</option>';
							}

							$('#work_cate').append(html_c);
							
							form.render();

							form.on('select(start_time_b)', function (data) {
								detail.start_time_b = data.value;
							});
							form.on('select(end_time_b)', function (data) {
								detail.end_time_b = data.value;
							});
							$('[name="title"]').on('input', function () {
								var _val = $(this).val();
								detail.title = _val;
							});
							form.on('radio(labor_type)', function(data){
							   detail.labor_type=data.value;
							})
							form.on('select(work_cate)', function(data){
							   detail.cid=data.value;
							})
							$('[form-input="remark"]').on('input', function () {
								var _val = $(this).val();
								detail.remark = _val;
							});
						},
						btn: ['确定提交'],
						btnAlign: 'c',
						yes: function (idx) {
							if (detail.start_time_a == '' || detail.end_time_a == '') {
								layer.msg('请选择工作时间范围');
								return;
							}
							if(detail.labor_type==0){
								layer.msg('请选择工作类型');
								return;
							}
							if (detail.title == '') {
								layer.msg('请填写工作内容');
								return;
							}
							that.save(detail);
						}
					})
				}
			}
			//tool.get("/api/index/get_work_cate", {}, callback);
			tool.get("/json/get_work_cate.json", {}, callback);
		}
	};
	exports('oaSchedule', obj);
});  