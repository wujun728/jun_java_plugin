steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs',
			'./views/col.ejs',		
			'./views/header.ejs',				
			'./views/row.ejs',	
			'./views/sub_row.ejs',	
			'./views/pager.ejs',
			'./views/data.ejs',
			'./models/paginate.js','plugins/filter/filter.js',function($){
/**
 * @class Plugins.DataGrid
 */
$.Controller('Plugins.DataGrid.Default',
/** @Static */
		{
	defaults : {
		colList:[],
		fieldList:[],	
		subFieldList:[],
		paginate:new Plugins.DataGrid.Models.Paginate(),		
		sort:{},
		searchCriteria:{filter:[]},
		editable:false,	
		checkable:false,
		radioable:false,
		showPages:true,
		actions:[],
		subActions:[],
		useEditBtn:false,
		rows:1,
		renderColumns: function(){}
	}
},
/** @Prototype */
{
	init : function(){					
	},
	
	show: function(){
		var env=this;			
		var colList=this.options.colList;
		var fieldList=this.options.fieldList;			
		var dataList=this.options.paginate.getDataList();
		var currentPageNo=parseInt(this.options.paginate.getCurrentPageNo());
		var totalPages=parseInt(this.options.paginate.getTotalPages());		
		var canNext=this.options.paginate.canNext();
		var canPrev=this.options.paginate.canPrev();
		var editable=this.options.editable;	
		var useEditBtn=this.options.useEditBtn;
		var actions=this.options.actions;
		var checkable=this.options.checkable;
		var radioable=this.options.radioable;
		var showPages=this.options.showPages;
		var totalRecords=this.options.paginate.totalRecords;
		var pageSize=this.options.paginate.pageSize;
		var filter=this.options.searchCriteria.filter;
		var rows = this.options.rows;
		
		var pageNav={
			totalPages:totalPages,			
			currentPageNo:currentPageNo,
			canNext:canNext,
			canPrev:canPrev,
			totalRecords:totalRecords,
			pageSize:pageSize,
			showPages:showPages
		};	
		
		this.element.html("//plugins/data_grid/views/init.ejs",{
			colList:colList,
			fieldList:fieldList,
			dataList:dataList,
			pageNav:pageNav,
			editable: editable,	
			checkable:checkable,
			radioable:radioable,
			useEditBtn:useEditBtn,
			actions:actions,
			rows:rows
		}, function(){
			env.element.i18n();			
		    
		    for (var i=0;i<fieldList.length;i++){
				if (fieldList[i].filterList){					
					$("#filter"+fieldList[i].fieldName+"_content").plugins_filter({
						selectedFilter:filter,
						key:fieldList[i].fieldName,
						filterList:fieldList[i].filterList,
						callback:function(key,typeValue,typeName){
							filter=Plugins.Common.Utils.removeSubArray(filter,"fieldName",key);							
							filter.push({"fieldName":key,"filedValue":typeValue,"fieldTitle":typeName});							
							env.options.searchCriteria.filter=filter;
							env.options.paginate.setCurrentPageNo(1);	
							env.bindGrid();							
					    }
					});
				}
			}	
		    
		    for (var i=0;i<dataList.length;i++){
		    	env.find("[name='grid_button']").plugins_button_menu({actions:actions});
		    }
				
		});
	},	
	
	bindGrid: function(){
		var env=this;
		var pageNo=this.options.paginate.getCurrentPageNo()-1;
	    var pageSize=this.options.paginate.getPageSize();
		var sortName=this.options.sort.sortName;
		var sortType=this.options.sort.sortType;
		var searchCriteria=this.options.searchCriteria;
		searchCriteria.pageNo=pageNo;
		searchCriteria.pageSize=pageSize;
		searchCriteria.sortName=sortName;
		searchCriteria.sortType=sortType;
		var pageData={count:1,dataList:[]};
		$.when(env.options.callback(searchCriteria)).done(function(data){
			if (data.pageData.count>0 && data.pageData.content.length==0){
				env.options.paginate.prev();
				env.bindGrid();
			}
			else{
			    pageData.count=data.pageData.count;
			    pageData.dataList=data.pageData.content;
			    env.options.paginate.setTotalRecords(pageData.count);
			    env.options.paginate.setDataList(pageData.dataList);
		        env.options.paginate.calculatePages();	
		        env.show();
		        env.options.renderColumns(data);
			}
		});	
	},
	
    getCurrentPageNo: function(){
		return this.options.paginate.getCurrentPageNo();
	},
	
	getTotalPages:function(){
		return this.options.paginate.getTotalPages();
	},
	
	'.paginlist li[type="page"] a click':function(el,ev){		
		var newPageNo=el.attr("value");	
		this.options.paginate.setCurrentPageNo(newPageNo);	
		this.bindGrid();		
	},
	
	'.pagenxt click': function(el,ev){	
	    this.options.paginate.next();	
		this.bindGrid();		    

	},
	
	'.pagepre click': function(el,ev){	
	    this.options.paginate.prev();	
	    this.bindGrid();
	},
	
	'th > input:checkbox click': function(el,ev){
		var isChecked=el.attr("checked");
		if (isChecked){
			this.find("td > input:checkbox").attr("checked",true);
		}
		else{
			this.find("td > input:checkbox").removeAttr("checked");
		}
	},
	
	'td > input:checkbox click': function(el,ev){		
		var checkedAll=true;
		this.find("td > input:checkbox").each(function(){
			if (!$(this).attr("checked")){
				checkedAll=false;
			}
		});
		
		if (checkedAll){
			this.find("th > input:checkbox").attr("checked",true);
		}
		else{
			this.find("th > input:checkbox").removeAttr("checked");
		}
	},	
	
	'input[data-type="sort"] click': function(el,ev){
		var sortName=el.attr("sortname");
		var sortType=el.attr("sorttype");		
		var fieldList=this.options.fieldList;	
		for (var i=1;i<fieldList.length;i++){
			if (fieldList[i].fieldName==sortName || fieldList[i].sortName==sortName){
				if (sortType=="ASC"){
				    fieldList[i].sortType="DESC";
				}
				else{
					fieldList[i].sortType="ASC";
				}
				sortType=fieldList[i].sortType;
				break;
			}
		}
		this.options.sort.sortName=sortName;
		this.options.sort.sortType=sortType;
		this.options.paginate.setCurrentPageNo(1);	
		this.bindGrid();
	},
	
	'select[name="page_size_select"] change':function(el,ev){
		this.options.paginate.pageSize=el.val();
		this.options.paginate.setCurrentPageNo(1);	
		this.bindGrid();
	},
	
	'table>tbody>tr>td[class!="edit-area"] click':function(el,ev){
		el.parent("tr").toggleClass("rows-selected");
	},	
	
	'.dynatree-expander click':function(el,ev){
		var id=el.closest("tr").attr("value");
		if (el.parent().attr("class")=="dynatree-exp-el"){
			this.find("tr[parent='"+id+"']").hide();
			el.parent().removeClass("dynatree-exp-el");
			return;
		}
		el.parent().addClass("dynatree-exp-el");
		
		var subFieldList=this.options.subFieldList;	
		var editable=this.options.editable;	
		var checkable=this.options.checkable;
		var radioable=this.options.radioable;
		var useEditBtn=this.options.useEditBtn;
		var subActions=this.options.subActions;	
		var env=this;
		
		if (this.find("tr[parent='"+id+"']").length>0){
			this.find("tr[parent='"+id+"']").show();
			return;
		}
		
		$.when(env.options.getTreeData(id)).done(function(data){
			var padLeft=el.parent().css("padding-left").replace("px","");
			el.closest("tr").after("//plugins/data_grid/views/sub_row.ejs",{padLeft:padLeft,parent:id,dataList:data.list,radioable:radioable,subFieldList:subFieldList,editable:editable,checkable:checkable,useEditBtn:useEditBtn,subActions:subActions},function(){
				env.element.find("tr").i18n();
				env.find("[name='grid_button']").plugins_button_menu({actions:subActions});				
			});
		});	
		
	},
	
	getSort: function(){
		return this.options.sort;
	},
	
	getFilter: function(){
		return this.options.filter;
	},
	
	addRow: function(id,rowData){
		var fieldList=this.options.fieldList;	
		var editable=this.options.editable;	
		var checkable=this.options.checkable;	
		var radioable=this.options.radioable;
		var useEditBtn=this.options.useEditBtn;
		var actions=this.options.actions;	
		var env=this;
		this.element.find("table.tablelist tbody").append("//plugins/data_grid/views/row.ejs",{padLeft:"0",parent:"0",dataList:[rowData],fieldList:fieldList,editable:editable,radioable:radioable,checkable:checkable,useEditBtn:useEditBtn,actions:actions,pageNav:{
				currentPageNo:0,
				pageSize:0
			}},function(){
			env.element.find("tr[value='"+id+"']").i18n();
			env.find("[name='grid_button']").plugins_button_menu({actions:actions});
		});
		var total=this.element.find("i [name='total_records_label']").val();
		if (total){
			total=parseInt(total)+1;
		    this.element.find("i [name='total_records_label']").val(total);
		}
	},
	
	updateRow: function(id,rowData){
		var fieldList=this.options.fieldList;	
		var editable=this.options.editable;	
		var checkable=this.options.checkable;
		var radioable=this.options.radioable;
		var useEditBtn=this.options.useEditBtn;
		var actions=this.options.actions;	
		var env=this;
		this.element.find("tr[value='"+id+"']").html("//plugins/data_grid/views/data.ejs",{parent:"0",id:id,rowData:rowData,fieldList:fieldList,editable:editable,radioable:radioable,checkable:checkable,useEditBtn:useEditBtn,actions:actions},function(){
			env.element.find("tr[value='"+id+"']").i18n();
			env.find("[name='grid_button']").plugins_button_menu({actions:actions});
		});
	},
	
	deleteRow:function(id){
		this.element.find("tr[value='"+id+"']").remove();
		var total=this.element.find("i [name='total_records_label']").val();
		if (total){
			total=parseInt(total)-1;
		    this.element.find("i [name='total_records_label']").val(total);
		}
	},
	
	'input[data-type="filter"] click':function(el,ev){	
		Plugins.Common.Utils.stopPropagation(ev);
		$(".ui-dialog").remove();
		var key=el.attr("data-value");
		$("#filter_blk_"+key).dialog({resizable: false,width:150, position: { my: "left top", at: "left bottom", of: el}});		
		$("#filter_blk_"+key).parent().find(".ui-dialog-titlebar").remove();
		$("#filter_blk_"+key).parent().css({"border":"0px","background":"none"});	
		$("#filter_blk_"+key).css({"border":"0px","background":"none"});	
		$("#filter_blk_"+key).show();
		$("#filter_blk_"+key).parent().show();
	}

});

});