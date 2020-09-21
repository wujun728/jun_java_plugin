steal( 'jquery/controller','jquery/view/ejs' )
	.then('./views/init.ejs',
			'./views/condition_add.ejs',
			'./views/edit_condition.ejs',
		  './models/services.js',function($){
	env=this;
	var element;
	var tableName_;
	/**
	 * @class data_select
	 */
	$.Controller('Plugins.DataCondition.Default',
	/** @Static */
	{
		defaults : {}
	},{
		init:function(){
		},
		show:function(tableName){
			var user=Plugins.Common.Storage.getCredentials();
			env=this;
			element=this.element;
			tableName_=tableName;
			Plugins.DataCondition.Models.Services.getCustomQueryCondition({
				tableName:tableName_,
				success:function(data){
					element.append("//plugins/data_condition/views/init.ejs",{
						user:user.user,
						result:data.result,
						tableName:data.tableName,
						fieldName:data.fieldName,
						filterNames:data.filterNames,
						filterConditions:data.filterConditions,
						filterValues:data.filterValues
					},function(){
						element.i18n();
						element.show();
						if(data.result==2){
							$(".filterValue_date").plugins_date_picker({defaultValue:""});
							$(".filterValue_date").attr('name','filterValue');
						}
					});
				}
			});
		},
		edit:function(tableName,editSelfSearchDefectDTO){
			tableName_=tableName;
			env=this;
			element=this.element;
			element.append("//plugins/data_condition/views/edit_condition.ejs",{
				tableName:editSelfSearchDefectDTO.tableName,
				filterNames:editSelfSearchDefectDTO.cols,
				conditions:editSelfSearchDefectDTO.customQueryConditions
			},function(){
				element.i18n();
				element.show();
				$.each(editSelfSearchDefectDTO.customQueryConditions,function(i,n){
					if(n.flag==2){
						$(".filterValues_date").plugins_date_picker({defaultValue:n.condition.filterValue});
					}
				});
			});
		},
		getConditionData:function(){
			var args=new Array();
			$.each($(this.element).eachArrayPlus(),function(i,n){
				if(n!=null&&n!=-undefined&&n.filterName!=null&&n.filterCondition!=null&&n.filterJoin!=null && n.filterName!="0" && n.filterCondition!="0" && n.filterValue!=""){
					var data={
							filterName:n.filterName,
							filterCondition:n.filterCondition,
							filterValue:n.filterValue,
							filterJoin:n.filterJoin,
							sequence:i
					};
					args.push(data);
				}
			});
			return args;
		},
		".conditionfieldname change":function(el,ev){
			if (el.val()=="0"){
				return;
			}
			Plugins.DataCondition.Models.Services.getCustomQueryConditionByChange({
				fieldName:el.val(),
				tableName:tableName_,
				success:function(data){
					//set filter condition
					var filterConditionHtml="<option value='0' data-i18n='common.choose'></option>";
					$.each(data.filterConditions,function(i,n){
						filterConditionHtml+="<option value='"+n+"' data-i18n='select.filter_conditions."+n+"'></option>";
					});
					el.parent().next().children().html(filterConditionHtml);
					el.parent().next().children().i18n();
					//set filter value
					var filterValueHtml="";
					if(data.result==0){
						//input
						filterValueHtml="<input type='text' name='filterValue' class='dfinput' /> ";
					}else if(data.result==1){
						//select
						filterValueHtml="<select name='filterValue' class='dfinput'>";
						$.each(data.filterValues,function(i,n){
							filterValueHtml+="<option value='"+n+"'>"+n+"</option>";
						});
						filterValueHtml+="</select>";
					}else if(data.result==2){
						//Date
						filterValueHtml="<div name='filterValue'></div>";
					}else if(data.result==3){
						var user=Plugins.Common.Storage.getCredentials();
						//user
						filterValueHtml="<input type='hidden' name='filterValue' value='"+user.user.id+"'/>" +
								"<input type='text' class='dfinput filterValue_user' value='"+user.user.name+"("+user.user.username+")"+"'/> ";
					}else if(data.result==4){
						//select
						filterValueHtml="<select name='filterValue' class='dfinput'>";
						$.each(data.filterValues,function(i,n){
							filterValueHtml+="<option value='"+n+"'>"+$.t(data.tableName+"."+data.tableName+"."+data.fieldName+"."+n)+"</option>";
						});
						filterValueHtml+="</select>";
					}
					el.parent().next().next().html(filterValueHtml);
					el.parent().next().next().i18n();
					if(data.result==2){
						el.parent().next().next().children().plugins_date_picker({defaultValue:""});
					}
				}
			});
		},
		'.filterValue_user click':function(el){
			$("body").generatePup({id:"customqueryconditionuser"});
			$("#customqueryconditionuser").user_widgets_search_user_default({
				isMultiple:false,
				userType:"ProjectUser",
				callback:function(selectedUser){
					if (selectedUser.length>0){
					    el.val(selectedUser[0].userName);
					    el.prev().val(selectedUser[0].userId);
					}
				}});
			$("#customqueryconditionuser").user_widgets_search_user_default('show');		
		},
		//remove search condition
		'#data_condition_edit .addconditionremove click':function(el,ev){
			el.parent().parent().prev().remove();
			el.parent().parent().remove();
		},
		//add search condition for and 
		'#data_condition_edit .addconditionand click':function(el,ev){
			this.dataConditionAdd("AND",el.parent().parent());
		},
		//add search condition for or 
		'#data_condition_edit .addconditionor click':function(el,ev){
			this.dataConditionAdd("OR",el.parent().parent());
		},
		//remove search condition
		'#data_condition .addconditionremove click':function(el,ev){
			el.parent().parent().prev().remove();
			el.parent().parent().remove();
		},
		//add search condition for and 
		'#data_condition .addconditionand click':function(el,ev){
			this.dataConditionAdd("AND",el.parent().parent());
		},
		//add search condition for or 
		'#data_condition .addconditionor click':function(el,ev){
			this.dataConditionAdd("OR",el.parent().parent());
		},
		dataConditionAdd:function(filterType,dom){
			var user=Plugins.Common.Storage.getCredentials();
			var filterTypeTitle;
			if("AND"==filterType){
				filterTypeTitle=filterType+"(并且)";
			}else{
				filterTypeTitle=filterType+"(或者)";
			}
			Plugins.DataCondition.Models.Services.getCustomQueryCondition({
				tableName:tableName_,
				success:function(data){
					dom.after("//plugins/data_condition/views/condition_add.ejs",{
						user:user.user,
						filterTypeTitle:filterTypeTitle,
						filtertype:filterType,
						tableName:data.tableName,
						result:data.result,
						filterNames:data.filterNames,
						filterConditions:data.filterConditions,
						filterValues:data.filterValues
					},function(){
						element.i18n();
						if(data.result==2){
							var dateDom=dom.next().next().find("div[class='filterValue_date']");
							dateDom.plugins_date_picker({defaultValue:""});
						}
					});
				}
			});
		}
	});
});
	