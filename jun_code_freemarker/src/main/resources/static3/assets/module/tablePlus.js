layui.define(['jquery','layer','table'], function(exports) {
	var $ = layui.$,
	layer = layui.layer,
	table = layui.table;
	
    var MOD_NAME='tablePlus';
	
    var tablePlus=$.extend({},table);
	
    tablePlus._render = tablePlus.render;
	
	tablePlus.excel = function(data,page_size,obj){
		//表头工具栏导出按钮
		$('[lay-id="'+obj.id+'"]').find('[lay-event="LAYTABLE_EXCEL"]').click(function(){
			if(data.count==0){
				layer.msg('暂无数据');
				return false;
			}
			else{
				let _page = parseInt(data.count/page_size);
				let page = data.count%page_size>0?(_page+1):_page;
				let pageHtml='<p style="padding:16px 10px; text-align:center; color:red">由于导出数据比较消耗服务器资源，建议使用搜索功能筛选好数据再导出</p><p style="padding:0 10px; text-align:center;">共查询到<strong> '+data.count+' </strong>条数据，每次最多导出<strong>'+page_size+'</strong>条，共<strong>'+page+'</strong>页，请点击下面的页码导出</p><div id="exportPage" class="layui-box layui-laypage" style="padding:10px 0; width:100%;text-align:center;">';
				for (i = 1; i <= page; i++) {
					pageHtml += '<a href="javascript:;" data-page="'+i+'">'+i+'</a>';
				}
				pageHtml+='</div>';							
				layer.open({
					  type: 1,
					  title: '导出数据',
					  area:['580px','240px'],
					  content: pageHtml,
					  success:function(res){
							var tableWhere = $.extend({},obj.where);
							tableWhere.limit=page_size;										
							$('#exportPage').on('click','a',function(){
								tableWhere.page=$(this).data('page');
								let msg = layer.msg('正在导出数据...', {time:5000});
								$.ajax({
									url: obj.url,
									data: tableWhere,
									success:function(res){
										table.exportFile(obj.id, res.data,'xls');
										layer.close(msg);
									}
								});
							})
						}
					});
				return false;
			}
		});
	}
	//重写渲染方法
    tablePlus.render=function(params){
		let is_excel = params.is_excel||false;
		let excel_limit = params.excel_limit||1000;
		if(is_excel){
			let toolbar = ['filter', {title:'导出EXCEL',layEvent: 'LAYTABLE_EXCEL',icon: 'layui-icon-export'}];
			if(!params.toolbar){
				params.toolbar = true;
			}
			if(!params.defaultToolbar){
				params.defaultToolbar = toolbar;
			}	
			else{
				let _toolbar = params.defaultToolbar;
				params.defaultToolbar = _toolbar.concat(toolbar);
			}
			if(typeof params.done === "function"){
				let _done = params.done;
				params.done = function(data, curr, count){
					let obj = this;
					_done(data, curr, count);
					tablePlus.excel(data,excel_limit,obj);
				}
			}
			else{
				params.done = function(data){
					let obj = this;
					tablePlus.excel(data,excel_limit,obj);
				}
			}
		}
		var init = tablePlus._render(params);
		return init;
		//console.log(params);
    };
    exports(MOD_NAME, tablePlus);
});