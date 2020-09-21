steal( 'jquery/controller','jquery/view/ejs' )
	.then('./views/init.ejs',
			'./views/editcustomquery.ejs',
		  './models/services.js',function($){
	env=this;
	var element;
	var overlay_;
	var tableName_;
	var savecallback_;
	/**
	 * @class data_select
	 */
	$.Controller('Plugins.Customquery.Default',
	/** @Static */
	{
		defaults : {}
	},{
		init:function(){
		},
		show:function(el,tableName,overlay,savecallback,requiredColumns){
			var defectCustomqueryPublic=Plugins.Common.Storage.hasPermission("DEFECT_CUSTOMQUERY_PUBLIC");
			el.attr('disabled',true);
			overlay_=overlay;
			savecallback_=savecallback;
			element=this.element;
			tableName_=tableName;
			
			element.html("//plugins/customquery/views/init.ejs",{
				defectCustomqueryPublic:defectCustomqueryPublic
			},function(){
				//display data
				$("#selectCols").plugins_data_select_default();
				$("#selectCols").controller().show(tableName_,null,requiredColumns);
				//condition data
				$("#selectConditions").plugins_data_condition_default();
				$("#selectConditions").controller().show(tableName_);
				//sort data
				$("#selectSorts").plugins_data_sort_default();
				$("#selectSorts").controller().show(tableName_);
				//load element
				element.i18n();
				element.show();
				element.center();
				overlay_.show();
				el.attr('disabled',false);
			});
		},
		editCustomQuery:function(el,tableName,overlay,customQueryId,savecallback,requiredColumns){
			var defectCustomqueryPublic=Plugins.Common.Storage.hasPermission("DEFECT_CUSTOMQUERY_PUBLIC");
			el.attr('disabled',true);
			overlay_=overlay;
			savecallback_=savecallback;
			element=this.element;
			tableName_=tableName;
			Plugins.Customquery.Models.Services.getSelectedDataSelectByTableName({
				tableName:tableName_,
				customQueryId:customQueryId,
				success:function(data){
					element.html("//plugins/customquery/views/editcustomquery.ejs",{
						defectCustomqueryPublic:defectCustomqueryPublic,
						queryType:data.customQuery.queryType,
						queryName:data.customQuery.queryName,
						queryDesc:data.customQuery.queryDesc,
						customQueryId:data.customQuery.id
					},function(){
						//display data
						$("#selectCols_edit").plugins_data_select_default();
						$("#selectCols_edit").controller().edit(tableName_,data.selectColsSelected,null,null,requiredColumns);
						//condition data
						$("#selectConditions_edit").plugins_data_condition_default();
						$("#selectConditions_edit").controller().edit(tableName_,data.editSelfSearchDefectDTO);
						//sort data
						$("#selectSorts_edit").plugins_data_sort_default();
						$("#selectSorts_edit").controller().edit(tableName_,data.sorts);
						//load element
						element.i18n();
						element.show();
						element.center();
						overlay_.show();
					});
				},complete:function(){
					el.attr('disabled',false);
				}
			});
		},
		deleteCustomQuery:function(el,customQueryId,deleteCallback){
			if(confirm($.t("message.customquery.confirm_delete_customquery"))){
				el.attr('disabled',true);
				Plugins.Customquery.Models.Services.deleteCustomQuery({
					customQueryId:customQueryId,
					success:function(data){
						deleteCallback();
					},complete:function(){
						el.attr('disabled',false);
					}
				});
			}
		},
		//save edit
		'#customquery_condition_save_button_edit click':function(el,ev){
			$("#selectCols_edit").plugins_data_select_default();
			var selectdata=$("#selectCols_edit").controller().getSelectedData();
			if(!selectdata.length>0){
				alert($.t("message.customquery.selectCols"));
			}
			$("#selectConditions_edit").plugins_data_condition_default();
			var conditiondata=$("#selectConditions_edit").controller().getConditionData();
			$("#selectSorts_edit").plugins_data_sort_default();
			var sortdata=$("#selectSorts_edit").controller().getSortData();
			if(!sortdata.length>0){
				sortdata=null;
			}
			if($("#customquery_condition_table_edit").validate()){
				el.attr("disabled",true);
				var customQuery={
						id:$("#customqueryid_edit").val(),
						tableName:tableName_,
						queryType:$("#queryType_edit").val(),
						queryName:$("#selfsearchname_edit").val(),
						queryDesc:$("#selfsearchdesc_edit").val()
				}
				var searchCriteria={customQuery:customQuery,customQueryFieldsList:selectdata,
						customQueryConditionList:conditiondata,customQuerySortList:sortdata};
				Plugins.Customquery.Models.Services.saveCustomQueryConditionEdit({
					data: $.toJSON(searchCriteria),
					success:function(data){
						el.closest(".tip").find(".close-button").click();
						savecallback_(data.tableName);
					},complete:function(){
						el.attr("disabled",false);
					}
				});
			}
		},
		//save
		'#customquery_condition_save_button click':function(el,ev){
			$("#selectCols").plugins_data_select_default();
			var selectdata=$("#selectCols").controller().getSelectedData();
			if(!selectdata.length>0){
				alert($.t("message.customquery.selectCols"));
			}
			$("#selectConditions").plugins_data_condition_default();
			var conditiondata=$("#selectConditions").controller().getConditionData();
			$("#selectSorts").plugins_data_sort_default();
			var sortdata=$("#selectSorts").controller().getSortData();
			if(!sortdata.length>0){
				sortdata=null;
			}
			if($("#customquery_condition_table").validate()){
				el.attr("disabled",true);
				var customQuery={
						tableName:tableName_,
						queryType:$("#queryType").val(),
						queryName:$("#selfsearchname").val(),
						queryDesc:$("#selfsearchdesc").val()
				}
				var searchCriteria={customQuery:customQuery,customQueryFieldsList:selectdata,
						customQueryConditionList:conditiondata,customQuerySortList:sortdata};
				Plugins.Customquery.Models.Services.saveCustomQueryCondition({
					data: $.toJSON(searchCriteria),
					success:function(data){
						el.closest(".tip").find(".close-button").click();
						savecallback_(data.tableName);
					},complete:function(){
						el.attr("disabled",false);
					}
				});
			}
		},

		'#customquery_condition_cancel_button click':function(){
			el.closest(".tip").find(".close-button").click();
		},
		'#customquery_condition_cancel_button_edit click':function(){
			el.closest(".tip").find(".close-button").click();
		}
	});
});