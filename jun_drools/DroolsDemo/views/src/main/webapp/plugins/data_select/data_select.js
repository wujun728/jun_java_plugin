steal( 'jquery/controller','jquery/view/ejs' )
	.then('./views/init.ejs',
		  './models/services.js',function($){
	env=this;
	var element;
	var requiredColumns_;
	/**
	 * @class data_select
	 */
	$.Controller('Plugins.DataSelect.Default',
	/** @Static */
	{
		defaults : {}
	},{
		init:function(){
		},
		show:function(tableName, tableData,requiredColumns){
			requiredColumns_ = requiredColumns;
			env=this;
			element=this.element;
			if(!tableData){
				Plugins.DataSelect.Models.Services.getDataSelectByTableName({
					tableName:tableName,
					success:function(data){
						element.append("//plugins/data_select/views/init.ejs",{
						},function(){
							$.each(data.selectCols,function(i,n){
								if($.inArray(n,requiredColumns) != -1){
									$("#unselect_cols_div").append("<li style='width:100%' class='unselectdatali needdata' required>" +
											"<a dataid='"+n+"' style='color:gray'>"+$.t(data.tableName+"."+n)+"</a></li>");
									return true;
								}else{
									$("#unselect_cols_div").append("<li style='width:100%' class='unselectdatali displaynone'>" +
											"<a dataid='"+n+"'>"+$.t(data.tableName+"."+n)+"</a></li>");
								}
								$("#select_cols_div").append("<li style='width:100%' class='unselectdatali'>" +
										"<a dataid='"+n+"'>"+$.t(data.tableName+"."+n)+"</a></li>");
								
							});
							element.i18n();
							element.show();
						});
					}
				});
			}else{
				element.append("//plugins/data_select/views/init.ejs",{
				},function(){
					$.each(tableData,function(i,n){
						$("#select_cols_div").append("<li style='width:100%' class='unselectdatali'>" +
								"<a dataid='"+n.value+"'>"+n.name+"</a></li>");
						$("#unselect_cols_div").append("<li style='width:100%' class='unselectdatali displaynone'>" +
								"<a dataid='"+n.value+"'>"+n.name+"</a></li>");
					});
					element.i18n();
					element.show();
				});
			}
		},
		//edit data
		edit:function(tableName,selectedData,tableData,selectedTableData,requiredColumns){
			requiredColumns_ = requiredColumns;
			env=this;
			element=this.element;
			if(!tableData||!selectedTableData){
				element.html("");
				Plugins.DataSelect.Models.Services.getDataSelectByTableName({
					tableName:tableName,
					success:function(data){
						element.append("//plugins/data_select/views/init.ejs",{
						},function(){
							$.each(data.selectCols,function(i,n){
								if($.inArray(n,requiredColumns) != -1){
									$("#unselect_cols_div").append("<li style='width:100%' class='unselectdatali needdata' required>" +
											"<a dataid='"+n+"' style='color:gray'>"+$.t(data.tableName+"."+n)+"</a></li>");
									return true;
								}else{
									$("#unselect_cols_div").append("<li style='width:100%' class='unselectdatali displaynone'>" +
											"<a dataid='"+n+"'>"+$.t(data.tableName+"."+n)+"</a></li>");
								}
								$("#select_cols_div").append("<li style='width:100%' class='unselectdatali'>" +
										"<a dataid='"+n+"'>"+$.t(data.tableName+"."+n)+"</a></li>");
							});
							$.each(selectedData,function(i,n){
								$("#select_cols_div a[dataid='"+n.fieldName+"']").parent().addClass("displaynone").addClass("selectdatali").removeClass("unselectdatali");
								$("#unselect_cols_div a[dataid='"+n.fieldName+"']").parent().addClass("unselectdatali").addClass("needdata").removeClass("displaynone").removeClass("selectdatali");
							});
							element.i18n();
							element.show();
						});
					}
				});
			}else{
				element.append("//plugins/data_select/views/init.ejs",{
				},function(){
					$.each(tableData,function(i,n){
						$("#select_cols_div").append("<li style='width:100%' class='unselectdatali'>" +
								"<a dataid='"+n.value+"'>"+n.name+"</a></li>");
						$("#unselect_cols_div").append("<li style='width:100%' class='unselectdatali displaynone'>" +
								"<a dataid='"+n.value+"'>"+n.name+"</a></li>");
					});
					$.each(selectedTableData,function(i,n){
						$("#select_cols_div a[dataid='"+n.value+"']").parent().addClass("displaynone").addClass("selectdatali").removeClass("unselectdatali");
						$("#unselect_cols_div a[dataid='"+n.value+"']").parent().addClass("unselectdatali").addClass("needdata").removeClass("displaynone").removeClass("selectdatali");
					});
					element.i18n();
					element.show();
				});
			}
		},
		//get selected data
		getSelectedData:function(){
			var result=new Array();
			result.push({fieldName:"id",sequence:0});
			$.each($("#unselect_cols_div li.needdata a"),function(i,n){
				//var needdatas=$(n).attr("dataid").split(".");
				var data={
						fieldName:$(n).attr("dataid"),
						sequence:(i+1)
				};
				result.push(data);
			});
			return result;
		},
		//selected
		'#select_cols_div li.unselectdatali click':function(el,ev){
			el.removeClass("unselectdatali").addClass("selectdatali");
		},
		//unselected
		'#select_cols_div li.selectdatali click':function(el,ev){
			el.removeClass("selectdatali").addClass("unselectdatali");
		},
		'#unselect_cols_div li.unselectdatali click':function(el,ev){
			var colid = el.find("a").attr("dataid");
			if($.inArray(colid,requiredColumns_) != -1) return;
			el.removeClass("unselectdatali").addClass("selectdatali");
		},
		//unselected
		'#unselect_cols_div li.selectdatali click':function(el,ev){
			el.removeClass("selectdatali").addClass("unselectdatali");
		},
		//right move
		'#right_move click':function(){
			$.each($("#select_cols_div li.selectdatali"),function(i,n){
				$(n).removeClass("selectdatali").addClass("displaynone").addClass("unselectdatali");
				$("#unselect_cols_div a[dataid='"+$(n).find("a").attr("dataid")+"']").parent().removeClass("displaynone").addClass("unselectdatali").addClass("needdata");
			});
		},
		//left move
		'#left_move click':function(){
			$.each($("#unselect_cols_div li.selectdatali"),function(i,n){
				$(n).removeClass("selectdatali").addClass("displaynone").addClass("unselectdatali").removeClass("needdata");
				$("#select_cols_div a[dataid='"+$(n).find("a").attr("dataid")+"']").parent().removeClass("displaynone").removeClass("selectdatali").addClass("unselectdatali");
			});
		},
		//all right move
		'#all_right_move click':function(){
			$("#select_cols_div li").addClass("displaynone").addClass("unselectdatali");;
			$("#unselect_cols_div li").removeClass("displaynone").addClass("unselectdatali").addClass("needdata");
		},
		//all left move
		'#all_left_move click':function(){
			$.each($("#unselect_cols_div li"),function(i,li){
				var colid = $(li).find("a").attr("dataid");
				if($.inArray(colid,requiredColumns_) != -1) return;
				$(li).removeClass("selectdatali").addClass("displaynone").addClass("unselectdatali").removeClass("needdata");
			})
			//$("#unselect_cols_div li").removeClass("selectdatali").addClass("displaynone").addClass("unselectdatali").removeClass("needdata");
			$("#select_cols_div li").removeClass("displaynone").removeClass("selectdatali").addClass("unselectdatali");
		}
	});
});